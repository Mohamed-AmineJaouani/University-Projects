import java.util.HashMap;
import java.util.ArrayList;

public class Sommet {

    public int id;
    public HashMap<Integer, Sommet> voisins;
    
    public Sommet(int id){
	this.id = id;
	voisins = new HashMap<Integer, Sommet>();
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
