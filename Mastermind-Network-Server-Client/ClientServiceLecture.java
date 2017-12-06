import java.net.*;
import java.io.*;

public class ClientServiceLecture extends Thread {
    public Socket socket;

    public ClientServiceLecture(Socket s) { this.socket = s; }
    
    public void run() {
	try(BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
	    while(true) {
		String msg = br.readLine();
		if(msg == null) break;
		System.out.println(msg);
	    }
	} catch(IOException e) {
	    System.out.println("Déconnexion...");
	    //System.out.println("[ClientServiceLecture]-[run()] :\n" + e);
	}
    }
}
