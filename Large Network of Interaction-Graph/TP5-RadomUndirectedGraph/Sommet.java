import java.util.ArrayList;
public class Sommet {

    public int id;
    public ArrayList<Sommet> voisins;
    public ArrayList<Sommet> voisinsEntrants;
    public char color1;
    public char color2;
    public char color3;
    public char color4;
    public int index;
    public int decouverte;
    public boolean estDansLaPile;
    
    public Sommet(int id){
	this.id = id;
	voisins = new ArrayList<Sommet>();
	voisinsEntrants = new ArrayList<Sommet>();
	color1 = 'w';
	color2 = 'w';
	color3 = 'w';
	color4 = 'w';
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
