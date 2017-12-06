import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayDeque;
import java.util.Iterator;

public class RandomUndirectedGraph {

    public static ArrayList<Sommet> erdos_renyi(int n, double p){
	ArrayList<Sommet> graphe = new ArrayList<Sommet>();
	if(p < 0.0 || p > 1.0){
	    System.out.println("Entrez une probabilité entre 0 et 1");
	    System.exit(0);
	}
	else {
	    for(int i = 0 ; i < n ; i++){
		Sommet s = new Sommet(i);
		graphe.add(s);
	    }
	    for(int i = 0 ; i < n ; i++)
		for(int j = i+1 ; j < n ; j++)
		    if(Math.random() < p){
			graphe.get(i).voisins.add(graphe.get(j));
			graphe.get(j).voisins.add(graphe.get(i));
		    }
	}
	return graphe;
    }

    private static int NBX(int x, int n1, double gamma){
	return (int)(n1*Math.pow(x,-gamma)+0.5);
    }

    public static void permuter(Integer[] t){
	int r, tmp=0;
	for(int i = 0 ; i < t.length ; i++){
	    r = (int)(Math.random()*t.length);
	    tmp = t[i];
	    t[i] = t[r];
	    t[r] = tmp;
	}
    }
    
    public static Integer[] power_law(int n1, double gamma){
	Integer[] t = null;
	if(gamma < 1.0 ){
	    System.out.println("Entrez un reel superieur a 1");
	    System.exit(0);
	}
	else {
	    int x = 1;
	    int nbx;
	    int cptIndice = 0;
	    int tabSize = 0;
	    while((nbx = NBX(x++,n1,gamma)) != 0){ 
		tabSize += nbx;
	    }
	    	    
	    t = new Integer[tabSize];
	    x = 1;
	    while((nbx = NBX(x,n1,gamma)) != 0){
		for(int i = cptIndice ; i < cptIndice+nbx ; i++)
		    t[i] = x;
		cptIndice += nbx;
		x++;
	    }
	}

	permuter(t);

	return t;
    }
    
    public static ArrayList<Sommet> MolloyReed(Integer[] t){
	ArrayList<Integer> list = new ArrayList<Integer>();
	ArrayList<Sommet> graphe = new ArrayList<Sommet>();
	
	for(int i = 0 ; i < t.length ; i++)
	    for(int j = 0 ; j < t[i] ; j++)
		list.add(i);	

	Integer[] tab = list.toArray(t);
	
	permuter(tab);
		
	ArrayList<Integer> l = new ArrayList<Integer>(Arrays.asList(tab));
	
	for(int i = 0 ; i < t.length ; i++){
	    Sommet s = new Sommet(i);
	    graphe.add(s);
	}
	int s1, s2;

	for(int i = 0 ; i < l.size()-1 ; i+=2){
	    s1 = l.get(i);
	    s2 = l.get(i+1);
	    if(s1 != s2){
		Sommet smt1 = LoadGraph.contient(graphe, s1);
		Sommet smt2 = LoadGraph.contient(graphe, s2);
		
		if(!smt1.voisins.contains(smt2)){
		    smt1.voisins.add(smt2);
		    smt2.voisins.add(smt1);
		}
	    }
	}
	
	return graphe;
    }

    public static ArrayList<Sommet> MolloyLaw(int n1, double gamma){
	return MolloyReed(power_law(n1,gamma));
    }

    public static ArrayList<Sommet> parcoursBFS(ArrayList<Sommet> graph, Sommet sommet, int numParcours){
	ArrayDeque<Sommet> tas = new ArrayDeque<Sommet>();
	ArrayList<Sommet> sommetsVisites = new ArrayList<Sommet>();
	Sommet s;

	tas.push(sommet);
	
	while(tas.size() != 0){
	    s = tas.pop();
	    sommetsVisites.add(s);
	    
	    switch(numParcours){
	    case 1: s.color1 = 'b'; break;
	    case 2: s.color2 = 'b'; break;
	    case 3: s.color3 = 'b'; break;
	    case 4: s.color4 = 'b'; break;
	    }
	    
	    Iterator<Sommet> it = s.voisins.iterator();

	    while(it.hasNext()){
		Sommet voisin = it.next();
		
		switch(numParcours){
		case 1 : if(voisin.color1 == 'w'){
			voisin.color1 = 'g';
			tas.push(voisin);
		    }break;
		case 2: if(voisin.color2 == 'w'){
			voisin.color2 = 'g';
			tas.push(voisin);
		    }break;
		case 3: if(voisin.color3 == 'w'){
			voisin.color3 = 'g';
			tas.push(voisin);
		    }break;
		case 4: if(voisin.color4 == 'w'){
			voisin.color4 = 'g';
			tas.push(voisin);
		    }break;
		}
	    }
	}

	return sommetsVisites;
    }
    
    public static int diametre(ArrayList<Sommet> graph){
	Sommet sommetRandom = graph.get((int)(Math.random()*graph.size()));
	ArrayList<Sommet> l1 = parcoursBFS(graph, sommetRandom,1);
	
	ArrayList<Sommet> l2 = parcoursBFS(graph, l1.get(l1.size()-1),2);

	ArrayList<Sommet> l3 = parcoursBFS(graph, l2.get(l2.size()/2),3);

	ArrayList<Sommet> l4 = parcoursBFS(graph, l3.get(l3.size()-1),4);

	return Math.max(l2.size(), l4.size())-1;
    }
    
    public static void main(String[] args){
	if((args.length == 1 && args[0].equals("-help")) || args.length == 0)
	    System.out.println("Utilisation : java RandomUndirectedGraph [-er/-pl] entier double FichierSortie.dot [neato / circo / dot / ...] [commande d'ouverture de PNG]\n\tExemple : java RandomUndirectedGraph -pl 10 1.1 random.dot neato eog\n\tExemple : java RandomUndirectedGraph -er 10 0.4 random.dot circo eog");
	else if(args.length == 6){
	    ArrayList<Sommet> graphe = null;
	    int arg1 = 0;
	    double arg2 = 0;
	    try{
		arg1 = Integer.parseInt(args[1]);
		arg2 = Double.parseDouble(args[2]);
	    }catch(Exception e){
		System.out.println("Veuillez entrer un entier et un double");
		System.exit(0);
	    }
	    if(arg1 > 0 && arg2 > 0.0){
		if(args[0].equals("-er")){  // -er pour erdos renyi
		    graphe = erdos_renyi(arg1,arg2);
		}
		else if(args[0].equals("-pl")){ // -pl pour power low
		    graphe = MolloyLaw(arg1, arg2);
		}
		else {
		    System.out.println("Veuillez entrer de bons arguments (java RandomUndirectedGraph -help)");
		    System.exit(0);
		}
	    }
	    else {
		System.out.println("Veuillez entrer de bons arguments (java RandomUndirectedGraph -help)");
		System.exit(0);
	    }
	    
	    String filename = args[3];

	    String extension = filename.substring(filename.length()-4);
	    
	    if(!extension.equals(".dot")){
		System.out.println("Veuillez entrer un fichier .dot comme fichier de sortie (java -help)");
		System.exit(0);
	    }
	    
	    Kcore.writeInFile(filename, graphe, false);
	    
	    Runtime runtime = Runtime.getRuntime();
	    
	    try{
		Process proc = runtime.exec(args[4]+" -Tpng -O "+filename);
		proc.waitFor();
		runtime.exec(args[5]+" "+ filename+".png");
	    }catch(IOException e){
		System.out.println(e);
	    }
	    catch(InterruptedException e){
		System.out.println(e);
	    }
	    	    
	    System.out.println("nombre de sommets :"+graphe.size());

	    int nbAretes = 0;
	    for(Sommet s : graphe)
		nbAretes += s.voisins.size();
	    System.out.println("nombre d'aretes :"+nbAretes/2);
	    
	    //CFC.main(new String[]{filename});  !!! On peut appeler le main d'un autre programme java !!! 
	    CFC.statCFC(graphe);

	    int diameter = diametre(graphe);

	    CoeurDense.calculMaxDensite(graphe, false);

	    System.out.println("Diamètre : "+ diameter);
	}
	else
	    System.out.println("Veuillez entrer de bons arguments (java RandomUndirectedGraph -help)");
    }
}
