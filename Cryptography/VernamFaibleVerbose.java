import java.util.Random;

public class VernamFaibleVerbose {

    //Question 1 - partie 1/3
    public static String G() {
	Random random = new Random();
	String keyStr = "";
	for(int i=0 ; i<4 ; i++)
	    keyStr += (char)random.nextInt((int)Math.pow(2, 16));
	return keyStr;
    }
    //Question 1 - partie 2/3 - sous-partie 1/2
    public static String E(String plainText, String key) {
	String ret = "";
	for(int i=0 ; i<plainText.length() ; i++)
	    ret += plainText.charAt(i) ^ key.charAt(i % key.length());
	return ret;
    }
    //Question 1 - partie 2/3 - sous-partie 2/2
    public static String Echars(String plainText, String key) {
	String ret = "";
	for(int i=0 ; i<plainText.length() ; i++)
	    ret += (char)(plainText.charAt(i) ^ key.charAt(i % key.length()));
	return ret;
    }
    //Question 1 - partie 3/3 - sous-partie 1/2
    public static String D(String cipherText, String key) {
	String ret = "";
	for(int i=0 ; i<cipherText.length() ; i++)
	    ret += cipherText.charAt(i) ^ key.charAt(i % key.length());
	return ret;
    }
    //Question 1 - partie 3/3 - sous-partie 2/2
    public static String Dchars(String cipherText, String key) {
	String ret = "";
	for(int i=0 ; i<cipherText.length() ; i++)
	    ret += (char)(cipherText.charAt(i) ^ key.charAt(i % key.length()));
	return ret;
    }
    
    public static void main(String[] args) {
	String key = G();
	String plainText = "Salutations ami chinois et frère rebeu";
	String cipherText = E(plainText, key);
	String decipherText = D(cipherText, key);
	String eecipherText = E(cipherText, key);
	String cipherTextChars = Echars(plainText, key);
	String decipherTextChars = Dchars(cipherTextChars, key);
	System.out.println("key = " + key + "|\n");
	System.out.println("plainText = " + plainText + "|\n");
	System.out.println("cipherText = " + cipherText + "|\n");
	System.out.println("decipherText = " + decipherText + "|\n");
	System.out.println("eecipherText = " + eecipherText + "|\n");
	System.out.println("cipherTextChars = " + cipherTextChars + "|\n");
	System.out.println("decipherTextChars = " + decipherTextChars + "|");
    }

}
