\echo CREATION DES TRIGGERS
\echo

\echo DEBUT : NOUVEAUX TRIGGERS

\echo
\echo TRIGGER QUI EMPECHE UN UTILISATEUR D EMPRUNTER/RESERVER UN DOCUMENT S IL A UNE AMENDE DE 15€ OU PLUS
\echo
CREATE OR REPLACE FUNCTION check_amende() RETURNS TRIGGER AS $$
DECLARE
amende_util UTILISATEUR.amende%TYPE;
BEGIN
	SELECT amende INTO amende_util
	FROM UTILISATEUR
	WHERE id_utilisateur = NEW.id_utilisateur;

	IF amende_util >= 15 THEN
	   RAISE EXCEPTION 'Vous ne pouvez plus emprunter/réserver de document, veuillez payer votre amende (carte bloquée)';
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_check_amende ON PRET;

CREATE TRIGGER trig_check_amende
BEFORE INSERT ON PRET
FOR EACH ROW
EXECUTE PROCEDURE check_amende();





\echo
\echo TRIGGER QUI EMPECHE UN UTILISATEUR D EMPRUNTER/RESERVER UN DOCUMENT S IL A AU MOINS UN DOCUMENT EN RETARD
\echo
CREATE OR REPLACE FUNCTION check_retard() RETURNS TRIGGER AS $$
DECLARE
today DATE;
ligne_pret RECORD;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;

	SELECT * INTO ligne_pret
	FROM PRET NATURAL JOIN UTILISATEUR
	WHERE id_utilisateur = NEW.id_utilisateur AND date_fin < today AND date_rendu IS NULL;

	IF FOUND THEN
	   RAISE EXCEPTION 'Vous ne pouvez plus emprunter/réserver, car vous avez au moins un document en retard';
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_check_retard ON PRET;

CREATE TRIGGER trig_check_retard
BEFORE INSERT ON PRET
FOR EACH ROW
EXECUTE PROCEDURE check_retard();



\echo
\echo TRIGGER QUI EMPECHE UN UTILISATEUR D EMPRUNTER/RESERVER UN DOCUMENT S IL ESSAIE D EMPRUNTER AU PLUS TARD 3 SEMAINES (delai) AVANT LA FIN DE SON INSCRIPTION
\echo
CREATE OR REPLACE FUNCTION check_fin_inscription() RETURNS TRIGGER AS $$
DECLARE
today DATE;
delai INTEGER;
ligne_renouvellement RECORD;
BEGIN
	SELECT date_courante, delai_emprunt INTO today, delai
	FROM CONSTANTES;

	SELECT * INTO ligne_renouvellement
	FROM UTILISATEUR
	WHERE id_utilisateur = NEW.id_utilisateur;

	IF today > ligne_renouvellement.date_inscription + 365 - delai THEN
	   RAISE EXCEPTION 'Vous arrivez au terme de votre inscription, veuillez d''abord renouveler votre inscription pour emprunter/réserver ce document';
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_check_fin_inscription ON PRET;

CREATE TRIGGER trig_check_fin_inscription
BEFORE INSERT ON PRET
FOR EACH ROW
EXECUTE PROCEDURE check_fin_inscription();




\echo
\echo TRIGGER QUI EMPECHE UN UTILISATEUR D EMPRUNTER/RESERVER UN DOCUMENT QUI NE LUI EST PAS AUTORISE SELON SON TYPE D INSCRIPTION
\echo
CREATE OR REPLACE FUNCTION check_type_inscription() RETURNS TRIGGER AS $$
DECLARE
id_type_doc INTEGER;
id_type_inscr INTEGER;
BEGIN
	--on récupère le type du document emprunté
	SELECT id_type_document INTO id_type_doc
	FROM DOCUMENT
	WHERE id_document = NEW.id_document;

	--on récupère le type d'indcription de l'emprunteur
	SELECT id_type_inscription INTO id_type_inscr 
	FROM UTILISATEUR 
	WHERE id_utilisateur = NEW.id_utilisateur;

	--si l'utilisateur n'a pas le bon type d'inscription, on annule l'emprunt
	IF (id_type_inscr = 1 AND (id_type_doc = 2 OR id_type_doc = 3)) OR (id_type_inscr = 2 AND id_type_doc = 3) THEN
	   RAISE EXCEPTION 'Vous ne pouvez pas emprunter/réserver ce document. Renouvelez votre inscription d abord';
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_check_type_inscription ON PRET;

CREATE TRIGGER trig_check_type_inscription
BEFORE INSERT ON PRET
FOR EACH ROW
EXECUTE PROCEDURE check_type_inscription();





\echo FIN : NOUVEAUX TRIGGERS







\echo
\echo TRIGGER QUI VERIFIE QU ON NE PEUT PAS DES DEPASSER LES LIMITES D EMPRUNT
\echo

CREATE OR REPLACE FUNCTION check_nb_max_emprunts() RETURNS TRIGGER AS $$
DECLARE
max_emprunts_t CONSTANTES.max_emprunts_total%TYPE;
max_emprunts_b CONSTANTES.max_emprunts_bibliotheque%TYPE;
max_emprunts_d CONSTANTES.max_emprunts_dvd%TYPE;
max_emprunts_n CONSTANTES.max_emprunts_nouveaute%TYPE;
nb_prets_total INTEGER;
id_biblio DOCUMENT.id_bibliotheque%TYPE;
nb_prets_bibliotheque INTEGER;
nb_prets_dvd INTEGER;
nb_prets_nouveaute INTEGER;
type_doc INTEGER;
annee_pret INTEGER;
today DATE;
BEGIN
	--on récupère le nombre maximal de documents que l'utilisateur peut emprunter dans toutes les bibliothèque
	SELECT max_emprunts_total INTO max_emprunts_t 
	FROM CONSTANTES;

	--on récupère le nombre d'emprunts en cours de l'utilisateur
	SELECT COUNT(*) INTO nb_prets_total
	FROM PRET 
	WHERE id_utilisateur = NEW.id_utilisateur AND date_rendu IS NULL;

	--Si ce nombre a atteint la limite, il ne peut plus emprunter de document
	IF nb_prets_total = max_emprunts_t THEN
	   RAISE EXCEPTION 'Vous ne pouvez plus emprunter : vous avez atteint la limite des emprunts';
	   RETURN NULL;
	END IF;

	--on récupère le nombre maximal de documents que l'utilisateur peut emprunter par bibliothèque
	SELECT max_emprunts_bibliotheque INTO max_emprunts_b 
	FROM CONSTANTES;

	--on récupère l'id de la bibliothèque ou se trouve le document emprunté
	SELECT id_bibliotheque INTO id_biblio
	FROM DOCUMENT
	WHERE id_document = NEW.id_document;

	--on compte le nombre d'emprunts en cours de cet utilisateur dans cette bibliothèque
	SELECT COUNT(*) INTO nb_prets_bibliotheque
	FROM PRET NATURAL JOIN DOCUMENT
	WHERE id_utilisateur = NEW.id_utilisateur AND id_bibliotheque = id_biblio AND date_rendu IS NULL;

	--si ce nombre a atteint la limite, on annule l'emprunt
	IF nb_prets_bibliotheque = max_emprunts_b THEN
		RAISE EXCEPTION 'Vous ne pouvez plus emprunter : vous avez atteint la limite des emprunts par bibliothèque';
		RETURN NULL;
	END IF;

	--DVD

	--on recupère le type du document en cours d'emprunt
	SELECT id_type_document INTO type_doc
	FROM DOCUMENT
	WHERE id_document = NEW.id_document;

	IF type_doc = 3 THEN


	   SELECT max_emprunts_dvd into max_emprunts_d
	   FROM CONSTANTES;
	
	   --on compte le nombre de dvd déjà emprunté et non rendus
	   SELECT COUNT(*) INTO nb_prets_dvd
	   FROM PRET NATURAL JOIN DOCUMENT
	   WHERE id_utilisateur = NEW.id_utilisateur AND id_type_document = 3 AND date_rendu IS NULL;

	   --si le document en cours d'emprunt est un dvd et que le nombre d'emprunt de dvd est au maximum autorisé, on annule l'emprunt
	   IF nb_prets_dvd = max_emprunts_d THEN
	      RAISE EXCEPTION 'Vous ne pouvez empunter que % DVD', max_emprunts_d;
	      RETURN NULL;
	   END IF;
	END IF;


	--NOUVEAUTÉES


	--on récupère la date courante
	SELECT date_courante INTO today
	FROM CONSTANTES;

	--on récupère l'annee du document en cours d'emprunt	
	SELECT annee INTO annee_pret
	FROM DOCUMENT NATURAL JOIN OUVRAGE
	WHERE id_document = NEW.id_document;

	IF annee_pret = EXTRACT(YEAR FROM today) THEN

	   SELECT max_emprunts_nouveaute INTO max_emprunts_n
       	   FROM CONSTANTES;

	   --on compte le nombre de nouveautés déjà empruntés et non rendues
	   SELECT COUNT(*) INTO nb_prets_nouveaute
	   FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE
	   WHERE id_utilisateur = NEW.id_utilisateur AND annee_pret = EXTRACT(YEAR FROM today) AND date_rendu IS NULL;
	
	   --si le document en cours d'emprunt est une nouveautée et que le nombre d'emprunts de nouveautées a atteint la limite autorisée
	   --on annule l'emprunt
	   IF nb_prets_nouveaute = max_emprunts_n THEN
	      RAISE EXCEPTION 'Vous ne pouvez emprunter que % nouveautées', max_emprunts_n;
	      RETURN NULL;
	   END IF;
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_max_emprunts_total ON PRET;

CREATE TRIGGER trig_max_emprunts_total
BEFORE INSERT ON PRET
FOR EACH ROW
EXECUTE PROCEDURE check_nb_max_emprunts();




\echo
\echo TRIGGER QUI LISTE LES LIVRES QUI DOIVENT ETRE RENDUS DANS 2 JOURS
\echo 


CREATE OR REPLACE FUNCTION check_rendre_dans_deux_jours() RETURNS TRIGGER AS $$
DECLARE
today DATE;
lignes RECORD;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;

	FOR lignes IN
		SELECT nom_utilisateur, prenom, titre, sous_titre, nom_bibliotheque
		FROM UTILISATEUR NATURAL JOIN PRET NATURAL JOIN DOCUMENT NATURAL JOIN BIBLIOTHEQUE NATURAL JOIN OUVRAGE
		WHERE date_fin = today + 2
	LOOP
	     RAISE NOTICE '% % doit rendre le document % % dans deux jours à la bibliothèque %',
	     lignes.nom_utilisateur, lignes.prenom, lignes.titre, lignes.sous_titre, lignes.nom_bibliotheque;
	END LOOP;
	
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_rendre_dans_deux_jours ON CONSTANTES;

CREATE TRIGGER trig_rendre_dans_deux_jours
AFTER UPDATE OF date_courante ON CONSTANTES
FOR EACH ROW
EXECUTE PROCEDURE check_rendre_dans_deux_jours();





\echo
\echo TRIGGER QUI ALERTE QU UN LIVRE RESERVE VIENT D ETRE RENDU
\echo

CREATE OR REPLACE FUNCTION check_livre_reserve_rendu() RETURNS TRIGGER AS $$
DECLARE
ligne RECORD;
today DATE;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;
	
	SELECT titre, sous_titre, nom_bibliotheque, description_type_document INTO ligne 
	FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN BIBLIOTHEQUE NATURAL JOIN TYPE_DOCUMENT
	WHERE id_document = OLD.id_document AND date_debut >= OLD.date_fin;

	IF NOT FOUND THEN RETURN NEW;
	
	ELSE 
	   RAISE NOTICE 'Le document (%) % % qui était réservé a bien été rendu dans la bibliothèque %',
	   ligne.description_type_document, ligne.titre, ligne.sous_titre, ligne.nom_bibliotheque;

	   RETURN NEW;
	END IF;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_livre_reserve_rendu ON PRET;

CREATE TRIGGER trig_livre_reserve_rendu
BEFORE UPDATE OF date_rendu ON PRET
FOR EACH ROW
EXECUTE PROCEDURE check_livre_reserve_rendu();





\echo
\echo TRIGGER QUI ALERTE LORSQUE LE MONTANT ATTEINT 15€
\echo

CREATE OR REPLACE FUNCTION check_alerte_amende() RETURNS TRIGGER AS $$
DECLARE
lignes RECORD;
BEGIN
	FOR lignes IN 
		SELECT nom_utilisateur, prenom
		FROM UTILISATEUR
		WHERE amende >= 15.00 
	LOOP
		RAISE NOTICE '% % vous avez atteint 15 € d''amende', lignes.nom_utilisateur, lignes.prenom;
	END LOOP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_alerte_amende ON UTILISATEUR;

CREATE TRIGGER trig_alerte_amende
AFTER UPDATE OF amende ON UTILISATEUR
FOR EACH STATEMENT
EXECUTE PROCEDURE check_alerte_amende();





\echo
\echo TRIGGER AUGMENTANT DE 0.15 € PAR DOCUMENT EN RETARD L AMENDE DE L UTILISATEUR
\echo

CREATE OR REPLACE FUNCTION check_augmente_amende() RETURNS TRIGGER AS $$
DECLARE
lignes RECORD;
today DATE;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;

	FOR lignes IN
	  SELECT COUNT(*) as nb_document, id_utilisateur, nom_utilisateur, prenom
	  FROM PRET NATURAL JOIN UTILISATEUR
	  WHERE date_fin < today AND date_rendu IS NULL
	  GROUP BY id_utilisateur,nom_utilisateur,prenom
	LOOP
	
	  UPDATE UTILISATEUR SET amende = amende + lignes.nb_document * 0.15 WHERE id_utilisateur = lignes.id_utilisateur;
	  RAISE NOTICE '% % a reçu une amende de %€ pour un retard de document(s) !',
	  lignes.nom_utilisateur, lignes.prenom, lignes.nb_document * 0.15;
	  
	END LOOP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_augmente_amende ON CONSTANTES;

CREATE TRIGGER trig_augmente_amende
AFTER UPDATE OF date_courante ON CONSTANTES
FOR EACH STATEMENT
EXECUTE PROCEDURE check_augmente_amende();




CREATE OR REPLACE FUNCTION check_supprimer_utilisateurs() RETURNS TRIGGER AS $$
DECLARE
today DATE;
ligne RECORD;
ligne_pret RECORD;
BEGIN
	SELECT date_courante INTO today
	FROM CONSTANTES;

	FOR ligne IN
	    SELECT *
	    FROM UTILISATEUR
	    WHERE date_inscription <= today - 365
	LOOP
		SELECT * INTO ligne_pret 
		FROM PRET NATURAL JOIN UTILISATEUR
		WHERE id_utilisateur = ligne.id_utilisateur AND date_rendu IS NULL;

		IF NOT FOUND THEN
		   DELETE FROM PRET
		   WHERE id_utilisateur = ligne.id_utilisateur;
		   
		   DELETE FROM UTILISATEUR
		   WHERE id_utilisateur = ligne.id_utilisateur;
		ELSE
		   RAISE NOTICE 'On ne peut pas supprimer % % car il/elle possède encore des documents',ligne_pret.nom_utilisateur, ligne_pret.prenom;
		END IF;
	END LOOP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_supprimer_utilisateurs ON CONSTANTES;

CREATE TRIGGER trig_supprimer_utilisateurs
AFTER UPDATE OF date_courante ON CONSTANTES
FOR EACH ROW
EXECUTE PROCEDURE check_supprimer_utilisateurs();


/*

\echo TRIGGER NON DEMANDÉ MAIS PEUT ÊTRE UTILE A AFFICHER
\echo
\echo TRIGGER QUI SIGNAL QU UN LIVRE VIENT D ETRE RENDU
\echo

CREATE OR REPLACE FUNCTION check_livre_rendu() RETURNS TRIGGER AS $$
DECLARE
ligne RECORD;
BEGIN
	SELECT titre, sous_titre, nom_bibliotheque, description_type_document INTO ligne 
	FROM PRET NATURAL JOIN DOCUMENT NATURAL JOIN OUVRAGE NATURAL JOIN BIBLIOTHEQUE NATURAL JOIN TYPE_DOCUMENT
	WHERE id_document = OLD.id_document;

	IF OLD.date_rendu IS NOT NULL THEN
	   RAISE NOTICE 'Le document (%) % % a déjà été rendu dans la bibliothèque %',ligne.description_type_document, ligne.titre, ligne.sous_titre, ligne.nom_bibliotheque;
	   RETURN NULL;
	ELSEIF NEW.date_rendu <= OLD.date_fin THEN
	   RAISE NOTICE 'Le document (%) % % a bien été rendu dans la bibliothèque %',ligne.description_type_document, ligne.titre, ligne.sous_titre, ligne.nom_bibliotheque;
	   RETURN NEW;
	ELSE
	   RAISE NOTICE 'Le document (%) % % a été rendu après la date de fin dans la bibliothèque %',ligne.description_type_document, ligne.titre, ligne.sous_titre, ligne.nom_bibliotheque;
	   RETURN NEW;
	END IF;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_livre_rendu ON PRET;

CREATE TRIGGER trig_livre_rendu
BEFORE UPDATE OF date_rendu ON PRET
FOR EACH ROW
EXECUTE PROCEDURE check_livre_rendu();*/
