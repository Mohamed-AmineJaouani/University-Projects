#ifndef CASE_NORMALE_HPP
#define CASE_NORMALE_HPP

#include "Case.hpp"
#include "Constantes.hpp"
#include "../jeux/EchelleSerpent/ObjetEchelleSerpent.hpp"
class CaseNormale : public Case {
protected:
  int randomSpecificite();

public:
  CaseNormale(int, int, int, int); //(specificite, position, nbPionsMax, nbPions)
  CaseNormale(int, int, int, int, forward_list<Pion*>, ObjetEchelleSerpent*); //(specificite, position, nbPionsMax, nbPions, listePions, obj)
  // Sert pour la premiere case ou se trouvent tous les pions au debut, ou pour modifier une case en cours de partie si un jeu le permet
  virtual ~CaseNormale();
  
};

#endif //CASE_NORMALE_HPP
