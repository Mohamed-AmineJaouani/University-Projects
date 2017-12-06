#include <iostream>
#include "EchelleSerpent.hpp"
using namespace std;


/********** DEBUT : CONSTRUCTEURS / DESTRUCTEURS **********/
EchelleSerpent::EchelleSerpent(string nom, int nbHumains, int nbTotal, int nbPionsParJoueur, int nbCP, int nbE, int nbS, int nbO, int nbV):
  Jeu(nom, nbHumains, nbTotal, nbPionsParJoueur), nbCasesPlateau(nbCP), nbCasesEchelles(2*nbE), nbCasesSerpents(2*nbS), nbCasesOranges(nbO), nbCasesVertes(nbV) {
  nbCasesNonNeutres = nbCasesEchelles + nbCasesSerpents + nbCasesOranges + nbCasesVertes;
  nbCasesPlateauRestantes = nbCP;
  nbCasesEchellesRestantes = nbCasesEchelles;
  nbCasesSerpentsRestantes = nbCasesSerpents;
  nbCasesOrangesRestantes = nbCasesOranges;
  nbCasesVertesRestantes = nbCasesVertes;
  cout << "Construction d'un jeu EchelleSerpent" << endl;
}

EchelleSerpent::EchelleSerpent(string nom, int nbHumains, int nbTotal, int nbPionsParJoueur, Joueur** tab, int nbCP, int nbE, int nbS, int nbO, int nbV):
  Jeu(nom, nbHumains, nbTotal, nbPionsParJoueur, tab), nbCasesPlateau(nbCP), nbCasesEchelles(2*nbE), nbCasesSerpents(2*nbS), nbCasesOranges(nbO), nbCasesVertes(nbV)  {
  nbCasesNonNeutres = nbCasesEchelles + nbCasesSerpents + nbCasesOranges + nbCasesVertes;
  nbCasesPlateauRestantes = nbCP;
  nbCasesEchellesRestantes = nbCasesEchelles;
  nbCasesSerpentsRestantes = nbCasesSerpents;
  nbCasesOrangesRestantes = nbCasesOranges;
  nbCasesVertesRestantes = nbCasesVertes;
  cout << "Construction d'un jeu EchelleSerpent" << endl;
}

EchelleSerpent::~EchelleSerpent() {
  cout << "Destruction d'un jeu EchelleSerpent" << endl;
}
/********** FIN : CONSTRUCTEURS / DESTRUCTEURS **********/




/********** DEBUT : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/
int EchelleSerpent::getNbCasesEchelles() { return nbCasesEchelles; }
int EchelleSerpent::getNbCasesSerpents() { return nbCasesSerpents; }
int EchelleSerpent::getNbCasesOranges() { return nbCasesOranges; }
int EchelleSerpent::getNbCasesVertes() { return nbCasesVertes; }
int EchelleSerpent::getNbCasesNonNeutres() { return nbCasesNonNeutres; }
int EchelleSerpent::getNbCasesPlateauRestantes() { return nbCasesPlateauRestantes; }
int EchelleSerpent::getNbCasesEchellesRestantes() { return nbCasesEchellesRestantes; }
int EchelleSerpent::getNbCasesSerpentsRestantes() { return nbCasesSerpentsRestantes; }
int EchelleSerpent::getNbCasesOrangesRestantes() { return nbCasesOrangesRestantes; }
int EchelleSerpent::getNbCasesVertesRestantes() { return nbCasesVertesRestantes; }

void EchelleSerpent::setNbCasesEchelles(int a) { nbCasesEchelles = a; }
void EchelleSerpent::setNbCasesSerpents(int a) { nbCasesSerpents = a; }
void EchelleSerpent::setNbCasesOranges(int a) { nbCasesOranges = a; }
void EchelleSerpent::setNbCasesVertes(int a) { nbCasesVertes = a; }
void EchelleSerpent::setNbCasesNonNeutres(int a) { nbCasesNonNeutres = a; }
void EchelleSerpent::setNbCasesPlateauRestantes(int a) { nbCasesPlateauRestantes = a; }
void EchelleSerpent::setNbCasesEchellesRestantes(int a) { nbCasesEchellesRestantes = a; }
void EchelleSerpent::setNbCasesSerpentsRestantes(int a) { nbCasesSerpentsRestantes = a; }
void EchelleSerpent::setNbCasesOrangesRestantes(int a) { nbCasesOrangesRestantes = a; }
void EchelleSerpent::setNbCasesVertesRestantes(int a) { nbCasesVertesRestantes = a; }
/********** FIN : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/
