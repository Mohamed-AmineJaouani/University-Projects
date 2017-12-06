#ifndef JEU_HPP
#define JEU_HPP

#include "Constantes.hpp"
#include "Pion.hpp"
#include "Joueur.hpp"
using namespace std;

/***** CLASSE CONCRETE ET NON PLUS ABSTRAITE *****/

class Jeu {
public:
  Jeu();
  Jeu(string, int, int, int); // nomJeuOuVariante, nbJoueursHumains, nbJoueursTotal, nbPionsParJoueur
  Jeu(string, int, int, int, Joueur**); // nomJeuOuVariante, nbJoueursHumains, nbJoueursTotal, nbPionsParJoueur, tableauJoueurs
  virtual ~Jeu();

  string getNomJeuOuVariante();
  int getNbJoueursHumains();
  int getNbJoueursTotal();
  int getNbPionsParJoueur();
  Joueur** getTableauJoueurs();

  void setNomJeuOuVariante(string);
  void setNbJoueursHumains(int);
  void setNbJoueursTotal(int);
  void setNbPionsParJoueur(int);
  void setTableauJoueurs(Joueur**);

  Joueur* operator[](int);
  
  virtual Joueur* creationJoueur(); // =0; ABSTRAITE ou NON ? ...
  int getScoreJoueur(int); //Recuperer le score d'un joueur en donnant son id
  void getScores(); //Afficher les scores de tous les joueurs

protected:
  string nomJeuOuVariante;
  int nbJoueursHumains; //Parametre donne par l'utilisateur
  int nbJoueursTotal; // (=humains + robots) //Parametre donne par l'utilisateur
  int nbPionsParJoueur;
  Joueur** tableauJoueurs;
  
};

#endif //JEU_HPP
