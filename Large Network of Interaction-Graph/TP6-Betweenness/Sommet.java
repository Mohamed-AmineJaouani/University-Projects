import java.util.HashMap;
import java.util.ArrayList;

public class Sommet {

    public int id;
    public HashMap<Integer, Sommet> voisins;
    public HashMap<Integer, Sommet> voisinsEntrants;
    public char color;
    public long index;
    public int decouverte;
    public boolean estDansLaPile;
    public double centralite;
    public int distanceProx;  //pour dijsktra la distance doit etre null mais pour betweeness elle doit etre a 0 donc j'ai décidé de prendre 2 vars
    public Integer distanceBC; //betweeness centrality
    public double sigma;
    public double delta;
    public ArrayList<Sommet> pred;

    public Sommet(int id){
	this.id = id;
	voisins = new HashMap<Integer, Sommet>();
	voisinsEntrants = new HashMap<Integer, Sommet>();
	color = 'w';
	index = -1;
	decouverte = -1;
	estDansLaPile = false;
	centralite = 0;
	distanceProx = Integer.MAX_VALUE;
	pred = new ArrayList<Sommet>();
    }

    public boolean isVisited(){
	return index != -1;
    }
    
    public String toString(){
	String s = "";
	s += "id : " + this.id + " voisins : ";
	for(Sommet v : this.voisins.values())
	    s += v.id + ", ";
	s += "\n";
	return s;
    }
}
