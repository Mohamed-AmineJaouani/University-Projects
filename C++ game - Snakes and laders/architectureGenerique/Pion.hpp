#ifndef PION_HPP
#define PION_HPP

#include "Constantes.hpp"
using namespace std;

class Pion {
public:
  Pion();
  Pion(int, int, string, int); //idJoueur, idPion, couleur, position
  virtual ~Pion();
  
  int getIdJoueur();
  int getIdPion();
  string getCouleur();
  int getPosition();
  
  void setIdJoueur(int);
  void setIdPion(int);
  void setCouleur(string);
  void setPosition(int);

  bool operator==(const Pion*&);
  friend ostream& operator<<(ostream&, Pion*&);

private:
  int idJoueur;
  int idPion;
  string couleur;
  int position; // position sur le plateau (Case*)

};

#endif //PION_HPP
