#ifndef CASE_HPP
#define CASE_HPP

#include "Constantes.hpp"
#include "Pion.hpp"
#include <forward_list>
#include "../jeux/EchelleSerpent/ObjetEchelleSerpent.hpp"

/***** CLASSE ABSTRAITE *****/

class Case {
protected:
  virtual int randomSpecificite() = 0;

public:
  Case();

  Case(int, int, int, int); // specificite, position, nbPionsMax, nbPions
  Case(int, int, int, int, forward_list<Pion*>, ObjetEchelleSerpent*); // specificite, position, nbPionsMax, nbPions, listePions, obj  // Sert pour la premiere case ou se trouvent tous les pions au debut, ou pour modifier une case en cours de partie si un jeu le permet
  virtual ~Case();

  int getSpecificite();
  int getPosition();
  int getNbPionsMax();
  int getNbPions();
  forward_list<Pion*> getListePions();
  ObjetEchelleSerpent* getObj();

  void setSpecificite(int);
  void setPosition(int);
  void setNbPionsMax(int);
  void setNbPions(int);
  void setListePions(forward_list<Pion*>);
  void setObj(ObjetEchelleSerpent*);

  friend ostream& operator<<(ostream&, Case*&);

  bool isEmpty();
  bool ajouterPion(Pion*);
  bool ajouterPions(forward_list<Pion*>, int);
  bool retirerPion(Pion*);
  bool retirerPions(forward_list<Pion*>, int);

  ObjetEchelleSerpent* obj; // Sert pour les CaseEchelleSerpent

protected:
  int specificite;
  int position; // position sur le plateau (Case*)
  int nbPionsMax; //= Jeu::nbJoueursTotal * Jeu::nbPionsParJoueur
  int nbPions;
  forward_list<Pion*> listePions; // forward_list car peu importe l'ordre

};

#endif //CASE_HPP
