#ifndef CONSTANTES_HPP
#define CONSTANTES_HPP
#include <iostream>
using namespace std;

/********** DEBUT : LISTE ET NOM DES JEUX  **********/
// Used in "main.cpp"
const string LISTE_DES_JEUX = "1. Serpent-Echelle \n2. Serpent-Echelle (variante cases bonus/malus) \n3. Serpent-Echelle (variante pedagogique) \n4. Serpent-Echelle (variante a plusieurs pions) \n5. Cartagena (variante) \n6. Numeri";
const string ECHELLE_SERPENT = "EchelleSerpent";
const string ECHELLE_SERPENT_ORANGE_VERTE = "EchelleSerpentOrangeVerte";
const string ECHELLE_SERPENT_PEDAGOGIQUE = "EchelleSerpentPedagogique";
const string ECHELLE_SERPENT_PLUSIEURS_PIONS = "EchelleSerpentPlusieursPions";
const string CARTAGENA = "Cartagena";
const string NUMERI = "Numeri";
/********** FIN : LISTE ET NOM DES JEUX  **********/




/********** DEBUT : PARAMETRE DE JEU  **********/
// Used in "main.cpp", "Case.hpp", "Case.cpp"
const int NB_JOUEURS_MAX = 4;

// Used in "Plateau.cpp"
const int DELAY_ROBOT = 2;
const int DELAY_DE = 1;

const int DIFFICULTE_FACILE = 1;
const int DIFFICULTE_MOYENNE = 10;
const int DIFFICULTE_DIFFICILE = 100;
/********** FIN : PARAMETRE DE JEU  **********/




/********** DEBUT : ECHELLE_SERPENT CONSTANTES POUR LES VARIANTES  **********/
// Used in "../jeux/EchelleSerpent/CaseEchelleSerpent.hpp"
const int NB_SPECIFICITES_ES = 2; // Echelle + Serpent
const int NB_SPECIFICITES_ESOV = 4; // Echelle + Serpent + Orange + Verte
const int NEUTRE = 0;
const int ECHELLE = 1;
const int SERPENT = 2;
/*const int ECHELLE_BAS = 1;
const int ECHELLE_HAUT = 2;
const int SERPENT_QUEUE = 3;
const int SERPENT_TETE = 4;*/

const int ORANGE = 3;
const int VERTE = 4;


// Used in "../jeux/EchelleSerpent/ObjetEchelleSerpent.hpp"
const string TYPE_ECHELLE_BAS = "TypeEchelleBas";
const string TYPE_ECHELLE_HAUT = "TypeEchelleHaut";
const string TYPE_SERPENT_QUEUE = "TypeSerpentQueue";
const string TYPE_SERPENT_TETE = "TypeSerpentTete";
/********** DEBUT : ECHELLE_SERPENT CONSTANTES POUR LES VARIANTES  **********/




/********** DEBUT : OPTIONS DISPENSABLES DE JEU  **********/
// Used in "Jeu.cpp" pour la creation des pions lors de la creation des joueurs
const string NOIR = "\033[1;30m";
const string ROUGE = "\033[1;31m";
const string VERT = "\033[1;32m";
const string JAUNE = "\033[1;33m";
const string BLEU = "\033[1;34m";
const string MAGENTA = "\033[1;35m";
const string CYAN = "\033[1;36m";
const string BLANC = "\033[1;37m";
const string SUFFIXE_COULEUR = "\033[0m";

const string COULEUR_1 = "31"; // ROUGE
const string COULEUR_2 = "34"; // BLEU
const string COULEUR_3 = "32"; // VERT
const string COULEUR_4 = "33"; // JAUNE
const string COULEUR_5 = "35"; // MAGENTA
const string COULEUR_6 = "36"; // CYAN
const string COULEUR_7 = "37"; // BLANC
const string COULEUR_8 = "30"; // NOIR

// Used in "Joueur.hpp" pour les noms des robots
const string NOM_1 = "Picasso";
const string NOM_2 = "Saitama";
const string NOM_3 = "KitKat";
const string NOM_4 = "Malabar";
const string NOM_DEFAULT = "M&M's";
/********** FIN : OPTIONS DISPENSABLES DE JEU  **********/

#endif //CONSTANTES_HPP
