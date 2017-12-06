#include <iostream>
#include "Jeu.hpp"
using namespace std;

/***** CLASSE CONCRETE ET NON PLUS ABSTRAITE *****/


static int idJoueur = 0;
static int joueursHumainsCrees = 0;
static int joueursRobotsCrees = 0;

/********** DEBUT : CONSTRUCTEURS / DESTRUCTEURS **********/
Jeu::Jeu(): nomJeuOuVariante(""), nbJoueursHumains(0), nbJoueursTotal(0), nbPionsParJoueur(0), tableauJoueurs(nullptr) {
  cout << "Construction d'un Jeu" << endl;
}

Jeu::Jeu(string nom, int nbHumains, int nbTotal, int nbPions):
  nomJeuOuVariante(nom), nbJoueursHumains(nbHumains), nbJoueursTotal(nbTotal), nbPionsParJoueur(nbPions) {
  
  tableauJoueurs = new Joueur*[nbJoueursTotal];
  for(int i=0 ; i<nbJoueursTotal ; i++) {
    tableauJoueurs[i] = creationJoueur();
  }

  cout << "Construction d'un Jeu" << endl;
}

Jeu::Jeu(string nom, int nbHumains, int nbTotal, int nbPions, Joueur** tab):
  nomJeuOuVariante(nom), nbJoueursHumains(nbHumains), nbJoueursTotal(nbTotal), nbPionsParJoueur(nbPions), tableauJoueurs(tab) {
  cout << "Construction d'un Jeu" << endl;
}

Jeu::~Jeu() { cout << "Destruction d'un Jeu" << endl; }
/********** FIN : CONSTRUCTEURS / DESTRUCTEURS **********/




/********** DEBUT : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/
string Jeu::getNomJeuOuVariante() { return nomJeuOuVariante; }
int Jeu::getNbJoueursHumains() { return nbJoueursHumains; }
int Jeu::getNbJoueursTotal() { return nbJoueursTotal; }
int Jeu::getNbPionsParJoueur() { return nbPionsParJoueur; }
Joueur** Jeu::getTableauJoueurs() { return tableauJoueurs; }

void Jeu::setNomJeuOuVariante(string nom) { nomJeuOuVariante = nom; }
void Jeu::setNbJoueursHumains(int nb) { nbJoueursHumains = nb; }
void Jeu::setNbJoueursTotal(int nb) { nbJoueursTotal = nb; }
void Jeu::setNbPionsParJoueur(int nb) { nbPionsParJoueur = nb; }
void Jeu::setTableauJoueurs(Joueur** tab) { tableauJoueurs = tab; } //=========================> marche avec l'histoire de pointeur en argument ???

Joueur* Jeu::operator[](int i) { return tableauJoueurs[i]; }
/********** FIN : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/




/********** DEBUT : FONCTIONS SUPPLEMENTAIRES **********/
int Jeu::getScoreJoueur(int joueur) { return tableauJoueurs[joueur]->getScore(); }

void Jeu::getScores() {
  for(int i=0 ; i<nbJoueursTotal ; i++)
    cout << "Score joueur " << i << ": " << tableauJoueurs[i]->getScore() << endl;
}

Joueur* Jeu::creationJoueur() {
  cout << "--- Creation de joueur ---" << endl;
  string nom;
  bool estHumain = false;
  if(joueursHumainsCrees < nbJoueursHumains) {
    cout << "Entrez le nom du joueur :" << endl;
    cin >> nom;
    joueursHumainsCrees++;
    estHumain = true;
  }
  else {
    switch(joueursRobotsCrees++) {
    case 0:
      nom = NOM_1;
      break;
    case 1:
      nom = NOM_2;
      break;
    case 2:
      nom = NOM_3;
      break;
    case 3:
      nom = NOM_4;
      break;
    default:
      nom = NOM_1;
      break;
    }
  }

  int score = 0;

  string couleur;
  if(idJoueur == 0) couleur = ROUGE;
  else if(idJoueur == 1) couleur = BLEU;
  else if(idJoueur == 2) couleur = VERT;
  else couleur = JAUNE; // if(idJoueur == 3)
  idJoueur++;
  
  int idPion = 0;
  Pion** tab = new Pion*[nbPionsParJoueur];
  for(int i=0 ; i<nbPionsParJoueur ; i++) {
    tab[i] = new Pion(idJoueur, idPion++, couleur, 0);
  }
  return new Joueur(estHumain, idJoueur, nom, score, nbPionsParJoueur, tab);
}
/********** FIN : FONCTIONS SUPPLEMENTAIRES **********/
