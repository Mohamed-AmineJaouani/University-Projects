import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.io.RandomAccessFile;

public class LoadGraph {
    
    
    public static Sommet[] charger_graph(String filename, boolean oriented){
	FileReader fileReader = null;
	BufferedReader br = null;
	try{
	    fileReader = new FileReader(filename);
	    br = new BufferedReader(fileReader);
	}catch(FileNotFoundException fnfe){
	    System.out.println("Fichier introuvable : "+filename);
	    System.exit(0);
	}

	
	Sommet[] graphe = null;
	String line = "";
	try{
	    br.readLine();//on ignore la premiere ligne dans ce cas car on n'a pas besoin du nom du graph
	    line = br.readLine();
	    int max = 0;
	    do{
		String[] tab = line.trim().split(" -> | -- | ;| \t|;");
		if(tab[0].equals("}")) break;
		int id1 = Integer.parseInt(tab[0]), id2 = Integer.parseInt(tab[1]);
		if(id1 > max)
		    max = id1;
		if(id2 > max)
		    max = id2;
	    }while((line = br.readLine()) != null);

	    graphe = new Sommet[max+1];
	    
	    fileReader = new FileReader(filename);
	    br = new BufferedReader(fileReader);

	    br.readLine();//on ignore la premiere ligne dans ce cas car on n'a pas besoin du nom du graph
	    line = br.readLine();
	    do{	
		Sommet s1, s2;
		String[] tab = line.trim().split(" -> | -- | ;| \t|;");
		
		//si on trouve le character '}' on peut sortir de la boucle car on a atteint la fin du fichier
		if(tab[0].equals("}")) break;
		
		int ids1 = Integer.parseInt(tab[0]);
		if((s1 = graphe[ids1]) == null){ //on verifie que ce sommet n'existe pas deja
		    s1 = new Sommet(ids1);
		    graphe[ids1] = s1;
		}
		
		if(tab.length != 1){
		    int ids2 = Integer.parseInt(tab[1]);
		    if((s2 = graphe[ids2]) == null){
			s2 = new Sommet(ids2);
			graphe[ids2] = s2;
		    }
		    
		    if(oriented){
			s1.voisins.add(s2);
			s2.voisinsEntrants.add(s1);
		    }
		    else{
			if(!s1.voisins.contains(s2)){
			    s1.voisins.add(s2);
			    s2.voisins.add(s1);
			}
		    }
		}
	    }while((line = br.readLine()) != null);
	}catch(IOException ioe){
	    System.out.println(ioe);
	    System.exit(0);
	}
	
	return graphe;
    }
    
    public static int maxId(Sommet[] graph){
	return graph.length-1;
    }
}
