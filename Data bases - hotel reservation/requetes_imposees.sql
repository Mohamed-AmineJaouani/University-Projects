\echo -----DEBUT AFFICHAGE DES TABLES-----
SELECT * FROM table_client;
SELECT * FROM table_chambre;
SELECT * FROM table_service;
SELECT * FROM table_reservation;	  
\echo -----FIN AFFICHAGE DES TABLES-----



\echo -----DEBUT PARTIE REPONSE AUX QUESTIONS-----
\echo ***** PROJET QUESTION 1 *****
\echo Quel est le taux d’occupation d’un lieu en 2014 ?
\echo (Tapez ’EXECUTE taux_occupation_annee(id_hotel, annee)’)
PREPARE taux_occupation_annee(INTEGER, INTEGER) AS
SELECT SUM(EXTRACT(DAY FROM(date_fin - date_debut)))/365/COUNT(c.id_chambre)*100 AS taux_occupation
FROM table_reservation AS r
INNER JOIN table_chambre AS c
ON c.id_chambre = r.id_chambre
WHERE EXTRACT(YEAR FROM(date_debut)) = $2
      AND EXTRACT(YEAR FROM(date_fin)) = $2
      AND id_hotel = $1;



\echo ***** PROJET QUESTION 2 *****
\echo Donner les recettes du mois en cours, par type de prestation, en ordre croissant de recettes
\echo > PRESTATION SERVICE AVEC MOIS
SELECT nom, SUM(prix_acces) AS recette_mois
FROM table_service AS s
INNER JOIN relation_reservation_service AS rel
ON s.id_service = rel.id_service
INNER JOIN table_reservation AS r
ON rel.id_reservation = r.id_reservation
WHERE EXTRACT(MONTH FROM(date_debut)) = EXTRACT(MONTH FROM(CURRENT_DATE)) OR EXTRACT(MONTH FROM(date_fin)) = EXTRACT(MONTH FROM(CURRENT_DATE))
GROUP BY nom
ORDER BY recette_mois ASC;


\echo > PRESTATION CHAMBRE AVEC MOIS
SELECT categorie, SUM(prix_nuit*(EXTRACT(DAY FROM(date_fin - date_debut)))) AS recette_mois
FROM table_chambre AS c
INNER JOIN table_reservation AS r
ON c.id_chambre = r.id_chambre
WHERE EXTRACT(MONTH FROM(date_debut)) = EXTRACT(MONTH FROM(CURRENT_DATE)) OR EXTRACT(MONTH FROM(date_fin)) = EXTRACT(MONTH FROM(CURRENT_DATE))
GROUP BY categorie
ORDER BY recette_mois ASC;



\echo ***** PROJET QUESTION 3 *****
\echo Donner la liste des prestations n’ayant fait l’objet d’aucune reservation le mois dernier

\echo > PRESTATION DE SERVICE
SELECT id_service, nom, prix_acces FROM table_service
EXCEPT
SELECT s.id_service, nom, prix_acces FROM table_service s
INNER JOIN relation_reservation_service rel
ON s.id_service = rel.id_service
INNER JOIN table_reservation r
ON rel.id_reservation = r.id_reservation
WHERE EXTRACT(MONTH FROM(date_debut)) = EXTRACT(MONTH FROM(CURRENT_TIMESTAMP - INTERVAL '1 month')) AND EXTRACT(YEAR FROM(date_debut)) = EXTRACT(YEAR FROM(CURRENT_TIMESTAMP));


\echo > PRESTATION DE CHAMBRE
SELECT id_chambre FROM table_chambre
EXCEPT
SELECT id_chambre FROM table_reservation
WHERE EXTRACT(MONTH FROM(date_debut)) = EXTRACT(MONTH FROM(CURRENT_TIMESTAMP - INTERVAL '1 month')) AND EXTRACT(YEAR FROM(date_debut)) = EXTRACT(YEAR FROM(CURRENT_TIMESTAMP));



\echo ***** PROJET QUESTION 5 *****
\echo En moyenne, combien de jours a l’avance les clients font-ils une reservation ?
SELECT AVG(EXTRACT (DAY FROM (date_debut - date_reservation))) AS moy_jours_av_résa FROM table_reservation;





\echo ***** PROJET QUESTION 8 *****
\echo Quels sont les clients qui sont venus au moins une fois par semaine ces 6 derniers mois ?
SELECT * FROM table_client AS c WHERE c.id_client IN (
       SELECT r.id_client FROM table_reservation AS r
       WHERE EXTRACT(WEEK FROM(date_debut))
       IN (
       	  SELECT * FROM GENERATE_SERIES(EXTRACT(WEEK FROM(CURRENT_DATE - INTERVAL '6 month'))::int, EXTRACT(WEEK FROM(CURRENT_DATE))::int)
	  WHERE EXTRACT(YEAR FROM(CURRENT_DATE - INTERVAL '6 month')) = EXTRACT(YEAR FROM(CURRENT_DATE))
	)
       HAVING COUNT(DISTINCT EXTRACT(WEEK FROM(date_debut))) = (EXTRACT(WEEK FROM(CURRENT_DATE)) - EXTRACT(WEEK FROM(CURRENT_DATE - INTERVAL '6 month')))
       GROUP BY r.id_client
);

\echo ***** PROJET QUESTION 10 *****
\echo _on remarque que des clients viennent toujours ensemble. Écrivez une requête qui
\echo associe à chaque client les autres clients qui viennent systématiquement les mêmes
\echo jours qu’eux.

SELECT id_client,nom,prenom FROM table_client WHERE id_client IN ( SELECT DISTINCT id_client FROM table_reservation r1 NATURAL JOIN table_reservation r2 WHERE r1.date_debut = r2.date_debut);


\echo -----FIN PARTIE REPONSE AUX QUESTIONS-----


