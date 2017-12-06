import java.math.BigInteger;

public class Dechiffrement {
  
  private static void usage(){
    System.err.println("usage :  rsa_decrypt nom_cle nombre");
    System.exit(1);
  }

    public static String dechiffrement(String clef, String nombre){
	BigInteger clair = null;
	try{ 
	    BigInteger c = new BigInteger(nombre);
	    
	    RSAKeyPrivate key = new RSAKeyPrivate(clef);
	    
	    BigInteger privateExponent = key.getPrivateExponent();
	    BigInteger modulus = key.getModulus();
	    
	    //System.out.println("modulus : "+modulus +" exposant  : "+privateExponent);
	    clair = c.modPow(privateExponent, modulus);//decryptage
	    
	    //System.out.println(clair);
	    
	}catch(Exception e){
	    System.out.println(e.getClass()+" : "+e.getMessage());
	}
	return Integer.toBinaryString(clair.intValue());
    }
    
    
    public static void main(String[] args) {
	
	if(args.length != 2) usage();
	dechiffrement(args[0]+".priv", args[1]);
    }
}
