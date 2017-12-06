import java.util.ArrayList;
import java.util.Stack;

public class CFC {
    public static int I = 0;    
    public static Stack<Sommet> pile = new Stack<Sommet>();
    public static ArrayList<ArrayList<Sommet>> composantes = new ArrayList<ArrayList<Sommet>>();
    
    public static void parcours(Sommet s){
	s.index = I;
	s.decouverte = I++;
	pile.push(s);
	s.estDansLaPile = true;
	
	for(Sommet voisin : s.voisins){
	    if(voisin.index == -1){
		parcours(voisin);
		s.index = Math.min(s.index, voisin.index);
	    }
	    else if(voisin.estDansLaPile)
		s.index = Math.min(s.index, voisin.index);
	}
	if(s.index == s.decouverte){
	    ArrayList<Sommet> listCFC = new ArrayList<Sommet>();
	    Sommet tmp;
	    
	    do{
		tmp = pile.pop();
		tmp.estDansLaPile = false;
		listCFC.add(tmp);
	    }while(tmp.id != s.id);
	    composantes.add(listCFC);
	}
    }

    public static void statCFC(ArrayList<Sommet> graph){
	for(Sommet s : graph)
	    if(s.index == -1)
		parcours(s);
	int max = 0;
	for(ArrayList<Sommet> l : composantes)
	    if(l.size() > max)
		max = l.size();
	
	System.out.println("Nombres de composantes : "+composantes.size() +", taille de la plus grosse : "+max);
    }
    
    public static void main(String[] args){
	if(args.length != 2)
	    System.out.println("Veuillez entrer en argument le nom du fichier .dot et l'orientation (java CFC -help)");
	else if(args[0].equals("-help"))
	    System.out.println("Utilisation:\n\tjava CFC [nom du fichier .dot] oriented \nExemple: java CFC Exemple.dot 1");
	else{
	    boolean oriented;
	    oriented = (Integer.parseInt(args[1]) == 1)? true : false;

	    ArrayList<Sommet> graph = LoadGraph.charger_graph(args[0], oriented);
	    statCFC(graph);
	}
    }
}
