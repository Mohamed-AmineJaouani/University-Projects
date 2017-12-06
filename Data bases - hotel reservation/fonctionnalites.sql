
/* effectuer une recherche de disponibilité selon certains critères (date, heure, tarif, etc.) */
\echo Function to execute : recherche_disponibilite(id_hotel, date_debut(format DATE), prix_nuit max, categorieEnum);
PREPARE recherche_disponibilite(INTEGER,DATE,INTEGER,categorieEnum) AS
SELECT * FROM table_chambre WHERE id_hotel = $1 AND prix_nuit <= $3 AND categorie = $4
EXCEPT
SELECT * FROM table_chambre WHERE id_hotel = $1 AND id_chambre IN (
        SELECT id_chambre FROM table_reservation WHERE DATE(date_debut) = $2);




/* effectuer la réservation d’un créneau libre pour un certain nombre de personnes */
\echo Function to execute : reserver_creneau(id_client, id_chambre, date_reservation, date_debut, date_fin, nb_personnes, id_promotion);
PREPARE reserver_creneau(INTEGER, INTEGER, TIMESTAMP, TIMESTAMP, TIMESTAMP, INTEGER, INTEGER) AS
INSERT INTO table_reservation (id_client, id_chambre, date_reservation, date_debut, date_fin, nb_personnes, id_promotion) VALUES ($1, $2, $3, $4, $5, $6, $7);

INSERT INTO table_client (nom,prenom,email,date_naissance,nuits_reservees_annee) VALUES('Jaouani','Mohamed','mohamed.936@hotmail.fr','1993-12-02',0);
INSERT INTO table_reservation (id_client,id_chambre, date_reservation, date_debut,date_fin,nb_personnes,id_promotion) VALUES(currval('table_client_id_client_seq'),6, '2015-04-22 12:00:00','2015-05-02 12:00:00','2015-05-09 12:00:00',4,NULL);





/*obtenir des reductions a partir d'un certains nombre de resas en moins d'un an */
UPDATE table_reservation SET id_promotion = 1
WHERE (SELECT COUNT(*) FROM table_reservation WHERE id_client IN ( SELECT id_client FROM table_client)) BETWEEN 10 AND 19;
UPDATE table_reservation SET id_promotion = 2
WHERE (SELECT COUNT(*) FROM table_reservation WHERE id_client IN ( SELECT id_client FROM table_client)) BETWEEN 20 AND 29;
UPDATE table_reservation SET id_promotion = 3
WHERE (SELECT COUNT(*) FROM table_reservation WHERE id_client IN ( SELECT id_client FROM table_client)) >= 30;





/*annuler et éventuellement se faire rembourser si la réservation est suffisamment éloi-
gnée dans le temps */
\echo Function to execute : annuler_reservation_relation(id_reservation);
PREPARE annuler_reservation_relation(INTEGER) AS
DELETE FROM relation_reservation_service WHERE id_reservation = $1;

\echo Function to execute : annuler_reservation(id_reservation);
PREPARE annuler_reservation(INTEGER) AS
DELETE FROM table_reservation WHERE id_reservation = $1 AND CURRENT_DATE < DATE(date_debut)-15;


DELETE FROM table_reservation WHERE id_reservation = 1 AND CURRENT_DATE < DATE(date_debut)-15;




/* effectuer des statistiques sur l’occupation du lieu */
/*\echo (Tapez ’EXECUTE taux_occupation_annee(id_hotel, annee)’)
PREPARE taux_occupation_annee(INTEGER, INTEGER) AS
SELECT SUM(EXTRACT(DAY FROM(date_fin - date_debut)))/365/COUNT(c.id_chambre)*100 AS taux_occupation
FROM table_reservation AS r
INNER JOIN table_chambre AS c
ON c.id_chambre = r.id_chambre
INNER JOIN table_hotel as h
ON ARRAY[c.id_chambre] <@ h.id_chambre
WHERE EXTRACT(YEAR FROM(date_debut)) = $2 AND EXTRACT(YEAR FROM(date_fin)) = $2
      AND id_hotel = $1;*/




/*visualiser les recettes */


SELECT SUM(prix_nuit) AS recette_nuit,SUM(prix_acces) AS recette_service , SUM(prix_nuit)+SUM(prix_acces) AS recette_totale
FROM table_reservation 
NATURAL JOIN relation_reservation_service 
NATURAL JOIN table_chambre 
NATURAL JOIN table_service;




/* supprimer un utilisateur ou une réservation */
DELETE FROM table_reservation WHERE id_reservation = 1;

DELETE FROM relation_reservation_service where id_reservation in ( select id_reservation FROM table_reservation where id_client = 1); 	 

DELETE FROM table_reservation WHERE id_client = 1;

DELETE FROM table_client WHERE id_client = 1;



/*annuler une représentation ou une journée */
DELETE FROM relation_reservation_service where id_reservation in (select id_reservation from table_reservation where '2015-05-12' BETWEEN date_debut AND date_fin);

DELETE FROM table_reservation WHERE '2015-05-12' BETWEEN date_debut AND date_fin;

