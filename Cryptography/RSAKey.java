import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.math.BigInteger;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;

import java.net.URL;

public class RSAKey implements RSAPrivateKey,RSAPublicKey{

  private BigInteger modulus;//le module n

  private BigInteger privateExponent;//exposent privé d

  private BigInteger publicExponent;//exposant public e

  private static final long serialVersionUID=1L;//numéro de série de la classe pour la sérialisation

  /**
   * construit une nouvelle paire de clé RSA
   * @param modulus le module n
   * @param privateExponent l'exposant privé d
   * @param publicExponent l'exposant public e
   * */
  public RSAKey(BigInteger modulus,BigInteger privateExponent,BigInteger publicExponent){
    this.modulus=modulus;
    this.privateExponent=privateExponent;
    this.publicExponent=publicExponent;
  }

  /**
   * construit une nouvelle paire de clé a partir d'un fichier de sauvegarde
   * @param filename le nom du fichier
   */
  public RSAKey(String filename) throws IOException,ClassNotFoundException{
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
   * retourne la clé privée
   * @return la clé privée
   */
  public RSAKeyPrivate getPrivateKey(){
    return new RSAKeyPrivate(modulus,privateExponent);
  }

  /**
   * retourne la clé public
   * @return la clé public
   */
  public RSAKeyPublic getPublicKey(){
    return new RSAKeyPublic(modulus,publicExponent);
  }

  /**
   * enregistre cette clé dans un fichier
   * @param filename le nom du fichier dans lequel la clé est sauvée
   */
  public void store(String filename) throws IOException{
    URL keyringURL = getClass().getClassLoader().getResource("keyring");
    File keyFile = new File(keyringURL.getFile(),filename);
    FileOutputStream fos = new FileOutputStream(keyFile);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(this);
    oos.close();
  }

  /**
   * lit la clé dupuis un fichier
   * @param filename le nom du fichier dans lequel se trouve la clé
   */
  public void load(String filename) throws IOException,ClassNotFoundException{
    URL keyringURL = getClass().getClassLoader().getResource("keyring");
    File keyFile = new File(keyringURL.getFile(),filename);
    FileInputStream fis = new FileInputStream(keyFile);
    ObjectInputStream ois = new ObjectInputStream(fis);
    RSAKey key =(RSAKey) ois.readObject();
    ois.close();
    modulus = key.getModulus();
    privateExponent = key.getPrivateExponent();
    publicExponent = key.getPublicExponent();
  }

  
  
}
