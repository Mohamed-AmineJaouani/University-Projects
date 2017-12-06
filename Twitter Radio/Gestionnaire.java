import java.util.*;
import java.net.*;
import java.io.*;

public class Gestionnaire {
    public int port;
    public int maxDiffuseurs;
    public int nbDiffuseurs = 0;
    public ArrayList<Diffuseur> liste;
    public PrintWriter terminal;
    
    public Gestionnaire(String port, String maxDiffuseurs) {
	this.port = Integer.parseInt(port);
	this.maxDiffuseurs = Integer.parseInt(maxDiffuseurs);
	this.liste = new ArrayList<Diffuseur>();
    }

    public synchronized void add_diff(Diffuseur diff) {
	this.liste.add(diff);
	this.nbDiffuseurs++;
    }
    public synchronized void del_diff(Diffuseur diff) {
	this.liste.remove(diff);
	this.nbDiffuseurs--;
    }
    /*
    public synchronized void del_diff(Diffuseur diff) {
	Diffuseur diffRemoved = null;
	for(Diffuseur diffList : this.liste) {
	    if(diffList.equals(diff))
		diffRemoved = diffList;
	}
	this.liste.remove(diffRemoved);
	this.nbDiffuseurs--;
    }
    */


    /*
    public int getPort() { return this.port; }
    public int getMaxDiffuseurs() { return this.max_diffuseurs; }
    public int getNbDiffuseurs() { return this.nb_diffuseurs; }
    public ArrayList<Diffuseur> getListe() { return this.liste; }

    public void setPort(int port) { this.port = port; }
    public void setMaxDiffuseurs(int max_diff) { this.max_diffuseurs = max_diff; }
    public void setNbDiffuseurs(int nb_diff) { this.nb_diffuseurs = nb_diff; }
    public void setListe(ArrayList<Diffuseur> liste) { this.liste = liste; }
    */


}
