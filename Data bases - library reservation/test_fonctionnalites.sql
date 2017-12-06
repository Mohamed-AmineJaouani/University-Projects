\i create_all.sql
\echo TEST FONCTIONNALITES


\echo
\echo

\echo TEST UN LIVRE DOIT ETRE RENDU DANS DEUX JOURS
\echo

\echo L UTILISATEUR 1 EMPRUNTE LE DOCUMENT 1
SELECT emprunter(1,1);
\echo

\echo ON CHANGE LA DATE AU LENDEMAIN
UPDATE CONSTANTES SET date_courante = date_courante + 1;

\echo L UTILISATEUR 2 EMPRUNTE LE DOCUMENT 2, L UTILISATEUR 3 EMPRUNTE LE DOCUMENT 3

SELECT emprunter(36,2);
SELECT emprunter(21,3);

\echo SELECT * FROM PRET;
SELECT * FROM PRET;

\echo ON CHANGE LA DATE COURANTE A 3 JOURS AVANT LA DATE DE RENDU PREVU DU PREMIER DOCUMENT
UPDATE CONSTANTES SET date_courante = date_courante + delai_emprunt - 4;

UPDATE CONSTANTES SET date_courante = date_courante + 1;
UPDATE CONSTANTES SET date_courante = date_courante + 1;


\echo

SELECT reserver(1, 4, NULL);

SELECT * FROM PRET;

SELECT reserver(1, 5, NULL);

SELECT * FROM PRET;

SELECT reserver(1, 6, NULL);

SELECT * FROM PRET;

\echo UN LIVRE RESERVE VIENT D ETRE RENDU
SELECT rendre (1,1);


\echo EMPRUNTS POUR LES STATISTIQUES 

SELECT emprunter(46,3);
SELECT emprunter(41,3);
SELECT emprunter(42,4);
SELECT emprunter(43,4);

\echo FIN EMPRUNTS POUR LES STATISQUES

\echo
\echo STATISTIQUES
\echo
\echo

\echo SELECT * FROM PRET;

SELECT * FROM PRET;

UPDATE CONSTANTES SET date_courante = date_courante + 30;

\echo EMPRUNTS LE MOIS DERNIER
\echo
SELECT stat_emprunts_mois_dernier();

\echo POURCENTAGE DE LIVRES EMPRUNTES
\echo
SELECT stat_pourcentage_livre_emprunte();

\echo
\echo


\echo
\echo
\echo INSCRIRE UN USAGER ET RENOUVELLER SON INSCRIPTION

\echo INSCRIPTION D UN NOUVEL USAGER
SELECT inscription('SHX', 'SHEXXS', 28, 'shexxs@gmail.com', 3);

SELECT * FROM UTILISATEUR;

\echo RENOUVELLEMENT DE L UTILISATEUR 6 | NORMAL -> CD

SELECT renouvellement(6,2);

SELECT * FROM UTILISATEUR;
\echo RENOUVELLEMENT DE L UTILISATEUR 2 | CD -> DVD

SELECT renouvellement(2,3);

SELECT * FROM UTILISATEUR;

SELECT emprunter(67,6);

\echo 
\echo RECHERCHER UN DOCUMENT SELON LE TITRE OU L AUTEUR ET AFFICHER SA DISPONIBILITE
\echo

\echo SELECT date_courante FROM CONSTANTES;
SELECT date_courante FROM CONSTANTES;

SELECT * FROM PRET;
\echo le petit chaperon rouge
SELECT rechercher_titre('le petit chaperon rouge');

\echo
\echo cyrano
SELECT rechercher_titre('cyrano');

\echo
\echo oda
SELECT rechercher_auteur('oda');

\echo
\echo one
SELECT rechercher_titre('one');

\echo
\echo manga
SELECT rechercher_genre('manga');

\echo
\echo conte
SELECT rechercher_genre('conte');

\echo
\echo fr
SELECT rechercher_langue('fr');

\echo
\echo jap
SELECT rechercher_langue('jap');



\echo MODIFICATION DE LA DATE COURANTE POUR ETRE A LA FIN DE L INSCRIPTION DE L UTILISATEUR 1
UPDATE CONSTANTES SET date_courante = DATE(NOW()) + 355;

\echo SELECT date_courante FROM CONSTANTES;
SELECT date_courante FROM CONSTANTES;

\echo L UTILISATEUR 1 VEUT EMPRUNTER LE DOCUMENT 2 MAIS IL ARRIVE AU TERME DE SON INSCRIPTION
SELECT emprunter(2,1);
SELECT * FROM PRET;

\echo L UTILISATEUR 1 VEUT RESERVER LE DOCUMENT 1 MAIS IL ARRIVE AU TERME DE SON INSCRIPTION
SELECT reserver(1,1,NULL);
SELECT * FROM PRET;


\echo
\echo

\echo EMPRUNTER ET RETOURNER UN DOCUMENT

\echo UN UTILISATEUR REND UN DOCUMENT NON EMPRUNTÉ

SELECT rendre(5,5);

\echo UN UTILISATEUR REND UN DOCUMENT 

SELECT rendre(36,2);

\echo

SELECT * FROM PRET;

\echo ON CHANGE LA DATE TEL QUE CERTAINS EMPRUNTS SOIENT VIEUX DE 2 MOIS ET RENDU
UPDATE CONSTANTES SET date_courante = date_courante + 130;

SELECT clear_emprunts();

SELECT * FROM PRET;

SELECT rendre(46,3);
SELECT rendre(41,3);
SELECT rendre(42,4);
SELECT rendre(43,4);
SELECT rendre(21,3);
SELECT rendre(1,4);
SELECT rendre(1,5);

SELECT clear_emprunts();

SELECT * FROM PRET;

\echo on remet la date courante a aujourd hui

UPDATE CONSTANTES SET date_courante = DATE(NOW());

\echo l utilisateur 3 emprunte le document 2
SELECT emprunter(2,3);

\echo l utilisateur 4 reserve le document 2 aujourdhui qui commence 30 jours plus tard
SELECT reserver(2,4, DATE(NOW()) + 30);

\echo l utilisateur 5 réserve le document 2 mais le delai de cet réservation en chevauche une autre
SELECT reserver(2,5, DATE(NOW()) + 25);

SELECT * FROM PRET;

\echo on efface les prets pour le prochain test
\i clear_prets.sql

\echo l utilisateur 3 emprunte le document 2
SELECT emprunter(2,3);

\echo l utilisateur 4 réserve le document 2 aujourdhui qui commence dans 70 jours
SELECT reserver(2,4, DATE(NOW()) + 70);

\echo l utilisateur 5 réserve le document 2 entre l emprunt et la réservation et le delai le permet
SELECT reserver(2,5, DATE(NOW()) + 30);
