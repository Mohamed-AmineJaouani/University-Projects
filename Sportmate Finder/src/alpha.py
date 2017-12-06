from constantes import *
from Base import *
from algo import *

base = Base()
#base.destroy()
#base.init()
cursor = base.db.cursor()


#for a in SPORTS:
    #cursor.execute("SELECT sport.name, pack.name FROM sport INNER JOIN pack WHERE sport.name LIKE ?",(a,))
    #cursor.execute("SELECT sport.name, pack.name FROM sport INNER JOIN pack WHERE pack.id = sport.id_pack AND sport.name LIKE ?",(a,))
    #cursor.execute("SELECT pack.name FROM sport INNER JOIN pack WHERE sport.name LIKE ?",(a,))
    #for row in cursor:
    #row = cursor.fetchall()
        #print a,'  ->  ',row


#cursor.execute("SELECT * FROM user")
#for row in cursor: print row

def printAlpha(base, id1, id2): 
    cursor.execute("SELECT * FROM user WHERE id = ?",(id1,))
    for row in cursor: print row
    cursor.execute("SELECT * FROM user WHERE id = ?",(id2,))
    for row in cursor: print row

    print '\n--tour id1:',id1
    cursor.execute("SELECT sport.name FROM sport WHERE id IN (SELECT id_sport FROM sport_liked WHERE id_user = ?)",(id1,))
    for row in cursor:
        print row[0]

    print '\n--tour id2:',id2
    cursor.execute("SELECT sport.name FROM sport WHERE id IN (SELECT id_sport FROM sport_liked WHERE id_user = ?)",(id2,))
    for row in cursor:
        print row[0]

    print '\nsous_distance_sport_level =',sous_distance_sport_level(base, id1, id2)
    print 'sous_distance_age =',sous_distance_age(base, id1, id2)
    print 'sous_distance_ville =',sous_distance_ville(base, id1, id2)
    print 'sous_distance_study_field =',sous_distance_study_field(base, id1, id2)
    print 'sous_distance_study_level =',sous_distance_study_level(base, id1, id2)
    print 'distance_informations =',distance_informations(base, id1, id2)
    print 'distance_gouts =',distance_gouts(base, id1, id2)



idA = 1
idB = 4
idC = 6
idD = 10
idE = 7
idF = 3
print '\n\t--- ROULEMENT DE TAMBOURIN ---'
printAlpha(base, idA, idB)
print distance_utilisateur(base, idA, idB)

print '\n\t--- ROULEMENT DE TAMBOURIN ---'
printAlpha(base, idA, idC)
print distance_utilisateur(base, idA, idC)

print '\n\t--- ROULEMENT DE TAMBOURIN ---'
printAlpha(base, idA, idD)
print distance_utilisateur(base, idA, idD)

print '\n\t--- ROULEMENT DE TAMBOURIN ---'
printAlpha(base, idB, idC)
print distance_utilisateur(base, idB, idC)

print '\n\t--- ROULEMENT DE TAMBOURIN ---'
printAlpha(base, idB, idD)
print distance_utilisateur(base, idB, idD)

print '\n\t--- ROULEMENT DE TAMBOURIN ---'
printAlpha(base, idC, idD)
print distance_utilisateur(base, idC, idD)

print '\n\t--- ROULEMENT DE TAMBOURIN ---'
printAlpha(base, idE, idF)
print distance_utilisateur(base, idE, idF)


def printSomeUsers():
    for id in range(20):
        print '\n--tour id:',id
        cursor.execute("SELECT sport.name FROM sport WHERE id IN (SELECT id_sport FROM sport_liked WHERE id_user = ?)",(id,))
        for row in cursor:
            print row[0]



print '10sorensen :',dice_sorensen('basket ball mixte', 'golf')


#comparer avec similarite_utilisateurs(l1, l2)
