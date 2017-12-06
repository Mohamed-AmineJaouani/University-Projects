#include <iostream>
#include "Pion.hpp"
using namespace std;

/********** DEBUT : CONSTRUCTEURS / DESTRUCTEURS **********/
Pion::Pion(): idJoueur(0), idPion(0), couleur(""), position(0) {
  cout << "                              Construction d'un Pion par defaut" << endl;
}

Pion::Pion(int idPlayer, int identifiant, string colour, int pos): idJoueur(idPlayer), idPion(identifiant), couleur(colour), position(pos) {
  cout << "                              Contruction du Pion : " << this << endl;
}

Pion::~Pion() {
  cout << "                              Destruction du Pion : " << this << endl;
}
/********** FIN : CONSTRUCTEURS / DESTRUCTEURS **********/




/********** DEBUT : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/
int Pion::getIdJoueur() { return idJoueur; }
int Pion::getIdPion() { return idPion; }
string Pion::getCouleur() { return couleur; }
int Pion::getPosition() { return position; }


void Pion::setIdJoueur(int idPlayer) { idJoueur = idPlayer; }
void Pion::setIdPion(int identifiant) { idPion = identifiant; }
void Pion::setCouleur(string colour) { couleur = colour; }
void Pion::setPosition(int new_pos) { position = new_pos; }

ostream& operator<<(ostream& o, Pion*& p) { // Pion*&  ???
  o << p->getCouleur() << p->getIdPion() << SUFFIXE_COULEUR;
  return o;
}
/*
ostream& operator<<(ostream& o, Pion& p) {
  o << "<PION: (" << p.x << ", " << p.y << "), "
    << "idJoueur=" << p.idJoueur << ", "
    << "idPion=" << p.idPion << ", "
    << "couleur=" << p.couleur << ">";
  return o;
}
*/
bool Pion::operator==(const Pion*& other) {

  if(idJoueur == other->idJoueur
     && idPion == other->idPion
     && couleur == other->couleur
     && position == other->position)
    return true;
  else
    return false;     
}
/********** FIN : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/
