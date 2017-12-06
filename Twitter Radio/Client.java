import java.io.*;
import java.net.*;
import java.util.Scanner;

/* Prend en argument dans cet ordre : 
   args[0] : une adresse ip ou se connecter,
   args[1] : le port associe a cette adresse,
   args[2] : un flag indiquant si on souhaite communiquer en TCP (flag = 1) ou écouter un diffuseur en UDP (flag = 0)
   args[3] : le nom d'un terminal (cmd : tty) pour afficher les messages recus et envoyes entre entites
 */
public class Client{
    public static void main(String [] args){

	try{
	    FileWriter fw = new FileWriter(args[3],true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter pw = new PrintWriter(bw);

	    // On souhaite ecouter un diffuseur sur sa chaine de multi-diffusion
	    if (args[2].equals("0")) {
		MulticastSocket mso = new MulticastSocket(Integer.parseInt(args[1]));
		mso.joinGroup(InetAddress.getByName(args[0]));
		byte[] data = new byte[200];
		DatagramPacket paquet = new DatagramPacket(data, data.length);
		while(true) {
		    mso.receive(paquet);
		    String st = new String(paquet.getData(), 0, paquet.getLength());
		    pw.println("RECV:"+Outils.retirer_diez(st.substring(10,18))+" "+Outils.retirer_diez(st.substring(19)));
		    pw.flush();
		}
	    }
	    
	    // on souhaite communiquer en TCP avec une autre entite (gestionnaire ou diffuseur)
	    else if(args[2].equals("1")){
		Socket s = new Socket(args[0],Integer.parseInt(args[1]));
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter pr = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		
		Scanner sc = new Scanner(System.in);
		String mess = sc.nextLine();//LIST ou LAST ou MESS
		String[] tab = mess.split(" ");
		
		if(tab.length == 3){
		    //MESS AWK blabla
		    String message = Outils.rajouter_diez(mess.substring(6+tab[1].length()),Outils.FLAG_MESS);
		    mess = tab[0]+" "+Outils.rajouter_diez(tab[1],Outils.FLAG_ID)+" "+message;
		    pr.println(mess);
		}
		//LAST 10
		else if(tab.length == 2){
		    mess = tab[0]+" "+Outils.completer_zero(Integer.parseInt(mess.substring(5)),Outils.FLAG_NB_MESS);
		    pr.println(mess);
		}
		else
		    pr.print(mess);
		pr.flush();

		System.out.println("SEND:"+mess);
		//pw.flush();
		String reception = br.readLine();
		System.out.println("RECV:"+reception);
		//pw.flush();
		switch(reception.substring(0,4)){
		case "LINB": //si on envoi LIST
		    int num_diff = Integer.parseInt(reception.substring(5,7));
		    for(int i = 0 ; i < num_diff ; i++){
			reception = br.readLine();
			System.out.println("[Client]::RECV: "+reception);
			//pw.flush();
		    }
		    break;
		case "OLDM": //si on envoi LAST
		    do{
			reception = br.readLine();
			System.out.println("[Client]::RECV: "+reception);
			//pw.flush();
		    }while(!reception.substring(0,4).equals("ENDM"));
		    break;
		case "ACKM": //si on envoi MESS
		    //deja copier dans le terminal
		    break;
		default:
		    pr.println("MERR instruction incorrect");
		    pr.flush();
		    pw.println("MERR instruction incorrect");
		    pw.flush();
		    break;			
		}
	    }
	}catch(Exception e){
	    System.out.println(e);
	}
    }
}
