#ifndef OBJET_ECHELLE_SERPENT_HPP
#define OBJET_ECHELLE_SERPENT_HPP

using namespace std;

class ObjetEchelleSerpent {
public:
  ObjetEchelleSerpent();
  ObjetEchelleSerpent(int, string, int, int); // idOES, type, positionTete, positionQueue
  virtual ~ObjetEchelleSerpent();

  int getIdOES();
  string getType();
  int getPositionTete();
  int getPositionQueue();

  void setIdOES(int);
  void setType(string);
  void setPositionTete(int);
  void setPositionQueue(int);

private:
  int idOES; // Sert pour l'affichage du plateau (pour reconnaitre la paire (tete, queue)
  string type; // TYPE_ECHELLE_BAS ou TYPE_ECHELLE_HAUT ou TYPE_SERPENT_QUEUE ou TYPE_SERPENT_TETE
  int positionTete;
  int positionQueue;

};

#endif //OBJET_ECHELLE_SERPENT_HPP
