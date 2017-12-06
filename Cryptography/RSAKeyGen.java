/**
 * Générateur de clé RSA
 */


import java.math.BigInteger;
import java.io.IOException;

public class RSAKeyGen{


  /**
   * genere une paire de clé pour RSA dont  le module a le nombre de bits donné
   */
  public static RSAKey genKey(int nbBit){
    RandomGen random = new RandomGen(nbBit/2,true);
    
    System.out.println("Caclul de p.");
    BigInteger p = random.rand();
    System.out.println("p = "+p);
    
    System.out.println("Caclul de q.");
    BigInteger q = null;
    do{
    q = random.rand();
    }while(p.equals(q)); //pd ne doit pas etre egal a q
    System.out.println("q = "+q);
    
    System.out.println("Caclul de n.");
    BigInteger n = p.multiply(q);
    System.out.println("n = "+n);
    
    System.out.println("Calul de phi(n).");
    BigInteger phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    System.out.println("phi(n) = "+phiN);
    
    System.out.println("Calcul de e.");
    boolean found = false;
    BigInteger e=null;
    while (!found){ //la boucle s'arrete quand e premier avec phi(n)
      e = random.rand();
      if(e.compareTo(phiN) < 0 && e.gcd(phiN).equals(BigInteger.ONE)) found = true;
    }
    System.out.println("e = "+e);
    
    System.out.println("calcul de d.");
    BigInteger d = e.modInverse(phiN);
    System.out.println("d = "+d);

    return new RSAKey(n,d,e);
  }
    
  /**
   * genere un fichier name.pub et un fichier name .priv qui contiennent les clés public et privé 
   */
  public static void genKey(String name,int nbBit) throws IOException{
    RSAKey key = genKey(nbBit);
    RSAKeyPublic pubKey = key.getPublicKey();
    pubKey.store(name +".pub");
    RSAKeyPrivate privKey = key.getPrivateKey();
    privKey.store(name+".priv");
  }

  private static void usage(){
    System.err.println("genkey key_name nb_bits");
    System.exit(1);
  }

  /*test*/
  public static void main(String[] args){
    try{
      if(args.length != 2 ) usage();
      try{
	int nbBit = Integer.parseInt(args[1]);
  	genKey(args[0],nbBit);
      }catch(NumberFormatException e){
	usage();
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
