import java.util.HashMap;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.ArrayList;

public class BarabasiAlbert {

    private static void usage(){
	System.out.println("Veuillez entrer 5 arguments : d, no, n, orientation(true, false) et fichier.dot\nExemple : java BarabasiAlbert 5 6 15 true a.dot");
    }

    public static Sommet randomSommet(HashMap<Sommet, Integer> map){
	int size = 0;
	for(Integer deg : map.values())
	    size += deg;
	
	Sommet[] tab = new Sommet[size];
	int cpt = 0;

	for(Sommet i : map.keySet())
	    for(int j = 0 ; j < map.get(i) ; j++)
		tab[cpt++] = i;
	    	
	int r = (int)Math.random()*tab.length;
	return tab[r];
    }
    
    public static Sommet[] createRandomGraphBA(int d, int no, int n, boolean oriented){
	Sommet[] graph = new Sommet[n];
	int nbArretes = 0;

	for(int i = 0 ; i < no ; i++)
	    graph[i] = new Sommet(i);
	
	for(int i = 0 ; i < no ; i++)
	    for(int j = i+1 ; j < no ; j++){
		graph[i].voisins.add(graph[j]);  //non-orienté donc on ajoute i dans les voisins de j vice-et-versa
		graph[j].voisins.add(graph[i]);
		if(!oriented)
		    nbArretes++;
		else if(oriented){
		    graph[j].voisinsEntrants.add(graph[i]);
		    graph[i].voisinsEntrants.add(graph[j]);
		    nbArretes += 2;
		}
	    }
	
	HashMap<Sommet, Integer> proba = new HashMap<Sommet, Integer>();	
	
	double r = 0.0;
	int nbArretesEligible = nbArretes; //au début de l'ajout, le nb d'arretes eligibles et egal au nb d'arretes du graphe
	int degre;
	ArrayList<Sommet> eligibles;
	for(int i = no ; i < n ; i++){
	    eligibles = new ArrayList<Sommet>(Arrays.asList(graph));
	    graph[i] = new Sommet(i); 
	    for(int j = 0 ; j < d ; j++){
		for(Sommet s : eligibles){
		    if(s == null) continue;
		    proba.put(s, s.voisins.size() + s.voisinsEntrants.size()); //on reinitialise la hashmap des degres
		}
		
		Sommet s = randomSommet(proba);
		degre = s.voisins.size() + s.voisinsEntrants.size();
		graph[i].voisins.add(s);
		proba.remove(s);
		eligibles.remove(s); //on retire le sommet des sommet eligibles
			
		if(oriented) 
		    s.voisinsEntrants.add(graph[i]);
		else
		    s.voisins.add(graph[i]);
	    }
	}
	return graph;
    }
    
    public static void writeInFile(String filename, Sommet[] graph, boolean oriented){
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
			    else{
				writer.println(s.id+" -- "+voisin.id+" ;");
				map.put(s.id, voisin.id);
			    }
			}
		    }
	    }
	    writer.println("}");
	    writer.flush();
	    writer.close();
	}catch(FileNotFoundException fnfe){
	    System.out.println("erreur de fichier"+fnfe);
	}
	catch(UnsupportedEncodingException uee){
	    System.out.println("type d'encodage inconnue"+uee);
	}
    }
    
    public static void main(String[] args){
	if(args.length != 5){
	    usage();
	}
	else {
	    int d = Integer.parseInt(args[0]);
	    int no = Integer.parseInt(args[1]);
	    int n = Integer.parseInt(args[2]);
	    boolean oriented = false;
	    if(args[3].equals("true"))
		oriented = true;
	    else if(args[3].equals("false"))
		oriented = false;
	    else{
		usage();
		System.exit(0);
	    }
	    Sommet[] graph = createRandomGraphBA(d,no,n,oriented);
	    writeInFile(args[4], graph, oriented);
	}	
    }
}
/*
  public static void FloydWarshall(){
  d[i][j] = { 0 si i=j, +linfini si pas darc(darrete) i->j , 1 sinon
		    
  pour k de 0 a n-1 :
  pour i de 0 a n-1 :
  pour j de 0 a n-1 :
  si d[i][k] + d[k][j] < d[i][j] :
  d[i][j] = d[i][k] + d[k][j]
  }
    
  }





*/
