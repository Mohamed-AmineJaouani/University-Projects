\i create_all.sql
\echo
\echo
\echo
\echo L utilisateur 3 va emprunter plus de 20 documents dans la meme biblioheque puis plus de 40 documents dans des bibliothèques différentes

SELECT emprunter(1,3);
SELECT emprunter(2,3);
SELECT emprunter(3,3);
SELECT emprunter(4,3);
SELECT emprunter(5,3);


SELECT emprunter(16,3);
SELECT emprunter(17,3);
SELECT emprunter(18,3);
SELECT emprunter(19,3);
SELECT emprunter(20,3);

SELECT emprunter(31,3);
SELECT emprunter(32,3);
SELECT emprunter(33,3);
SELECT emprunter(34,3);
SELECT emprunter(35,3);

SELECT emprunter(46,3);
SELECT emprunter(47,3);
SELECT emprunter(48,3);
SELECT emprunter(49,3);
SELECT emprunter(50,3);

SELECT emprunter(56,3); -- le livre de trop dans la meme bibliothèque


SELECT emprunter(6,3);
SELECT emprunter(7,3);
SELECT emprunter(8,3);
SELECT emprunter(9,3);
SELECT emprunter(10,3);
SELECT emprunter(11,3);
SELECT emprunter(12,3);
SELECT emprunter(13,3);
SELECT emprunter(14,3);
SELECT emprunter(15,3);

SELECT emprunter(36,3);
SELECT emprunter(37,3);
SELECT emprunter(38,3);
SELECT emprunter(39,3);
SELECT emprunter(40,3);

SELECT emprunter(51,3);
SELECT emprunter(52,3);
SELECT emprunter(53,3);
SELECT emprunter(54,3);
SELECT emprunter(55,3);

SELECT emprunter(71,3); --le 2eme livre de trop


\echo
\echo
\echo
\echo
\echo
\echo L utilisateur 4 va emprunter 4 nouveauté puis un document qui n est pas une nouveauté
\echo

SELECT emprunter(71,4);
SELECT emprunter(72,4);
SELECT emprunter(73,4);
SELECT emprunter(74,4); 
\echo
SELECT emprunter(21,4);
