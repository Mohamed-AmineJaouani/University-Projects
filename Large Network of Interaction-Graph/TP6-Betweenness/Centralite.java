import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Stack;
import java.util.LinkedList;
import java.util.ArrayList;

public class Centralite {

    /*  
	Je ne comprend pas pourquoi cela ne fonctionne pas !? Je veut renvoyer une liste contenant les min au debut et les infinis a la fin

     public static LinkedList<Sommet> voisinsMin(Sommet s){
	LinkedList<Sommet> voisinsMin = new LinkedList<Sommet>();
	int min = Integer.MAX_VALUE;
	
	for(Sommet voisin : s.voisins.values()){
	    if(voisin.distanceProx == null){
	       voisinsMin.addLast(voisin);
	    }
	    else if(voisin.distanceProx < min){
	       voisinsMin.addFirst(voisin);
	    }
	}
	return voisinsMin;
    }*/


    public static void Dijkstra(HashMap<Integer, Sommet> graphe, Sommet depart){
	depart.distanceProx = 0;
	
	ArrayDeque<Sommet> tas = new ArrayDeque<Sommet>();
	tas.add(depart);
	
	Sommet s;
	while(!tas.isEmpty()){
	    s = tas.removeFirst();
	    s.color = 'b';
	    
	    for(Sommet voisin : s.voisins.values()){
		if(voisin.color != 'b'){
		    voisin.distanceProx = (s.distanceProx +1 < voisin.distanceProx)? s.distanceProx + 1 : voisin.distanceProx;
		    if(voisin.color == 'w'){
			voisin.color = 'g';
			tas.add(voisin);
		    }
		}
	    }
	}
	
	int dsum = 0;
	for(Sommet sommet : graphe.values()){
	    dsum += sommet.distanceProx;
	    sommet.color = 'w'; //on reinitialise les valeurs de color et distance pour les prochains parcours Dijkstra
	    sommet.distanceProx = Integer.MAX_VALUE;
	}

	depart.centralite = (double)graphe.size() / dsum;
	System.out.println(depart.id+"\t"+depart.centralite);
    }
    
    public static void Brandes(HashMap<Integer, Sommet> graphe){
	for(Sommet s : graphe.values()){
	    Stack<Sommet> pile = new Stack<Sommet>();
	    ArrayDeque<Sommet> tas = new ArrayDeque<Sommet>();
	    s.sigma = 1;
	    s.distanceBC = 0;

	    for(Sommet u : graphe.values())
		if(u != s){
		    u.pred.clear();
		    u.sigma = 0;
		    u.distanceBC = -1;
		}
		    
	    tas.add(s);
	    Sommet v;
	    while(tas.size() != 0){
		v = tas.removeFirst();
		pile.push(v);
		for(Sommet voisin : v.voisins.values()){
		    if(voisin.distanceBC < 0){
			tas.add(voisin);
			voisin.distanceBC = v.distanceBC + 1;
		    }
		    if(voisin.distanceBC == v.distanceBC + 1){
			voisin.sigma = voisin.sigma + v.sigma;
			voisin.pred.add(v);
		    }
		}
	    }
	    for(Sommet vv : graphe.values())
	    	if(s != vv)
	    	    vv.delta = 0;
	    Sommet w;
	    while(pile.size() != 0){
		w = pile.pop();
		for(Sommet som : w.pred)
		    som.delta += ((som.sigma / w.sigma) * (1 + w.delta));
		if(w != s)
		    w.centralite += w.delta;
	    }
	}
	
	//Affichage du résultat
	for(int i = 0 ; i < graphe.size() ; i++)
	    System.out.println(i+"\t"+graphe.get(i).centralite);
    }
    
    public static void main(String[] args){
	if(args.length == 0 || args[0].equals("-help"))
	    System.out.println("java Centralite fichier.dot [-p/-b]\n\tExemple : java Centralite Ex2-undir.dot -p");
	else if(args.length != 2)
	    System.out.println("Entrez un fichier .dot en argument et une option (-p/-b) (java Proximite -help)");
	else{
	    HashMap<Integer, Sommet> graphe = LoadGraph.charger_graph(args[0], false);
	    if(args[1].equals("-p"))
		for(int i = 0 ; i < graphe.size() ; i++)
		    Dijkstra(graphe,graphe.get(i));
	    
	    else if(args[1].equals("-b"))
		Brandes(graphe);
	    
	    else 
		System.out.println("Veuillez entrer des arguments valides (java Centralite -help)");
	}
    }
}
