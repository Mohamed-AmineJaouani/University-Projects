#include <iostream>
#include <forward_list>
#include "Case.hpp"
using namespace std;


/********** DEBUT : CONSTRUCTEURS / DESTRUCTEURS **********/
Case::Case() {
  cout << "                              Creation d'une Case par defaut" << endl;
}

Case::Case(int spe, int pos, int nbMax, int nb):
  specificite(spe), position(pos), nbPionsMax(nbMax), nbPions(nb) {

  // forward_list par defaut vide
  obj = nullptr;
  cout << "                              Construction de la case : " << this << endl;
}

Case::Case(int spe, int pos, int nbMax, int nb, forward_list<Pion*> pions, ObjetEchelleSerpent* o):
  specificite(spe), position(pos), nbPionsMax(nbMax), nbPions(nb), listePions(pions), obj(o) {
  cout << "                              Construction de la case : " << this <<endl;
}
Case::~Case() {
  cout << "                              Destruction de la Case : " << this << endl;
}
/********** FIN : CONSTRUCTEURS / DESTRUCTEURS **********/




/********** DEBUT : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/
int Case::getSpecificite() { return specificite; }
int Case::getPosition() { return position; }
int Case::getNbPionsMax() { return nbPionsMax; }
int Case::getNbPions() { return nbPions; }
forward_list<Pion*> Case::getListePions() { return listePions; }
ObjetEchelleSerpent* Case::getObj() { return obj; }

void Case::setSpecificite(int spe) { specificite = spe; }
void Case::setPosition(int new_pos) { position = new_pos; }
void Case::setNbPionsMax(int a) { nbPionsMax = a; }
void Case::setNbPions(int a) { nbPions = a; }
void Case::setListePions(forward_list<Pion*> fl) { listePions = fl; }
void Case::setObj(ObjetEchelleSerpent* oes) { obj = oes; }

ostream& operator<<(ostream& o, Case*& c) {
  if(c->obj != nullptr) {
    string type = (c->obj)->getType();
    int idOES = (c->obj)->getIdOES();
    if(c->specificite == ECHELLE || c->specificite == SERPENT) {
      if(type == TYPE_ECHELLE_BAS)
	o << BLANC << "e" << idOES << SUFFIXE_COULEUR;
      else if(type == TYPE_ECHELLE_HAUT)
	o << BLANC << "E" << idOES << SUFFIXE_COULEUR;
      else if(type == TYPE_SERPENT_QUEUE)
	o << NOIR << "s" << idOES << SUFFIXE_COULEUR;
      else if(type == TYPE_SERPENT_TETE)
	o << NOIR << "S" << idOES << SUFFIXE_COULEUR;
    }
  }
  if(c->specificite == ORANGE)
    o << CYAN << "O " << SUFFIXE_COULEUR;
  else if(c->specificite == VERTE)
    o << MAGENTA << "V " << SUFFIXE_COULEUR;

  int retrait = 0;
  if(c->specificite == ECHELLE || c->specificite == SERPENT)
    retrait = 2;
  else if(c->specificite == ORANGE || c->specificite == VERTE)
    retrait = 1;
    
  int maxEspace = 2 + c->getNbPionsMax();
  
  switch(c->nbPions) {
  case 0:
    for(int i=0 ; i<maxEspace-retrait ; i++)
      o << " ";
    break;
  case 1:
    for(int i=0 ; i<retrait ; i++)
      o << " ";
    o << *((c->listePions).begin());
    for(int i=0 ; i<maxEspace-1-retrait-retrait ; i++)
      o << " ";
    break;
  default:
    for(int i=0 ; i<retrait ; i++)
      o << " ";
    int cpt = 0;
    for(auto it=c->listePions.begin() ; it!=c->listePions.end(); ++it) {
      o << *it;
      cpt++;
    }
    for(int i=0 ; i<maxEspace-cpt-retrait-retrait ; i++)
      o << " ";
    break;
    }
  return o;
}
/*
ostream& operator<<(ostream& o, Case*& c) {
  //o << "\n";
  o << "<CASE: specificite=" << c->specificite << ", "
  << "position=" << c->position << ", "
    << "nbPions=" << c->nbPions << ", "
    << "nbPionsMax=" << c->nbPionsMax << ", ";

  o << "listePions=[";
  switch(c->nbPions) {
  case 0:
    o << "]>";
    break;
  case 1:
    o << "\n  " << *c->listePions.begin() << "]>";
    break;
  default:
    int cpt = 0;
    for(auto it=c->listePions.begin() ; it!=c->listePions.end(); ++it) {
      if(cpt++ == c->nbPions -1)
	o << "\n  " << *it << "]>";
      else 
	o << "\n  " << *it << ",";
    }
    break;
  }
  return o;
}
*/
/********** FIN : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/




/********** DEBUT : FONCTIONS SUPPLEMENTAIRES **********/
bool Case::isEmpty() { return nbPions == 0; }

bool Case::ajouterPion(Pion* p) {
  if(nbPions < nbPionsMax) {
    listePions.push_front(p);
    nbPions++;
    p->setPosition(position);
  }
  else {
    cout << "[Case.cpp/ajouterPion] Erreur : Impossible de rajouter le pion. Case remplie." << endl;
    return false;
  }
  return true;
}

bool Case::ajouterPions(forward_list<Pion*> fl, int nbPionsToAdd) {
  if(nbPions + nbPionsToAdd > nbPionsMax) {
    cout << "[Case.cpp/ajouterPions] Erreur : Impossible de rajouter les pions. Pas assez de place." << endl;
    return false;
  }
  else {
    listePions.splice_after(listePions.before_begin(), fl);
    nbPions += nbPionsToAdd;
    
    for(auto it=fl.begin() ; it!=fl.end() ; ++it)
      (*it)->setPosition(position);
    
    return true;
  }
}

bool Case::retirerPion(Pion* p) {
  if(nbPions == 0) {
    cout << "[Case.cpp/retirerPion] Erreur : Impossible de retirer le pion. Case vide." << endl;
    return false;
  }
  else {
    for(auto it=listePions.begin() ; it!=listePions.end(); ++it) {
      if(*it == p) {
	listePions.remove(p);
	nbPions--;
	//cout << "Pion supprime: " << p << endl;
	return true;
      }
    }
    cout << "[Case.cpp/retirerPion] Erreur : Impossible de retirer le pion. Il n'est pas present." << endl;
    return false;
  }
}

bool Case::retirerPions(forward_list<Pion*> fl, int nbPionsToDel) {
  bool all_deleted = true;
  if(nbPions - nbPionsToDel < 0) {
    cout << "[Case.cpp/retirerPions] Erreur : Impossible de retirer les pions. Pas assez de pions." << endl;
    return false;
  }
  else {
    for(auto it=fl.begin() ; it!=fl.end(); ++it) {
      //cout << "RETIRER PIONS ON PASSE : " << *it << endl;
      if(!retirerPion(*it)) {
	cout << "[Case.cpp/retirerPions] Erreur : Impossible de retirer le pion. Il n'est pas present." << endl;
	all_deleted = false;
      }
    }
  }
  return all_deleted;
}
/********** FIN : FONCTIONS SUPPLEMENTAIRES **********/
