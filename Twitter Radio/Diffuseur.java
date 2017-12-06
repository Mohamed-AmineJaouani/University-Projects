import java.util.*;
import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Diffuseur extends Thread {
    public String id;                  // Id de taille au plus 8
    public String ipv4_machine;   // Addresse ipv4 de la machine du diffuseur
    public int port_reception;         // Port de reception au plus 9999
    public String ipv4_diff;           // Addresse ipv4 de multidiffusion de taille 15
    public int port_diffusion;         // Port de multidiffusion au plus 9999
    public ArrayList<String> liste;    // Liste des messages
    public ArrayList<String> liste_ajout;
    public PrintWriter terminal;       // terminal pour afficher les erreurs
    
    public Diffuseur(String id, String ipv4_diff, String port_diffusion, String ipv4_machine, String port_reception){
	
	try{
	    String ipv4_machine_complet = Outils.completer_ip(ipv4_machine);
	    String ipv4_diff_complet = Outils.completer_ip(ipv4_diff);
	    if(Outils.verifie_arguments_diffuseur(Outils.rajouter_diez(id,Outils.FLAG_ID), ipv4_machine_complet, port_reception, ipv4_diff_complet, port_diffusion)){
		this.id = Outils.rajouter_diez(id,Outils.FLAG_ID);
		this.ipv4_diff = ipv4_diff_complet;
		this.port_diffusion = Integer.parseInt(port_diffusion);
		this.ipv4_machine = ipv4_machine_complet;
		this.port_reception = Integer.parseInt(port_reception);
	    }
	}catch(Exception e){
	    this.terminal.println(e);
	    this.terminal.flush();
	}
	this.liste = new ArrayList<String>();
	this.liste_ajout = new ArrayList<String>();
	this.liste.add(Outils.rajouter_diez(this.id,Outils.FLAG_ID)+" "+Outils.rajouter_diez("bonjour je m'appelle mohamed amine jaouani",Outils.FLAG_MESS));
	this.liste.add(Outils.rajouter_diez(this.id,Outils.FLAG_ID)+" "+Outils.rajouter_diez("aurevoir a demain",Outils.FLAG_MESS));
	this.liste.add(Outils.rajouter_diez(this.id,Outils.FLAG_ID)+" "+Outils.rajouter_diez("il est 16h42",Outils.FLAG_MESS));
	this.liste.add(Outils.rajouter_diez(this.id,Outils.FLAG_ID)+" "+Outils.rajouter_diez("nous sommes le 25 avril 2015",Outils.FLAG_MESS));
	this.liste.add(Outils.rajouter_diez(this.id,Outils.FLAG_ID)+" "+Outils.rajouter_diez("les exams sont dans 3 semaines",Outils.FLAG_MESS));
	this.liste.add(Outils.rajouter_diez(this.id,Outils.FLAG_ID)+" "+Outils.rajouter_diez("salut",Outils.FLAG_MESS));
	this.liste.add(Outils.rajouter_diez(this.id,Outils.FLAG_ID)+" "+Outils.rajouter_diez("t'aimes la dinde sechee",Outils.FLAG_MESS));
    }
    
    public void run(){
	try{
	    int num_mess=0;
	    DatagramSocket dso = new DatagramSocket();
	    byte[] data;
	    
	    String s="",envoi="";
	    PrintWriter historique = new PrintWriter(new FileWriter("historique.txt",true));
	    while(true){
		Iterator<String> it = this.liste.iterator();
		while(it.hasNext()){
		    //DIFF 0012 CRZ##### blabla####...####\r\n
		    Thread.sleep(2000);
		    envoi = Outils.completer_zero(num_mess%10000,Outils.FLAG_NUM_MESS)+" "+it.next();
		    historique.println(envoi);
		    historique.flush();
		    this.terminal.println("DIFF "+envoi.substring(0,4)+" "+Outils.retirer_diez(envoi.substring(5,13))+" "+Outils.retirer_diez(envoi.substring(14))+"\n");
		    this.terminal.flush();
		    num_mess++;
		    envoi = "DIFF "+envoi;
		    data = envoi.getBytes();
		    InetSocketAddress ia = new InetSocketAddress(this.ipv4_diff,this.port_diffusion);
		    DatagramPacket paquet = new DatagramPacket(data,data.length,ia);
		    dso.send(paquet);
		}
		this.liste.addAll(this.liste_ajout);
		this.liste_ajout.clear();
	    }
	}catch(Exception e){
	    this.terminal.println("[Diffuseur.java] : "+e);
	    this.terminal.flush();
	}
    }
    
    public static void main(String[] args){
	try{
	    /* args[0] = @gestionnaire, args[1] = portGestionnaire */
	    Socket socket = new Socket(args[0],Integer.parseInt(args[1]));
	    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	    Scanner sc = new Scanner(System.in);
	    String s = sc.nextLine();
	    System.out.println("nextLine"+s+"!");
	    String[] tab = s.split(" ");
	    String message_regi = tab[0]+" "+Outils.rajouter_diez(tab[1],Outils.FLAG_ID)+" "+
		Outils.completer_ip(tab[2])+" "+tab[3]+" "+Outils.completer_ip(tab[4])+" "+tab[5];
	    pw.print(message_regi);
	    pw.flush();
	    String recu = br.readLine();
	    if(Outils.msgIsCorrect(recu)){
		if(recu.equals("REOK")){
		    Diffuseur diff = new Diffuseur(tab[1],tab[2],tab[3],tab[4],tab[5]);
		    Diffuseur_Thread_Client dtc = new Diffuseur_Thread_Client(diff);
		    Diffuseur_Thread_RUOK du = new Diffuseur_Thread_RUOK(diff,socket);
		    try{
			FileWriter f = new FileWriter(args[2],true);
			BufferedWriter bf = new BufferedWriter(f);
			diff.terminal = new PrintWriter(bf);
		    }catch(IOException ioe){
			System.out.println(ioe);
		    }
		    diff.terminal.println("SEND:"+s);
		    diff.terminal.flush();
		    diff.terminal.println("RECV:REOK");
		    diff.terminal.flush();
		    
		    diff.start();
		    dtc.start();
		    du.start();
		}else if(recu.equals("RENO")){
		    System.out.println("Connexion refusée");
		}
	    }else System.out.println("[diff] message non correct");
	}catch(Exception e){
	    System.out.println("[Diffusueur] :: Vous devez entrez 3 arguments de la forme : addressegest port_gest terminal");
	    System.out.println(e);
	}
    }
}