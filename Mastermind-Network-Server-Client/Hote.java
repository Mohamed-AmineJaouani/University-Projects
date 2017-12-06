import java.net.*;
import java.io.*;
import java.lang.*;

public class Hote implements Runnable {
    public int port;
    private PrintWriter pw1;
    private BufferedReader br1;
    private PrintWriter pw2;
    private BufferedReader br2;

    
    public Hote(int port) {
	this.port = port;
    }
    
    public void run() {
	try(ServerSocket server = new ServerSocket(port)) {
	    while(true) {
		Socket j1 = server.accept();
		try{
		    br1 = new BufferedReader(new InputStreamReader(j1.getInputStream()));
		    pw1 = new PrintWriter(new OutputStreamWriter(j1.getOutputStream()));
		} catch(IOException e){ System.out.println("HSM :\n" + e); }
		sendMessage("Bienvenue Joueur 1 ! En attente du Joueur 2...", pw1);

		Socket j2 = server.accept();
		try{
		    br2 = new BufferedReader(new InputStreamReader(j2.getInputStream()));
		    pw2 = new PrintWriter(new OutputStreamWriter(j2.getOutputStream()));
		} catch(IOException e){ System.out.println("HSM :\n" + e); }
		sendMessage("Bienvenue Joueur 2 !", pw2);
		try{Thread.sleep(1000);}catch(Exception e){}
		sendMessage("\nLes deux joueurs sont connectés. La partie peut donc commencer !\n", pw1, pw2);
		HoteServiceMastermind hsm = new HoteServiceMastermind(j1, j2);
		Thread tr = new Thread(hsm);
		tr.start();
	    }
	} catch(IOException e) {
	    System.out.println("Erreur lors de l'exécution de l'Hote :\n" + e);
	}
    }

    public static void sendMessage(String msg, PrintWriter... pwTab) {
	for(PrintWriter pw : pwTab) {
	    pw.println(msg);
	    pw.flush();
	}
    }

}
