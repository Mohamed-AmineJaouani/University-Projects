#include <iostream>
#include "CaseNormale.hpp"
using namespace std;

/********** DEBUT : CONSTRUCTEURS / DESTRUCTEURS **********/
CaseNormale::CaseNormale(int spe, int pos, int nbMax, int nb):
  Case(spe, pos, nbMax, nb) {
  cout << "                              Construction de la CaseNormale : " << this << endl;
}

CaseNormale::CaseNormale(int spe, int pos, int nbMax, int nb, forward_list<Pion*> pions, ObjetEchelleSerpent* oes):
  Case(spe, pos, nbMax, nb, pions, oes) {
  cout << "                              Construction de la CaseNormale : " << this <<endl;
}

CaseNormale::~CaseNormale() {
  cout << "                              Destruction de la CaseNormale : " << this << endl;
}
/********** FIN : CONSTRUCTEURS / DESTRUCTEURS **********/




/********** DEBUT : FONCTIONS SUPPLEMENTAIRES **********/
int CaseNormale::randomSpecificite() {
  return NEUTRE; // 0
}
/********** FIN : FONCTIONS SUPPLEMENTAIRES **********/
