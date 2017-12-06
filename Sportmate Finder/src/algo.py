import numpy as np
from constantes import *

    
### DEBUT : PARTIE CALCUL DE LA DISTANCE ENTRE DEUX ENTITES DE MEME TYPE  ###

def distance_utilisateur(base, id1, id2):
    """Calcule la distance entre deux utilisateurs en calculant les distances entre leurs informations et leurs gouts"""
    return 0.5*distance_informations(base, id1, id2) + 3*distance_gouts(base, id1, id2)



def distance_gouts(base, id1, id2):
    """Calcule la distance entre les gouts de deux utilisateurs dont on donne les id en argument"""
    cursor = base.db.cursor()
    cursor.execute("SELECT sport.name FROM sport WHERE id IN (SELECT id_sport FROM sport_liked WHERE id_user = ?)",(id1,))
    liste_sports_1 = []
    for row in cursor:
        liste_sports_1.append(row[0])

    cursor.execute("SELECT sport.name FROM sport WHERE id IN (SELECT id_sport FROM sport_liked WHERE id_user = ?)",(id2,))
    liste_sports_2 = []
    for row in cursor:
        liste_sports_2.append(row[0])

    vecteur_gouts_moyens_1 = modelisation_profil_utilisateur(liste_sports_1)
    vecteur_gouts_moyens_2 = modelisation_profil_utilisateur(liste_sports_2)

    distance = sous_distance_manhattan(vecteur_gouts_moyens_1, vecteur_gouts_moyens_2)
    return distance
    
def sous_distance_manhattan(c1, c2):
    #quantifie la difference brute entre 2 utilisateurs -> tres bon pour nous
    """Calcule la distance de Manhattan entre les deux contenus c1 et c2 donnes sous forme de liste de TF-IDF"""
    d = 0.0
    for i in range(len(c1)):
        d += np.abs(c1[i] - c2[i])
    return d


        
### Sous-fonctions de distance entre utilisateurs ###

def distance_informations(base, id1, id2):
    """Renvoie la distance entre deux utilisateurs en se basant sur leurs informations (niveau sportif, age, ville, domaine d'etudes et niveau d'etudes)"""
    distance = 0.0
    distance += 0.6*sous_distance_sport_level(base, id1, id2) + 0.1*sous_distance_age(base, id1, id2) + 0.1*sous_distance_ville(base, id1, id2) + 0.1*sous_distance_study_field(base, id1, id2) + 0.1*sous_distance_study_level(base, id1, id2)
    return distance
    
def sous_distance_sport_level(base, id1, id2):
    """Renvoie la difference de niveau sportif entre deux utilisateurs"""
    cursor = base.db.cursor()
    cursor.execute("SELECT sport_level FROM user WHERE id = ?",(id1,))
    for row in cursor:
        sportLevel1 = row[0]
    cursor.execute("SELECT sport_level FROM user WHERE id = ?",(id2,))
    for row in cursor:
        sportLevel2 = row[0]
    return np.abs(sportLevel1 - sportLevel2)

def sous_distance_age(base, id1, id2):
    """Renvoie la difference d'age entre les deux utilisateurs"""
    cursor = base.db.cursor()
    cursor.execute("SELECT age FROM user WHERE id = ?",(id1,))
    for row in cursor:
        age1 = row[0]
    cursor.execute("SELECT age FROM user WHERE id = ?",(id2,))
    for row in cursor:
        age2 = row[0]
    return np.abs(age1 - age2)
    
def sous_distance_ville(base, id1, id2):
    """Renvoie 1 si la distance entre les villes de ces deux utilisateurs est > 0 (i.e. si leur ville est differente), et 0 s'ils sont dans la meme ville"""
    cursor = base.db.cursor()
    cursor.execute("SELECT city FROM user WHERE id = ?",(id1,))
    for row in cursor:
        city1 = row[0]
    cursor.execute("SELECT city FROM user WHERE id = ?",(id2,))
    for row in cursor:
        city2 = row[0]
    if city1 == city2: return 0
    else: return 1

def sous_distance_study_field(base, id1, id2):
    """Renvoie 1 si la distance entre les domaines d'etudes de ces deux utilisateurs est > 0 (i.e. si leur domaine est different), et 0 sinon"""
    cursor = base.db.cursor()
    cursor.execute("SELECT study_field FROM user WHERE id = ?",(id1,))
    for row in cursor:
        studyField1 = row[0]
    cursor.execute("SELECT study_field FROM user WHERE id = ?",(id2,))
    for row in cursor:
        studyField2 = row[0]
    if studyField1 == None or studyField2 == None:
        return 1
    if studyField1 == studyField2: return 0
    else: return 1

def sous_distance_study_level(base, id1, id2):
    """Renvoie 1 si la distance entre les niveaux d'etudes de ces deux utilisateurs est > 0 (i.e. si leur niveau d'etude est different), et 0 sinon"""
    cursor = base.db.cursor()
    cursor.execute("SELECT study_level FROM user WHERE id = ?",(id1,))
    for row in cursor:
        studyLevel1 = row[0]
    cursor.execute("SELECT study_level FROM user WHERE id = ?",(id2,))
    for row in cursor:
        studyLevel2 = row[0]
    if studyLevel1 == None or studyLevel2 == None:
        return 5.0
    else:
        return np.abs(studyLevel1 - studyLevel2)
    




### DEBUT : PARTIE CALCUL DE LA DISTANCE ENTRE DEUX ENTITES DE MEME TYPE  ###


def similarite_sport(sport1, sport2):
    """Prend en argument deux sports et renvoie leur similarite au sens de dice_sorensen"""
    l1 = DICO_SPORTS[sport1]
    l2 = DICO_SPORTS[sport2]
    return dice_sorensen(l1, l2)

def dice_sorensen(l1, l2):
    """ Fonction de Dice-Sorensen (cours 4 page 13). Prend en argument deux listes de mots-clefs, chaque liste correspondant a la liste des tags du sport en question"""
    nbMotsCommuns = 0
    for elt in l1:
        if elt in l2:
            nbMotsCommuns += 1
    return 2*float(nbMotsCommuns)/(len(l1)+ len(l2))



def modelisation_profil_utilisateur(list_sport_liked):
    """ Fonction moyenne des tfidf (cours4 page 14) mais comme on fait de l'aleatoire il est possible qu'il aime des choses tres differentes
    et ca ne sert a rien"""
    vecteur_gouts_moyens = [0.0]*len(ENSEMBLE_MOTS_CLEFS)
    for sport in list_sport_liked:
        for i in range(len(DICO_SPORTS_TFIDF[sport])): # = len(ENSEMBLE_MOTS_CLEFS)
            vecteur_gouts_moyens[i] += DICO_SPORTS_TFIDF[sport][i]

    if len(list_sport_liked) == 0:
        return [0.0]*len(ENSEMBLE_MOTS_CLEFS)
    return [k/len(list_sport_liked) for k in vecteur_gouts_moyens]


def similarite_cosinus(tfidf1, tfidf2):
    """Prend en arguments deux vecteurs de TF-IDF et renvoie la similarite cosinus. Plus le nombre est proche de 1 plus les sports se ressemblent"""
    if len(tfidf1) != len(tfidf2):
        print 'les tailles doivent etre les memes'
    else:
        numerateur = 0.0
        sumxcarre = 0.0
        sumycarre = 0.0
        for i in range(len(tfidf1)):
            numerateur += tfidf1[i]*tfidf2[i]
            sumxcarre += tfidf1[i]*tfidf1[i]
            sumycarre += tfidf2[i]*tfidf2[i]
    return float(numerateur) / np.sqrt(sumxcarre*sumycarre)



def sigma(l1, l2):
    cpt = 0
    moyenne_l1 = float(sum(l1))/len(l1)
    moyenne_l2 = float(sum(l2))/len(l2)
    for i in range(len(l1)):
        cpt += (l1[i] - moyenne_l1) * (l2[i] - moyenne_l2)
    return cpt


def similarite_utilisateurs(l1,l2):
    """Prend en argument deux utilisateurs sous forme de vecteurs de leurs gouts moyens (via la fonction modelisation_profil_utilisateur)"""
    if len(l1) != len(l2):
        print 'les list doivent etre de meme taille'
    else:
        return float(sigma(l1,l2)) / (np.sqrt(float(sigma(l1,l1)))* np.sqrt(float(sigma(l2,l2))))


def comparaison_similarite_utilisateurs(base, id1, id2):
    cursor = base.db.cursor()
    cursor.execute("SELECT sport.name FROM sport WHERE id IN (SELECT id_sport FROM sport_liked WHERE id_user = ?)",(id1,))
    liste_sports_1 = []
    for row in cursor:
        liste_sports_1.append(row[0])

    cursor.execute("SELECT sport.name FROM sport WHERE id IN (SELECT id_sport FROM sport_liked WHERE id_user = ?)",(id2,))
    liste_sports_2 = []
    for row in cursor:
        liste_sports_2.append(row[0])

    vecteur_gouts_moyens_1 = modelisation_profil_utilisateur(liste_sports_1)
    vecteur_gouts_moyens_2 = modelisation_profil_utilisateur(liste_sports_2)

    return similarite_utilisateurs(vecteur_gouts_moyens_1, vecteur_gouts_moyens_2)
