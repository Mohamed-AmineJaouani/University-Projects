GOALS = main
HEADERS_DIR = .
ES_DIR = ../jeux/EchelleSerpent/
all : $(GOALS)

main : Pion.o Joueur.o Case.o Jeu.o Plateau.o CaseNormale.o $(ES_DIR)EchelleSerpent.o $(ES_DIR)ObjetEchelleSerpent.o $(ES_DIR)CaseEchelleSerpent.o main.o
	g++ -std=c++11 -o main Pion.o Joueur.o Case.o Plateau.o Jeu.o CaseNormale.o EchelleSerpent.o ObjetEchelleSerpent.o CaseEchelleSerpent.o main.o
Pion.o : Pion.cpp Constantes.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c Pion.cpp
Joueur.o : Joueur.cpp Pion.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c Joueur.cpp
Case.o : Case.cpp Constantes.hpp Pion.hpp $(ES_DIR)ObjetEchelleSerpent.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c Case.cpp
Plateau.o : Plateau.cpp Constantes.hpp Pion.hpp Jeu.hpp Case.hpp $(ES_DIR)CaseEchelleSerpent.hpp $(ES_DIR)EchelleSerpent.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c Plateau.cpp
Jeu.o : Jeu.cpp Constantes.hpp Pion.hpp Joueur.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c Jeu.cpp
CaseNormale.o : CaseNormale.cpp Constantes.hpp Case.hpp $(ES_DIR)ObjetEchelleSerpent.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c CaseNormale.cpp
$(ES_DIR)EchelleSerpent.o : $(ES_DIR)EchelleSerpent.cpp Jeu.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c $(ES_DIR)EchelleSerpent.cpp
$(ES_DIR)ObjetEchelleSerpent.o : $(ES_DIR)ObjetEchelleSerpent.cpp Case.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c $(ES_DIR)ObjetEchelleSerpent.cpp
$(ES_DIR)CaseEchelleSerpent.o :  $(ES_DIR)CaseEchelleSerpent.cpp  Constantes.hpp Case.hpp $(ES_DIR)ObjetEchelleSerpent.hpp $(ES_DIR)EchelleSerpent.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c  $(ES_DIR)CaseEchelleSerpent.cpp
main.o : main.cpp Constantes.hpp Plateau.hpp Jeu.hpp $(ES_DIR)EchelleSerpent.hpp
	g++ -std=c++11 -Wall -I $(HEADERS_DIR) -c main.cpp

clean :
	rm *.o $(GOALS)
