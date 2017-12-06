\echo /* sous requete dans le WHERE*/
\echo requete qui liste toutes les chambres des hotels de Paris
SELECT id_chambre,prix_nuit,categorie FROM table_chambre WHERE id_hotel IN ( SELECT id_hotel FROM table_hotel WHERE ville LIKE 'Paris');

\echo /* sous requete dans le from */
\echo requete qui liste tous les hotels de Paris
SELECT nom,ville,nb_etoiles,type_hotel FROM (( SELECT nom AS nom,ville AS ville, nb_etoiles AS nb_etoiles, type_hotel AS type_hotel FROM table_hotel  WHERE pays LIKE 'France' AND ville LIKE 'Paris' ))  AS hotel;

\echo /*une requete qui porte sur trois tables */

/*SELECT * FROM table_reservation WHERE table_reservation.id_chambre IN ( 
       SELECT id_chambre FROM table_chambre WHERE id_chambre IN ( 
       	      SELECT id_chambre FROM table_hotel WHERE prix > 100 )
       );*/

\echo requete qui liste les chambres et les hotels dont le prix de la chambre est supérieur a 100
SELECT id_chambre,categorie,prix_nuit,id_hotel,nom,adresse,ville,pays,nb_etoiles FROM ((table_reservation NATURAL JOIN table_chambre) NATURAL JOIN table_hotel) WHERE prix_nuit > 100;  

\echo /* sous requete dans le select */
\echo requete qui liste la somme des prix des reservations de chaque client
SELECT r.id_reservation, r.id_client, id_promotion, (SELECT SUM(prix_acces) AS somme FROM (relation_reservation_service NATURAL JOIN table_service) AS s1 WHERE s1.id_service = r.id_reservation ) FROM table_reservation r;   

\echo /* deux agregats avec group by et having */
\echo requete qui liste la moyenne des prix des services proposes
SELECT AVG(prix_acces),id_service FROM table_service GROUP BY id_service;

\echo requete qui renvoi le nombre de services reserves  
SELECT COUNT(id_service),id_service FROM relation_reservation_service GROUP BY id_service;

\echo /*une jointure externe */
\echo requete qui liste les dix premiers services reserves
SELECT * FROM table_reservation LEFT JOIN relation_reservation_service ON table_reservation.id_reservation <= 10;

\echo /* une sous-requete corrélée */
\echo requete qui liste les clients ayant la meme _date de naissance
SELECT id_client, nom, prenom,date_naissance 
FROM table_client c1 
WHERE EXISTS ( SELECT * 
FROM table_client c2 
WHERE c1.id_client <> c2.id_client
AND c1.date_naissance = c2.date_naissance); 


SELECT id_client, nom, prenom,date_naissance
FROM table_client c1 
WHERE DISTINCT ( SELECT * 
FROM table_client c2 
WHERE c1.id_client <> c2.id_client
AND c1.date_naissance = c2.date_naissance);

\echo Remise de 40 % si aujourd hui est l anniverssaire du client
UPDATE table_reservation SET id_promotion = 3 WHERE id_client IN ( SELECT id_client FROM table_client WHERE date_naissance = CURRENT_DATE );
