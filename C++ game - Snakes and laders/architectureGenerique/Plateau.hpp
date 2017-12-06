#ifndef PLATEAU_HPP
#define PLATEAU_HPP

#include <thread>
#include <chrono>
#include <forward_list>
#include "Constantes.hpp"
#include "Pion.hpp"
#include "Jeu.hpp"
#include "Case.hpp"
#include "../jeux/EchelleSerpent/EchelleSerpent.hpp"
#include "../jeux/EchelleSerpent/CaseEchelleSerpent.hpp"
//#include "../jeux/Cartagena/CaseCartagena.hpp"
//#include "../jeux/Numeri/CaseNumeri.hpp"


using namespace std;

/* template <int n, class T>  (template <int nbBonus, CaseEchelleSerpent>) */
//template <class T> // Contient le type de case
class Plateau {
public:
  Plateau(Jeu*, int, int); // jeu, nbLignes, nbColonnes, (nbCases: pase necessaire de donner en arg car = nbLignes*nbColonnes), (plateauNomJeu: recuperer via jeu), (plateauNbPionsParJoueur: recuperer via jeu), (plateauNbJoueursTotal: recuperer via jeu)
  virtual ~Plateau();

  Jeu* getJeu();
  int getNbLignes();
  int getNbColonnes();
  int getNbCases();
  string getPlateauNomJeu();
  int getPlateauNbPionsParJoueur();
  int getPlateauNbJoueursTotal();
  Case** getPlateau();
  /*
 _ _ _ _
|_|_|_|_|
|_|_|_|_|
|_|_|_|_|

  */
  void setJeu(Jeu*);
  void setNbLignes(int);
  void setNbColonnes(int);
  void setNbCases(int);
  void setPlateauNomJeu(string);
  void setPlateauNbPionsParJoueur(int);
  void setPlateauNbJoueursTotal(int);
  void setPlateau(Case**);

  friend ostream& operator<<(ostream&, Plateau&);
  Case* operator[](int);

  int question();
  bool finDePartie();
  //void deplacementPion(Pion);
  int deplacement(Joueur*);
  int deplacementPion(Pion*, int);
  void lancer();

private:  
  Jeu* jeu;
  int nbLignes;
  int nbColonnes;
  int nbCases;
  string plateauNomJeu;
  int plateauNbPionsParJoueur;
  int plateauNbJoueursTotal;
  Case** plateau; // Doit etre Case** car Case est abstraite
   
};

#endif //PLATEAU_HPP
