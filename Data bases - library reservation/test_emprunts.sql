\i create_all.sql
\echo
\echo
\echo

\echo ****** TEST REEMPRUNTER ET RESERVER LE MEME DOCUMENT PAR LE MEME UTILISATEUR ******
\echo

\echo L utilisateur 1 emprunte le document 1
SELECT emprunter(1,1);
\echo

SELECT * FROM PRET;
\echo

\echo L utilisateur 1 réemprunte le document 1
SELECT emprunter(1,1);
\echo

\echo L utilisateur 1 essaye de réserver le document 1 qu il vient d emprunter
SELECT reserver(1,1,NULL);
\echo

\echo ****** FIN  TEST REEMPRUNTER ET RESERVER LE MEME DOCUMENT PAR LE MEME UTILISATEUR ******
\echo


\echo L utilisateur 1 essaye de réserver le document 2 qui n est ni emprunté ni réservé
SELECT reserver(2,1,NULL);
\echo

\echo L utilisateur 2 essaye d emprunter le document 1 qui est déjà emprunté par l utilisateur 1
SELECT emprunter(1,2);
\echo

\echo L utilisateur 5 qui a un type d inscription normal essaye d emprunter le document 36 qui est un CD 
SELECT emprunter(42,5);
\echo

\echo L utilisateur 2 qui a un type d inscription CD essaye d emprunter le document 41 qui est un DVD
SELECT emprunter(41,2);
\echo


\echo SELECT * FROM PRET;
SELECT * FROM PRET;

\echo L utilisateur 1 renouvelle son emprunt, la date de fin est reporté
SELECT renouveler_emprunt(1,1);
\echo

\echo SELECT * FROM PRET;
SELECT * FROM PRET;
\echo L utilisateur 1 renouvelle son emprunt, la date de fin est reporté
SELECT renouveler_emprunt(1,1);
\echo

\echo SELECT * FROM PRET;
SELECT * FROM PRET;
\echo L utilisateur 1 renouvelle son emprunt, mais echoue car on ne peut renouveller que 2 (max_renouvellement) fois
SELECT renouveler_emprunt(1,1);
\echo


\echo L utilisateur 2 emprunte le document 2
SELECT emprunter(2,2);

\echo L utilisateur 3 reserve le document 2
SELECT reserver(2,3,NULL);
\echo

\echo SELECT * FROM PRET;
SELECT * FROM PRET;
\echo

\echo L utilisateur veut renouveller son emprunt mais il est déjà réservé par quelqu un d autre
SELECT renouveler_emprunt(2,2);
\echo

\echo L utilisateur 3 va emprunter 5 documents que l utilisateur 4 va essayer de réserver mais la limite est à 5 
SELECT emprunter(3,3);
SELECT emprunter(4,3);
SELECT emprunter(5,3);
SELECT emprunter(6,3);
SELECT emprunter(7,3);

SELECT reserver(1,4,NULL);
SELECT reserver(3,4,NULL);
SELECT reserver(4,4,NULL);
SELECT reserver(5,4,NULL);
SELECT reserver(6,4,NULL);
SELECT reserver(7,4,NULL);

\echo
\echo On change la date courante afin que l utilisateur 1 ait des documents en retard puis il essaye de d emprunter et réserver un document
\echo
UPDATE CONSTANTES SET date_courante = DATE(NOW()) + 120;
SELECT date_courante FROM CONSTANTES;

\echo

SELECT emprunter(10,1);
\echo
SELECT reserver(10,1,NULL);
\echo

\echo SELECT * FROM PRET;
SELECT * FROM PRET;

\echo L utilisateur 1 va rendre son document en retard puis essayer d emprunter puis reserver un document
\echo

SELECT rendre(1,1);
\echo

\echo SELECT * FROM PRET;
SELECT * FROM PRET;

SELECT emprunter(16,1);
SELECT reserver(7,1,NULL);
