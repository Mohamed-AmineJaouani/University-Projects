\echo -----DEBUT PARTIE CREATION DES TABLES-----
CREATE TABLE table_hotel (
       id_hotel SERIAL PRIMARY KEY,
       nom VARCHAR(30) NOT NULL,
       adresse VARCHAR(100),
       code_postal INTEGER,
       ville VARCHAR(30),
       pays VARCHAR(30),
       nb_etoiles INTEGER,
       type_hotel VARCHAR(30));
CREATE TYPE categorieEnum AS ENUM('lit simple', 'lit double', 'suite', 'suite royale');
CREATE TABLE table_chambre (
       id_chambre SERIAL PRIMARY KEY,
       id_hotel INTEGER,
       categorie categorieEnum,
       prix_nuit FLOAT,
       FOREIGN KEY (id_hotel) REFERENCES table_hotel (id_hotel));
CREATE TABLE table_client (
       id_client SERIAL PRIMARY KEY,
       nom VARCHAR(30) NOT NULL,
       prenom VARCHAR(30) NOT NULL,
       email VARCHAR(40),
       date_naissance DATE,
       nuits_reservees_annee INTEGER);
CREATE TABLE table_service (
       id_service SERIAL PRIMARY KEY,
       nom VARCHAR(30),
       prix_acces FLOAT);
CREATE TABLE table_promotion (
       id_promotion SERIAL PRIMARY KEY,
       promotion INTEGER NOT NULL,
       description VARCHAR(50));
CREATE TABLE table_reservation (
       id_reservation SERIAL PRIMARY KEY,
       id_client INTEGER,
       id_chambre INTEGER,
       date_reservation TIMESTAMP NOT NULL,
       date_debut TIMESTAMP NOT NULL,
       date_fin TIMESTAMP NOT NULL,
       nb_personnes INTEGER,
       id_promotion INTEGER,
       FOREIGN KEY (id_client) REFERENCES table_client (id_client),
       FOREIGN KEY (id_chambre) REFERENCES table_chambre (id_chambre),
       FOREIGN KEY (id_promotion) REFERENCES table_promotion (id_promotion));
CREATE TABLE relation_hotel_service (
       id_hotel INTEGER,
       id_service INTEGER,
       FOREIGN KEY (id_hotel) REFERENCES table_hotel (id_hotel),
       FOREIGN KEY (id_service) REFERENCES table_service (id_service));
CREATE TABLE relation_reservation_service (
       id_reservation INTEGER,
       id_service INTEGER,
       FOREIGN KEY (id_reservation) REFERENCES table_reservation (id_reservation),
       FOREIGN KEY (id_service) REFERENCES table_service (id_service)); 
\echo -----FIN PARTIE CREATION DES TABLES-----





\echo -----DEBUT PARTIE INSERTION DANS LES TABLES-----
INSERT INTO table_hotel (nom, adresse, code_postal, ville, pays, nb_etoiles, type_hotel) VALUES
       ('Le Mino', '15 avenue du vieux port', 13005, 'Marseille', 'France', 0, 'Nature'),
       ('Le Simpliste', '1 rue des hotels', 75013, 'Paris', 'France', 1, 'Traditionnel'),
       ('Le Grandiose', '10 rue des hotels', 75013, 'Toulouse', 'France', 5, 'Glace'),
       ('Ritz','15 place Vendôme',75001,'Paris','France',5,'Traditionnel'),
       ('The Nadler Soho','10 Carlisle Street',145456,'Londres','Angleterre',4,'Traditionnel');
INSERT INTO table_chambre (id_hotel, categorie, prix_nuit) VALUES
       (1, 'lit simple', 25),
       (1, 'lit double', 35),
       (1, 'suite', 45),
       (2, 'lit simple', 30),
       (2, 'lit simple', 30),
       (3, 'lit simple', 30),
       (3, 'lit double', 45),
       (3, 'lit double', 45),
       (3, 'suite', 70),
       (3, 'suite', 70),
       (3, 'suite royale', 150),
       (4, 'suite', 80),
       (4, 'suite', 80),	
       (4, 'suite', 90),
       (5, 'suite royale', 200),	
       (5, 'suite royale', 280),
       (5, 'suite royale', 2500);
INSERT INTO table_client (nom, prenom, email, date_naissance, nuits_reservees_annee) VALUES
       ('Bonbon', 'JeanJean', 'jambon@mail.com', date '1980-03-05', 0),
       ('Etoile', 'Patrick', NULL, date '1990-01-01', 0),
       ('Doe', 'Bob', 'bobdoe@mail.com', date '1980-03-05', 0),
       ('Bonbon', 'Rose', 'rosebonbon@mail.com', date '1990-01-01', 0),
       ('Smart','Ize','samrtize@mail.com', date '1992-09-18',10),
       ('Manon','Rose','mr@mail.com',date '1994-04-08',2),
       ('Jean','Jean','jeanjean@mail.com',date '1994-04-08',2),
       ('Sapin','Michel','ms@mail.com',date '1994-04-08',2),
       ('Fontaine','Jean','jeandlf@mail.com',date '1994-04-08',2);
INSERT INTO table_service (nom, prix_acces) VALUES
       ('television', 10),
       ('piscine', 18),
       ('salle de sport', 20),
       ('massage', 35),
       ('soins thermaux', 50);
INSERT INTO table_promotion (promotion, description) VALUES
       (10, '10% de promotion apres 10 nuits'),
       (25, '25% de promotion apres 20 nuits'),
       (40, '40% de promotion apres 30 nuits ou si anniversaire');
INSERT INTO table_reservation (id_client, id_chambre, date_reservation, date_debut, date_fin, nb_personnes, id_promotion) VALUES
       (2, 1, '2015-04-19 12:00:00', '2015-04-29 12:00:00', '2015-05-17 12:00:00', 1, NULL),
       (2, 1, '2015-05-01 12:00:00', CURRENT_TIMESTAMP, '2015-05-17 12:00:00', 1, NULL),
       (3, 3, '2014-02-20 12:00:00', '2015-03-01 12:00:00', '2015-03-08 12:00:00', 1, NULL),
       (3, 4, '2015-05-02 12:00:00', CURRENT_TIMESTAMP, '2015-07-02 12:00:00', 2, NULL),
       (1, 6, '2015-03-22 12:00:00', '2015-05-04 08:00:00', '2015-05-24 12:00:00', 2, NULL),
       (1, 6, '2015-05-20 12:00:00', '2015-06-01 12:00:00', '2015-07-07 12:00:00', 4, NULL),
       (2, 1, '2015-05-20 12:00:00', '2015-06-07 12:00:00', '2015-07-08 12:00:00', 1, NULL),
       (1, 2, '2015-06-01 12:00:00', '2015-06-11 12:00:00', '2015-07-06 12:00:00', 1, NULL),
       (4, 8, '2015-04-01 12:00:00', '2015-04-14 12:00:00', '2015-07-02 12:00:00', 4, NULL),
       (5, 12, '2015-07-01 12:00:00', '2015-07-27 12:00:00', '2015-08-01 12:00:00', 2, NULL),
       (5, 13, '2015-07-18 12:00:00', '2015-07-27 12:00:00', '2015-08-09 12:00:00', 7, NULL),
       (6, 14, '2015-04-23 12:00:00', CURRENT_TIMESTAMP, '2015-05-18 12:00:00', 4, NULL),
       (6, 15, '2015-07-18 12:00:00', '2015-05-27 12:00:00', '2015-07-12 12:00:00', 3, NULL),
       (7, 16, '2015-07-20 12:00:00', '2015-05-27 12:00:00', '2015-07-11 12:00:00', 5, NULL),
       (7, 17, '2015-04-25 12:00:00', CURRENT_TIMESTAMP, '2015-05-10 12:00:00', 6, NULL),
       (8, 17, '2015-05-14 12:00:00', '2015-06-27 12:00:00', '2015-07-17 12:00:00', 1, NULL),
       (8, 15, '2015-04-14 12:00:00', CURRENT_TIMESTAMP, '2015-06-19 12:00:00', 2, NULL),
       (9, 14, '2015-06-27 12:00:00', '2015-05-27 12:00:00', '2015-07-16 12:00:00', 4, NULL),
       (9, 12, '2015-04-11 12:00:00', CURRENT_TIMESTAMP, '2015-06-01 12:00:00', 3, NULL);
INSERT INTO relation_hotel_service (id_hotel, id_service) VALUES
       (1, 1),
       (2, 1), (2, 2),
       (3, 1), (3, 2), (3, 3), (3, 4), (3, 5),
       (4, 1), (4, 2), (4, 3), (4, 4), (4, 5),
       (5, 1), (5, 2), (5, 3), (5, 4);
INSERT INTO relation_reservation_service (id_reservation, id_service) VALUES
       (3, 1),
       (4, 1), (4, 2),
       (5, 2), (5, 4), (5, 5),
       (6, 1), (6, 2), (6, 4), (6, 5),
       (7, 1),
       (8, 1),
       (9, 1), (9, 2), (9, 3), (9, 4), (9, 5),
       (10, 1), (10, 2), (10, 3), (10, 4), (10, 5),
       (11, 1), (11, 2), (11, 3), (11, 4), (11, 5),
       (12, 1), (12, 2), (12, 3), (12, 4), (12, 5),
       (13, 1), (13, 2), (13, 3), (13, 4),
       (14, 1), (14, 2), (14, 4),
       (15, 1), (15, 2), (15, 4),
       (16, 2), (16, 3),
       (17, 1), (17, 2), (17, 3),
       (18, 2), (18, 5),
       (19, 1), (19, 2),(19, 4);
\echo -----FIN PARTIE INSERTION DANS LES TABLES-----






\echo -----DEBUT PARTIE PREPARE EXECUTE-----
\echo Function to execute : insertion_hotel(nom, adresse, code_postal, ville, pays, nb_etoiles, type_hotel);
PREPARE insertion_hotel(VARCHAR, VARCHAR, INTEGER, VARCHAR, VARCHAR, INTEGER, VARCHAR) AS
INSERT INTO table_hotel (nom, adresse, code_postal, ville, pays, nb_etoiles, type_hotel) VALUES ($1, $2, $3, $4, $5, $6, $7);

\echo Function to execute : insertion_reservation(id_client, id_chambre, date_reservation, date_debut, date_fin, nb_personnes, id_promotion);
PREPARE insertion_reservation(INTEGER, INTEGER, TIMESTAMP, TIMESTAMP, TIMESTAMP, INTEGER, INTEGER) AS
INSERT INTO table_reservation (id_client, id_chambre, date_reservation, date_debut, date_fin, nb_personnes, id_promotion) VALUES
       ($1, $2, $3, $4, $5, $6, $7);

\echo Function to execute : insertion_relation_hotel_service(id_service);
PREPARE insertion_relation_hotel_service(INTEGER) AS
INSERT INTO relation_hotel_service (id_hotel, id_service) VALUES (currval('table_hotel_id_hotel_seq'), $1);


\echo Function to execute : insertion_relation_reservation_service(id_service);
PREPARE insertion_relation_reservation_service(INTEGER) AS
INSERT INTO relation_reservation_service (id_reservation, id_service) VALUES (currval('table_reservation_id_reservation_seq'), $1);

\echo Function to execute : insertion_chambre(id_hotel, categorie, prix_nuit);
PREPARE insertion_chambre(INTEGER, categorieEnum, INTEGER) AS
INSERT INTO table_chambre (id_hotel, categorie, prix_nuit) VALUES ($1, $2, $3);

\echo Function to execute : insertion_client(nom, prenom, email, date_naissance, nuits_reservees_annee);
PREPARE insertion_client(VARCHAR, VARCHAR, VARCHAR, DATE, INTEGER) AS
INSERT INTO table_client (nom, prenom, email, date_naissance, nuits_reservees_annee) VALUES ($1, $2, $3, $4, $5);

\echo Function to execute : insertion_service(nom, prix_acces);
PREPARE insertion_service(VARCHAR, INTEGER) AS
INSERT INTO table_service (nom, prix_acces) VALUES ($1, $2);

\echo Function to execute : insertion_promotion(promotion, description);
PREPARE insertion_promotion(INTEGER, VARCHAR) AS
INSERT INTO table_promotion (promotion, description) VALUES ($1, $2);
\echo -----FIN PARTIE PREPARE EXECUTE-----
