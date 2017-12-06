import java.util.ArrayList;
import java.util.Stack;

public class CFC {
    public static int I = 0;    
    public static Stack<Sommet> pile = new Stack<Sommet>();
    public static ArrayList<ArrayList<Sommet>> composantes = new ArrayList<ArrayList<Sommet>>();
    
    public static Sommet tousLesSommetsSontVisites(ArrayList<Sommet> graph){
	for(Sommet s : graph)
	    if(!s.isVisited())
		return s;
	return null;
    }

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
    
    public static void main(String[] args){
	if(args.length != 1)
	    System.out.println("Veuillez entrer en argument le nom du fichier .dot (java CFC -help)");
	else if(args[0].equals("-help"))
	    System.out.println("Utilisation:\n\tjava CFC [nom du fichier .dot]\nExemple: java CFC Exemple.dot");
	else{
	    ArrayList<Sommet> graph = LoadGraph.charger_graph(args[0]);
	    for(Sommet s : graph)
		if(s.index == -1)
		    parcours(s);
	    System.out.println("Nombre de composantes fortements connexes : " + composantes.size());
	    for(int i =  0 ; i < composantes.size() ; i++){
		System.out.print("[ ");
		for(Sommet s : composantes.get(i))
		    System.out.print(s.id+" ");
		System.out.print("] ");
	    }
	    System.out.println();
	}
    }
}
