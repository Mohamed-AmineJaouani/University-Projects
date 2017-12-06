import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Modularite{
    public static int nbArr = 0;
    
    public static void usage(){
	System.out.println("Utilisation : java Modularite fichier.dot communautes.txt\n\tExemple : java Modularite network.dot communities.txt");
    }

    public static ArrayList<ArrayList<Integer>> communautes(String filename){
	FileReader fileReader = null;
	BufferedReader br = null;
	try{
	    fileReader = new FileReader(filename);
	    br = new BufferedReader(fileReader);
	}catch(FileNotFoundException fnfe){
	    System.out.println("Fichier introuvable : "+filename);
	    System.exit(0);
	}
	
	ArrayList<ArrayList<Integer>> communautes = new ArrayList<ArrayList<Integer>>();
	
	try{
	    String line = br.readLine();
	    ArrayList<Integer> communaute;
	    do{
		communaute = new ArrayList<Integer>();
		String[] communitie = line.trim().split(" |\t");
		for(String s : communitie)
		    communaute.add(Integer.parseInt(s));
		communautes.add(communaute);
	    }while((line = br.readLine()) != null);
	}catch(IOException ioe){
	    System.exit(0);
	}
	return communautes;
    }
    
    public static int ac(HashMap<Integer, Sommet> graph, ArrayList<ArrayList<Integer>> communautes, int i){
	int ac = 0;
	for(int j = 0 ; j < communautes.get(i).size() ; j++){
	    for(Sommet s : graph.values())
		if(s.voisins.keySet().contains(communautes.get(i).get(j)))
		    ac++;
	}
	return ac;
    }

    public static int ecc(HashMap<Integer, Sommet> graph, ArrayList<ArrayList<Integer>> communautes, int i){
	int ecc = 0;
	for(int j = 0 ; j < communautes.get(i).size() ; j++){
	    for(int k = j+1 ; k < communautes.get(i).size() ; k++)
		if(graph.get(communautes.get(i).get(j)).voisins.keySet().contains(communautes.get(i).get(k)))
		    ecc++;
	}
	return ecc;
    }

    public static int eccprime(HashMap<Integer, Sommet> graph, ArrayList<ArrayList<Integer>> communautes, int c, int cprime){
	int eccprime = 0;
	for(int i = 0 ; i < communautes.get(c).size() ; i++){
	    if(graph.get(i) == null) continue;
	    for(Sommet voisin : graph.get(i).voisins.values())
		if(communautes.get(cprime).contains(voisin))
		    eccprime++;
	}
	return eccprime;
    }
    
    public static double modularite(HashMap<Integer, Sommet> graph, ArrayList<ArrayList<Integer>> communautes){
	double ecc = 0.0;
	double ac = 0.0;
	double modularite = 0.0;
	
	for(int i = 0 ; i < communautes.size() ; i++){
	    ecc = 0.0;
	    ac = 0.0;

	    ac = ac(graph, communautes,i);
	    ecc = ecc(graph, communautes, i);

	    ac = ac/2;
	    
	    int nbArretes = 0;
	    for(Sommet s : graph.values())
		nbArretes += s.voisins.size();

	    nbArr = nbArretes/2;
	    ecc /= nbArr;
	    ac /= nbArr;
	    modularite += (ecc - (ac*ac));
	}
	BigDecimal bd = new BigDecimal(modularite);
	bd = bd.setScale(10, RoundingMode.HALF_EVEN);
	modularite = bd.doubleValue();
	return modularite;
    }

    public static double deltaQ(HashMap<Integer, Sommet> graph, ArrayList<ArrayList<Integer>> communautes, int c, int cprime){
	double eccprime = (double)eccprime(graph, communautes, c,cprime) / nbArr;
	double acc = (double)ac(graph, communautes,c) / nbArr;
	double accprime = (double)ac(graph,communautes, cprime) / nbArr;
	double res = 2*(eccprime - acc*accprime);
	
	BigDecimal bd = new BigDecimal(res);
	bd = bd.setScale(10, RoundingMode.HALF_EVEN);
	res = bd.doubleValue();
	return res;
    }
    
    
    
    public static void main(String[] args){
	if(args.length != 2)
	    usage();
	else{
	    HashMap<Integer, Sommet> graph = LoadGraph.charger_graph(args[0], false);
	    ArrayList<ArrayList<Integer>> communautes = communautes(args[1]);
	    System.out.println(modularite(graph, communautes));
	}
    }
}
