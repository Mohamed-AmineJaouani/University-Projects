import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Propagation {

    private static void usage(){
	System.out.println("Usage :\njava Propagation [-v] fichier.dot nbSommetsInfectes (-tc1/-tc3) (nbVoisinsInfectes/ProbaInfection) (-tg1/-tg2) (nbToursAvantGuerisons/ProbaInfection) (-td1/-td2) (nbTourAvantImmunisation/ProbaImmunisation) MaxTours\n\tExemple: java Propagation [-v] network.dot 10 -tc1 2 -tg1 2 -td1 4 1000\n\tExemple: java Propagation [-v] network.dot 10 -tc3 0.5 -tg2 0.2 -td1 4 1000\n\tExemple: java Propagation [-v] network.dot 10 -tc3 0.5 -tg2 0.2 -td1 0.7 1000");
	System.exit(0);
    }

    public static int nbVoisinsInfectes(Sommet s, boolean oriented){
	int nb = 0;
	if(!oriented){
	    for(Sommet v : s.voisins)
		if(v.etat == 'I')
		    nb++;
	}
	else 
	    for(Sommet v : s.voisinsEntrants)
		if(v.etat == 'I')
		    nb++;
	return nb;
    }

    public static boolean contaminationVoisins(Sommet[] graph, int nbVoisinsInfectes, boolean oriented){
	boolean changement = false;

	for(Sommet s : graph)
	    if(s != null)
		if(nbVoisinsInfectes(s,oriented) >= nbVoisinsInfectes)
		    if(s.etat == 'S'){
			s.etat = 'I';
			changement = true;
		    }
	return changement;
    }
    public static double produitProbaVoisins(Sommet s, double pc, boolean oriented){
	if(s.voisins.size() == 0)
	    return 0.0;
	double proba = 1.0;
	if(!oriented)
	    for(Sommet voisin : s.voisins)
		proba *= pc;
	else
	    for(Sommet voisin : s.voisinsEntrants)
		proba *= pc;
	return proba;
    }
    
    public static boolean contaminationProba(Sommet[] graph, double pc, boolean oriented){
	boolean changement = false;
	double rand;
	for(Sommet s : graph)
	    if(s != null){
		rand = Math.random();
		if(rand >= produitProbaVoisins(s,pc, oriented))
		    if(s.etat == 'S'){
			s.etat = 'I';
			changement = true;
		    }
	    }
	return changement;
    }

    public static boolean guerisonTours(Sommet[] graph, int nbToursAvantGuerison){
	boolean changement = false;
	for(Sommet s : graph)
	    if(s != null)
		if(s.etat == 'I' && s.tourinfecte == nbToursAvantGuerison){
		    s.etat = 'R';
		    changement = true;
		}
		else
		    s.tourinfecte++;
	return changement;
    }

    public static boolean guerisonProba(Sommet[] graph, double pg){
	boolean changement = false;
	double rand;
	for(Sommet s : graph)
	    if(s != null){
		rand = Math.random();
		if(s.etat == 'I' && rand < pg){
		    s.etat = 'R';
		    changement = true;
		}
		else
		    s.tourinfecte++;
	    }
	return changement;
    }
    
    public static boolean desimmunisationTours(Sommet[] graph, int nbToursAvantImmunisation){
	boolean changement = false;
	for(Sommet s : graph)
	    if(s != null)
		if(s.etat == 'R' && s.tourgueri == nbToursAvantImmunisation){
		    s.etat = 'S';
		    changement = true;
		}
		else
		    s.tourgueri++;
	return changement;
    }

    public static boolean desimmunisationProba(Sommet[] graph, double pd){
	boolean changement = false;
	double rand;
	for(Sommet s : graph)
	    if(s != null){
		rand = Math.random();
		if(s.etat == 'R' && rand < pd){
		    s.etat = 'S';
		    changement = true;
		}
		else
		    s.tourgueri++;
	    }
	return changement;
    }

    public static void writeVerboseInFile(Sommet[] graph, int tour){
	FileWriter file = null;
	PrintWriter pr = null;
	try{
	    file = new FileWriter("verbose.txt", true);
	    pr = new PrintWriter(new BufferedWriter(file));
	}catch(IOException ioe){
	    System.out.println(ioe);
	    System.exit(0);
	}

	String affichage = "Tour "+tour+" : ";
	for(Sommet s : graph)
	    if(s != null)
		affichage += s.toStringEtat();
        pr.println(affichage);
	pr.flush();
	pr.close();
    }
    
    public static void main(String[] args){
	if(args.length != 9 && args.length != 10)
	    usage();
	else{
	    Sommet[] graph = null;
	    int sommetsInf = 0;
	    int nbVoisinsInfectes = 0;
	    double pc = 0.0;
	    int nbToursAvantGuerison=0;
	    double pg = 0.0;
	    int nbToursAvantDesimmunisation = 0;
	    double pd = 0.0;

	    int indiceFichier = 0, indiceSommetsDepartsInfectes = 0, indiceOptionC = 0, indiceC = 0, indiceOptionG = 0, indiceG = 0, indiceOptionD = 0, indiceD = 0, indiceMaxTours = 0;
	    
	    boolean verbose = false;
	    boolean oriented = false;
	    try{
		if(args.length == 10){
		    if(args[0].equals("-v"))
			verbose = true;
		    else if(args[0].equals("-o"))
			oriented = true;
		    else if(args[0].equals("-vo")){
			verbose = true;
			oriented = true;
		    }
		    indiceFichier = 1;
		    indiceSommetsDepartsInfectes = 2;
		    indiceOptionC = 3;
		    indiceC = 4;
		    indiceOptionG = 5;
		    indiceG = 6;
		    indiceOptionD = 7;
		    indiceD = 8;
		    indiceMaxTours = 9;
		}
		else if(args.length == 9){
		    indiceFichier = 0;
		    indiceSommetsDepartsInfectes = 1;
		    indiceOptionC = 2;
		    indiceC = 3;
		    indiceOptionG = 4;
		    indiceG = 5;
		    indiceOptionD = 6;
		    indiceD = 7;
		    indiceMaxTours = 8;
		}
		else
		    usage();
		graph = LoadGraph.charger_graph(args[indiceFichier], oriented);
		sommetsInf = Integer.parseInt(args[indiceSommetsDepartsInfectes]);
		if(args[indiceOptionC].equals("-tc1"))
		    nbVoisinsInfectes = Integer.parseInt(args[indiceC]);
		else if(args[indiceOptionC].equals("-tc3")){
		    pc = Double.parseDouble(args[indiceC]);
		    if(pc < 0.0 || pc > 1.0){
			System.out.println("La probabilité pc doit etre entre 0 et 1");
			System.exit(0);
		    }
		}
		else
		    usage();
		
		if(args[indiceOptionG].equals("-tg1"))
		    nbToursAvantGuerison = Integer.parseInt(args[indiceG]);
		else if(args[indiceOptionG].equals("-tg2")){
		    pg = Double.parseDouble(args[indiceG]);
		    if(pg < 0.0 || pg > 1.0){
			System.out.println("La probabilité pg doit etre entre 0 et 1");
			System.exit(0);
		    }
		}
		else usage();
		
		if(args[indiceOptionD].equals("-td1"))
		    nbToursAvantDesimmunisation = Integer.parseInt(args[indiceD]);
		else if(args[indiceOptionD].equals("-td2")){
		    pd = Double.parseDouble(args[indiceD]);
		    if(pd < 0.0 || pd > 1.0){
			System.out.println("La probabilité pd doit etre entre 0 et 1");
			System.exit(0);
		    }
		}
		else usage();
	    
		if(sommetsInf == graph.length){
		    System.out.println("Vous ne pouvez pas infecter tous les sommets au départ");
		    System.exit(0);
		}
		else if(sommetsInf > graph.length){
		    System.out.println("Le nombre de sommet infectés est supérieurs au nombre de sommets dans le graphe");
		    System.exit(0);
		}
	    
		int rand;
		for(int i = 0 ; i < sommetsInf ; i++){
		    rand = (int)(Math.random()*graph.length);
		    if(graph[rand] != null && graph[rand].etat == 'S')
			graph[rand].etat = 'I';
		    else
			i--;
		}
	    
		int tour = 0;
		File f = null;
		f = new File("verbose.txt");
		f.delete();
		while(tour < Integer.parseInt(args[indiceMaxTours])){
		    if(verbose)
			writeVerboseInFile(graph, tour);
		    tour++;
		    boolean contamination = false;
		    if(args[indiceOptionC].equals("-tc1"))
			contamination = contaminationVoisins(graph, nbVoisinsInfectes, oriented);
		    else if(args[indiceOptionC].equals("-tc3"))
			contamination = contaminationProba(graph, pc, oriented);
		    else
			usage();

		    boolean guerison = false;
		    if(args[indiceOptionG].equals("-tg1"))
			guerison = guerisonTours(graph, nbToursAvantGuerison);
		    else if(args[indiceOptionG].equals("-tg2"))
			guerison = guerisonProba(graph, pg);
		    else usage();

		    boolean desimmunisation = false;
		    if(args[indiceOptionD].equals("-td1"))
			desimmunisation = desimmunisationTours(graph, nbToursAvantDesimmunisation);
		    else if(args[indiceOptionD].equals("-td2"))
			desimmunisation = desimmunisationProba(graph, pd);
		    else usage();
		
		    if(!contamination && !guerison && !desimmunisation)
			break;
		}
	    
		if(verbose)
		    writeVerboseInFile(graph, tour);
		System.out.println("Nombre de tours : "+tour);
		int nbSommetsInfectes = 0;
		int nbSommetsSains = 0;
		int nbSommetsResistants = 0;
		for(Sommet s : graph){
		    if(s != null){
			if(s.etat == 'S')
			    nbSommetsSains++;
			else if(s.etat == 'I')
			    nbSommetsInfectes++;
			else if(s.etat == 'R')
			    nbSommetsResistants++;
		    }
		}
		System.out.println("Nombre de sommets Infectés : "+nbSommetsInfectes);
		System.out.println("Nombre de sommets Sains : "+nbSommetsSains);
		System.out.println("Nombre de sommets Résistants : "+nbSommetsResistants);
	    }
	    catch(NumberFormatException nfe){
		System.out.println("Erreur d'arguments : entrez des entiers pour tc1 tg1 et td1 et des reels pour tc3 tg2 et td2");
		usage();
	    }
	}
    }
}
