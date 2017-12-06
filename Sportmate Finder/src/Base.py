#!/usr/bin/env python2.7

import sqlite3
from outils import *

class Base():
    def __init__(self):
        self.db = sqlite3.connect('db/base.db')
        self.nom = 'Je suis une base :)'
       
        
    def creer(self):
        """Fonction interne permettant de creer les tables de la bd"""
        try:
            cursor = self.db.cursor()
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS study_field(
            id INTEGER PRIMARY KEY UNIQUE,
            name TEXT NOT NULL           
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS sport_level(
            id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
            sport_level INTEGER UNSIGNED NOT NULL,
            description TEXT NOT NULL
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS pack(
            id INTEGER PRIMARY KEY UNIQUE,
            name TEXT NOT NULL
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS sport(
            id INTEGER PRIMARY KEY UNIQUE,
            name TEXT NOT NULL,
            id_pack INTEGER NOT NULL REFERENCES pack(id)
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS user(
            id INTEGER PRIMARY KEY UNIQUE, 
            lastname VARCHAR(100) NOT NULL,
            firstname VARCHAR(100) NOT NULL,
            age INTEGER NOT NULL,
            country VARCHAR(100) NOT NULL,
            city VARCHAR(100) NOT NULL,
            sport_level INTEGER REFERENCES sport_level(sport_level),
            sex VARCHAR(1),
            study_field INTEGER REFERENCES study_field(id),
            study_level INTEGER CHECK(study_level <= 5 AND study_level >= 1)
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS pack_liked(
            id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
            id_user INTEGER REFERENCES user(id),
            id_pack INTEGER REFERENCES pack(id)
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS sport_liked(
            id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
            id_user INTEGER REFERENCES user(id),
            id_sport INTEGER REFERENCES sport(id)
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS sport_user(
            id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
            id_user INTEGER REFERENCES user(id),
            id_sport INTEGER REFERENCES sport(id)
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS friend(
            id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
            id_user1 INTEGER REFERENCES user(id),
            id_user2 INTEGER REFERENCES user(id) CHECK(id_user1 <> id_user2)
            )
            """)
            cursor.execute("""
            CREATE TABLE IF NOT EXISTS jour(
            id INTEGER PRIMARY KEY UNIQUE,
            name TEXT NOT NULL
            )
            """)
            self.db.commit()
            cursor.close()
            print "Creation de la base reussie."
            return True

        except:
            cursor.close()
            print "Creation de la base echouee."
            return False

        
        ########## DEBUT : PARTIE FONCTIONS D'AFFICHAGE ##########
    def printTableUser(self):
        """Affiche tous les utilisateurs de la base"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM user")
        for row in cursor:
            print row
        cursor.close()
    def printTableStudyField(self):
        """Affiche tous les domaines d'etudes existants"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM study_field")
        for row in cursor:
            print row 
        cursor.close()
    def printTableSportLevel(self):
        """Affiche tous les niveaux de sport existants"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM sport_level")
        for row in cursor:
            print row 
        cursor.close()
    def printTablePack(self):
        """Affiche tous les packs existants"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM pack")
        for row in cursor:
            print row 
        cursor.close()
    def printTableSport(self):
        """Affiche tous les sports existants"""
        cursor = self.db.cursor()
        cursor.execute("SELECT sport.id, sport.name, pack.name FROM sport INNER JOIN pack WHERE id_pack = pack.id")
        for row in cursor:
            print row 
        cursor.close()
    def printTableSportPack(self):
        """Affiche tous les sports et le pack dans lequel ils se trouvent"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM sport_pack")
        for row in cursor:
            print row 
        cursor.close()
    def printTablePackLiked(self):
        """Affiche tous les packs aimes par utilisateur"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM pack_liked")
        for row in cursor:
            print row 
        cursor.close()
    def printTableSportLiked(self):
        """Affiche tous les sports aimes par chaque utilisateur"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM sport_liked")
        for row in cursor:
            print row 
        cursor.close()
    def printTableSportUser(self):
        """Affiche tous les sports pratiques par chaque utilisateur"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM sport_user")
        for row in cursor:
            print row 
        cursor.close()
    def printTableFriend(self):
        """Affiche tous les amis existants"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM friend")
        for row in cursor:
            print row 
        cursor.close()
    def printTableJour(self):
        """Affiche tous les jours existants dans la base"""
        cursor = self.db.cursor()
        cursor.execute("SELECT * FROM jour")
        for row in cursor:
            print row 
        cursor.close()
    def printAll(self):
        """Affiche toutes les tables de la base"""
        print "\n--- AFFICHAGE DES SPORTs ---"
        self.printTableSport()
        print "\n--- AFFICHAGE DES PACKs ---"
        self.printTablePack()
        print "\n--- AFFICHAGE DES SPORT_LEVELs ---"
        self.printTableSportLevel()
        print "\n--- AFFICHAGE DES STUDY_FIELDs ---"
        self.printTableStudyField()
        print "\n--- AFFICHAGE DES USERs ---"
        self.printTableUser()
        print "\n--- AFFICHAGE DES PACK_LIKEDs ---"
        self.printTablePackLiked()
        print "\n--- AFFICHAGE DES SPORT_LIKEDs ---"
        self.printTableSportLiked()
        print "\n--- AFFICHAGE DES SPORT_USERs ---"
        self.printTableSportUser()
        print "\n--- AFFICHAGE DES FRIENDs ---"
        self.printTableFriend()
        print "\n--- AFFICHAGE DES JOURs ---"
        self.printTableJour()

        print "\n--- DIS BONJOURS LA BASE ! ---"
        print self.nom

    ########## FIN : PARTIE FONCTIONS D'AFFICHAGE ##########

    
    ########## DEBUT : PARTIE FONCTIONS D'AJOUT ##########
    def addUser(self, id, lastname, firstname, age, country, city, idSportLevel, sex, studyField, studyLevel):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO user(id, lastname, firstname, age, country, city, sport_level, sex, study_field, study_level) VALUES(?,?,?,?,?,?,?,?,?,?);", (id, lastname, firstname, age, country, city, idSportLevel, sex, studyField, studyLevel))
        self.db.commit()
        cursor.close()
    def addStudyField(self, id, nom):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO study_field(id, name) VALUES(?,?);", (id, nom.lower()))
        self.db.commit()
        cursor.close()
    def addSportLevel(self, sportLevel, descr):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO sport_level(sport_level, description) VALUES(?,?);", (sportLevel, descr))
        self.db.commit()
        cursor.close()
    def addPack(self, id, nom):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO pack(id, name) VALUES(?,?);", (id, nom.lower()))
        self.db.commit()
        cursor.close()
    def addSport(self, id, nom, id_pack):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO sport(id, name, id_pack) VALUES(?,?,?);", (id, nom.lower(), id_pack))
        self.db.commit()
        cursor.close()
    def addPackLiked(self, idUser, idPack):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO pack_liked(id_user, id_pack) VALUES(?,?);", (idUser, idPack))
        self.db.commit()
        cursor.close()
    def addSportLiked(self, idUser, idSport):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO sport_liked(id_user, id_sport) VALUES(?,?);", (idUser, idSport))
        self.db.commit()
        cursor.close()
    def addSportUser(self, idUser, idSport):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO sport_user(id_user, id_sport) VALUES(?,?);", (idUser, idSport))
        self.db.commit()
        cursor.close()
    def addFriend(self, idUser1, idUser2):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO friend(id_user1, id_user2) VALUES(?,?);", (idUser1, idUser2))
        self.db.commit()
        cursor.close()
    def addJour(self, id, name):
        cursor = self.db.cursor()
        cursor.execute("INSERT INTO jour(id, name) VALUES(?,?);", (id, name.lower()))
        self.db.commit()
        cursor.close()
    ########## FIN : PARTIE FONCTIONS D'AJOUT ##########

        
    ########## DEBUT : PARTIE INITIALISATION ##########
    def init_sport_level(self):
        """Fonction internet permettant de remplir la bd des niveaux sportifs"""
        cursor = self.db.cursor()
        self.addSportLevel(0, "Pas d'activite")
        self.addSportLevel(1, "Peu d'activite")
        self.addSportLevel(2, "Faible activite (1 - 3 jours par semaine)")
        self.addSportLevel(3, "Activite moderee (3 - 5 jours par semaine)")
        self.addSportLevel(4, "Activite intense (6 - 7 jours par semaine)")
        self.addSportLevel(5, "Activite tres intense (2x/jour, activites intenses en plus)")
        self.db.commit()
        cursor.close()
        
    def init_db_txt(self):
        """Fonction interne permettant de remplir la bd avec le fichier bd.txt"""
        cursor = self.db.cursor()
        cpt = 1
       
        dSF, dS, dP, dJ, dSP, l = parse_txt('bd.txt')
        for k in dSF.keys():
            self.addStudyField(dSF[k], k)
        for k in dS.keys():
            self.addSport(dS[k], k, dP[dSP[k]])
        for k in dP.keys():
            self.addPack(dP[k], k)
        for k in dJ.keys():
            self.addJour(dJ[k], k)
        for elt in l:
            lastname = "lastname"+str(cpt)
            firstname = "firstname"+str(cpt)

            if elt[NUM_STUDY_LEVEL] == '':
                studyLevel = None
            else:
                studyLevel = int(elt[NUM_STUDY_LEVEL])
            if elt[NUM_STUDY_FIELD].lower() == '':
                studyField = None
            else:
                studyField = dSF[elt[NUM_STUDY_FIELD].lower()]
            self.addUser(cpt, lastname, firstname, randomAge(), 'france', randomCity(), randomSportLevel(), elt[NUM_SEX], studyField, studyLevel)
            if test_sport_valide(elt[NUM_SPORT1].lower()):
                self.addSportUser(cpt, dS[elt[NUM_SPORT1].lower()])
                self.addSportLiked(cpt, dS[elt[NUM_SPORT1].lower()])
                self.addPackLiked(cpt, dP[elt[NUM_PACK1].lower()])
            if test_sport_valide(elt[NUM_SPORT2].lower()):
                self.addSportUser(cpt, dS[elt[NUM_SPORT2].lower()])
                self.addSportLiked(cpt, dS[elt[NUM_SPORT2].lower()])
                self.addPackLiked(cpt, dP[elt[NUM_PACK2].lower()])
            cpt += 1
    def init(self):
        """Fonction publique permettant d'initialiser la base"""
        self.creer()
        self.init_sport_level()
        self.init_db_txt()
        
    def destroy(self):
        """Fonction publique permettant de detruire la base"""
        cursor = self.db.cursor()
        cursor.execute("DROP TABLE IF EXISTS jour")
        cursor.execute("DROP TABLE IF EXISTS pack_liked")
        cursor.execute("DROP TABLE IF EXISTS sport_liked")
        cursor.execute("DROP TABLE IF EXISTS sport_user")
        cursor.execute("DROP TABLE IF EXISTS friend")
        cursor.execute("DROP TABLE IF EXISTS sport")
        cursor.execute("DROP TABLE IF EXISTS pack")
        cursor.execute("DROP TABLE IF EXISTS user")
        cursor.execute("DROP TABLE IF EXISTS study_field")
        cursor.execute("DROP TABLE IF EXISTS sport_level")
        self.db.commit()
        cursor.close()
        
    def reset(self):
        """Fonction publique permettant de reinitialiser la base"""
        self.destroy()
        self.init()
    ########## FIN : PARTIE INITIALISATION ##########

