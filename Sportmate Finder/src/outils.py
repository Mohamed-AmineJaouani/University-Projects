#!/usr/bin/python2.7
import random
from constantes import *

NUM_SEX = 0
NUM_STUDY_FIELD = 1
NUM_STUDY_LEVEL = 2
NUM_SPORT1 = 3
NUM_PACK1 = 4
NUM_JOUR1 = 5
NUM_SPORT2 = 6
NUM_PACK2 = 7
NUM_JOUR2 = 8

def test_sport_valide(sport):
    return sport != '' and (sport.lower() in SPORTS)

def split_list(f):
    """Fonction qui parse le fichier bd.txt pour en extraire les utilisateurs et les sports qu'ils ont choisis (ce fichier a une structure particuliere (il est mal fait)"""
    l = [[]]
    with f:
        while True:
            line = f.readline()
            if line == '':
                break
            #La premiere ligne correspond a un sport, forcement
            sexe1, domaine1, niveau1, sport1, pack1, jour1, _ = line.split('|',6)
            if test_sport_valide(sport1):
                line = f.readline()
                sexe2, domaine2, niveau2, sport2, pack2, jour2, _ = line.split('|',6)
                #Si la deuxieme ligne n'est pas un sport, c'est qu'on a un utilisateur qui a choisi le sport1
                if test_sport_valide(sport2):
                    line = f.readline()
                    sexe3, domaine3, niveau3, sport3, pack3, jour3, _ = line.split('|',6)
                    if not test_sport_valide(sport3):
                        l += [[sexe3, domaine3, niveau3, sport1.lower(), pack1, jour1, sport2.lower(), pack2, jour2]]
                    else:
                        line = f.readline()
                        sexe4, domaine4, niveau4, sport4, pack4, jour4, _ = line.split('|',6)
                        if not test_sport_valide(sport4):
                            l += [[sexe4, domaine4, niveau4, sport1.lower(), pack1, jour1, sport2.lower(), pack2, jour2]]

                            line = f.readline()
                            sexe5, domaine5, niveau5, sport5, pack5, jour5, _ = line.split('|',6)
                            l += [[sexe5, domaine5, niveau5, sport4.lower(), pack4, jour4, sport5.lower(), pack5, jour5]]
                        else: #on a encore une 4eme ligne de sport et deux utilisateur juste apres
                            line = f.readline()
                            sexe5, domaine5, niveau5, sport5, pack5, jour5, _ = line.split('|',6)
                            l += [[sexe5, domaine5, niveau5, sport1.lower(), pack1, jour1, sport2.lower(), pack2, jour2]]
                            line = f.readline()
                            sexe6, domaine6, niveau6, sport6, pack6, jour6, _ = line.split('|',6)
                            l += [[sexe6, domaine6, niveau6, sport4.lower(), pack4, jour4, sport5.lower(), pack5, jour5]]
                else:
                    l += [[sexe2, domaine2, niveau2, sport1.lower(), pack1, jour1, sport2.lower(), pack2, jour2]]
    return l[1:]



def randomAge():
    return int(random.uniform(17,31))

def randomCity():
    t = ['ivry-sur-seine', 'paris', 'vitry-sur-seine', 'paris', 'kremlin bicetre', 'paris', 'aulnay-sous-bois', 'paris', 'aubervilliers', 'paris','bezons','paris', 'argenteuille', 'paris', 'sartrouville', 'paris', 'choisy', 'paris', 'courneuve', 'paris', 'bourget', 'paris', 'drancy', 'paris', 'orly']
    return t[int(random.uniform(0, len(t)))].lower()


def randomSportLevel():
    t = [0,1,2,3,4,5]
    r = int(random.uniform(0,100))
    if r < 20:
        return t[0]
    elif r >= 20 and r < 40:
        return t[1]
    elif r >= 40 and r< 60:
        return t[2]
    elif r >= 60 and r < 75:
        return t[3]
    elif r >= 75 and r < 90:
        return t[4]
    else:
        return t[5]

def remplirDico(l):
    """
    parse la bd et remplit des dictionnaire par categorie (study_field, sport, pack, jour, sport_pack (pour retrouver a quel pack appartient un sport))
    """
    dSF , dS, dP, dJ, dSP = dict(), dict(), dict(), dict(), dict()

    cptSF, cptS, cptP, cptJ = 1,1,1,1
    for line in l:
        lineSF, lineS1, lineP1, lineJ1 = line[NUM_STUDY_FIELD].lower(), line[NUM_SPORT1].lower(), line[NUM_PACK1].lower(), line[NUM_JOUR1].lower()
        lineS2, lineP2, lineJ2 =  line[NUM_SPORT2].lower(), line[NUM_PACK2].lower(), line[NUM_JOUR2].lower()
        
        if lineSF != '' and lineSF not in dSF.keys():
            dSF[lineSF] = cptSF
            cptSF += 1
        if test_sport_valide(lineS1) and lineS1 not in dS.keys():
            dS[lineS1] = cptS
            dSP[lineS1] = lineP1 
            cptS += 1    
        if lineP1 != '' and lineP1 not in dP.keys():
            dP[lineP1] = cptP
            cptP += 1    
        if lineJ1 != '' and lineJ1 not in dJ.keys():
            dJ[lineJ1] = cptJ
            cptJ += 1
        if test_sport_valide(lineS2) and lineS2 not in dS.keys():
            dS[lineS2] = cptS
            dSP[lineS2] = lineP2
            cptS += 1    
        if lineP2 != '' and lineP2 not in dP.keys():
            dP[lineP2] = cptP
            cptP += 1    
        if lineJ2 != '' and lineJ2 not in dJ.keys():
            dJ[lineJ2] = cptJ
            cptJ += 1
    return dSF, dS, dP, dJ, dSP


def parse_txt(fichier):
    f = open(fichier, 'r')
    l = split_list(f)
    dSF, dS, dP, dJ, dSP  = remplirDico(l)
    return dSF, dS, dP, dJ, dSP, l


def printSportsTFIDF():
    for k in DICO_SPORTS_TFIDF.keys():
        print 'DICO_SPORTS_TFIDF[',k,'] = ', DICO_SPORTS_TFIDF[k]

dSF, dS, dP, dJ, dSP, l = parse_txt('bd.txt') #l est la liste de tous les utilisateurs
