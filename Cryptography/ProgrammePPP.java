import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Random;

public class ProgrammePPP {
    public static void main(String[] args) {
	if(args.length != 0 && args.length != 1 && args.length != 2)
	    perror("Erreur sur le nombre d'argument. Veuillez entrer un nombre.");
	else if(args.length == 0)
	    printHelp();
	else {
	    //GESTION DES ARGUMENTS
	    if(args.length == 2 && !args[1].equals("-v"))
		perror("Erreur sur le 2ème argument : \"-v\" pour l'option verbose");
	    boolean verbose = (args.length == 2 && args[1].equals("-v"))? true : false;
	    
	    BigInteger n = null;
	    try {
		n = new BigInteger(args[0]);
	    } catch(NumberFormatException e) { System.out.println(e); }
	    if(n.doubleValue() < 2) perror("Erreur : Votre nombre doit être supérieur ou égal à 2.");

	    
	    long debut = System.currentTimeMillis();
	    String result = (PPP(n, verbose))? n + " est probablement pseudo-premier." : n + " n'est pas pseudo-premier.";
	    
	    if(verbose)
		result = (System.currentTimeMillis() - debut + "ms : ") + result;

	    System.out.println(result);
	}
    }


    public static boolean PPP(BigInteger n, boolean verbose) {
	boolean result = true;
	double pn = 1/(2 * Math.sqrt(n.doubleValue()));
	double a = 1 - pn;
	double K_double = - (Math.log(Math.pow(2, 100))/Math.log(a));
	double K = Math.ceil(K_double);
	if(verbose)
	    System.out.println("pn = "+ pn +" , (1 - pn) = "+ a +" , K = "+ K); // +" , K_double = "+ K_double);

	Random rand = new Random();
	BigInteger nbRandomBI;
	double cpt = K;

	while(cpt > 0) {
	    if(verbose)
		System.out.println("cpt = "+ cpt);

	    nbRandomBI = new BigInteger(n.bitLength(), rand);
	    if(verbose)
		System.out.println("Nombre tiré aléatoirement entre 2 et n-1 : nbRandomBI = "+ nbRandomBI);

	    //Si nbRandom n'est pas premier avec n, on retire un nombre aléatoire
	    if( !nbRandomBI.gcd(n).equals(BigInteger.ONE) ) continue;
	    else {
		if(testFermat(nbRandomBI, n) == false) {
		    result = false;
		    if(verbose)
			System.out.println(nbRandomBI +" ne passe pas le test de Fermat pour n = "+ n);
		    break;
		}
		cpt--;
	    }
	}
	return result;
    }


    public static boolean testFermat(BigInteger a, BigInteger n) {
	return a.modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE);
    }

    public static void perror(String m) {
	System.out.println(m);
	System.exit(0);
    }

    public static void printHelp() {
	System.out.println("Arguments à entrer :\n\t1er argument : votre nombre à tester\n\t2ème argument: \"-v\" pour l'option verbose (optionnel)");
    }
}
