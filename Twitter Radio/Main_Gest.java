import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.lang.*;
import java.io.*;

public class Main_Gest {

    public static void main(String[] args) {

	if(args.length != 3) {
	    System.out.println("Veuillez saisir trois arguments (dans l'ordre) : un port, un nombre maximal de diffuseurs, et un nom de terminal de debogage");
	    System.exit(0);
	}

	if(!Outils.verifie_arguments_gestionnaire(args[0], args[1]))
	    System.exit(0);


	Gestionnaire gest = new Gestionnaire(args[0], args[1]);
	try {
	    FileWriter f = new FileWriter(args[2], true);
	    BufferedWriter bf = new BufferedWriter(f);
	    gest.terminal = new PrintWriter(bf);
	} catch (IOException ioe) {
	    System.out.println(ioe);
	    ioe.printStackTrace();
	}


	int port = Integer.parseInt(args[0]);

	try {
	    ServerSocketChannel server = ServerSocketChannel.open();
	    server.socket().bind(new InetSocketAddress(port));
	    while (true) {
		SocketChannel socket = server.accept();
		Thread_Communication_Gest th = new Thread_Communication_Gest(gest, socket);
		th.start();
	    }
	} catch (Exception e) {
	    System.out.println(e);
	    e.printStackTrace();
	}
	
    }

}
