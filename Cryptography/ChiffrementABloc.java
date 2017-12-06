public class ChiffrementABloc {

    private static final int[] TAB_SUBSTITUTION = {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7}; //E, 4, D, 1, 2, F, B, 8, 3, A, 6, C, 5, 9, 0, 7
    private static final int[] TAB_SUBSTITUTION_INVERSE = {14, 3, 4, 8, 1, 12, 10, 15, 7, 13, 9, 6, 11, 2, 0, 5}; //E, 3, 4, 8, 1, C, A, F, 7, D, 9, 6, B, 2, 0, 5
    private static final int[] TAB_PERMUTATION = {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16};


    public ChiffrementABloc() { }



      /*****************************************************/
     /******* DEBUT : PARTIE (DE)CHIFFREMENT A BLOC *******/
    /*****************************************************/    
    
    public String Fk_chiffrementABloc(String k, String p16bits) {
	String ret = "";
	for(int i=1 ; i<=4 ; i++) {
	    if(i != 1)
		k = switchKey(k, false);
	    ret = XOR(p16bits, k);
	    ret = substitution(ret.substring(0, 4), false)
		+ substitution(ret.substring(4, 8), false)
		+ substitution(ret.substring(8, 12), false)
		+ substitution(ret.substring(12), false);
	    ret = permutation(ret);
	    p16bits = ret;
	}
	return ret;
    }
    public String Fk_dechiffrementABloc(String k, String c16bits) {
	String ret = "";
	for(int i=4 ; i>=1 ; i--) {
	    c16bits = permutation(c16bits);
	    c16bits = substitution(c16bits.substring(0, 4), true)
		+ substitution(c16bits.substring(4, 8), true)
		+ substitution(c16bits.substring(8, 12), true)
		+ substitution(c16bits.substring(12), true);
	    k = switchKey(k, true);
	    ret = XOR(c16bits, k);
	    c16bits = ret;
	}
	return ret;
    }

    
    /*******************************************************************/
    /*** DEBUT : PARTIE FONCTIONS AUXILIAIRES (DE)CHIFFREMENT A BLOC ***/
    //fonction substitution qui prend 4 bits et renvoie 4 bits
    public String substitution(String message4bits, boolean decrypt) {
	int entree = Integer.parseInt(message4bits, 2);
	int sortie;
	if( ! decrypt ) //encrypt
	    sortie = TAB_SUBSTITUTION[entree];
	else //decrypt
	    sortie = TAB_SUBSTITUTION_INVERSE[entree];
	String ret = Integer.toBinaryString(sortie);
	while(ret.length() < 4)
	    ret = '0' + ret;
	return ret;
    }
    //fonction permutation qui prend 16 bits et renvoie 16 bits
    public String permutation(String message16bits) {
	String sortie = "";
	for(int i=0 ; i<16 ; i++)
	    sortie += message16bits.charAt(TAB_PERMUTATION[i] -1);
	return sortie;
    }
    //Fonction permettant d'effectuer des decalages circulaires sur la clef
    public String switchKey(String clef16bits, boolean decrypt) {
	if( !decrypt ) //encrypt
	    return clef16bits.substring(12) + clef16bits.substring(0, 12);
	else //decrypt
	    return clef16bits.substring(4) + clef16bits.substring(0, 4);
    }
    public String XOR(String a, String b) {
	while(a.length() < b.length())
	    a = '0' + a;
	while(b.length() < a.length())
	    b = '0' + a;
	String ret = "";
	for(int i=0 ; i<a.length() ; i++)
	    ret += (char)a.charAt(i) ^ b.charAt(i);
	return ret;
    }

    /*** FIN : PARTIE FONCTIONS AUXILIAIRES (DE)CHIFFREMENT A BLOC ***/
    /*****************************************************************/

      /***************************************************/
     /******* FIN : PARTIE (DE)CHIFFREMENT A BLOC *******/
    /***************************************************/



      /******************************************************************/
     /******* DEBUT : PARTIE (DE)CHIFFREMENT MODE COMPTEUR (CTR) *******/
    /******************************************************************/    

    public String Fk_chiffrementCTR(String k, String m) {
	System.out.println("--1: m = " + m);
	m = bourrageBloc16Bits(m);
	System.out.println("--2: m = " + m);
	String r = new KeyGenerator16Bits().key;
	String c = r;
	System.out.println("--3: c = " + c);
	System.out.println("--4: l = " + (m.length()/16));	
	for(int i=1 ; i<=(m.length()/16) ; i++) {
	    c += XOR(
		     Fk_chiffrementABloc(k, additionModulo2pow16(r, i)),
		     m.substring(16*(i-1), 16*i)
		     );
	    System.out.println("--"+(i+4) +": c = " + c);
	}
	return c;
    }
    public String Fk_dechiffrementCTR(String k, String c) {
	String r = c.substring(0, 16);
	String ci = c.substring(16);
	System.out.println("-1: ci = " + ci);
	String p = "";
	System.out.println("--2: l = " + (ci.length()/16));	
	for(int i=1 ; i<=(ci.length()/16) ; i++) {
	    p += XOR(
		     ci.substring(16*(i-1), 16*i),
		     Fk_chiffrementABloc(k, additionModulo2pow16(r, i))
		     );
	    System.out.println("--"+(i+2)+": p = " + p);
	}
	return debourrageBloc16Bits(p);
    }


    
    /*****************************************************/
    /*** DEBUT : PARTIE FONCTIONS AUXILIAIRES POUR CTR ***/
    public String bourrageBloc16Bits(String m) {
	int nbBourrage = (m.length() % 16 == 0)? 16 : 16 - (m.length()%16);
	for(int i=0 ; i<nbBourrage ; i++)
	    m += (i == 0)? '1' : '0';
	return m;
    }
    public String debourrageBloc16Bits(String m) {
	int i;
	for(i=m.length() -1 ; i>=0 ; i--)
	    if(m.charAt(i) == '1') break;
	return m.substring(0, i);
    }
    public String additionModulo2pow16(String n, int i) {
	int in = Integer.parseInt(n, 2);
	int somme = (int)((in + i) % Math.pow(2, 16));
	String ret = Integer.toBinaryString(somme);
	while(ret.length() < 16)
	    ret = '0' + ret;
	return ret;
    }
    /*** FIN : PARTIE FONCTIONS AUXILIAIRES POUR CTR ***/
    /***************************************************/


      /****************************************************************/
     /******* FIN : PARTIE (DE)CHIFFREMENT MODE COMPTEUR (CTR) *******/
    /****************************************************************/    
    
}
