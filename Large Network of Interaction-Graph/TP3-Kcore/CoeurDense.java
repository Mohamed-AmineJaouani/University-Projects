import java.util.ArrayList;
import java.util.HashMap;

public class CoeurDense {

    public static double densite(ArrayList<Sommet> graph){
	if(graph.isEmpty())
	    return 0;
	else{
	    int arcs = 0;
	    for(Sommet s : graph)
		arcs += s.voisins.size();
	    return (double) arcs / graph.size(); 
	}
    }
    
    public static void main(String[] args){
	if(args.length == 1){
	    if(args[0].equals("-help"))
		System.out.println("Utilisation:\n\tjava CoeurDense Fichier.dot\nExemple: java CoeurDense Exemple.dot");
	    else{
		ArrayList<Sommet> graph = LoadGraph.charger_graph(args[0]);
		double d = 0;
		int k = 0;
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		do{
		    Kcore.calculerKcoeurs(graph,k);
		    d = densite(graph);
		    map.put(k,d);
		    k++;
		}while(d != 0);

		double maxV = 0;
		int minK = 0;
		for(double val : map.values())
		    if(val > maxV)
			maxV = val;
	    
		for(int key : map.keySet())
		    if(map.get(key) == maxV){
			minK = key;
			break;
		    }
		System.out.println(minK+" "+maxV);
	    }
	}
	else 
	    System.out.println("Veuillez entrez un fichier .dot en argument");
    }
}
