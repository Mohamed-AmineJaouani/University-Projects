import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AlgoNewman {
    public static void usage(){
	System.out.println("Utilisation : java AlgoNewman [-v] fichier.dot\n\tExemple : java AlgoNewman -v zachary.dot communities_zachary_0.txt");
    }

    public static ArrayList<ArrayList<Integer>> newman(String dot, boolean verbose){
	HashMap<Integer, Sommet> graph = LoadGraph.charger_graph(dot, false);
	double Qmax = -1.0;
	ArrayList<ArrayList<Integer>> communautes = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> communaute;
	for(Sommet s : graph.values()){
	    communaute = new ArrayList<Integer>();
	    communaute.add(s.id);
	    communautes.add(communaute);
	}
	
	ArrayList<ArrayList<Integer>> Cmax = communautes;
	double deltaQmax = -1.0;
	for(int i = 0 ; i < graph.size()-1 ; i++){
	    double deltaQ = 0.0;
	    int cmax = 0, cprimemax = 0;
	    
	    for(int c = 0 ; c < communautes.size() ; c++){
		for(int cprime = c+1 ; cprime < communautes.size() ; cprime++){
		    try{
		    deltaQ = Modularite.deltaQ(graph, communautes, c, cprime);
		    }catch(NumberFormatException e){
			continue;
		    }
		    if(deltaQ > deltaQmax){
			deltaQmax = deltaQ;
			cmax = c;
			cprimemax = cprime;
		    }
		}
	    }

	    ArrayList<Integer> Ci = new ArrayList<Integer>();
	    for(Integer elt : communautes.get(cmax))
		Ci.add(elt);
	    for(Integer elt : communautes.get(cprimemax))
		Ci.add(elt);
	    communautes.remove(cmax);
	    communautes.remove(cprimemax);
	    communautes.add(Ci);
	    double modularite = Modularite.modularite(graph, communautes);
	    if(verbose)
		System.out.println(modularite);
	    if(modularite > Qmax){
		Qmax = modularite;
		Cmax = communautes;
	    }
	}
	System.out.println(Qmax);
	return Cmax;
    }
    
    public static void main(String[] args){
	if(args.length != 2 && args.length != 1)
	    usage();
	else{
	    boolean verbose = false;
	    ArrayList<ArrayList<Integer>> Cmax;
	    if(args[0].equals("-v"))
		Cmax = newman(args[1],true);
	    else
		Cmax = newman(args[0],false);
	    
	    for(ArrayList<Integer> communaute : Cmax){
		for(Integer i : communaute)
		    System.out.print(i+" ");
		System.out.println();
	    }
	}
    }
}
