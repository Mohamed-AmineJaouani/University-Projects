#ifndef ECHELLE_SERPENT_HPP
#define ECHELLE_SERPENT_HPP

#include "../../architectureGenerique/Jeu.hpp"
using namespace std;

class EchelleSerpent : public Jeu {
public:
  EchelleSerpent(string, int, int, int, int, int, int, int, int); // nomJeuOuVariante, nbJoueursHumains, nbJoueursTotal, nbPionsParJoueur, nbCasesPlateau, nbCasesEchelles, nbCasesSerpents, nbCasesOranges, nbCasesVertes
  EchelleSerpent(string, int, int, int, Joueur**, int, int, int, int, int); // nomJeuOuVariante, nbJoueursHumains, nbJoueursTotal, nbPionsParJoueur, tableauJoueurs, nbCasesPlateau, nbCasesEchelles, nbCasesSerpents, nbCasesOranges, nbCasesVertes
  virtual ~EchelleSerpent();

  int getNbCasesPlateau();
  int getNbCasesEchelles();
  int getNbCasesSerpents();
  int getNbCasesOranges();
  int getNbCasesVertes();
  int getNbCasesNonNeutres();
  int getNbCasesPlateauRestantes();
  int getNbCasesEchellesRestantes();
  int getNbCasesSerpentsRestantes();
  int getNbCasesOrangesRestantes();
  int getNbCasesVertesRestantes();

  void setNbCasesPlateau(int);
  void setNbCasesEchelles(int);
  void setNbCasesSerpents(int);
  void setNbCasesOranges(int);
  void setNbCasesVertes(int);
  void setNbCasesNonNeutres(int);
  void setNbCasesPlateauRestantes(int);
  void setNbCasesEchellesRestantes(int);
  void setNbCasesSerpentsRestantes(int);
  void setNbCasesOrangesRestantes(int);
  void setNbCasesVertesRestantes(int);

private:
  int nbCasesPlateau; // Sert pour calculer la probabilite d'etre une case non-neutre
  int nbCasesEchelles;
  int nbCasesSerpents;
  int nbCasesOranges;
  int nbCasesVertes;

  /*** DEBUT : Sert a la repartition des cases par probabilite ***/
  int nbCasesNonNeutres;
  int nbCasesPlateauRestantes;
  int nbCasesEchellesRestantes;
  int nbCasesSerpentsRestantes;
  int nbCasesOrangesRestantes;
  int nbCasesVertesRestantes;
  /*** FIN : Sert a la repartition des cases par probabilite ***/
};

#endif //ECHELLE_SERPENT_HPP
