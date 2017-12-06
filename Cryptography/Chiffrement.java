
import java.math.BigInteger;

public class Chiffrement {

    private static void usage(){
	System.err.println("usage :  rsa_crypt nom_cle nombre");
	System.exit(1);
    }

    public static String chiffrement(String clef, String nombre){
	BigInteger c = null;
    	try {
	    BigInteger message = new BigInteger(nombre);
	    
	    RSAKeyPublic key = new RSAKeyPublic(clef);
	    
	    BigInteger publicExponent = key.getPublicExponent();
	    BigInteger modulus = key.getModulus();
	    
	    //System.out.println("modulus : "+modulus +" exposant  : "+publicExponent);
	    c = message.modPow(publicExponent, modulus);//encryptage
	    
	    //	    System.out.println(c);
	}catch(Exception e){
	    System.err.println(e.getClass()+ " : "+e.getMessage());
	}
	return Integer.toBinaryString(c.intValue());
    }
    
    public static void main(String[] args) {
	if(args.length != 2) usage();
	String c = chiffrement(args[0]+".pub", args[1]);
	System.out.println(c);
    }
}
