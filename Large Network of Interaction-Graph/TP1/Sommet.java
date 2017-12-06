import java.util.ArrayList;
public class Sommet {

    public int id;
    public ArrayList<Sommet> voisins;
    public char color;
    
    public Sommet(int id){
	this.id = id;
	voisins = new ArrayList<Sommet>();
	color = 'w';
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
