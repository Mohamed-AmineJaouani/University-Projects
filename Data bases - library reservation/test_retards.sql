\i create_all.sql
\echo
\echo
\echo
\echo TEST RETARDS

SELECT emprunter(1,1);
SELECT emprunter(2,1);
SELECT emprunter(3,1);

\echo SELECT * FROM PRET;
SELECT * FROM PRET;

\echo
\echo
\echo

\echo On change la date courante -> dernier jour d emprunt

UPDATE CONSTANTES SET date_courante = date_courante + delai_emprunt;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;

SELECT * FROM UTILISATEUR;

SELECT emprunter(4,1);

SELECT rendre(1,1);
SELECT rendre(2,1);
SELECT rendre(3,1);

SELECT emprunter(4,1);

SELECT acquitter(1);

SELECT emprunter(4,1);
