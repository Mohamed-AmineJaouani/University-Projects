import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.HashMap;
import java.util.Iterator;

public class ServeurServiceClient implements Runnable {

    private static int cptIdClient = 1;
    private static HashMap<Integer, Socket> mapClientsConnectes = new HashMap<Integer, Socket>();
    public static HashMap<Integer, Integer> mapInvitations = new HashMap<Integer, Integer>(); //clé : inviteur, valeur : invité
    private static int compteurHote = 0;
    
    public Socket socketClient;
    public int idClient;

    public ServeurServiceClient(Socket s) {
	this.socketClient = s;
	this.idClient = cptIdClient++;
	synchronized(this.mapClientsConnectes) {
	    this.mapClientsConnectes.put(idClient, socketClient);
	}
    }

    public void deconnexionClient() {
	synchronized(this.mapInvitations) {
	    //On supprime le client de la liste des clients connectes
	    mapClientsConnectes.remove(idClient);
	    //On supprime l'invitation du client
	    mapInvitations.remove(idClient);
	    //On supprime toutes les invitations a ce client
	    if(mapInvitations.containsValue(idClient)) {
		Iterator it = mapInvitations.keySet().iterator();
		while(it.hasNext()) {
		    int clef = (int)it.next();
		    if(mapInvitations.get(clef) == idClient)
			mapInvitations.remove(clef);
		}
	    }
	    try {
		this.socketClient.close();
	    } catch (IOException e) { System.out.println("[ServeurServiceClient.java]-[deconnexionClient()] :\n" + e); }
	}
	System.out.println("Deconnexion du client " + idClient  + "...");
    }

    public boolean verificationExistenceClient(int id) {
	return mapClientsConnectes.containsKey(id);
    }
    public boolean verificationInvitation(int idInvite) {
	int id = (mapInvitations.get(idInvite) == null) ? -1 : mapInvitations.get(idInvite);
	return id == this.idClient;
    }
    //Peut-etre pas necessaire ?
    public synchronized void ajoutInvitation(int idInvite) {
	mapInvitations.put(idClient, idInvite);
    }
    //Peut-etre pas necessaire ?
    public synchronized void suppressionInvitation(int idInvite) {
	mapInvitations.remove(idInvite);
    }
    

    
    public void run() {
	try(BufferedReader br = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
	    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socketClient.getOutputStream()))) {

	    pw.println("ACCEPT: VOUS ETES LE JOUEUR " + this.idClient);
	    pw.flush();

	    while(true) {
		/******** RECEPTION *********/
		String msg = br.readLine();
		if(msg == null) break; //Permet d'eviter un NullPointerException dans le cas ou le client Ctrl+C
		System.out.println("Message recu :"+msg);
		String[] msgTab = msg.split(" ");

		if(msgTab.length == 1) {
		    if(msgTab[0].equals("HELP")) {
			pw.println("Les commandes sont : HELP, QUIT, LIST, CONN id_joueur, ACPT id_joueur, REFU id_joueur");
			pw.flush();
		    }
		    else if(msgTab[0].equals("QUIT")) {
			//deconnexionClient(); //La deconnexion se fait quoiqu'il arrive lorsque le client se termine (finally)
			break;
		    }
		    else if(msgTab[0].equals("LIST")) {
			String envoi = "";
			for(int i : mapClientsConnectes.keySet()){
			    envoi += i+ " | ";
			    if(mapInvitations.get(i) != null && mapInvitations.get(i) == this.idClient){
				envoi += "X";
			    }
			    envoi +='\n';
			}
			pw.println(envoi);
			pw.flush();
		    }
		    else {
			pw.println("ERROR: COMMANDE INCONNUE");
			pw.flush();
		    }
		}
		else if(msgTab.length == 2) {
		    if(msgTab[0].equals("CONN")) {
			int idClientInvite = -1;
			try {
			    idClientInvite = Integer.parseInt(msgTab[1]);
			} catch(NumberFormatException e) { System.out.println("Mauvais argument, entier attendu :\n" + e); }

			if(idClientInvite != idClient && verificationExistenceClient(idClientInvite)) { //Le joueur ne peut pas s'inviter lui-meme
			    ajoutInvitation(idClientInvite);
			    Socket socketInvite = mapClientsConnectes.get(idClientInvite);
			    PrintWriter pwInvite = new PrintWriter(new OutputStreamWriter(socketInvite.getOutputStream()));
			    pwInvite.println("INVITATION ENVOYE PAR LE JOUEUR " + this.idClient);
			    pwInvite.flush();
			    pw.println("En attente de la reponse du joueur " + idClientInvite + "...");
			    pw.flush();
			}
			else {
			    String envoi = (idClientInvite == idClient) ? "ERROR: VOUS NE POUVEZ PAS VOUS INVITER" : "ERROR: LE JOUEUR " + idClientInvite + " N'EXISTE PAS";
			    pw.println(envoi);
			    pw.flush();
			}
		    }
		    else if(msgTab[0].equals("ACPT") || msgTab[0].equals("REFU")) {
			boolean acpt = (msgTab[0].equals("ACPT"))? true : false;
			int idClientInvite = -1;
			try {
			    idClientInvite = Integer.parseInt(msgTab[1]);
			} catch(NumberFormatException e) { System.out.println("Mauvais argument, entier attendu :\n" + e); }
			
			if(verificationInvitation(idClientInvite)) {
			    Socket socketInvite = mapClientsConnectes.get(idClientInvite);
			    PrintWriter pwInvite = new PrintWriter(new OutputStreamWriter(socketInvite.getOutputStream()));
			    String envoi = (acpt)? "INVITATION ACCEPTEE PAR LE JOUEUR " + idClient : "INVITATION REFUSEE PAR LE JOUEUR " + idClient;
			    pwInvite.println(envoi);
			    pwInvite.flush();

			    if(acpt) {
				int port = ServeurTCP.PORTS_HOTE[compteurHote++ % ServeurTCP.PORTS_HOTE.length];
				
				String reponse = "Hote: port = " + port + " (@IP identique à celle du serveur)";
				
				pwInvite.println(reponse);
				pwInvite.flush();
				pw.println(reponse);
				pw.flush();
			    }
			    else {
				//On supprime l'invitation
				suppressionInvitation(idClientInvite);
			    }
			}
			else {
			    pw.println("ERROR: LE JOUEUR " + idClientInvite + " NE VOUS A JAMAIS INVITE");
			    pw.flush();
			}
		    }
		    else {
			pw.println("ERROR: COMMANDE INCONNUE");
			pw.flush();
		    }
		}
		else {
		    pw.println("ERROR: COMMANDE INCONNUE");
		    pw.flush();
		}
	    }//fin while true
	    //br.close(); //Pas necessaire car try-with-resources
	    //La fermeture des ressources est geree par la fonction deconnexionClient
	} catch(IOException e) {
	    System.out.println("Erreur lors de l'exécution d'un ServeurServiceLecture \n" + e);
	} finally {
	    deconnexionClient();
	}
    }
}
