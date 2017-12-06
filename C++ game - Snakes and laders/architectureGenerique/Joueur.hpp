#ifndef JOUEUR_HPP
#define JOUEUR_HPP

#include "Pion.hpp"
using namespace std;

class Joueur {
public:
  Joueur();
  Joueur(bool, int, string, int, int, Pion**); // estHumain, idJoueur, nom, score, nbPions, tabPions
  virtual ~Joueur();

  bool getEstHumain();
  int getIdJoueur();
  string getNom();
  int getScore();
  int getNbPions();
  Pion** getTabPions();

  void setEstHumain(bool);
  void setIdJoueur(int);
  void setNom(string);
  void setScore(int);
  void setNbPions(int);
  void setTabPions(Pion**);

  friend ostream& operator<<(ostream&, Joueur&);

private:
  bool estHumain;
  int idJoueur;
  string nom;
  int score;
  int nbPions;
  Pion** tabPions; // Pion** car l'ordre importe, et l'acces est plus facile

};

#endif //JOUEUR_HPP
