import java.util.HashMap;
import java.util.ArrayList;

public class Sommet {
    
    public int id;
    public ArrayList<Sommet> voisins;
    public ArrayList<Sommet> voisinsEntrants;
    
    public Sommet(int id){
	this.id = id;
	voisins = new ArrayList<Sommet>();
	voisinsEntrants = new ArrayList<Sommet>();
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
