#!/usr/bin/python2.7
import random
    
def split_list(f):
    l = [[]]
    l1 = []
    l2 = []
    l3 = []
    l4 = []
    
    with f:
        while True:
            line = f.readline()
            if line == '':
                break

            sexe1, domaine1, niveau1, sport1, pack1, jour1, heure_debut1, heure_fin1, _ = line.split('|',8)
            if sport1 != '': #on stock la ligne du sport 1
                l1 += [sport1, pack1, jour1, heure_debut1, heure_fin1]

            line = f.readline()
            sexe2, domaine2, niveau2, sport2, pack2, jour2, heure_debut2, heure_fin2, _ = line.split('|',8)
            if sport2 != '': #on stock la ligne du sport 2
                l2 += [sport2, pack2, jour2, heure_debut2, heure_fin2]
                line = f.readline()
                sexe3, domaine3, niveau3, sport3, pack3, jour3, heure_debut3, heure_fin3, _ = line.split('|',8)
                if sport3 == '': #on a un utilisateur donc in peut le creer
                    l += [[sexe3, domaine3, niveau3, sport1, pack1, jour1, heure_debut1, heure_fin1, sport2, pack2, jour2, heure_debut2, heure_fin2]]
                else: #sinon il s'agit d un deuxieme utilisateur selon le excel
                    l3 += [sport3, pack3, jour3, heure_debut3, heure_fin3]
                    line = f.readline()
                    sexe4, domaine4, niveau4, sport4, pack4, jour4, heure_debut4, heure_fin4, _ = line.split('|',8)
                    
                    if sport4 == '': #on a un utilisateur et le suivant juste apres selon excel
                        l += [[sexe4, domaine4, niveau4, sport1, pack1, jour1, heure_debut1, heure_fin1, sport2, pack2, jour2, heure_debut2, heure_fin2]]
                        line = f.readline()
                        sexe5, domaine5, niveau5, sport5, pack5, jour5, heure_debut5, heure_fin5, _ = line.split('|',8)
                        l += [[sexe5, domaine5, niveau5, sport4, pack4, jour4, heure_debut4, heure_fin4, sport5, pack5, jour5, heure_debut5, heure_fin5]]
                    else: #on a encore une 4eme ligne de sport et deux utilisateur juste apres
                        l4 += [sport4, pack4, jour4, heure_debut4, heure_fin4]
                        line = f.readline()
                        sexe5, domaine5, niveau5, sport5, pack5, jour5, heure_debut5, heure_fin5, _ = line.split('|',8)
                        l += [[sexe5, domaine5, niveau5, sport1, pack1, jour1, heure_debut1, heure_fin1, sport2, pack2, jour2, heure_debut2, heure_fin2]]
                        line = f.readline()
                        sexe6, domaine6, niveau6, sport6, pack6, jour6, heure_debut6, heure_fin6, _ = line.split('|',8)
                        l += [[sexe6, domaine6, niveau6, sport4, pack4, jour4, heure_debut4, heure_fin4, sport5, pack5, jour5, heure_debut5, heure_fin5]]
            else:
                l += [[sexe2, domaine2, niveau2, sport1, pack1, jour1, heure_debut1, heure_fin1, sport2, pack2, jour2, heure_debut2, heure_fin2]]

    return l[1:]

def random_sexe():
    r = random.randint(1,2)
    if r == 1:
        return 'F'
    else:
        return 'M'
    
def complete_sexes(l):
    for list in l:
        if not len(list) == 0:
            if list[0] == '':
                list[0] = random_sexe()

NUM_PACK = 4
NUM_STUDY_FIELD = 1
NUM_SPORT = 3
NUM_JOUR = 5

def remplirDico(l):
    dSF , dS, dP, dJ = dict(), dict(), dict(), dict()

    cptSF, cptS, cptP, cptJ = 1,1,1,1
    for line in l:
        lineSF, lineS, lineP, lineJ = line[NUM_STUDY_FIELD].lower(), line[NUM_SPORT].lower(), line[NUM_PACK].lower(), line[NUM_JOUR].lower()
        if lineSF != '' and lineSF not in dSF.keys():
            dSF[lineSF] = cptSF
            cptSF += 1    
        if lineS != '' and lineS not in dS.keys():
            dS[lineS] = cptS
            cptS += 1    
        if lineP != '' and lineP not in dP.keys():
            dP[lineP] = cptP
            cptP += 1    
        if lineJ != '' and lineJ not in dJ.keys():
            dJ[lineJ] = cptJ
            cptJ += 1    
    return dSF, dS, dP, dJ

def parse_txt(fichier):
    f = open(fichier, 'r')
    l = split_list(f)
    complete_sexes(l)
    dSF, dS, dP, dJ  = remplirDico(l)
    return dSF, dS, dP, dJ
    

dSF, dS, dP, dJ = parse_txt('bd.txt')
