import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class LoadGraph {
    
    public static ArrayList<Sommet> charger_graph(String filename){
	FileReader fileReader = null;
	BufferedReader br = null;
	try{
	    fileReader = new FileReader(filename);
	    br = new BufferedReader(fileReader);
	}catch(FileNotFoundException fnfe){
	    System.out.println("Fichier introuvable : "+filename);
	    System.exit(0);
	}
	ArrayList<Sommet> listeAdjacence = new ArrayList<Sommet>();
	String line = "";
	try{
	    line = br.readLine();//on ignore la premiere ligne dans ce cas car on n'a pas besoin du nom du graph et on se limite aux graphes orientees.		
	    line = br.readLine();
	    do{	
		Sommet s1, s2, scmp;
		String[] tab = line.trim().split(" |->|;");

		//si on trouve le character '}' on peut sortir de la boucle car on a atteint la fin du fichier
		if(tab[0].equals("}")) break;
		
		if((scmp = contient(listeAdjacence, Integer.parseInt(tab[0]))) == null){ //on verifie que ce sommet n'existe pas deja
		    s1 = new Sommet(Integer.parseInt(tab[0]));
		    listeAdjacence.add(s1);
		}
		else
		    s1 = scmp;
		
		if(tab.length != 1){
		    if((scmp = contient(listeAdjacence, Integer.parseInt(tab[3]))) == null){
			s2 = new Sommet(Integer.parseInt(tab[3]));			
			listeAdjacence.add(s2);
		    }
		    else
			s2 = scmp;
		    
		    s1.voisins.add(s2);
		}
	    }while((line = br.readLine()) != null);
	}catch(IOException ioe){
	    System.exit(0);
	}
	return listeAdjacence;
    }
    
    //j'ai besoin que la fonction retourne le sommet trouvé pour l'utiliser par la suite, si il n'existe pas on retourne null
    public static Sommet contient(ArrayList<Sommet> graph, int idSommet){ 
	for(Sommet s : graph)
	    if(s.id == idSommet)
		return s;
	return null;
    }
    
    public static int maxEntrants(ArrayList<Sommet> graph){
	int max = 0;
	int cpt = 0;
	for(Sommet s : graph){
	    for(Sommet ss : graph)
		if(s != ss)
		    if(contient(ss.voisins, s.id) != null)
			cpt++;
	    if(cpt >= max)
		max = cpt;
	    cpt = 0;
	}
	return max;
    }
    
    public static int maxId(ArrayList<Sommet> graph){
	int max = 0;
	for(Sommet s : graph)
	    if(s.id >= max)
		max = s.id;
	return max;
    }
}
