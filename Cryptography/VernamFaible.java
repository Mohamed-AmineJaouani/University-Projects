import java.util.Random;

public class VernamFaible {
    public static final int ENCRYPT = 0;
    public static final int DECRYPT = 1;

    //Question 1 - partie 1/3
    public static String G() {
	Random random = new Random();
	String keyStr = "";
	for(int i=0 ; i<4 ; i++)
	    keyStr += (char)random.nextInt((int)Math.pow(2, 16));
	return keyStr;
    }
    //Question 1 - partie 2/3
    public static String E(String key, String plainText) {
	String ret = "";
	for(int i=0 ; i<plainText.length() ; i++)
	    ret += (char)(plainText.charAt(i) ^ key.charAt(i % key.length()));
	return ret;
    }
    //Question 1 - partie 3/3
    public static String D(String key, String cipherText) {
	String ret = "";
	for(int i=0 ; i<cipherText.length() ; i++)
	    ret += (char)(cipherText.charAt(i) ^ key.charAt(i % key.length()));
	return ret;
    }

    public static void main(String[] args) {
	if(args.length != 0  && args.length != 1 && args.length != 3)
	    perror("Erreur sur le nombre d'arguments.");
	else if(args.length == 0)
	    printHelp();
	else if(args.length == 1)
	    if(args[0].equals("--keygen"))
		System.out.println(G());
	    else
		perror("Erreur sur l'argument. Lancez le programme sans argument pour afficher l'aide aux arguments.");
	else {
	    int action = ENCRYPT; //par défaut
	    
	    if(args[0].equals("--encrypt")) action = ENCRYPT;
	    else if(args[0].equals("--decrypt")) action = DECRYPT;
	    else perror("Erreur sur le 1er argument");

	    try {
		String res = "";
		if(action == ENCRYPT)
		    res = E(args[1], args[2]);
		else if(action == DECRYPT)
		    res = D(args[1], args[2]);
		System.out.println(res);
	    } catch(Exception e) {
		System.out.println("Veuillez vérifiez vos arguments :\n" + e);
	    }
	    
	}
    }

    public static void perror(String m) {
	System.out.println(m);
	System.exit(0);
    }

    public static void printHelp() {
	System.out.println("Lancez le programme sans argument pour afficher l'aide (ce que vous venez de faire)\n\nLancez le programme avec l'option \"--keygen\" pour générer et afficher une clef aléatoire de 4 char\n\nPour chiffrer ou déchiffrer suivre la norme suivante :\n\tArguments à entrer :\n\t\t1er argument : \"--encrypt\" pour chiffrer et \"--decrypt\" pour déchiffrer\n\t\t2ème argument: la clef à utiliser\n\t\t3ème argument: le message à (de)chiffrer");
    }
    
    /*
    public static void main(String[] args) {
       
	String key = G();
	String plainText = "Salutations ami chinois et frère rebeu";
	String cipherText = E(key, plainText);
	String decipherText = D(key, cipherText);
	System.out.println("key = " + key + "|\n");
	System.out.println("plainText = " + plainText + "|\n");
	System.out.println("cipherText = " + cipherText + "|\n");
	System.out.println("decipherText = " + decipherText + "|\n");
    }
    */
}
