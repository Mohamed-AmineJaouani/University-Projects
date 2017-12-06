import java.util.*;
import java.net.*;
import java.io.*;

public class Diffuseur_Thread_RUOK extends Thread{
    
    public Diffuseur diff;
    public Socket socket;
    
    public Diffuseur_Thread_RUOK(Diffuseur diff,Socket socket){
	this.diff = diff;
	this.socket = socket;
    }
    
    public void run(){
	try{
	    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	    while(true){
		String s = br.readLine();
		diff.terminal.println("RECV:"+s);
		diff.terminal.flush();
		if(Outils.msgIsCorrect(s)){
		    if(s.equals("RUOK")){
			pw.print("IMOK");
			pw.flush();
			diff.terminal.println("SEND:IMOK\r\n");
			diff.terminal.flush();
		    }
		    else System.out.println("c'est pas RUOK !");
		}else System.out.println("[Diffuseur_T_RUOK] : mgIsNotCorrect");
	    }
	    //pw.close();
	    //br.close();
	    //socket.close();
	}catch(Exception e){
	    System.out.println("[Diffuseur_Thread_RUOK.java] : "+e);
	}
    }
}