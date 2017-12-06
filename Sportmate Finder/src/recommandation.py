#!/usr/bin/python2.7
import numpy
from Base import *
from outils import *
from constantes import *
from algo import *
base = Base()

def user_insert(id):
    lastname = raw_input("Entrez votre nom : ")
    firstname = raw_input("Entrez votre prenom : ")
    age = raw_input("Entrez votre age : ")
    country = raw_input("Entrez votre pays : ")
    city = raw_input("Entrez votre ville : ")
    sport_level = raw_input("Entrez votre niveau sportif\n0 - Pas d'activite\n1 - Peu d'activite\n2 - Faible activite (1 - 3 jours par semaine)\n3 - Activite moderee (3 - 5 jours par semaine)\n4 - Activite intense (6 - 7 jours par semaine)\n5 - Activite tres intense (2x/jour, activites intenses en plus) : ")
    sex = raw_input("Entrez votre sexe (M/F) : ")
    study_field = raw_input("Entrez votre domaine d'etude \n\n"+str(STUDY_FIELDS))
    study_level = raw_input("Entrez votre niveau d'etude (1-5) : ")

    base.addUser(id, lastname, firstname, age, country, city, sport_level, sex, dSF[study_field], study_level)
    

def sport_user_insert(id):
    sport_user = raw_input("\nEntrez les sports que vous pratiques separes par des ;\n\n"+str(SPORTS))
    sport_user_list = sport_user.split(';')
    for sport_user in sport_user_list:
        base.addSportUser(id, dS[sport_user])
    

def sport_liked_insert(id):
    sport_liked = raw_input("\nEntrez les sports que vous aimez separes par des ; : \n\n"+str(SPORTS))
    sport_liked_list = sport_liked.split(';')
    for sport in sport_liked_list:
        base.addSportLiked(id, dS[sport])

def pack_liked_insert(id):
    pack_liked = raw_input("\nEntrez les packs que vous aimez separes par des ; : "+str(PACKS))
    pack_liked_list = pack_liked.split(';')    
    for pack in pack_liked_list:
        base.addPackLiked(id, dP[pack])
        
def new_user():
    cursor = base.db.cursor()
    cursor.execute("SELECT count(id) from user")
    for row in cursor:
        next_id = int(row[0])
    base.printAll()
    next_id += 1

    user_insert(next_id)
    sport_user_insert(next_id)
    sport_liked_insert(next_id)
    pack_liked_insert(next_id)
    base.db.commit()
    cursor.close()
    return next_id

def recommandation_user():
    id_new_user = new_user()
    #id_new_user = 75
    cursor = base.db.cursor()
    min_distance = 100.0
    distance_totale = 0.0
    user_nearest = 0

    choix = 'y'
    while choix == 'y':
        choix = raw_input("Voulez vous une recommandation d'ami ?(y/n)\n")
        if choix == 'y':
            print "RECOMMANDATION D'AMIS\n"
            cursor.execute("SELECT id FROM user WHERE id <> ? AND id NOT IN (SELECT id_user2 FROM friend WHERE id_user1 = ?)",(id_new_user,id_new_user))
            #cursor.execute("SELECT id FROM user WHERE id <> ?",(id_new_user,))
            for row in cursor:
                distance_totale = distance_utilisateur(base, id_new_user, int(row[0]))
                if distance_totale < min_distance:
                    min_distance = distance_totale
                    user_nearest = int(row[0])
            cursor.execute("SELECT user.*, sport.name from user JOIN sport_liked ON user.id = id_user JOIN sport ON id_sport = sport.id WHERE user.id = ?",(user_nearest,))
            for row in cursor:
                print row
            print distance_totale
            choix_ajout = raw_input("Voulez vous l'ajouter ?(y/n)")
            if choix_ajout == 'y':
                cursor.execute("SELECT * FROM user WHERE id IN (SELECT id_user2 FROM friend WHERE id_user1 = ?)",(user_nearest,))
                for row in cursor:
                    print row
                base.addFriend(id_new_user, user_nearest)
                cursor.execute("SELECT * FROM user WHERE id IN (SELECT id_user2 FROM friend WHERE id_user1 = ?)",(user_nearest,))
                for row in cursor:
                    print row
    return id_new_user


def recommandation_sport(id):
    choix = 'y'
    while choix == 'y':
        choix = raw_input("Voulez vous une recommandation de sport ?(y/n)\n")
        if choix == 'y':
            print "RECOMMANDATION DE SPORTS\n"
            sport = raw_input("Entrez un sport : ")
            cursor = base.db.cursor()
            cursor.execute("SELECT sport.name FROM sport WHERE name <> ? AND sport.id NOT IN(SELECT id_sport FROM sport_liked WHERE id_user = ?)",(sport,id))
            lname = []
            lID = []
            dl = 0.0
            for row in cursor:
                d = similarite_sport(sport, str(row[0]))
                if d > dl:
                    dl = d
                    lname = [str(row[0])]
                    lID = [dS[str(row[0])]]
                elif d == dl:
                    lname.append(str(row[0]))
                    lID.append(dS[str(row[0])])
            print lname
            
            choix_ajout = raw_input("Voulez vous l'ajouter ?(y/n)")
            if choix_ajout == 'y':
                for i in lID:
                    base.addSportLiked(id, i)
                cursor.execute("SELECT sport.name FROM sport WHERE id IN (SELECT id_sport FROM sport_liked WHERE id_user = ?)",(id,))
                for row in cursor:
                    print row
        else:
            break

def recommandation_totale():
    #id = recommandation_user()
    id = 70
    recommandation_sport(id)

recommandation_totale()
