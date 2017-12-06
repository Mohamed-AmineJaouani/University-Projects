import java.util.ArrayList;

public class CoeurDense {

    public static double densite(ArrayList<Sommet> graph, boolean oriented){
	if(graph.isEmpty())
	    return 0;
	else{
	    int arcs = 0;
	    for(Sommet s : graph)
		arcs += s.voisins.size();
	    if(oriented)
		return (double) arcs / graph.size();
	    else
		return (double) (arcs/2) / graph.size();
	}
    }

    public static void calculMaxDensite(ArrayList<Sommet> graph, boolean oriented){
	double d = 0;
	int k = 0;
	
	int kmin = -1;
	double dmax = -1;
	
	do{
	    Kcore.calculerKcoeurs(graph,k);
	    d = densite(graph, oriented);
	    
	    if(d > dmax){
		dmax = d;
		kmin = k;
	    }
	    
	    k++;
	}while(d != 0);
	
	System.out.println("k:"+kmin+" d:"+dmax);
    }
    
    public static void main(String[] args){
	if(args.length == 2){
	    if(args[0].equals("-help"))
		System.out.println("Utilisation:\n\tjava CoeurDense Fichier.dot\nExemple: java CoeurDense Exemple.dot");
	    else{
		if(!args[0].substring(args[0].length()-4, args[0].length()).equals(".dot")){
		    System.out.println("Entrez un fichier .dot en argument");
		    System.exit(0);
		}
		boolean oriented = (Integer.parseInt(args[1]) == 1)? true : false;
		ArrayList<Sommet> graph = LoadGraph.charger_graph(args[0], oriented);
		calculMaxDensite(graph, oriented);
	    }
	}
	else 
	    System.out.println("Veuillez entrez le bon nombre d'arguments (java CoeurDense -help)");
    }
}
