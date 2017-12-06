#include <iostream>
#include "Constantes.hpp"
#include "Plateau.hpp"
#include "Jeu.hpp"
#include "../jeux/EchelleSerpent/EchelleSerpent.hpp"
using namespace std;



/* ??CREER UNE FONCTION DE PARAMETRAGE POUR ALLEGER LE MAIN (cette fonction reprend toutes les entrees clavier (cin))
** PUIS INSTANCIER LES OBJETS DANS LE MAIN */

/* ??OU CREER UNE FONCTION DE PARAMETRAGE ET INSTANCIER DIRECTEMENT LES OBJETS DEDANS A L'AIDE DE REFERENCES (qui seront donnees par le main) */


int main() {
  int choixJeu, nbJoueursHumains, nbJoueursDisponibles, nbJoueursRobots, nbJoueursTotal, nbPionsParJoueur, nbLignesPlateau, nbColonnesPlateau, nbCasesPlateau, nbEchelles, nbSerpents, nbCasesOranges, nbCasesVertes, nbCasesOrangesVertesDispo, difficultePedagogique;

  Jeu* jeu;
  




  /****** DEBUT : CHOIX DU JEU  ******/
  cout << "Choix du jeu :\n" << LISTE_DES_JEUX << endl;
  cin >> choixJeu;
  cin.clear(); // A securiser encore plus si time
  /****** FIN : CHOIX DU JEU  ******/






  /****** DEBUT : CHOIX DU NOMBRE DE JOUEURS HUMAINS  ******/
  cout << "Nombre de joueurs humains (4 max.) :" << endl;
  cin >> nbJoueursHumains;
  cin.clear(); // A securiser encore plus si time

  switch(nbJoueursHumains) {
  case 0:
    nbJoueursDisponibles = NB_JOUEURS_MAX - 0;
    break;
  case 1:
    nbJoueursDisponibles = NB_JOUEURS_MAX - 1;
    break;
  case 2:
    nbJoueursDisponibles = NB_JOUEURS_MAX - 2;
    break;
  case 3:
    nbJoueursDisponibles = NB_JOUEURS_MAX - 3;
    break;
  case 4:
    nbJoueursDisponibles = NB_JOUEURS_MAX - 4;
    break;
  default:
    cout << "[main.cpp] : Erreur sur le nombre de joueurs" << endl;
    exit(EXIT_FAILURE);
  }
  /****** FIN : CHOIX DU NOMBRE DE JOUEURS HUMAINS  ******/






  /****** DEBUT : CHOIX DU NOMBRE DE JOUEURS ROBOTS  ******/
  cout << "Nombre de joueurs robots (" << nbJoueursDisponibles << " disponible(s)) :" << endl;
  cin >> nbJoueursRobots;
  cin.clear(); // A securiser encore plus si time

  switch(nbJoueursRobots) {
  case 0:
  case 1:
  case 2:
  case 3:
  case 4:
    nbJoueursTotal = nbJoueursHumains + nbJoueursRobots;
    cout << "Il y a " << nbJoueursTotal << " joueurs (" << nbJoueursHumains << " humains et " << nbJoueursRobots << " robots)" << endl;
    break;
  default:
    cout << "[main.cpp] : Erreur sur le nombre de joueurs" << endl;
    exit(EXIT_FAILURE);
  }
  /****** FIN : CHOIX DU NOMBRE DE JOUEURS ROBOTS  ******/






  /****** DEBUT : CHOIX DE LA TAILLE DU PLATEAU ******/
  /**** DEBUT : CHOIX DU NOMBRE DE LIGNES ****/
  do {
    nbLignesPlateau = 0;
    cout << "Taille du plateau > nombre de lignes (entre 4 et 10) :" << endl;
    cin >> nbLignesPlateau;
    cin.clear(); // A securiser encore plus si time
  } while(nbLignesPlateau < 4 || 10 < nbLignesPlateau);
  /**** FIN : CHOIX DU NOMBRE DE LIGNES ****/


  /**** DEBUT : CHOIX DU NOMBRE DE COLONNES ****/
  do {
    nbColonnesPlateau = 0;
    cout << "Taille du plateau > nombre de colonnes (entre 4 et 10) :" << endl;
    cin >> nbColonnesPlateau;
    cin.clear(); // A securiser encore plus si time
  } while(nbColonnesPlateau < 4 || 10 < nbColonnesPlateau);
  /**** FIN : CHOIX DU NOMBRE DE COLONNES ****/

  nbCasesPlateau = nbLignesPlateau * nbColonnesPlateau;
  /****** FIN : CHOIX DE LA TAILLE DU PLATEAU ******/






  /****** DEBUT : RELEVE DES PARAMETRES POUR LES EchelleSerpent...  ******/
  switch(choixJeu) {
  case 1:
    /**** DEBUT : CHOIX DU NOMBRE D'ECHELLES (et non pas de caseEchelles car cela est deja pris en compte EchelleSerpent) ****/
    do {
      nbEchelles = 0;
      cout << "Nombre d'echelles (entre 0 et 4) :" << endl;
      cin >> nbEchelles;
      cin.clear(); // A securiser encore plus si time
    } while(nbEchelles < 0 || 4 < nbEchelles);
    /**** FIN : CHOIX DU NOMBRE D'ECHELLES ****/

    /**** DEBUT : CHOIX DU NOMBRE DE SERPENTS ****/
    do {
      nbSerpents = 0;
      cout << "Nombre de serpents (entre 0 et 4) :" << endl;
      cin >> nbSerpents;
      cin.clear(); // A securiser encore plus si time
    } while(nbSerpents < 0 || 4 < nbSerpents);
    /**** FIN : CHOIX DU NOMBRE DE SERPENTS ****/

    break;


  case 2:
  case 3:
  case 4:
    /**** DEBUT : CHOIX DU NOMBRE D'ECHELLES (et non pas de caseEchelles car cela est deja pris en compte EchelleSerpent) ****/
    do {
      nbEchelles = 0;
      cout << "Nombre d'echelles (entre 0 et 4) :" << endl;
      cin >> nbEchelles;
      cin.clear(); // A securiser encore plus si time
    } while(nbEchelles < 0 || 4 < nbEchelles);
    /**** FIN : CHOIX DU NOMBRE D'ECHELLES ****/

    /**** DEBUT : CHOIX DU NOMBRE DE SERPENTS ****/
    do {
      nbSerpents = 0;
      cout << "Nombre de serpents (entre 0 et 4) :" << endl;
      cin >> nbSerpents;
      cin.clear(); // A securiser encore plus si time
    } while(nbSerpents < 0 || 4 < nbSerpents);
    /**** FIN : CHOIX DU NOMBRE DE SERPENTS ****/


    /**** DEBUT : CHOIX DU NOMBRE DE CASES ORANGES ****/
    int nbCasesOrangesVertesDispo = 0;
    if(nbEchelles + nbSerpents == 0)
      nbCasesOrangesVertesDispo = nbCasesPlateau/2;
    else
      nbCasesOrangesVertesDispo = nbCasesPlateau/2 - (nbEchelles+nbSerpents)/2;
    do {
      nbCasesOranges = 0;
      cout << "Nombre de cases oranges (" << nbCasesOrangesVertesDispo/2 << "dispo.) :" << endl;
      cin >> nbCasesOranges;
      cin.clear(); // A securiser encore plus si time
    } while(nbCasesOranges < 0 || nbCasesOrangesVertesDispo/2 < nbCasesOranges);
    /**** FIN : CHOIX DU NOMBRE DE CASES ORANGES ****/

    /**** DEBUT : CHOIX DU NOMBRE DE CASES VERTES ****/
    do {
      nbCasesVertes = 0;
      cout << "Nombre de cases vertes (" << nbCasesOrangesVertesDispo/2 << "dispo.) :" << endl;
      cin >> nbCasesVertes;
      cin.clear(); // A securiser encore plus si time
    } while(nbCasesVertes < 0 || nbCasesOrangesVertesDispo/2 < nbCasesVertes);
    /**** FIN : CHOIX DU NOMBRE DE CASES VERTES ****/

    break;
  }
  /****** FIN : RELEVE DES PARAMETRES POUR LES EchelleSerpent...  ******/





  /****** DEBUT : RELEVE DES PARAMETRES POUR LES JEUX A PLUSIEURS PIONS  ******/
  switch(choixJeu) {
  case 4:
  case 5:
  case 6:
    /****** DEBUT : CHOIX DU NOMBRE DE PIONS PAR JOUEUR ******/
    do {
      nbPionsParJoueur = 0;
      cout << "Nombre de pions par joueur (de 2 à 4 max.) :" << endl;
      cin >> nbPionsParJoueur;
      cin.clear(); // A securiser encore plus si time
    } while(nbPionsParJoueur < 2 || 4 < nbPionsParJoueur);
    /****** FIN : CHOIX DU NOMBRE DE PIONS PAR JOUEUR ******/
    break;
  }
  /****** DEBUT : RELEVE DES PARAMETRES POUR LES JEUX A PLUSIEURS PIONS  ******/



  /****** DEBUT : CREATION DU JEU  ******/
  switch(choixJeu) {
  case 1:  // EchelleSerpentStandard

    jeu = new EchelleSerpent(ECHELLE_SERPENT, nbJoueursHumains, nbJoueursTotal, 1, nbCasesPlateau, nbEchelles, nbSerpents, 0, 0);
    break;

  case 2: // EchelleSerpentOrangeVerte

    jeu = new EchelleSerpent(ECHELLE_SERPENT_ORANGE_VERTE, nbJoueursHumains, nbJoueursTotal, 1, nbCasesPlateau, nbEchelles, nbSerpents, nbCasesOranges, nbCasesVertes);
    break;

  case 3: // EchelleSerpentPedagogique
    /**** DEBUT : CHOIX DE LA DIFFICULTE DES QUESTIONS ****
    do {
      difficultePedagogique = 0;
      cout << "Niveau de difficulté (Facile = 1, Moyen = 2, Difficile = 3) :" << endl;
      cin >> difficultePedagogique;
      cin.clear(); // A securiser encore plus si time
    } while(difficultePedagogique < 1 || 3 < difficultePedagogique);
    /**** FIN : CHOIX DE LA DIFFICULTE DES QUESTIONS ****/

    jeu = new EchelleSerpent(ECHELLE_SERPENT_PEDAGOGIQUE, nbJoueursHumains, nbJoueursTotal, 1, nbCasesPlateau, nbEchelles, nbSerpents, nbCasesOranges, nbCasesVertes);
    break;


  case 4:  // EchelleSerpentPlusieursPions

    jeu = new EchelleSerpent(ECHELLE_SERPENT_PLUSIEURS_PIONS, nbJoueursHumains, nbJoueursTotal, nbPionsParJoueur, nbCasesPlateau, nbEchelles, nbSerpents, nbCasesOranges, nbCasesVertes);
    break;



  case 5: // Cartagena
    //jeu = new Cartagena(CARTAGENA, nbJoueursHumains, nbJoueursTotal, ...);
    break;



  case 6: // Numeri
    //jeu = new Numeri(NUMERI, nbJoueursHumains, nbJoueursTotal, ...);
    break;


  default:
    cout << "[main.cpp] : Erreur sur le choix du jeu" << endl;
    exit(EXIT_FAILURE);
  }
  /****** FIN : CREATION DU JEU  ******/






  /****** DEBUT : CREATION DU PLATEAU ET LANCEMENT DU JEU ******/
  Plateau p(jeu, nbLignesPlateau, nbColonnesPlateau);

  p.lancer();
  /****** FIN : CREATION DU PLATEAU ET LANCEMENT DU JEU ******/

  return 0;
}
