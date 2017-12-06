import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Iterator;

public class Parcours {
    
    public static void main(String[] args){
	if(args.length == 2){
	    ArrayList<Sommet> graph = LoadGraph.charger_graph(args[0]);

	    int numSommet = Integer.parseInt(args[1]);
	    Sommet sommet;
	    
	    if((sommet = LoadGraph.contient(graph, numSommet)) == null)
		System.out.println("Le graphe ne contient pas le sommet : " + numSommet);
	    else{
		ArrayDeque<Sommet> tas = new ArrayDeque<Sommet>();
		Sommet s;
		int cptSommet = 0;

		tas.push(sommet);

		while(tas.size() != 0){
		    s = tas.pop();
	
		    s.color = 'b';
		    cptSommet++;

		    Iterator<Sommet> it = s.voisins.iterator();
		    
		    while(it.hasNext()){
			Sommet voisin = it.next();
			if(voisin.color == 'w'){
			    voisin.color = 'g';
			    tas.push(voisin);
			}
		    }
		}
		System.out.println(cptSommet);
	    }
	}
	else if(args.length == 1 && args[0].equals("-help"))
	    System.out.println("Utilisation :\n\tjava Parcours [nom du fichier .dot] [numero de sommet d'origine]\nExemple : java Parcours Exemple.dot 1");
	else 
	    System.out.println("Entrez le nom du fichier .dot suivi d'un numéro de sommet (java Parcours -help)");
    }
}
