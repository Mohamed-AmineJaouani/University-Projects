import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
public class Outils{

    public final static int FLAG_ID = 0;
    public final static int FLAG_MESS = 1;
    public final static int FLAG_NUM_MESS = 2;
    public final static int FLAG_NB_MESS = 3;
   
    //fonction préliminaire qui renvoi true si un message est de la bonne forme sinon on ne traite pas ce message
    public static boolean msgIsCorrect(String s){
	System.out.println("[Outils.java || msgIsCorrect()] : "+s); 
	String[] tab = s.split(" ");
      	switch(s.substring(0,4)){
	case "DIFF":
	    return (s.length() == 159);
	case "MESS":
	    return (s.length() == 154);
	case "ACKM":
	    return true;
	case "LAST":
	    return (tab[1].length() == 3);
	case "OLDM":
	    return (s.length() == 159);
	case "ENDM":
	    return true;
	case "REGI":
	    return (tab[1].length() == 8 && tab[2].length() == 15 && tab[3].length() == 4 && tab[4].length() == 15 && tab[5].length() == 4);
	case "REOK":
	    return true;
	case "RENO":
	    return true;
	case "RUOK":
	    return true;
	case "IMOK":
	    return true;
	case "LIST":
	    return true;
	case "LINB":
	    return (tab[1].length() == 2);
	case "ITEM":
	    return (tab[1].length() == 8 && tab[2].length() == 15 && tab[3].length() == 4 && tab[4].length() == 15 && tab[5].length() == 4);
	}
	return false;
    }
    
    public static boolean verifie_arguments_gestionnaire(String port, String max_diffuseur){
	try{
	    int _port = Integer.parseInt(port);
	    int _max_diffuseur = Integer.parseInt(max_diffuseur);
	    if(_port < 1024 || _port > 9999 || _max_diffuseur < 0 || _max_diffuseur > 99){
		System.out.println("[Outils.java]::Erreur:: Veuillez choisir un port compris entre 1024 et 9999 ainsi qu'un nombre maximal de diffuseur compirs entre 0 et 99");
		return false;
	    }
	}catch(NumberFormatException nfe){
	    System.out.println("[Outils.java]::Erreur:: Le port et le nombre max de diffuseur ne sont pas entiers \n\n\n");
	    System.out.println(nfe);
	    nfe.printStackTrace();
	}
	return true;
    }

    public static boolean verifie_arguments_diffuseur(String id, String ipv4_machine, String port_reception, String ipv4_diff, String port_diffusion){
	System.out.println("id="+id+" ip4_machine="+ipv4_machine+" port reception="+ port_reception+" ipv4_diff="+ipv4_diff+" port_diff="+port_diffusion);
	if(id.length() == 8 
	   && port_reception.length() == 4
	   && ipv4_diff.length() == 15 
	   && ipv4_machine.length() == 15
	   && port_diffusion.length() == 4 ){
	    
	    try{
		int port_reception1=Integer.parseInt(port_reception);
		int port_diffusion1=Integer.parseInt(port_diffusion);
		if(port_reception1 < 1024 || port_reception1 > 9999 || port_diffusion1 < 1024 || port_diffusion1 > 9999){
		    System.out.println("[Outils.java]::Erreur:: Mauvais numéro de port -> [1024-9999]");
		    return false;
		}
	    }catch(NumberFormatException nfe){
		System.out.println(nfe);
		return false;
	    }
	}
	else {
	    System.out.println("[Outils.java]::Erreur:: id doit être de taille 8 / port_reception doit être de taille 4 / ipv4 doit être de taille 15 / port_diffusion doit être de taille 4");
	    return false;
	}
	return true;
    }


    public static String rajouter_diez(String s, int flag){
	int taille = s.length();
	if(flag == FLAG_ID){ // on complete un id avec des #
	    if(taille >= 8)
		return s.substring(0,8);
	    for(int i = 0 ; i < 8-taille ; i++)
		s+="#";
	}
	else if(flag == FLAG_MESS){ // on complete un message avec des #
	    if(taille >= 140)
		return s.substring(0,140);
	    for(int i =  0 ; i < 140-taille ; i++)
		s+="#";
	}
	return s;
    }

    public static String retirer_diez(String s){
	String r="";
	for(int i = 0 ; i < s.length() ; i++){
	    if(s.charAt(i) == '#')
		break;
	    else
		r+=s.charAt(i);
	}
	return r;
    }
    
    public static String completer_zero(int n, int flag){
	String s="";
	if(n >= 0 && n <= 9){
	    if(flag == FLAG_NUM_MESS)
		s+="000"+n;
	    else if(flag == FLAG_NB_MESS)
		s+="00"+n;
	}
	else if(n >= 10 && n <= 99){
	    if(flag == FLAG_NUM_MESS)
		s+="00"+n;
	    else if(flag == FLAG_NB_MESS)
		s+="0"+n;
	}
	else if(n >= 100 && n <= 999){
	    if(flag == FLAG_NUM_MESS)
		s+="0"+n;
	    else if(flag == FLAG_NB_MESS)
		s+=""+n;
	}
	else if(n >=1000 && n <= 9999 && flag == FLAG_NUM_MESS)
	    s=""+n;
	return s;
    }

    public static String completer_ip(String ip){
	if(ip.length() == 15)
	    return ip;
	String[] tab = ip.split("\\.");
	String r="";
	for(String s : tab){
	    if(s.length() == 3){
		r+=s+".";
	    }
	    else if(s.length() == 1)
		r+="00"+s+".";
	    else if(s.length() == 2)
		r+="0"+s+".";
	    else {System.out.println("[Outils.java]::Erreur:: Mauvaise Addresse ip");System.exit(0);}
	}
	System.out.println(r.substring(0,15));
	return r.substring(0,15);
    }

    public static void main(String[] args){
	
    }
}
