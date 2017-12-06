import java.io.IOException;
public class ChiffrementHybride {

    ChiffrementABloc cab = new ChiffrementABloc();

    public String completerBinaire(String cle){
	while(cle.length() < 16){
	    cle = '0'+cle;
	}
	return cle;
    }
    
    public String chiffrementHybride(String clefRSA, String m){
	// ENVOI
	// on commence par chiffrer la clef de session symetrique du TP5 avec RSA grace a la clef publique du destinataire
	// on chiffre le message avec la clef de chiffrement du TP5
	// on envoi le message chiffré avec la clef chiffré
	System.out.println("ENVOI DU MESSAGE");
	System.out.println("MESSAGE                  : 1101101101101101");
	String clefSym = new KeyGenerator16Bits().key;
	System.out.println("CLEF SYMETRIQUE          : "+clefSym);
	String cleSymetriqueChiffre = completerBinaire(Chiffrement.chiffrement(clefRSA+".pub", clefSym));

	System.out.println("CLEF SYMETRIQUE CHIFFRÉE : "+cleSymetriqueChiffre);
	String c = cab.Fk_chiffrementCTR(clefSym, m);
	System.out.println("CHIFFREMENT DU MESSAGE   : "+c);
	System.out.println("MESSAGE FINAL A ENVOYER  : "+cleSymetriqueChiffre+c);
	System.out.println("\n\n\n");
	
	// RECEPTION
	// on recoit un message chiffré accompagné d'une clef chiffré
	// on déchiffre la clef grace a sa propre clef privee RSA -> on a la clef pour dechiffrer le message
	// on dechiffre le message avec cette clef
       	System.out.println("RECEPTION DU MESSAGE");
	String finalC = cleSymetriqueChiffre+c;
	System.out.println("MESSAGE CRYPTÉE RECU     : "+finalC);
	String clefSymChiffre = finalC.substring(0,16);
	System.out.println("CLEF SYMETRIQUE CHIFFRE  : "+clefSymChiffre);
	String clefSym2 = completerBinaire(Dechiffrement.dechiffrement(clefRSA+".priv", clefSymChiffre));
	System.out.println("CLEF SYMETRIQUE CLAIR    : "+clefSym2);
	String mm = cab.Fk_dechiffrementCTR(clefSym2, finalC.substring(16));
	System.out.println("MESSAGE EN CLAIR         : "+mm);
	return mm;
    }
    
    public static void main(String[] args){
	String m = "1101101101101101";
	
	ChiffrementHybride ch = new ChiffrementHybride();
	String finalcypher = ch.chiffrementHybride(args[0], m);
    }
}
