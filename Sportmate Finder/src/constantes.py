import numpy as np

STUDY_FIELDS = ['mathematiques', 'departement de formation de sciences exactes', 'medecine', 'etudes interculturelles de langues appliquees', 'chimie', 'linguistique ', 'lettres, arts, cinema', 'langues et civilisations de lasie orientale', 'etudes psychanalytiques', 'departement de formation de lettres et sciences humaines', 'sciences du vivant', 'informatique', 'physique', 'sciences sociales', 'geographie, histoire et sciences de la societe ', 'etudes anglophones', 'ecole dingenieur denis diderot', 'odontologie', 'sciences de la terre de lenvironnement et des planetes', 'institut dhematologie', 'institut de la pensee contemporaine (dit ihp)', 'iut']


ENSEMBLE_MOTS_CLEFS = ['INDIVIDUEL', 'DEUX', 'EQUIPE', 'PROJECTILE', 'BALLE', 'BALLON', 'MOYEN DE PROJECTION', 'RAQUETTE', 'BATTE', 'ARME', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'EAU', 'TAPIS', 'JAMBES', 'BRAS', 'COEUR', 'EPAULES', 'BUSTE', 'FESSES', 'EQUILIBRE', 'MOUVEMENT', 'COORDINATION', 'VITESSE', 'ENDURANCE', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'FORCE', 'PRECISION', 'SOUPLESSE', 'RYTHME_LENT', 'RYTHME_RAPIDE', 'ZEN']


PACKS = ['bien-etre', 'golf/athletisme', 'sports aquatiques', 'combat', 'sports de raquette', 'gym tonic', 'sports collectifs', 'cours de musculation', 'danses', 'boxe'] #On a un nombre limite de pakcs dans la base


SPORTS = ['etirements', 'golf', 'kendo', 'athletisme endurance', 'badminton', 'relaxation / respiration', 'circuit cardio training', 'futsal', 'musculation minceur', 'taekwondo', 'yoga', 'volley ball', 'zumba', 'abdo fessier', 'krav maga', 'ju jitsu bresilien', 'danse contemporaine', 'football', 'danse orientale', 'musculation', 'tennis de table', 'judo', 'mixed martial arts', 'karate', 'cardio et musculation', 'hip hop', 'tai chi chuan', 'step-fitness', 'boxe francaise savate', 'boxe francaise debutants', 'boxe anglaise', 'salsa libre', 'rock confirmes', 'natation sauvetage', 'basket ball mixte', 'musculation dietetique', 'salsa intermediaire', 'salsa debutant']

#On a selectionne un nombre limite de sports dans la base


ETIREMENTS = ['INDIVIDUEL', 'TAPIS', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'SOUPLESSE', 'POSITIONNEMENT', 'RYTHME_LENT', 'ZEN']
GOLF = ['INDIVIDUEL', 'DEUX', 'PROJECTILE', 'BALLE', 'MOYEN DE PROJECTION', 'BATTE', 'EXTERIEUR', 'TERRE', 'BRAS', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'PRECISION', 'RYTHME_LENT']
KENDO = ['INDIVIDUEL', 'ARME', 'INTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'MOUVEMENT', 'VITESSE', 'ENDURANCE', 'FORCE', 'PRECISION', 'POSITIONNEMENT', 'ANTICIPATION_TRAJECTOIRE', 'RYTHME_LENT', 'RYTHME_RAPIDE']
ATHLETISME_ENDURANCE = ['INDIVIDUEL', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'JAMBES', 'COEUR', 'MOUVEMENT', 'VITESSE', 'ENDURANCE', 'RYTHME_RAPIDE']
BADMINTON = ['INDIVIDUEL',  'DEUX',  'PROJECTILE',  'BALLE',  'MOYEN DE PROJECTION',  'RAQUETTE',  'INTERIEUR',  'TERRE',  'JAMBES',  'BRAS',  'COEUR',  'EQUILIBRE',  'MOUVEMENT',  'VITESSE',  'ENDURANCE',  'ANTICIPATION',  'TRAJECTOIRE',  'POSITIONNEMENT',  'PRECISION', 'RYTHME_RAPIDE']
RELAXATION_RESPIRATION = ['INDIVIDUEL', 'TAPIS', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'COEUR', 'POSITIONNEMENT', 'RYTHME_LENT', 'ZEN']
CIRCUIT_CARDIO_TRAINING = ['INDIVIDUEL', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'BRAS', 'JAMBES', 'COEUR', 'ENDURANCE', 'MOUVEMENT', 'VITESSE', 'RYTHME_RAPIDE']
FUTSAL = ['EQUIPE', 'PROJECTILE', 'BALLON', 'INTERIEUR', 'TERRE', 'JAMBES', 'COEUR', 'MOUVEMENT', 'COORDINATION', 'VITESSE', 'FORCE', 'ENDURANCE', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'RYTHME_RAPIDE']
MUSCULATION_MINCEUR = ['INDIVIDUEL', 'TAPIS', 'INTERIEUR',  'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'BUSTE', 'MOUVEMENT', 'ENDURANCE', 'FORCE', 'POSITIONNEMENT', 'RYTHME_LENT']
TAEKWONDO = ['INDIVIDUEL', 'TAPIS', 'KIMONO', 'INTERIEUR', 'TERRE', 'JAMBES', 'COEUR', 'MOUVEMENT', 'ENDURANCE', 'POSITIONNEMENT', 'RYTHME_RAPIDE']
YOGA = ['INDIVIDUEL', 'TAPIS', 'INTERIEUR', 'TERRE', 'ZEN', 'POSITIONNEMENT', 'RYTHME_LENT', 'ZEN']
VOLLEY_BALL = ['EQUIPE',  'PROJECTILE',  'BALLON',  'INTERIEUR',  'EXTERIEUR',  'TERRE',  'JAMBES',  'BRAS',  'COEUR',  'EQUILIBRE',  'MOUVEMENT',  'COORDINATION',  'VITESSE',  'ENDURANCE',  'ANTICIPATION_TRAJECTOIRE',  'POSITIONNEMENT',  'PRECISION',  'FORCE', 'RYTHME_RAPIDE']
ZUMBA = ['INDIVIDUEL', 'INTERIEUR', 'TERRE', 'JAMBES', 'FESSES', 'MOUVEMENT', 'ENDURANCE', 'VITESSE', 'POSITIONNEMENT', 'RYTHME_RAPIDE']
RUGBY = ['EQUIPE', 'PROJECTILE', 'BALLON', 'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'MOUVEMENT', 'COORDINATION', 'VITESSE', 'ENDURANCE', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'FORCE', 'RYTHME_RAPIDE']
ABDO_FESSIER = ['INDIVIDUEL', 'TAPIS', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'FESSES', 'BUSTE', 'MOUVEMENT', 'FORCE', 'RYTHME_LENT']
KRAV_MAGA = ['INDIVIDUEL', 'TAPIS', 'KIMONO', 'ARME', 'INTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'ENDURANCE', 'FORCE', 'MOUVEMENT', 'RYTHME_RAPIDE']
JU_JITSU_BRESILIEN = ['INDIVIDUEL', 'DEUX', 'TAPIS', 'INTERIEUR', 'KIMONO', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'BUSTE', 'EPAULES', 'MOUVEMENT', 'ENDURANCE', 'FORCE', 'EQUILIBRE', 'VITESSE', 'ANTICIPATION_TRAJECTOIRE', 'COORDINATION', 'RYTHME_RAPIDE']
DANSE_CONTEMPORAINE = ['INDIVIDUEL', 'INTERIEUR', 'TERRE', 'JAMBES', 'MOUVEMENT', 'POSITIONNEMENT', 'EQUILIBRE', 'MOUVEMENT', 'SOUPLESSE', 'RYTHME_LENT']
FOOTBALL = ['EQUIPE', 'PROJECTILE', 'BALLON', 'EXTERIEUR', 'TERRE', 'JAMBES', 'COEUR', 'MOUVEMENT', 'COORDINATION', 'VITESSE', 'ENDURANCE', 'FORCE', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'RYTHME_RAPIDE']
DANSE_ORIENTALE = ['INDIVIDUEL',  'DEUX',  'EQUIPE',  'INTERIEUR', 'TERRE',  'JAMBES',  'BRAS',  'COEUR',  'EQUILIBRE',  'MOUVEMENT',  'ENDURANCE',  'POSITIONNEMENT',  'SOUPLESSE',  'COORDINATION', 'RYTHME_RAPIDE']
MUSCULATION = ['INDIVIDUEL', 'TAPIS', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'BUSTE', 'MOUVEMENT', 'ENDURANCE', 'POSITIONNEMENT', 'FORCE', 'RYTHME_LENT']
TENNIS_DE_TABLE = ['INDIVIDUEL', 'DEUX', 'INTERIEUR', 'PROJECTILE', 'BALLE', 'MOYEN DE PROJECTION', 'RAQUETTE', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'MOUVEMENT', 'EQUILIBRE', 'COORDINATION', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'PRECISION', 'RYTHME_RAPIDE', 'RYTHME_LENT']
JUDO = ['INDIVIDUEL', 'INTERIEUR', 'KIMONO', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'BUSTE', 'EPAULES', 'MOUVEMENT', 'ENDURANCE', 'FORCE', 'EQUILIBRE', 'COORDINATION', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'PRECISION', 'RYTHME_RAPIDE', 'RYTHME_LENT']
MIXED_MARTIAL_ARTS = ['INDIVIDUEL', 'INTERIEUR', 'KIMONO', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'BUSTE', 'EPAULES', 'MOUVEMENT', 'VITESSE', 'ENDURANCE', 'FORCE', 'EQUILIBRE', 'COORDINATION', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'PRECISION', 'RYTHME_RAPIDE']
KARATE = ['INDIVIDUEL', 'DEUX', 'INTERIEUR', 'KIMONO', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'BUSTE', 'EPAULES', 'MOUVEMENT', 'VITESSE', 'ENDURANCE', 'FORCE', 'EQUILIBRE', 'COORDINATION', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'PRECISION', 'RYTHME_RAPIDE']
CARDIO_ET_MUSCULATION = ['INDIVIDUEL', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'EPAULES', 'BUSTE', 'FESSES', 'MOUVEMENT', 'VITESSE', 'ENDURANCE', 'FORCE', 'RYTHME_RAPIDE']
HIP_HOP = ['INDIVIDUEL', 'DEUX', 'EQUIPE', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'EQUILIBRE', 'MOUVEMENT', 'ENDURANCE', 'POSITIONNEMENT', 'SOUPLESSE', 'COORDINATION', 'RYTHME_LENT', 'RYTHME_RAPIDE']
TAI_CHI_CHUAN = ['INDIVIDUEL', 'DEUX', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'BUSTE', 'EQUILIBRE', 'MOUVEMENT', 'COORDINATION', 'POSITIONNEMENT', 'SOUPLESSE', 'RYTHME_LENT', 'ZEN']
STEP_FITNESS = ['INDIVIDUEL', 'INTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'EPAULES', 'BUSTE', 'FESSES', 'EQUILIBRE', 'MOUVEMENT', 'COORDINATION', 'VITESSE', 'ENDURANCE', 'POSITIONNEMENT', 'RYTHME_RAPIDE']
BOXE_FRANCAISE = ['INDIVIDUEL', 'INTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'EPAULES', 'BUSTE', 'EQUILIBRE', 'MOUVEMENT', 'COORDINATION', 'VITESSE', 'ENDURANCE', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'PRECISION', 'FORCE', 'RYTHME_RAPIDE']
BOXE_ANGLAISE = ['INDIVIDUEL', 'INTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'EPAULES', 'BUSTE', 'EQUILIBRE', 'MOUVEMENT', 'COORDINATION', 'VITESSE', 'ENDURANCE', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'PRECISION', 'FORCE', 'RYTHME_RAPIDE']
SALSA = ['INDIVIDUEL', 'DEUX', 'EQUIPE', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'EQUILIBRE', 'MOUVEMENT', 'ENDURANCE', 'POSITIONNEMENT', 'SOUPLESSE', 'COORDINATION', 'RYTHME_RAPIDE', 'RYTHME_LENT']
ROCK = ['INDIVIDUEL', 'DEUX', 'EQUIPE', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'SOLIDE', 'JAMBES', 'BRAS', 'COEUR', 'EQUILIBRE', 'MOUVEMENT', 'ENDURANCE', 'POSITIONNEMENT', 'SOUPLESSE', 'COORDINATION', 'RYTHME_RAPIDE', 'RYTHME_LENT']
NATATION = ['INDIVIDUEL', 'INTERIEUR', 'EAU', 'JAMBES', 'BRAS', 'COEUR', 'EPAULES', 'BUSTE', 'MOUVEMENT', 'ENDURANCE', 'FORCE', 'RYTHME_LENT', 'RYTHME_RAPIDE', 'ZEN']
BASKET = ['EQUIPE', 'PROJECTILE', 'BALLON', 'INTERIEUR', 'EXTERIEUR', 'TERRE', 'JAMBES', 'BRAS', 'COEUR', 'EQUILIBRE', 'MOUVEMENT', 'COORDINATION', 'VITESSE', 'ENDURANCE', 'ANTICIPATION_TRAJECTOIRE', 'POSITIONNEMENT', 'PRECISION', 'FORCE', 'RYTHME_RAPIDE']


DICO_SPORTS = dict()
DICO_SPORTS['etirements'] = ETIREMENTS
DICO_SPORTS['golf'] = GOLF
DICO_SPORTS['kendo'] = KENDO
DICO_SPORTS['athletisme endurance'] = ATHLETISME_ENDURANCE
DICO_SPORTS['badminton'] = BADMINTON
DICO_SPORTS['relaxation / respiration'] = RELAXATION_RESPIRATION
DICO_SPORTS['circuit cardio training'] = CIRCUIT_CARDIO_TRAINING
DICO_SPORTS['futsal'] = FUTSAL
DICO_SPORTS['musculation minceur'] = MUSCULATION_MINCEUR
DICO_SPORTS['taekwondo'] = TAEKWONDO
DICO_SPORTS['yoga'] = YOGA
DICO_SPORTS['volley ball'] = VOLLEY_BALL
DICO_SPORTS['zumba'] = ZUMBA
DICO_SPORTS['rugby'] = RUGBY
DICO_SPORTS['abdo fessier'] = ABDO_FESSIER
DICO_SPORTS['krav maga'] = KRAV_MAGA
DICO_SPORTS['ju jitsu bresilien'] = JU_JITSU_BRESILIEN
DICO_SPORTS['danse contemporaine'] = DANSE_CONTEMPORAINE
DICO_SPORTS['football'] = FOOTBALL
DICO_SPORTS['danse orientale'] = DANSE_ORIENTALE
DICO_SPORTS['musculation'] = MUSCULATION
DICO_SPORTS['musculation minceur'] = MUSCULATION
DICO_SPORTS['musculation dietetique'] = MUSCULATION
DICO_SPORTS['tennis de table'] = TENNIS_DE_TABLE
DICO_SPORTS['judo'] = JUDO
DICO_SPORTS['mixed martial arts'] = MIXED_MARTIAL_ARTS
DICO_SPORTS['karate'] = KARATE
DICO_SPORTS['cardio et musculation'] = CARDIO_ET_MUSCULATION
DICO_SPORTS['hip hop'] = HIP_HOP
DICO_SPORTS['tai chi chuan'] = TAI_CHI_CHUAN
DICO_SPORTS['step-fitness'] = STEP_FITNESS
DICO_SPORTS['boxe francaise savate'] = BOXE_FRANCAISE
DICO_SPORTS['boxe francaise debutants'] = BOXE_FRANCAISE
DICO_SPORTS['boxe anglaise'] = BOXE_ANGLAISE
DICO_SPORTS['salsa libre'] = SALSA
DICO_SPORTS['salsa debutant'] = SALSA
DICO_SPORTS['salsa intermediaire'] = SALSA
DICO_SPORTS['rock confirmes'] = ROCK
DICO_SPORTS['natation sauvetage'] = NATATION
DICO_SPORTS['basket ball mixte'] = BASKET


def TF():
    DICO_SPORTS_TF = dict()
    for sport in SPORTS:
        liste_TF = []
        for i in range(len(ENSEMBLE_MOTS_CLEFS)):
            if ENSEMBLE_MOTS_CLEFS[i] in DICO_SPORTS[sport]:
                liste_TF.append(float(1)/len(DICO_SPORTS[sport])) #chaque mot-clef n'apparait qu'une fois dans la liste des mots-clefs d'un sport
            else:
                liste_TF.append(0)
        DICO_SPORTS_TF[sport] = liste_TF
    return DICO_SPORTS_TF

DICO_SPORTS_TF = TF()

def IDF():
    LISTE_SPORTS_IDF = []
    for motClef in ENSEMBLE_MOTS_CLEFS:
        cpt = 0
        for sport in SPORTS:
            if motClef in DICO_SPORTS[sport]:
                cpt += 1
        LISTE_SPORTS_IDF.append(cpt)
    return [np.log(k) for k in LISTE_SPORTS_IDF]

LISTE_IDF = IDF()

def TFIDF():
    DICO_SPORTS_TFIDF = dict()
    for sport in SPORTS:
        liste_TFIDF = []
        for i in range(len(ENSEMBLE_MOTS_CLEFS)):
            liste_TFIDF.append(LISTE_IDF[i] * DICO_SPORTS_TF[sport][i])
        DICO_SPORTS_TFIDF[sport] = liste_TFIDF
    return DICO_SPORTS_TFIDF

DICO_SPORTS_TFIDF = TFIDF()


