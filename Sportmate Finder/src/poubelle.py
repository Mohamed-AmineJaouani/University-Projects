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


def complete_heures(l):
    for list in l:
        if list[6] == '':
            heure_debut = random.randint(8,20)
            minute_debut = random.randint(1,2)
            if minute_debut == 1:
                minute_debut = ''
            else:
                minute_debut = '30'
                
            list[6] = str(heure_debut) + 'h' + minute_debut
            fin = random.randint(1,3)
            if fin == 1:
                list[7] = str(heure_debut+1) + 'h' + minute_debut
            elif fin == 2:
                if minute_debut == 1:
                    list[7] = str(heure_debut+1) + 'h30'
                else:
                    list[7] = str(heure_debut+2) + 'h'
            else:
                list[7] = str(heure_debut+2) + 'h' + minute_debut

def complete_sport1(l):
    sport1 = []
    for list in l:
        if not list[3] in sport1 and not list[3] == '':
            sport1 += [list[3]]
    sport1 = sport1[1:]

    for list in l:
        if list[3] == '':
            list[3] = sport1[random.randint(0,len(sport1)-1)]

def complete_sport2(l):
    sport2 = []
    for list in l:
        if not list[3] in sport2 and not list[3] == '':
            sport2 += [list[3]]
    sport2 = sport2[1:]

    for list in l:
        if list[4] == '':
            list[4] = sport2[random.randint(0,len(sport2)-1)]


def complete_sports(l):
    complete_sport1(l)
    complete_sport2(l)
        
def complete_jours(l):
    jour = ['lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi']
    for list in l:
        if list[5] == '':
            list[5] = jour[random.randint(0,len(jour)-1)]
def complete_domaines(l):
    domaine = []
    for list in l:
        if not list[1] in domaine:
            domaine += [list[1]]
    domaine = domaine[1:]

    for list in l:
        if list[1] == '':
            list[1] = domaine[random.randint(0,len(domaine)-1)]

def complete_niveaux(l):
    niveaux = [1,2,3,4,5]
    for list in l:
        if list[2] == '':
            list[2] = niveaux[random.randint(0,len(niveaux)-1)

def randomCountry():
    t = ['france','espagne','italie','usa']
    r = int(random.uniform(0,100))
    if r < 80:
        return t[0]
    elif r >= 80 and r < 90:
        return t[1]
    elif r >= 90 and r< 95:
        return t[2]
    else:
        return t[3].lower()
