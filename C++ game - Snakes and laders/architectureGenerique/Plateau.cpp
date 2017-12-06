#include <iostream>
#include "Plateau.hpp"

using namespace std;


/********** DEBUT : CONSTRUCTEURS / DESTRUCTEURS **********/
Plateau::Plateau(Jeu* j, int rangeOrd, int rangeAbs):
  jeu(j), nbLignes(rangeOrd), nbColonnes(rangeAbs), nbCases(rangeOrd*rangeAbs), plateauNomJeu(j->getNomJeuOuVariante()), plateauNbPionsParJoueur(j->getNbPionsParJoueur()), plateauNbJoueursTotal(j->getNbJoueursTotal()) {

  plateau = new Case*[nbCases];
  int nbPionsMax = plateauNbPionsParJoueur * plateauNbJoueursTotal;
 

  if(plateauNomJeu == ECHELLE_SERPENT
     || plateauNomJeu == ECHELLE_SERPENT_ORANGE_VERTE
     || plateauNomJeu == ECHELLE_SERPENT_PEDAGOGIQUE
     || plateauNomJeu == ECHELLE_SERPENT_PLUSIEURS_PIONS) {
    for(int i=0 ; i<nbCases ; i++)
      plateau[i] = new CaseEchelleSerpent(NEUTRE, i, nbPionsMax, 0, (EchelleSerpent*)jeu); // On met la specificite de la case a NEUTRE et elle se chagera de changer sa specificite en fonction des parametres pertinents (nbCasesNonNeutres, etc.)


    // On place tous les pions sur la 1ere case
    forward_list<Pion*> list;    
    Joueur** tabJoueurs = jeu->getTableauJoueurs();
    for(int i=0 ; i<(jeu->getNbJoueursTotal()) ; i++) {
      Joueur* joueur = tabJoueurs[i];
      Pion** tab = joueur->getTabPions();
      for(int j=0 ; j<(joueur->getNbPions()) ; j++)
	list.push_front(tab[j]);
    }
    plateau[0]->ajouterPions(list, nbPionsMax);

  }

  
  /* 
  else if(plateauNomJeu == CARTAGENA) {
    for(int i=0 ; i<rangeAbs -1 ; i++)
      for(int j=0 ; j<rangeOrd -1 ; j++)
	plateau[i][j] = new CaseCartagena(NEUTRE, i, j, ...);
  }

  else if(plateauNomJeu == NUMERI) {
    for(int i=0 ; i<rangeAbs -1 ; i++)
      for(int j=0 ; j<rangeOrd -1 ; j++)
	plateau[i][j] = new CaseNumeri(NEUTRE, i, j, ...);
  }
    

  else {
    cout << "[Plateau.cpp] Erreur : Le nom du jeu n'est pas repertorie." << endl;
    exit(EXIT_FAILURE);
    
    for(int i=0 ; i<rangeAbs -1 ; i++)
      for(int j=0 ; j<rangeOrd -1 ; j++)
	plateau[i][j] = new CaseNormale(i, j, ...);
    

  }
    */

  cout << "Creation d'un Plateau de taille : " << nbLignes << "*" << nbColonnes << endl;
}

Plateau::~Plateau() {
  cout << "Destruction d'un Plateau" << endl;
}
/********** FIN : CONSTRUCTEURS / DESTRUCTEURS **********/






/********** DEBUT : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/
Jeu* Plateau::getJeu() { return jeu; }
int Plateau::getNbLignes() { return nbLignes; }
int Plateau::getNbColonnes() { return nbColonnes; }
int Plateau::getNbCases() { return nbCases; }
string Plateau::getPlateauNomJeu() { return plateauNomJeu; }
int Plateau::getPlateauNbPionsParJoueur() { return plateauNbPionsParJoueur; }
int Plateau::getPlateauNbJoueursTotal() { return plateauNbJoueursTotal; }
Case** Plateau::getPlateau() { return plateau; }

void Plateau::setJeu(Jeu *j) { jeu = j; }
void Plateau::setNbLignes(int a) { nbLignes = a; }
void Plateau::setNbColonnes(int a) { nbColonnes = a; }
void Plateau::setNbCases(int a) { nbCases = a; }
void Plateau::setPlateauNomJeu(string s) { plateauNomJeu = s; }
void Plateau::setPlateauNbPionsParJoueur(int i) { plateauNbPionsParJoueur = i; }
void Plateau::setPlateauNbJoueursTotal(int i) { plateauNbJoueursTotal = i; }
void Plateau::setPlateau(Case** c) { plateau = c; }

ostream& operator<<(ostream& o,Plateau& p){
  int cptLignes = p.getNbLignes();
  for(int i=p.getNbCases() ; i>=1 ; i-=p.getNbColonnes()) {
    if(cptLignes%2 == 0 && i == p.getNbCases())
      o << ROUGE << "|" << SUFFIXE_COULEUR;
    else if(i <= p.getNbColonnes())
      o << VERT << "|" << SUFFIXE_COULEUR;
    else
      o << "|";
      
    if(cptLignes % 2 == 1) { // Si ligne impaire : sensPion = g->d ; sensAffichage = d->g
      for(int c=p.getNbColonnes()-1 ; c>=0 ; c--) {
	o << (p.plateau[i -c -1]);
	if(c == 0 && i == p.getNbCases())
	  o << ROUGE << "|" << SUFFIXE_COULEUR;
	else
	  o << "|";
      }
       o << endl;
      cptLignes--;
    }
    else { // Si ligne paire : sensPion = d->g ; sensAffichage = g->d
      for(int c=0 ; c<=p.getNbColonnes()-1 ; c++)
	o << (p.plateau[i -c -1]) << "|";
       o << endl;
      cptLignes--;
    }
  }
  return o;
}

Case* Plateau::operator[](int pos) {
  return plateau[pos];
}
/********** FIN : ACCESSEURS ET REDEFINITION D'OPERATEUR(S) **********/




/********** DEBUT : FONCTIONS SUPPLEMENTAIRES **********/
// Affiche la question sur le terminal et renvoie la reponse au return
int Plateau::question() {
  srand(time(nullptr));
  int DIFFICULTE;
  int diff = (rand() % 3); // entre 0 et 2
  switch(diff){
  case 0: DIFFICULTE = DIFFICULTE_FACILE;break;
  case 1: DIFFICULTE = DIFFICULTE_MOYENNE;break;
  case 2: DIFFICULTE = DIFFICULTE_DIFFICILE;break;   
  }
  int nb1 = (rand() % DIFFICULTE) +1;
  int nb2 = (rand() % DIFFICULTE) +1;
  int op = (rand() % 4); //entre 0 et 3
  cout << "Quelle est le resultat de : ";
  switch(op){
  case 0: cout << nb1 << " + " << nb2 << " ?" << endl;return nb1+nb2;
  case 1: cout << nb1 << " - " << nb2 << " ?" << endl;return nb1-nb2;
  case 2: cout << nb1 << " * " << nb2 << " ?" << endl;return nb1*nb2;
  case 3: cout << nb1 << " / " << nb2 << " ?" << endl;return nb1/nb2;
  default: return 0;
  }
}




bool Plateau::finDePartie() {
  string nomJeu = jeu->getNomJeuOuVariante();
  

  /* FIN DE PARTIE [ECHELLE_SERPENT + CARTAGENA]: LORSQUE TOUS LES PIONS D'UN JOUEUR SE TROUVENT SUR LA DERNIERE CASE DU PLATEAU */
  if(nomJeu == ECHELLE_SERPENT
     || nomJeu == ECHELLE_SERPENT_ORANGE_VERTE
     || nomJeu == ECHELLE_SERPENT_PEDAGOGIQUE
     || nomJeu == ECHELLE_SERPENT_PLUSIEURS_PIONS
     || nomJeu == CARTAGENA) {
 
    Case* caseFinale = plateau[nbCases -1];
    int idJoueur;
    int cptPionsJoueurs[plateauNbJoueursTotal];
    for(int i=0 ; i<plateauNbJoueursTotal ; i++)
      cptPionsJoueurs[i] = 0;
    cout << "caseFinale nbPions= " << caseFinale->getNbPions() << endl; //X
    if(caseFinale->getNbPions() >= plateauNbPionsParJoueur) {
      forward_list<Pion*> list = caseFinale->getListePions();
      for(auto it=list.begin() ; it!=list.end() ; ++it) {
	idJoueur = (*it)->getIdJoueur();
	cout << "idJoueurs = " << idJoueur << endl; //X
	cptPionsJoueurs[idJoueur]++;
      }
      for(int i=0 ; i<plateauNbJoueursTotal ; i++) {
	cout << "cpt["<<i<<"]= "<<cptPionsJoueurs[i] << endl; //X
	if(cptPionsJoueurs[i] == plateauNbPionsParJoueur)
	  return true;
      }
    }
    else {
      //cout << "[EchelleSerpent.cpp]/finDePartie : Pas assez de pions sur la derniere case." << endl;
      return false;
    }
  }


  /* FIN DE PARTIE [NUMERI] : LORSQUE LES 3 DERNIERES CASES DU PLATEAU SONT OCCUPEES */
  else if(nomJeu == NUMERI) {
    Case* caseFinale_1 = plateau[nbCases -1];
    Case* caseFinale_2 = plateau[nbCases -2];
    Case* caseFinale_3 = plateau[nbCases -3];
    if(caseFinale_1->getNbPions() != 0 && caseFinale_2->getNbPions() != 0 && caseFinale_3->getNbPions() != 0)
      return true;
  }
  return false;
}




// Return: 0 = case NEUTRE ; 1 = case ORANGE ; -1 = case VERTE
int Plateau::deplacementPion(Pion* pion, int distance) {
  int ret = 0;
  Case* casePion = plateau[pion->getPosition()];
  casePion->retirerPion(pion);
      
  int new_position = 0;
  if(pion->getPosition() + distance >= nbCases -1)
    new_position = nbCases -1;
  else
    new_position = pion->getPosition() + distance;
  Case* new_casePion = plateau[new_position];
      
  new_casePion->ajouterPion(pion);

  // On verifie si la specificite de la nouvelle case
  if(new_casePion->getSpecificite() == ECHELLE
     || new_casePion->getSpecificite() == SERPENT) {
    ObjetEchelleSerpent* oes = new_casePion->getObj();
  
    if(oes->getType() == TYPE_ECHELLE_BAS) {
      (plateau[oes->getPositionQueue()])->retirerPion(pion);
      (plateau[oes->getPositionTete()])->ajouterPion(pion);
    }
    else if(oes->getType() == TYPE_SERPENT_TETE) {
      (plateau[oes->getPositionTete()])->retirerPion(pion);      
      (plateau[oes->getPositionQueue()])->ajouterPion(pion);
    }
  }
  else if(new_casePion->getSpecificite() == ORANGE)
    ret = 1;
  else if(new_casePion->getSpecificite() == VERTE)
    ret = -1;

  return ret;
}





// Return: 0 = case NEUTRE ; 1 = case ORANGE ; -1 = case VERTE
int Plateau::deplacement(Joueur* joueur) {
  int ret = 0;
  string nomJeu = jeu->getNomJeuOuVariante();
  Pion* pion = joueur->getTabPions()[0];

  srand(time(nullptr));
  int de = (rand() % 6) +1;
  char commande;
  int choixPion;

  if(joueur->getEstHumain() == false) {
    this_thread::sleep_for(chrono::seconds(DELAY_ROBOT));

    if(nomJeu == ECHELLE_SERPENT
       || nomJeu == ECHELLE_SERPENT_ORANGE_VERTE
       || nomJeu == ECHELLE_SERPENT_PEDAGOGIQUE) {
      cout << "Lancer de de . . . " << endl;
      this_thread::sleep_for(chrono::seconds(DELAY_DE));
      cout << "------------------- " << de << " !" << endl;
      ret = deplacementPion(pion, de);
    }

    else if(nomJeu == ECHELLE_SERPENT_PLUSIEURS_PIONS) {
      srand(time(nullptr));
      cout << "Lancer de de . . . " << endl;
      this_thread::sleep_for(chrono::seconds(DELAY_DE));
      cout << "------------------- " << de << " !" << endl;

      pion = joueur->getTabPions()[rand() % joueur->getNbPions()];
      ret = deplacementPion(pion, de);      
    }

    else if(nomJeu == CARTAGENA) {

    }

    else if(nomJeu == NUMERI) {

    }
  }


  else { // if(joueur->getEstHumain())

    if(nomJeu == ECHELLE_SERPENT
       || nomJeu == ECHELLE_SERPENT_ORANGE_VERTE
       || nomJeu == ECHELLE_SERPENT_PEDAGOGIQUE) {    
      do {
	cout << "Lancer dé : 'd'" << endl;
	cin >> commande;
	cin.clear();
      } while(commande != 'd');
      cout << "Lancer de de . . . " << endl;
      this_thread::sleep_for(chrono::seconds(DELAY_DE));
      cout << "------------------- " << de << " !" << endl;
      
      if(nomJeu == ECHELLE_SERPENT_PEDAGOGIQUE) {
	int reponse = question(); // Affiche le calcule et renvoie le resultat
	int proposition;
	cin >> proposition;
	
	if(proposition == reponse) {
	  cout << "Bonne reponse !" << endl;
	  ret = deplacementPion(pion, de);
	}
	else
	  cout << "Tu feras mieux la prochaine fois !" << endl;
      }
      else
	ret = deplacementPion(pion, de);
    }

    else if(nomJeu == ECHELLE_SERPENT_PLUSIEURS_PIONS) {
      do {
	cout << "Lancer dé : 'd' | Choisir pion : 'p'" << endl;
	cin >> commande;
	cin.clear();
      } while(commande != 'd' && commande != 'p');
	  
      if(commande == 'p') {
	do {
	  cout << "Choix du pion :" << endl;
	  cin >> choixPion;
	  cin.clear();
	} while(choixPion < 0 || (joueur->getNbPions()-1) < choixPion);
	cout << "Lancer de de . . . " << endl;
	this_thread::sleep_for(chrono::seconds(DELAY_DE));
	cout << "------------------- " << de << " !" << endl;

	pion = joueur->getTabPions()[choixPion];
	ret = deplacementPion(pion, de);
      }
      else { // if(commande == 'd')
	cout << "Lancer de de . . . " << endl;
	this_thread::sleep_for(chrono::seconds(DELAY_DE));
	cout << "------------------- " << de << " !" << endl;

	do {
	  cout << "Choix du pion :" << endl;
	  cin >> choixPion;
	  cin.clear();
	} while(choixPion < 0 || (joueur->getNbPions()-1) < choixPion);

	pion = joueur->getTabPions()[choixPion];
	ret = deplacementPion(pion, de);
      }
    }

    else if(nomJeu == CARTAGENA) {

    }

    else if(nomJeu == NUMERI) {

    }
  }
  return ret;
}




void Plateau::lancer() {
  cout << "Debut de la partie !" << endl;
  string nomJeu = jeu->getNomJeuOuVariante();

  int ret = 0;
  int tourJoueur = 0;
  int passeTourJoueur = -1;
  Joueur* joueur;
  char commande;
  int choixPion = 0;
  cout << *this << endl;

  while(!finDePartie()) {
    if(passeTourJoueur == tourJoueur) {
      tourJoueur++;
      passeTourJoueur = -1;
    }
    else {
      tourJoueur = tourJoueur % plateauNbJoueursTotal;
 
      joueur = (jeu->getTableauJoueurs())[tourJoueur];
 	
      cout << "Tour de " << joueur->getNom() << endl;
 
      ret = deplacement(joueur);
      if(ret == 1)
	tourJoueur--;
      else if(ret == -1)
	passeTourJoueur = tourJoueur;
   
      cout << *this << endl;
      tourJoueur++;
    }
    if(finDePartie())
      break;
  }
  joueur = (jeu->getTableauJoueurs())[--tourJoueur];
  cout << "GAME OVER!" << endl;
  cout << joueur->getNom() << " a eu raison de ses adversaires !" << endl;
}
/********** FIN : FONCTIONS SUPPLEMENTAIRES **********/
