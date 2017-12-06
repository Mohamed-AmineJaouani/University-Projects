import java.util.HashMap;
import java.util.ArrayList;

public class Sommet {
    
    public int id;
    public ArrayList<Sommet> voisins;
    public ArrayList<Sommet> voisinsEntrants;
    public char etat;
    public int tourinfecte;
    public int tourgueri;
    
    public Sommet(int id){
	this.id = id;
	this.etat = 'S';
	this.voisins = new ArrayList<Sommet>();
	this.voisinsEntrants = new ArrayList<Sommet>();
	this.tourinfecte = 0;
	this.tourgueri = 0;
    }
    
    public String toString(){
	String s = "";
	s += "id : " + this.id + " voisins : ";
	for(Sommet v : this.voisins)
	    s += v.id + ", ";
	s += "\n";
	return s;
    }

    public String toStringEtat(){
	return this.id+""+this.etat+" ";
    }
}
