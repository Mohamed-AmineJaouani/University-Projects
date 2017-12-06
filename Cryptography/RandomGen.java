/**
 * Génératuer de nombres aléatoires 
 * @author "Denis Berthod"
 */



//importation des classes utilisées qui ne se trouve pas dans le package java.lang
//
import java.math.BigInteger;

import java.util.Random;

import java.io.IOException;

public class RandomGen{


  private  int nbBit;//nombre de bits du nombre a obtenir

  private boolean probablyPrime;//est ce que le nombre doit être probablement premier

  /**
   *Constructeur par défaut retournant un générateur de nombre de 64 bit non forcement premiers
   */
  public RandomGen(){
    nbBit=64;
    probablyPrime=false;
  }

  /**
   *Contruit un nouveau générateur de nombre premiers
   *@param nbBit le nombre de bits des nombres produits par le générateur
   *@param probablyPrime si true alors les nombres générer auront une probabilité d'être premier de 2^(-100)
   */
  public RandomGen(int nbBit,boolean probablyPrime){
    this.nbBit=nbBit;
    this.probablyPrime=probablyPrime;
  }

  /**
   *determine le nombre de bits des nombres générer
   *@param nbBit le nombre de bit
   */
  public void setNbBit(int nbBit){
    this.nbBit = nbBit;
  }
  
  /**
   * determine si les nombres générers seront probablement premiers ou non
   * @param value si true alors les nombres seront probablement premier
   */
  public void setProbablyPrime(boolean value){
    probablyPrime = value;
  }

  /**
   * retourne une valeur aléatoire correspondant aux critères fixés
   * @return une valeur aléatoire correspondant eux critère fixés
   */
  public BigInteger rand(){
    BigInteger result;
    if(probablyPrime){
      result = new BigInteger(nbBit,100,new Random());
    }else{
      result = new BigInteger(nbBit,new Random());
    }
    return result;
  }

  private void usage(){
    System.out.println("Usage : java RandomGen [<nbBit> [prime]]");
  }

  /*classe principale pour les tests*/
  public static void main(String[] args){
    RandomGen gen = new RandomGen();
    if(args.length >= 1){//s'il y a au moins un argument
      gen.setNbBit(Integer.parseInt(args[0]));//initialiser le nombre de bits avec le premier argument
    }
    if(args.length >=2){//au moiins 2 arguments
      if(args[1].equals("prime")){ //si le deuxième argument est le mot "prime"
	gen.setProbablyPrime(true);
      }
    }
    while(true){//tant que le ctrl-c n'est pas presser 
      System.out.println(gen.rand());//affiche le nombre
      try{
	System.in.read();//attente qu'un caractere soit en ter au clavier
      }catch(IOException e){//rattrape une eventuelle erreur d'entrée sortie et affiche le message correspondant
	System.err.println("une erreur d'entrée/sortie s'est produite" + e.getMessage());
      }
    }
  }
}
