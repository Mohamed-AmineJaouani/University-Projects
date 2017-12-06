import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientTCP {
    public Socket socket;
    
    private static void perror(String msg) {
	System.out.println(msg);
	System.exit(0);
    }
   
    public static void main(String[] args) {
	if(args.length != 2) {
	    perror("Erreur nombre d'arguments : $1 = @IP serveur, $2 = port serveur");
	}
	else {
	    Scanner sc = new Scanner(System.in);
	  
	    try(Socket socket = new Socket(args[0], Integer.parseInt(args[1]))) {
		ClientServiceLecture servL = new ClientServiceLecture(socket);
		Thread t = new Thread(servL);
		t.start();

		try(PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
		    while(true) {
			String input = sc.nextLine();
			String inputToSend = "";
			
			for(int i=0 ; i<input.length() ; i++)
			    if(input.charAt(i) != '\0')
				inputToSend += input.charAt(i);

			pw.print(inputToSend + "\n");
			pw.flush();

			if(input.equals("QUIT"))
			    break;
		    }
		    servL.interrupt();
		} catch(IOException e) {
		    System.out.println("Erreur lors de l'exécution d'un ClientTCP\n :" + e);
		    //e.printStackTrace();
		}
	    } catch(UnknownHostException uhe) {
		System.out.println("L'hôte n'a pas été reconnu :\n" + uhe);
		//uhe.printStackTrace();
	    } catch(IOException ioe) {
		System.out.println("Une erreur de flux a été détecté :\n" + ioe);
		//ioe.printStackTrace();
	    }
	} //fin else args.length == 2
    }
}
