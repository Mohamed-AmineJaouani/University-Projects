import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class LoadGraph {
    static HashMap<Integer, Sommet> graphe = new HashMap<Integer, Sommet>();
    
    public static HashMap<Integer,Sommet> charger_graph(String filename, boolean oriented){
	FileReader fileReader = null;
	BufferedReader br = null;
	try{
	    fileReader = new FileReader(filename);
	    br = new BufferedReader(fileReader);
	}catch(FileNotFoundException fnfe){
	    System.out.println("Fichier introuvable : "+filename);
	    System.exit(0);
	}
	String line = "";
	try{
	    line = br.readLine();//on ignore la premiere ligne dans ce cas car on n'a pas besoin du nom du graph et on se limite aux graphes orientees.		
	    line = br.readLine();
	    do{	
		Sommet s1, s2, scmp;
		String[] tab = line.trim().split(" -> | -- | ;| \t");

		//si on trouve le character '}' on peut sortir de la boucle car on a atteint la fin du fichier
		if(tab[0].equals("}")) break;

		int ids1 = Integer.parseInt(tab[0]);
		
		if((s1 = graphe.get(ids1)) == null){ //on verifie que ce sommet n'existe pas deja
		    s1 = new Sommet(ids1);
		    graphe.put(ids1,s1);
		}
		
		if(tab.length != 1){
		    int ids2 = Integer.parseInt(tab[1]);
		    if((s2 = graphe.get(ids2)) == null){
			s2 = new Sommet(ids2);			
			graphe.put(ids2,s2);
		    }

		    if(oriented){
			s1.voisins.put(ids2,s2);
			s2.voisinsEntrants.put(ids1,s1);
		    }
		    else{
			s1.voisins.put(ids2,s2);
			s2.voisins.put(ids1,s1);
		    }
		}
	    }while((line = br.readLine()) != null);
	}catch(IOException ioe){
	    System.exit(0);
	}
	return graphe;
    }
    
    public static int maxId(){
	int max = 0;
	for(int i : graphe.keySet())
	    if(i > max)
		max = i;
	return max;
    }
}
