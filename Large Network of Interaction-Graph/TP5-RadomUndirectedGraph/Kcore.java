import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Kcore {

    public static void renumerotation(ArrayList<Sommet> graph){
	Collections.sort(graph, new Comparator<Sommet>() {
		public int compare(Sommet sommet1, Sommet sommet2){
		    return ((Integer)(sommet1.id)).compareTo((Integer)(sommet2.id));
		}
	    });

	int i = 0;
	for(Sommet s : graph)
	    s.id = i++;
    }

    public static void writeInFile(String filename, ArrayList<Sommet> graph, boolean oriented){
	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	try{
	    PrintWriter writer = new PrintWriter(filename, "UTF-8");
	    if(oriented)
		writer.println("digraph "+filename.substring(0,filename.length()-4)+" {");
	    else
		writer.println("graph "+filename.substring(0,filename.length()-4)+" {");
	    for(Sommet s : graph){
		if(s.voisins.isEmpty())
		    writer.println(s.id+";");
		else
		    for(Sommet voisin : s.voisins){
			if(oriented)
			    writer.println(s.id+" -> "+voisin.id+" ;");
			else{
			    if(map.keySet().contains(s.id)){
				if(map.get(s.id) == voisin.id){}
				
			    }
			    if(map.keySet().contains(voisin.id)){
				if(map.get(voisin.id) == s.id){}
			    }
			    else{// si on a deja 1 -- 2 et qu'on tombe sur 2 -- 1   1 -- 3
				writer.println(s.id+" -- "+voisin.id+" ;");
				map.put(s.id, voisin.id);
			    }
			}
		    }
	    }
	    writer.println("}");
	    writer.close();
	}catch(FileNotFoundException fnfe){
	    System.out.println("erreur de fichier"+fnfe);
	}
	catch(UnsupportedEncodingException uee){
	    System.out.println("type d'encodage inconnue"+uee);
	}
    }
    
    public static void calculerKcoeurs(ArrayList<Sommet> graph, int k){
	boolean changement;
	ArrayList<Sommet> sommetsASupprimer = new ArrayList<Sommet>();
	do{
	    changement = false;
	    for(Sommet s : graph)
		if(s.voisins.size() < k){
		    for(Sommet ve : s.voisinsEntrants)
			ve.voisins.remove(s);
		    for(Sommet vs : s.voisins)
			vs.voisinsEntrants.remove(s);
		    sommetsASupprimer.add(s);
		    changement = true;
		}
		
	    for(Sommet s : sommetsASupprimer)
		graph.remove(s);
		
	}while(changement);
    }
    
    public static void main(String[] args){
	if(args.length == 1 && args[0].equals("-help"))
	    System.out.println("Utilisation:\n\tjava Kcore [-r] Fichier.dot entier FichierSortie.dot [orienté (1:oui,0:non)]\nExemple: java Kcore [-r] Exemple.dot 1 k1.dot 1");
	else if(args.length != 4 && args.length != 5)
	    System.out.println("Veuillez entrer le bon nombre d'argument (java Kcore -help)");
	else{
	    int indiceDotEntree=0, indiceK=0, indiceDotSortie=0;
	    boolean oriented = true;
	    if(!args[0].equals("-r") && args.length == 4){
		indiceDotEntree = 0;
		indiceK = 1;
		indiceDotSortie = 2;
		oriented = (Integer.parseInt(args[3]) == 1)? true : false;
	    }
	    else if(args[0].equals("-r") && args.length == 5){
		indiceDotEntree = 1;
		indiceK = 2;
		indiceDotSortie = 3;
		oriented = (Integer.parseInt(args[4]) == 1)? true : false;
	    }
	    else {
		System.out.println("Mauvais arguments (java -help)");
		System.exit(0);
	    }
	    
	    ArrayList<Sommet> graph = LoadGraph.charger_graph(args[indiceDotEntree], oriented);
	    int k = Integer.parseInt(args[indiceK]);

	    /* if(k < 0){ //neccessaire ? dans le sujet il est écrit entier et non entier positif
		System.out.println("Veuillez entrer un k positif en argument");
		System.exit(0);
		}*/
	    if(args[indiceDotSortie].length() > 4){
		String extension = args[indiceDotSortie].substring(args[indiceDotSortie].length()-4);
		if(!extension.equals(".dot"))
		    System.out.println("Veuillez entrer un fichier .dot comme dernier argument");
		else{
		    calculerKcoeurs(graph,k);
		    int arcs = 0;
		    for(Sommet s : graph)
			arcs += s.voisins.size();
		    System.out.println("n="+graph.size()+" m="+arcs);

		    if(args[0].equals("-r"))
			renumerotation(graph);

		    writeInFile(args[indiceDotSortie],graph, oriented);
		}
	    }
	    else
		System.out.println("Veuillez entrer un nom de fichier .dot en sortie");
	}
    }
}
