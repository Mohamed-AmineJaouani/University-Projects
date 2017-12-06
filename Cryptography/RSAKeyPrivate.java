import java.security.interfaces.RSAPrivateKey;
import java.math.BigInteger;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;

import java.net.URL;

public class RSAKeyPrivate implements RSAPrivateKey{

    private BigInteger modulus;//le module n

    private BigInteger privateExponent;//exposent privé d


    private static final long serialVersionUID=1L;//numéro de série de la classe pour la sérialisation

    /**
     * construit une nouvelle  clé  privée RSA
     * @param modulus le module n
     * @param privateExponent l'exposant privé d
     * */
    public RSAKeyPrivate(BigInteger modulus,BigInteger privateExponent){
	this.modulus=modulus;
	this.privateExponent=privateExponent;
    }

    /**
     * construit une nouvelle clé privé a partir d'un fichier de sauvegarde
     * @param filename le nom du fichier
     */
    public RSAKeyPrivate(String filename) throws IOException,ClassNotFoundException{
	load(filename);
    }

    /**
     * retoune le module
     * @return le module
     * */
    public BigInteger getModulus(){
	return modulus;
    }

    /**
     * reoutoune l'exposant privé
     * @return l'exposant privé
     */
    public BigInteger getPrivateExponent(){
	return privateExponent;
    }


    //implementation des methode de java.security.Key
    public String getAlgorithm(){
	return "RSA";
    }

    public byte[] getEncoded(){
	return null;
    }

    public String getFormat(){
	return null;
    }
  
    /**
     * enregistre cette clé dans un fichier
     * @param filename le nom du fichier dans lequel la clé est sauvée
     */
    public void store(String filename) throws IOException{
	//    URL keyringURL = getClass().getClassLoader().getResource("keyring");
	//File keyFile = new File(keyringURL.getFile(),filename);
	FileOutputStream fos = new FileOutputStream(filename);
	ObjectOutputStream oos = new ObjectOutputStream(fos);
	oos.writeObject(this);
	oos.close();
    }

    /**
     * lit la clé dupuis un fichier
     * @param filename le nom du fichier dans lequel se trouve la clé
     */
    public void load(String filename) throws IOException,ClassNotFoundException{
	//URL keyringURL = getClass().getClassLoader().getResource("keyring");
	//File keyFile = new File(keyringURL.getFile(),filename);
	FileInputStream fis = new FileInputStream(filename);
	ObjectInputStream ois = new ObjectInputStream(fis);
	RSAKeyPrivate key =(RSAKeyPrivate) ois.readObject();
	ois.close();
	modulus = key.getModulus();
	privateExponent = key.getPrivateExponent();
    }
}
