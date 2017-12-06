import java.security.interfaces.RSAPublicKey;
import java.math.BigInteger;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;

import java.net.URL;

public class RSAKeyPublic implements RSAPublicKey{

    private BigInteger modulus;//le module n

    private BigInteger publicExponent;//exposant public e

    private static final long serialVersionUID=1L;//numéro de série de la classe pour la sérialisation

    /**
     * construit une nouvelle paire de clé RSA
     * @param modulus le module n
     * @param publicExponent l'exposant public e
     * */
    public RSAKeyPublic(BigInteger modulus,BigInteger publicExponent){
	this.modulus=modulus;
	this.publicExponent=publicExponent;
    }

    /**
     * construit une nouvelle clé public a partir d'un fichier de sauvegarde
     * @param filename le nom du fichier
     */
    public RSAKeyPublic(String filename) throws IOException,ClassNotFoundException{
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
     * retourne l'exposant public
     * @return l'exposant public
     * */
    public BigInteger getPublicExponent(){
	return publicExponent;
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
	FileInputStream fis = new FileInputStream(filename);
	ObjectInputStream ois = new ObjectInputStream(fis);
	RSAKeyPublic key =(RSAKeyPublic) ois.readObject();
	ois.close();
	modulus = key.getModulus();
	publicExponent = key.getPublicExponent();
    }
}
