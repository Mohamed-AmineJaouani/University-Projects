import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class HoteServiceMastermind implements Runnable {
    public static String couleurs = "couleurs : R,J,V,B,O,W,P,F";
    private static final String REGLES_UTILISATION_1 = "Veuillez commencer par choisir votre PASSWORD. Il s'agit d'une combinaison composée de 4 couleurs parmi la liste suivante : " + couleurs + ". Pour choisir une combinaison, suivez l'exemple suivant : \"PASSWORD VPWO\"";
    private static final String REGLES_UTILISATION_2 = "Pour envoyer votre proposition dans le but de deviner la combinaison de votre adversaire, suivez l'exemple suivant : \"ATTEMPT RBOW\"";
    private static final String help = "Les commandes sont : HELP, PASSWORD, ATTEMPT";
    
    private Socket j1, j2;
    String combinaison1="";
    String combinaison2="";
    BufferedReader br1;
    BufferedReader br2; 
    PrintWriter pw1;
    PrintWriter pw2;
    ArrayList<Character> lettresCouleurs;


    public HoteServiceMastermind(Socket j1, Socket j2) {
	this.j1 = j1;
	this.j2 = j2;
	try{
	    br1 = new BufferedReader(new InputStreamReader(j1.getInputStream()));
	    br2 = new BufferedReader(new InputStreamReader(j2.getInputStream()));
	    pw1 = new PrintWriter(new OutputStreamWriter(j1.getOutputStream()));
	    pw2 = new PrintWriter(new OutputStreamWriter(j2.getOutputStream()));
	} catch(IOException e){ System.out.println("HSM :\n" + e); }

	lettresCouleurs = new ArrayList<>(Arrays.asList('R','J','V','B','O','W','P','F'));
    }

    
    public void run() {
	try{
	    boolean recu1ok = false;
	    boolean recu2ok = false;

	    sendMessage(REGLES_UTILISATION_1, pw1, pw2);
	    sendMessage(REGLES_UTILISATION_2, pw1, pw2);
	    sendMessage("PASSWORD:");
	    initialisationPassword(recu1ok, recu2ok);
	    Jeu(recu1ok, recu2ok);
	    close();
	}
	catch(IOException e){ System.out.println("HSM :\n" + e); }
    }



    /******* PARTIE : FONCTIONS DU JEU *******/

    public void initialisationPassword(boolean recu1ok, boolean recu2ok) throws IOException{
	String recu1= "";
	String recu2 = "";
	pw1.println("PASSWORD:");
	pw1.flush();
	pw2.println("PASSWORD:");
	pw2.flush();

	while(!recu1ok){
	    recu1 = br1.readLine();
	    System.out.println(recu1);
	    String tab[] = new String[2];
	    tab = recu1.split(" ");
	    
	    if(!tab[0].equals("PASSWORD")){
		if(tab[0].equals("HELP")){
		    pw1.println(help);
		    pw1.flush();
		}
		else{
		    pw1.println("ERROR: COMMANDE INCONNU");
		    pw1.flush();
		}
		continue;
	    }
	    else if(tab[1].length() != 4){
		pw1.println("ERROR: COMBINAISON INCORRECT");
		pw1.flush();
		continue;
	    }
		    
	    recu1 = cleanString(tab[1]);
		    
	    for(int i = 0 ; i < recu1.length() ; i++){
		if(!lettresCouleurs.contains(recu1.charAt(i))){
		    pw1.println("ERROR Combinaison fausse : ");
		    pw1.flush();
		    break;
		}
		else
		    combinaison1 += recu1.charAt(i);
		if(i == recu1.length() -1)
		    recu1ok = true;
	    }
	}
	    
	System.out.println("Combinaison 1:"+combinaison1);
	pw1.println("PASSWORD: OK "+combinaison1+"\t"+couleurs);
	pw1.flush();

	while(!recu2ok){
	    recu2 = br2.readLine();
	    System.out.println(recu2);
	    String tab[] = new String[2];
	    tab = recu2.split(" ");

	    if(!tab[0].equals("PASSWORD")){
		if(tab[0].equals("HELP")){
		    pw2.println(help);
		    pw2.flush();
		}
		else{
		    pw2.println("ERROR: COMMANDE INCONNU");
		    pw2.flush();
		}
		continue;
	    }
	    else if(tab[1].length() != 4){
		pw2.println("ERROR: COMBINAISON INCORRECT");
		pw2.flush();
		continue;
	    }

	    recu2 = cleanString(tab[1]);
		
	    for(int i = 0 ; i < recu2.length() ; i++){
		if(!lettresCouleurs.contains(recu2.charAt(i))){
		    pw2.println("ERROR: Combinaison fausse : ");
		    pw2.flush();
		    break;
		}
		else
		    combinaison2 += recu2.charAt(i);
		if(i == recu2.length() -1)
		    recu2ok = true;
	    }
	}

	System.out.println("Combinaison 2:"+combinaison2);
	pw2.println("PASSWORD: OK "+combinaison2+"\t"+couleurs);
	pw2.flush();
    }


    public void Jeu(boolean recu1ok, boolean recu2ok) throws IOException{
	boolean enJeu = true;
	String attempt1 = "";
	String attempt2 = "";
	
	String prop1 = "";
	String prop2 = "";
	while(enJeu){
	    attempt1 = "";
	    attempt2 = "";
		
	    recu1ok = false;
	    recu2ok = false;
	    prop1 = "";
	    prop2 = "";

	    pw1.println("ATTEMPT:");
	    pw1.flush();
	    pw2.println("ATTEMPT:");
	    pw2.flush();
	    
	    while(!recu1ok){
		attempt1 = br1.readLine();
		attempt1 = cleanString(attempt1);
		String[] tab = new String[2];
		tab = attempt1.split(" ");
		if(!tab[0].equals("ATTEMPT")){
		    if(tab[0].equals("HELP")){
			pw1.println(help);
			pw1.flush();
		    }
		    else{
			pw1.println("ERROR: COMMANDE INCONNU");
			pw1.flush();
		    }
		    continue;
		}
		else if(tab[1].length() != 4){
		    pw1.println("ERROR: COMBINAISON INCORRECT");
		    pw1.flush();
		    continue;
		}
		    
		for(int i = 0 ; i < tab[1].length() ; i++){
		    if(!lettresCouleurs.contains(tab[1].charAt(i))){
			pw1.println("ERROR: Combinaison fausse");
			pw1.flush();
			break;
		    }
		    if(i == tab[1].length() -1){
			recu1ok = true;
			prop1 = tab[1];
		    }
		}
	    }
	    System.out.println("PROPOSITION JOUEUR 1"+prop1);
		
	    while(!recu2ok){
		attempt2 = br2.readLine();
		attempt2 = cleanString(attempt2);
		String[] tab = new String[2];
		tab = attempt2.split(" ");
		if(!tab[0].equals("ATTEMPT")){
		    if(tab[0].equals("HELP")){
			pw2.println(help);
			pw2.flush();
		    }
		    else{
			pw2.println("ERROR: COMMANDE INCONNU");
			pw2.flush();
		    }
		    continue;
		}
		else if(tab[1].length() != 4){
		    pw2.println("ERROR: COMBINAISON INCORRECT");
		    pw2.flush();
		    continue;
		}
		    
		for(int i = 0 ; i < tab[1].length() ; i++){
		    if(!lettresCouleurs.contains(tab[1].charAt(i))){
			pw2.println("ERROR: Combinaison fausse");
			pw2.flush();
			break;
		    }
		    if(i == tab[1].length() -1){
			recu2ok = true;
			prop2 = tab[1];
		    }
		}
	    }
	    System.out.println("PROPOSITION JOUEUR 2"+prop2);

	    String str = "(COMBINAISON J1, PROPOSITION J1) = ("+combinaison1+", "+prop1+
		") --- (COMBINAISON J2, PROPOSITION J2) = ("+combinaison2+", "+prop2 +")";

	    if(prop1.equals(combinaison2)){
		if(prop2.equals(combinaison1)){
		    pw1.println("EGALITE: "+str);
		    pw1.flush();
		    pw2.println("EGALITE: "+str);
		    pw2.flush();
		    enJeu = false;
		}
		else{
		    pw1.println("WINNING:"+str);
		    pw1.flush();
		    pw2.println("LOSING: "+str);
		    pw2.flush();
		    enJeu = false;
		}
	    }
	    else{
		String resultat = comparaisonCombinaison(combinaison1,prop2);
		pw2.println(resultat+"\t"+couleurs);
		pw2.flush();
	    }

	    if(prop2.equals(combinaison1)){
		if(!prop1.equals(combinaison2)){
		    pw2.println("WINNING: "+str);
		    pw2.flush();
		    pw1.println("LOSING: "+str);
		    pw1.flush();
		    enJeu = false;
		}
	    }
	    else{
		String resultat = comparaisonCombinaison(combinaison2,prop1);
		pw1.println(resultat+"\t"+couleurs);
		pw1.flush();
	    }
	}
	close();
    }


    
    public String comparaisonCombinaison(String combi, String propo){
	ArrayList<Integer> indiceCombi = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
	ArrayList<Integer> indicePropo = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
	
	ArrayList<Integer> toRemove = new ArrayList<>();
	Iterator itCombi, itPropo;
	String ret = "";

	for(int i=0 ; i<propo.length() ; i++)
	    if(propo.charAt(i) == combi.charAt(i)) {
		ret += 'O';
		toRemove.add(i);
	    }
	indiceCombi.removeAll(toRemove);
	indicePropo.removeAll(toRemove);
	toRemove.clear();

	itPropo = indicePropo.iterator();
	while(itPropo.hasNext()) {
	    int i = (int)itPropo.next();
	    itCombi = indiceCombi.iterator();
	    while(itCombi.hasNext()) {
		int j = (int)itCombi.next();
		if(propo.charAt(i) == combi.charAt(j)) {
		    ret = (i == j)? 'O' + ret : ret + 'X';
		    toRemove.add(j);
		    break;
		}
	    }
	    indiceCombi.removeAll(toRemove);
	    toRemove.clear();
	}

	for(int i=0 ; i<indiceCombi.size() ; i++)
	    ret += '_';

	return ret;
    }




    
    /******* PARTIE : FONCTIONS UTILITAIRES  *******/

    public static void sendMessage(String msg, PrintWriter... pwTab) {
	for(PrintWriter pw : pwTab) {
	    pw.println(msg);
	    pw.flush();
	}
    }
    
    public static String cleanString(String s){
	if(s == null){
	    System.out.println("Error: reception d'un message suite à une deconnexion");
	    System.exit(0);
	}
	String r = "";
	for(int i = 0 ; i < s.length() ; i++){
	    if(s.charAt(i) != '\0' || s.charAt(i) != '\n')
		r+= s.charAt(i);
	}
	return r;
    }

    public void close() throws IOException{
	br1.close();
	br2.close();
	pw1.close();
	pw2.close();
	j1.close();
	j2.close();
    }
			

}
