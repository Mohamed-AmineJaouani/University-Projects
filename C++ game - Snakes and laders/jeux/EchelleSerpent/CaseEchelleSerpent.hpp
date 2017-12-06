#ifndef CASE_ECHELLE_SERPENT_HPP
#define CASE_ECHELLE_SERPENT_HPP

#include "../../architectureGenerique/Constantes.hpp"
#include "../../architectureGenerique/Case.hpp"
#include "EchelleSerpent.hpp"
#include "ObjetEchelleSerpent.hpp"
using namespace std;

class CaseEchelleSerpent : public Case {
protected:
  virtual int randomSpecificite();


public:
  CaseEchelleSerpent(int, int, int, int, EchelleSerpent*); // specificite, position, nbPionsMax, nbPions, jeuVariante
  CaseEchelleSerpent(int, int, int, int, forward_list<Pion*>, ObjetEchelleSerpent*, EchelleSerpent*); // specificite, position, nbPionsMax, nbPions, listePions, obj, jeuVariante
  // Sert pour la premiere case ou se trouvent tous les pions au debut, ou pour modifier une case en cours de partie si un jeu le permet
  virtual ~CaseEchelleSerpent();

  EchelleSerpent* getJeuVariante();
  //int getNbCasesPlateau();
  //bool getIsObjetEchelleSerpent();
  //ObjetEchelleSerpent* getObj();
  //int getSpecificiteCase();

  void setJeuVariante(EchelleSerpent*);
  //void setNbCasesPlateau(int);
  //void setIsObjetEchelleSerpent(bool);
  //void setObj(ObjetEchelleSerpent*);
  //void setSpecificiteCase(int);
  
private:
  EchelleSerpent* jeuVariante;
  //int nbCasesPlateau; // Sert pour calculer la probabilite d'etre une case non-neutre
  //bool isObjetEchelleSerpent;
  //ObjetEchelleSerpent* obj;
  //int specificiteCase;

  //bool variantePedagogique;
  //bool variantePlusieursPions;

};

#endif //CASE_ECHELLE_SERPENT_HPP
