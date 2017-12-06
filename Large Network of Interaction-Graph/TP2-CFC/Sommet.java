import java.util.ArrayList;
public class Sommet {

    public int id;
    public ArrayList<Sommet> voisins;
    public char color;
    public int index;
    public int decouverte;
    public boolean estDansLaPile;
    public Sommet(int id){
	this.id = id;
	voisins = new ArrayList<Sommet>();
	color = 'w';
	index = -1;
	decouverte = -1;
	estDansLaPile = false;
    }

    public boolean isVisited(){
	return index != -1;
    }
    
    public String toString(){
	String s = "";
	s += "id : " + this.id + " voisins : ";
	for(Sommet v : this.voisins)
	    s += v.id + ", ";
		
	s += "\n";
	return s;
    }
}
