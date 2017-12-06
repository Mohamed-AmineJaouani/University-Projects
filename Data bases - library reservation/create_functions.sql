\echo
\echo FONCTIONS
\echo


\echo
\echo INSCRIRE UN USAGER
\echo


CREATE OR REPLACE FUNCTION inscription(nom TEXT, prenom TEXT, age INTEGER, email TEXT, id_type_inscr INTEGER) RETURNS VOID AS $$
DECLARE
today DATE;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;
	
	INSERT INTO UTILISATEUR VALUES(DEFAULT, nom, prenom, age, email, 0.00, id_type_inscr, today);

	RAISE NOTICE 'L''utilisateur a bien été inscrit';

END;
$$ LANGUAGE plpgsql;


\echo
\echo RENOUVELER UNE INSCRIPTION DANS UNE BIBLIOTHEQUE 
\echo


CREATE OR REPLACE FUNCTION renouvellement(id_util INTEGER, id_type_inscr INTEGER) RETURNS VOID AS $$
DECLARE
today DATE;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;
	
	UPDATE UTILISATEUR
	SET id_type_inscription = id_type_inscr
	WHERE id_utilisateur = id_util;
	
	UPDATE UTILISATEUR
	SET date_inscription = today
	WHERE id_utilisateur = id_util;
	
	RAISE NOTICE 'L''utilisateur a renouvelé son inscription';

END;
$$ LANGUAGE plpgsql;



\echo
\echo EMPRUNTER UN DOCUMENT 
\echo

CREATE OR REPLACE FUNCTION emprunter(id_doc INTEGER, id_util INTEGER) RETURNS VOID AS $$
DECLARE
ligne RECORD;
today DATE;
delai INTEGER;
BEGIN
	--on récupère la date courante
	SELECT date_courante INTO today
	FROM CONSTANTES;

	--on récupère le delai d'un emprunt
	SELECT delai_emprunt INTO delai
	FROM CONSTANTES;

	--on regarde dans pret si une ligne contient id_doc
	SELECT * INTO ligne
	FROM PRET 
	WHERE id_document = id_doc;

	--si on n'en trouve ou si la date rendu n'est pas NULL, alors on accepte l'emprunt sinon on l'annule
	IF NOT FOUND OR ligne.date_rendu IS NOT NULL THEN
	   INSERT INTO PRET VALUES (DEFAULT, id_doc, id_util, today, today + delai, NULL);
	   RAISE NOTICE 'L''emprunt a bien été enregistré !';
	ELSE
	   RAISE EXCEPTION 'Le document est déjà emprunté !';
	END IF;
END;
$$ LANGUAGE plpgsql;



   



\echo
\echo RESERVER UN DOCUMENT 
\echo

CREATE OR REPLACE FUNCTION reserver(id_doc INTEGER, id_util INTEGER, date_res DATE) RETURNS VOID AS $$
DECLARE
id_type_inscr INTEGER;
id_type_doc INTEGER;
ligne RECORD;
ligne_pret RECORD;
ligne_renouvellement RECORD;
delai INTEGER;
delai_renouv INTEGER;
nb_reservations INTEGER;
max_renouv INTEGER;
today DATE;
max_fin DATE;
max_res INTEGER;
amende_util UTILISATEUR.amende%TYPE;
BEGIN
		
	SELECT date_courante INTO today 
	FROM CONSTANTES;

	
	-- On vérifie que l'utilisateur n'a pas réservé 5 documents
	SELECT max_reservations INTO max_res
	FROM CONSTANTES;
	
	SELECT COUNT(*) INTO nb_reservations 
	FROM PRET
	WHERE date_debut > today AND id_utilisateur = id_util;
	
	--Si il a déjà réservé 5 document on annule l'emprunt	
	IF nb_reservations = max_res THEN 
	   RAISE EXCEPTION 'Vous avez déjà réservé(e) % documents', max_res;
	   RETURN;
	END IF;
	
	SELECT delai_emprunt INTO delai
	FROM CONSTANTES;

	--On vérifie que l'utilisateur n'a pas déjà emprunté ou réservé ce document
	SELECT * INTO ligne 
	FROM PRET 
	WHERE id_document = id_doc AND id_utilisateur = id_util AND date_rendu IS NULL;

	IF FOUND THEN
	   RAISE EXCEPTION 'Vous avez déjà emprunté ou réservé ce document';
	END IF;


	SELECT * INTO ligne 
	FROM PRET 
	WHERE id_document = id_doc AND id_utilisateur <> id_util;
	
	-- si oui pour chaque pret ou reservation, on prend le max des date_fin si l'utilisateur a entré NULL comme date de réservation
	-- s'il a entré une date de réservation, on vérifie qu'il y a de la place pour insérer cette réservation
	-- et qu'elle n'en chevauche pas une autre
	IF FOUND THEN
	   IF ligne.date_rendu IS NULL THEN
	      FOR ligne IN
	      	    SELECT * 
		    FROM PRET 
		    WHERE id_document = id_doc AND id_utilisateur <> id_util AND date_rendu IS NULL
	      LOOP
		 IF date_res IS NULL THEN

		    max_fin = MAX(ligne.date_fin);

		 ELSE

		    IF date_res + delai >= ligne.date_debut AND date_res < ligne.date_fin THEN
		       RAISE EXCEPTION 'Votre réservation en chevauche une autre, veuillez choisir une autre date';
		    END IF;

		 END IF;

	       END LOOP;
		 
 	       IF date_res IS NULL THEN
	       	  INSERT INTO PRET VALUES (DEFAULT, id_doc, id_util, max_fin, max_fin + delai, NULL);
		  RAISE NOTICE 'La réservation a bien été enregistré !';
	       ELSE
	          INSERT INTO PRET VALUES (DEFAULT, id_doc, id_util, date_res, date_res + delai, NULL);
	       END IF;

	    ELSE
	   	 RAISE EXCEPTION 'Le document n est pas emprunté, il est déjà disponible !';
		 RETURN;
	    END IF;
	  ELSE
	      RAISE EXCEPTION 'Vous ne pouvez pas réserver un document qui n''est ni emprunté ni réservé';
	      RETURN;
	  END IF;
END;
$$ LANGUAGE plpgsql;


\echo
\echo RENOUVELER UN EMPRUNT
\echo


CREATE OR REPLACE FUNCTION renouveler_emprunt(id_doc INTEGER, id_util INTEGER) RETURNS VOID AS $$
DECLARE
ligne RECORD;
delai INTEGER;
delai_renouv INTEGER;
nb_reservations INTEGER;
max_renouv INTEGER;
today DATE;
BEGIN
		
	SELECT date_courante INTO today	
	FROM CONSTANTES;

	SELECT delai_emprunt INTO delai 
	FROM CONSTANTES;

	SELECT delai_renouvellement INTO delai_renouv 
	FROM CONSTANTES;

	SELECT max_renouvellements INTO max_renouv 
	FROM CONSTANTES;
	
	SELECT * INTO ligne 
	FROM PRET 
	WHERE id_document = id_doc AND id_utilisateur <> id_util;

	IF FOUND AND today < ligne.date_debut THEN 
	   RAISE EXCEPTION 'Le document est déjà réservé par quelqu''un d''autre';	
	END IF;

	SELECT * INTO ligne 
	FROM PRET 
	WHERE id_document = id_doc AND id_utilisateur = id_util;
	
	IF NOT FOUND THEN
	   RAISE EXCEPTION 'Vous n''avez aucun document à renouveler';	
	
	ELSEIF today < ligne.date_debut THEN
	       RAISE EXCEPTION 'Vous ne pouvez pas renouveler une réservation';

	ELSEIF ligne.date_fin - ligne.date_debut < delai + max_renouv * delai_renouv THEN
	      	UPDATE PRET SET date_fin = ligne.date_fin + delai_renouv;
		RAISE NOTICE 'Le renouvellement a bien été éffectué';
	ELSE
		RAISE EXCEPTION 'Vous ne pouvez renouveler votre emprunt que % fois', max_renouv;

	END IF;
END;
$$ LANGUAGE plpgsql;


\echo
\echo RENDRE UN DOCUMENT 
\echo

CREATE OR REPLACE FUNCTION rendre(id_doc INTEGER, id_util INTEGER) RETURNS VOID AS $$
DECLARE
ligne RECORD;
today DATE;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;

	SELECT * INTO ligne 
	FROM PRET 
	WHERE id_document = id_doc AND id_utilisateur = id_util AND date_rendu IS NULL;

	IF FOUND THEN 
		UPDATE PRET SET date_rendu = today WHERE id_document = id_doc AND id_utilisateur = id_util;
		--RAISE NOTICE 'Le document a bien été rendu !';
	ELSE
		RAISE NOTICE 'Ce document n était pas emprunté';
	END IF;
END;
$$ LANGUAGE plpgsql;





\echo
\echo RECHERCER UN DOCUMENT 
\echo


CREATE OR REPLACE FUNCTION rechercher_titre(critere TEXT) RETURNS VOID  AS $$
DECLARE
ligne RECORD;
statut_doc TEXT;
today DATE;
BEGIN
	SELECT date_courante into today FROM CONSTANTES;

	FOR ligne IN
	    SELECT DISTINCT id_document, titre, sous_titre, description_type_document, nom_bibliotheque
	    FROM DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT
	    NATURAL JOIN BIBLIOTHEQUE
	    WHERE UPPER(titre) LIKE UPPER('%' || critere || '%')
	LOOP

	    IF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT NATURAL JOIN BIBLIOTHEQUE
	       	WHERE UPPER(titre) LIKE UPPER('%' || critere || '%')
		       AND today < date_debut AND date_rendu IS NULL) THEN
		statut_doc = 'Réservé';

	    ELSEIF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT NATURAL JOIN BIBLIOTHEQUE
	        WHERE UPPER(titre) LIKE UPPER('%' || critere || '%') 
		       AND today BETWEEN date_debut AND date_fin AND date_rendu IS NULL) THEN
		  statut_doc = 'Emprunté';

	    ELSEIF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT 
	        NATURAL JOIN BIBLIOTHEQUE
	        WHERE UPPER(titre) LIKE UPPER('%' || critere || '%') 
		       AND today > date_fin AND date_rendu IS NULL) THEN
		  statut_doc = 'En Retard';

	    ELSE
		  statut_doc = 'Disponible';

	    END IF; 
		RAISE NOTICE '% | % | % | % | % | %',
		ligne.id_document, ligne.titre, ligne.sous_titre, ligne.description_type_document, ligne.nom_bibliotheque, statut_doc;
	END LOOP;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION rechercher_auteur(critere TEXT) RETURNS VOID  AS $$
DECLARE
ligne RECORD;
statut_doc TEXT;
today DATE;
BEGIN
	SELECT date_courante into today FROM CONSTANTES;

	FOR ligne IN
	    SELECT DISTINCT id_document, titre, sous_titre, description_type_document, nom_bibliotheque
	    FROM DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT
	    NATURAL JOIN BIBLIOTHEQUE NATURAL JOIN AUTEUR NATURAL JOIN RELATION_OUVRAGE_AUTEUR
	    WHERE UPPER(nom) LIKE UPPER('%' || critere || '%') 
	       OR UPPER(prenom) LIKE UPPER('%' || critere || '%')
	LOOP

	    IF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT NATURAL JOIN BIBLIOTHEQUE 
		NATURAL JOIN AUTEUR NATURAL JOIN RELATION_OUVRAGE_AUTEUR
	       	WHERE (UPPER(nom) LIKE UPPER('%' || critere || '%') 
		   OR UPPER(prenom) LIKE UPPER('%' || critere || '%'))
		   AND today < date_debut AND date_rendu IS NULL) THEN
		  statut_doc = 'Réservé';

	    ELSEIF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT NATURAL JOIN BIBLIOTHEQUE 
		NATURAL JOIN AUTEUR NATURAL JOIN RELATION_OUVRAGE_AUTEUR
	        WHERE (UPPER(nom) LIKE UPPER('%' || critere || '%') 
      		   OR UPPER(prenom) LIKE UPPER('%' || critere || '%'))
		   AND today BETWEEN date_debut AND date_fin AND date_rendu IS NULL) THEN
		  statut_doc = 'Emprunté';

            ELSEIF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT NATURAL JOIN BIBLIOTHEQUE 
		NATURAL JOIN AUTEUR NATURAL JOIN RELATION_OUVRAGE_AUTEUR
	        WHERE (UPPER(nom) LIKE UPPER('%' || critere || '%') 
      		   OR UPPER(prenom) LIKE UPPER('%' || critere || '%'))
		   AND today > date_fin AND date_rendu IS NULL) THEN
		  statut_doc = 'En Retard';

	    ELSE
		  statut_doc = 'Disponible';

	    END IF; 
                RAISE NOTICE '% | % | % | % | % | %',
		ligne.id_document, ligne.titre, ligne.sous_titre, ligne.description_type_document, ligne.nom_bibliotheque, statut_doc;
	END LOOP;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION rechercher_genre(critere TEXT) RETURNS VOID  AS $$
DECLARE
ligne RECORD;
statut_doc TEXT;
today DATE;
BEGIN
	SELECT date_courante into today
	FROM CONSTANTES;

	FOR ligne IN
	    SELECT DISTINCT id_document, id_genre, titre, sous_titre, description_type_document, nom_bibliotheque
	    FROM DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT
	    NATURAL JOIN BIBLIOTHEQUE NATURAL JOIN RELATION_OUVRAGE_GENRE NATURAL JOIN GENRE
	    WHERE UPPER(description_genre) LIKE UPPER('%' || critere || '%') 
	LOOP

	    IF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT NATURAL JOIN BIBLIOTHEQUE 
	    	NATURAL JOIN RELATION_OUVRAGE_GENRE NATURAL JOIN GENRE
	       	WHERE UPPER(description_genre) LIKE UPPER('%' || critere || '%') 
	              AND today < date_debut AND date_rendu IS NULL) THEN
	 	statut_doc = 'Réservé';


	    ELSEIF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT NATURAL JOIN BIBLIOTHEQUE 
		NATURAL JOIN RELATION_OUVRAGE_GENRE NATURAL JOIN GENRE
	        WHERE UPPER(description_genre) LIKE UPPER('%' || critere || '%') 
      		      AND today BETWEEN date_debut AND date_fin AND date_rendu IS NULL) THEN
		statut_doc = 'Emprunté';

	    ELSEIF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT NATURAL JOIN BIBLIOTHEQUE 
		NATURAL JOIN RELATION_OUVRAGE_GENRE NATURAL JOIN GENRE
	        WHERE UPPER(description_genre) LIKE UPPER('%' || critere || '%') 
      		      AND today > date_fin AND date_rendu IS NULL) THEN
		statut_doc = 'En Retard';

	    ELSE
		statut_doc = 'Disponible';

	    END IF; 
		RAISE NOTICE '% | % | % | % | % | %',
		ligne.id_document, ligne.titre, ligne.sous_titre, ligne.description_type_document, ligne.nom_bibliotheque, statut_doc;
	END LOOP;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION rechercher_langue(critere TEXT) RETURNS VOID  AS $$
DECLARE
ligne RECORD;
statut_doc TEXT;
today DATE;
BEGIN
	SELECT date_courante into today
	FROM CONSTANTES;

	FOR ligne IN
	    SELECT DISTINCT id_document, titre, sous_titre, description_type_document, nom_bibliotheque
	    FROM DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT
	    NATURAL JOIN BIBLIOTHEQUE
	    WHERE UPPER(langue) LIKE UPPER('%' || critere || '%') 
	LOOP

	    IF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT
		NATURAL JOIN BIBLIOTHEQUE
	       	WHERE UPPER(langue) LIKE UPPER('%' || critere || '%') 
		   AND today < date_debut AND date_rendu IS NULL) THEN
		statut_doc = 'Réservé';


	    ELSEIF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT 
	        NATURAL JOIN BIBLIOTHEQUE
	        WHERE UPPER(langue) LIKE UPPER('%' || critere || '%')
		   AND today BETWEEN date_debut AND date_fin AND date_rendu IS NULL) THEN
		statut_doc = 'Emprunté';


 	    ELSEIF ligne.id_document IN 
	       (SELECT id_document 
	        FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN TYPE_DOCUMENT 
	        NATURAL JOIN BIBLIOTHEQUE
	        WHERE UPPER(langue) LIKE UPPER('%' || critere || '%')
		   AND today > date_fin AND date_rendu IS NULL) THEN
		statut_doc = 'En Retard';


	    ELSE
		statut_doc = 'Disponible';

	    END IF; 
		RAISE NOTICE '% | % | % | % | % | %',
		ligne.id_document, ligne.titre, ligne.sous_titre, ligne.description_type_document, ligne.nom_bibliotheque, statut_doc;
	END LOOP;
END;
$$ LANGUAGE plpgsql;


\echo
\echo SUPPRIMER LES EMPRUNTS VIEUX DE 2 MOIS
\echo


CREATE OR REPLACE FUNCTION clear_emprunts() RETURNS VOID AS $$
DECLARE
today DATE;
BEGIN
	SELECT date_courante INTO today 
	FROM CONSTANTES;

	DELETE FROM PRET
	WHERE date_rendu IS NOT NULL AND date_debut <= today - 60;
END;
$$ LANGUAGE plpgsql;






\echo
\echo STATISTIQUE : NOMBRE D EMPRUNTS LE MOIS DERNIER
\echo

CREATE OR REPLACE FUNCTION stat_emprunts_mois_dernier() RETURNS INTEGER AS $$
DECLARE
today DATE;
cpt INTEGER;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;
	
	SELECT COUNT(*) INTO cpt
	FROM PRET
	WHERE (EXTRACT(MONTH FROM date_debut) = EXTRACT(MONTH FROM today) - 1) AND (EXTRACT(YEAR FROM date_debut) = EXTRACT(YEAR FROM today));
	
	RETURN cpt;
END;
$$ LANGUAGE plpgsql;






\echo
\echo STATISTIQUE : POURCENTAGE DE LIVRE EMPRUNTE DANS CHAQUE BIBLIOTHEQUE
\echo

CREATE OR REPLACE FUNCTION stat_pourcentage_livre_emprunte() RETURNS VOID AS $$
DECLARE
ligne RECORD;
today DATE;
cpt_total NUMERIC(10,2);
cpt_emprunte NUMERIC(10,2);
pourcentage NUMERIC(10,2);
BEGIN
	FOR ligne IN 
	    SELECT * FROM BIBLIOTHEQUE
	LOOP
		SELECT COUNT(*) INTO cpt_total
		FROM DOCUMENT NATURAL JOIN BIBLIOTHEQUE
		WHERE id_bibliotheque = ligne.id_bibliotheque;

		SELECT COUNT(*) INTO cpt_emprunte
		FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN BIBLIOTHEQUE
		WHERE id_bibliotheque = ligne.id_bibliotheque;

		pourcentage = (cpt_emprunte / cpt_total) * 100;
		RAISE NOTICE '%   |   % %% des documents sont empruntés',ligne.nom_bibliotheque, pourcentage;
	END LOOP;
END;
$$ LANGUAGE plpgsql;







\echo
\echo ACQUITTEMENT D UNE AMENDE
\echo 

CREATE OR REPLACE FUNCTION acquitter(id_util INTEGER) RETURNS VOID AS $$
DECLARE
amende_util UTILISATEUR.amende%TYPE;
BEGIN
	SELECT amende INTO amende_util 
 	FROM UTILISATEUR 
	WHERE id_utilisateur = id_util;
	
	IF amende_util < 15.00 THEN
	   RAISE NOTICE 'Vous ne pouvez pas régler votre amende tant que celle-ci n''a pas atteinte 15.00€';
	ELSE
	   UPDATE UTILISATEUR SET amende = 0.00 WHERE id_utilisateur = id_util;
	   RAISE NOTICE 'Votre amende a bien été payée';
	END IF;
END;
$$ LANGUAGE plpgsql;
