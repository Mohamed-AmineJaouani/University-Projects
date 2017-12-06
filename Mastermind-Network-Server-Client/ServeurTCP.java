import java.net.*;
import java.io.*;

public class ServeurTCP {
    public static final int[] PORTS_HOTE = {1028, 1029, 1030, 1031}; //On limite le nombre d'hôtes à 4
    public static int port_serveur;

    public static void main(String[] args) {
	//boucle qui, avant meme de traiter des clients, va "allumer" tous les hotes, cad lancer les thread des hotes qui auront à l'intérieur un serverSocket pour accepter les clients
	if(args.length != 1){
	    System.out.println("Veuillez entrez un numero de port en argument");
	}
	else{
	    
	    for(int i=0 ; i<PORTS_HOTE.length ; i++)
		new Thread(new Hote(PORTS_HOTE[i])).start();
	    
	    
	    try(ServerSocket server = new ServerSocket(Integer.parseInt(args[0]))) {
		while(true) {
		    port_serveur = Integer.parseInt(args[0]);
		    Socket socket = server.accept();
		    ServeurServiceClient servC = new ServeurServiceClient(socket);
		    Thread tr = new Thread(servC);
		    tr.start();
		}
	    } catch(IOException e) {
		System.out.println("Erreur lors de l'exécution du ServeurTCP :\n" + e);
		//e.printStackTrace();
	    }
	    catch(NumberFormatException e){
		System.out.println("Erreur de numéro de port :\n" + e);
		//e.printStackTrace();
	    }
	}
    }
}
