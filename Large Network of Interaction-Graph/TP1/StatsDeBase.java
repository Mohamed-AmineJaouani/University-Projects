import java.util.ArrayList;

public class StatsDeBase {

    public static void main(String[] args){
	if(args.length == 1){
	    if(args[0].equals("-help"))
		System.out.println("Utilisation :\n\tjava StatsDeBase [nom du fichier .dot] \nExemple : java StatsDeBase Exemple.dot");
	    else{
		ArrayList<Sommet> graph = LoadGraph.charger_graph(args[0]);
		int cpt = 0, maxSortant = 0;
		
		for(Sommet s : graph){
		    //System.out.println(s);  // si on veut afficher le graphe
		    cpt += s.voisins.size();
		    if(s.voisins.size() >= maxSortant)
			maxSortant = s.voisins.size();
		}
		
		System.out.print(graph.size()+" ");
		System.out.print(cpt+" ");
		System.out.print(maxSortant+" ");
		System.out.print(LoadGraph.maxEntrants(graph)+" ");
		System.out.println(LoadGraph.maxId(graph)+" ");
	    }
	}
	else 
	    System.out.println("Entrez le nom du fichier .dot (java StatsDeBase -help)");
    }
}
