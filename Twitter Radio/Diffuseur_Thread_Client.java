import java.util.*;
import java.net.*;
import java.io.*;

public class Diffuseur_Thread_Client extends Thread{
    public Diffuseur diff;
    public String id_client;
    public String message_client;
    
    public Diffuseur_Thread_Client(Diffuseur diff){
	this.diff = diff;
    }

    public synchronized void run(){
	try{
	    ServerSocket server = new ServerSocket(this.diff.port_reception);
	    while(true){
		Socket socket = server.accept();
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		String s = br.readLine();
		System.out.println(s+" "+s.length());
		diff.terminal.println("RECV:"+s);
		diff.terminal.flush();
		if(Outils.msgIsCorrect(s)){
		    System.out.println("le message est correct");
		    //MESS CRZ##### blabla#####....#####
		    if(s.substring(0,4).equals("MESS")){		
			this.diff.liste_ajout.add(s.substring(5)); //on ajoute le message du client dans la liste avec son id
			System.out.println("sssssssssss "+s);
			System.out.println("message ajouté");
			pw.println("ACKM");
			pw.flush();
			diff.terminal.println("SEND:ACKM\r\n");
			diff.terminal.flush();
		    }
		    //LAST 
		    else if(s.substring(0,4).equals("LAST")){
			String[] tab = s.split(" ");
			try{
			    int nb_mess = Integer.parseInt(tab[1]);
			    int nb_total_lignes=0;
			    BufferedReader bf = new BufferedReader(new FileReader("historique.txt"));
			    while(bf.readLine() != null){ // on compte le nombre de ligne du fichier
				nb_total_lignes++;
			    }
			    bf = new BufferedReader(new FileReader("historique.txt")); // on revient au debut du fichier
			    for(int i = 0 ; i < nb_total_lignes-nb_mess ; i++)
				bf.readLine();
			    for(int i = 0 ; i < nb_mess ; i++){
				String s_last = bf.readLine();
				if(s_last != null){
				    pw.println("OLDM "+s_last);
				    pw.flush();
				    this.diff.terminal.println("[Diffuseur_Th_Client]::SEND: "+s_last);
				    this.diff.terminal.flush();
				}
				else break;
			    }
			    pw.println("ENDM");
			    pw.flush();
			    this.diff.terminal.println("[Diffuseur_Th_Client]::SEND: ENDM");
			    this.diff.terminal.flush();
			    bf.close();
			}catch(NumberFormatException nfe){
			    System.out.println("[Diffuseur_Thread_Client.java] : "+nfe);
			}
		    }
		    else
			System.out.println("Le message est incorrect, veuillez réessayer");
		    pw.close();
		    br.close();
		    socket.close();
		}
		else 
		    System.out.println("[Diffuseur_Thread_Client.java] : msgIsNotCorrect");
	    }
	    
	}catch(Exception e){
	    System.out.println("[Diffuseur_Thread_Client.java] : "+e);
	}
    }
}
