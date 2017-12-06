import java.net.*;
import java.lang.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.Iterator;
import java.io.*;


public class Thread_Communication_Gest extends Thread {
    private Gestionnaire gest;
    private SocketChannel socket;
    public int BUFF_SIZE = 200;

    public Thread_Communication_Gest (Gestionnaire gest, SocketChannel s) {
	this.gest = gest;
	this.socket = s;
    }

    public void run() {
	// if 4 premiers octets = REGI, alors envoyer à la fonction pour gérer les diffuseurs
	// if 4 premiers octets = LIST, alors envoyer à la fonction pour gérer les clients
	try {
	    this.socket.configureBlocking(false);
	    Selector selector = Selector.open();
	    this.socket.register(selector, SelectionKey.OP_READ);

	    boolean exit = false;
	    ByteBuffer buff = ByteBuffer.allocate(BUFF_SIZE);
	    Iterator<SelectionKey> it;
	    SelectionKey keySwitch, keyAlive;
	    String toSend = "";

	    do {
		selector.select();
		it = selector.selectedKeys().iterator();

		while(it.hasNext()) {
		    keySwitch = it.next();
		    it.remove();

		    if(keySwitch.isReadable()) {
			buff.clear();
			int bytesRead = this.socket.read(buff);
			byte[] bytes = buff.array();
			//System.out.println("[Th_Com_Gest_erk]: int bytesRead="+bytesRead);
			String mess = new String(bytes, 0, bytesRead);
			//System.out.println("[Thread_Com_Gest.java] : keySwitch:: ByteBuffer convert to String : var mess1 = "+mess);
			//mess += "\r\n";
			//System.out.println("[Thread_Com_Gest.java] : keySwitch:: ByteBuffer convert to String : var mess2 = "+mess);
		
			if(Outils.msgIsCorrect(mess)) {
			    
			    switch(mess.substring(0, 4)) {
			    case "REGI":
				// part 1 : register diff
				
				// On affiche le message recu sur le terminal de debogage
				this.gest.terminal.println("RECV:"+mess);
				this.gest.terminal.flush();

				if(this.gest.nbDiffuseurs >= this.gest.maxDiffuseurs) {
				    toSend = "RENO\r\n";
				    buff.clear();
				    buff.put(toSend.getBytes());
				    buff.flip();
				    while(buff.hasRemaining())
					this.socket.write(buff);
				    
				    // On affiche le message envoye sur le terminal de debogage
				    this.gest.terminal.println("SEND:"+toSend);
				    this.gest.terminal.flush();

				    exit = true;
				    //*1 : apres avoir envoye RENO, le gest ferme la connexion (s'assurer que ca soit fait a la fin du do while)
				}
			    
				else {
				    String[] tab = mess.split(" ");
				    //System.out.println("[Thread_Com_Gest.java] : new Diffuseur args : id=tab[1]="+tab[1]+" | ip1=tab[2]="+tab[2]+" | port1=tab[3]="+tab[3]+" | ip2=tab[4]="+tab[4]+" | port2=tab[5]="+tab[5]);
				    Diffuseur diff = new Diffuseur(tab[1], tab[2], tab[3], tab[4], tab[5]);
				    this.gest.add_diff(diff);
			
				    toSend = "REOK\r\n";
				    buff.clear();
				    buff.put(toSend.getBytes());
				    buff.flip();
				    while(buff.hasRemaining())
					this.socket.write(buff);

				    // On affiche le message envoye sur le terminal de debogage
				    this.gest.terminal.println("SEND:"+toSend);
				    this.gest.terminal.flush();


				    // part 2 : check if diff is still alive (if it has been registered)
				    boolean isAlive = true;
				    do {
					Thread.sleep(20000);
					toSend = "RUOK\r\n";
					buff.clear();
					buff.put(toSend.getBytes());
					buff.flip();
					while(buff.hasRemaining())
					    this.socket.write(buff);
					
					// On affiche le message envoye sur le terminal de debogage
					this.gest.terminal.println("SEND:"+toSend);
					this.gest.terminal.flush();
				    
					selector.select(10000); // a augmenter (fixe a 10000 pour le test)
					it = selector.selectedKeys().iterator();
					if(!it.hasNext()) {
					    this.gest.del_diff(diff);
					    isAlive = false;
					}
					else
					    while(it.hasNext()) {
						keyAlive = it.next();
						it.remove();
						if(keyAlive.isReadable()) {
						    buff.clear();
						    bytesRead = this.socket.read(buff);
						    bytes = buff.array();
						    //System.out.println("[Th_Com_Gest_erk]: int bytesRead="+bytesRead);
						    mess = new String(bytes, 0, bytesRead);
						    //System.out.println("[Thread_Com_Gest.java] : keyAlive:: ByteBuffer convert to String : var mess = "+mess);

						    // On affiche le message recu sur le terminal de debogage
						    this.gest.terminal.println("RECV:"+mess);
						    this.gest.terminal.flush();
						    
						    if(!mess.equals("IMOK")) { 
							this.gest.del_diff(diff);
							isAlive = false;
						    }
						}
					    }
					//*2 : apres avoir recu un msg different de IMOK, le gest ferme la connexion (s'assurer que ca soit fait)
				    } while(isAlive);
				} // FIN DU else du "if(this.gest.nbDiffuseurs >= this.gest.maxDiffuseurs)"
				break;

			    
			    case "LIST":
				// part 2 : communication with clients

				// On affiche le message recu sur le terminal de debogage
				this.gest.terminal.println("[Gestionnaire]::RECV: "+mess);
				this.gest.terminal.flush();

				String num_diff = "";
				if(this.gest.nbDiffuseurs < 10)
				    num_diff += "0"+this.gest.nbDiffuseurs;
				else num_diff += this.gest.nbDiffuseurs;
			    
				toSend = "LINB "+num_diff+"\r\n";
				buff.clear();
				buff.put(toSend.getBytes());
				buff.flip();
				while(buff.hasRemaining())
				    this.socket.write(buff);
				
				// On affiche le message envoye sur le terminal de debogage
				this.gest.terminal.println("SEND:"+toSend);
				this.gest.terminal.flush();

				for (Diffuseur diff : this.gest.liste) {
				    toSend = "ITEM "+diff.id+" "+diff.ipv4_diff+" "+diff.port_diffusion+" "+diff.ipv4_machine+" "+diff.port_reception+"\r\n";
				    buff.clear();
				    buff.put(toSend.getBytes());
				    buff.flip();
				    while(buff.hasRemaining())
					this.socket.write(buff);

				// On affiche le message envoye sur le terminal de debogage
				    this.gest.terminal.println("SEND:"+toSend);
				    this.gest.terminal.flush();
				}
				//*3 : apres avoir envoye sa liste de diff, le gest ferme la connexion (s'assurer que ca soit fait)
				exit = true;
				break;

			    default:
				// Il est impératif de respecter scrupuleusement la spéciﬁcation fournie dans le sujet ainsi que les formats de message. Toute violation sera jugée très défavorablement!
				toSend = "MERR Veuillez taper une instruction correcte\r\n";
				buff.clear();
				buff.put(toSend.getBytes());
				buff.flip();
				while(buff.hasRemaining())
				    this.socket.write(buff);

				// On affiche le message envoye sur le terminal de debogage
				this.gest.terminal.println("SEND:"+toSend);
				this.gest.terminal.flush();

				break;
			    } // fin du switch(mess.substring(0, 4))
			} // fin du if(Outils.msgIsCorrect(mess))
			else {
			    System.out.println("[Th_Com_Gest]:Erreur:: message non correct");
			    exit = true;
			}
		    } // fin du if(keySwitch.isReadable())
	
		} // fin du while(it.hasNext())
	    } while(!exit);
	
	    this.socket.close();
	    // close() streams & sockets
	    // bien fermer la connexion pour *1, *2 et *3
	
	}
	catch (Exception e) {
	    System.out.println(e);
	    e.printStackTrace();
	}
    }
}
