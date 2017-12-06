public class TestChiffrementABloc {

    public static void main(String[] args) {
	ChiffrementABloc cab = new ChiffrementABloc();
	String sk1, sk2, sk3, sk4;
	String m = new KeyGenerator16Bits().key;
	KeyGenerator16Bits k = new KeyGenerator16Bits();
	//	printTest(k);
	//	printTestEncDec(k.key, m, cab);
	//	printTestBourrage(m, cab);
	//	printTestDebourrage(m, cab);
	//	printTestAdditionModulo2pow16(cab);
	printTestCTR(k.key, "101100111000111100001111100000111111000000", cab);
    }
    public static void printTestCTR(String k, String m, ChiffrementABloc cab) {
	//TESTER LES FONCTIONS DE CHIFFRAGE ET DE DECHIFFRAGE EN CTR
	printBlocs();
	System.out.println("-----m = " + m);
	String c = cab.Fk_chiffrementCTR(k, m);
	System.out.println("-----c = " + c);
	String d = cab.Fk_dechiffrementCTR(k, c);
	System.out.println("-----d = " + d);
    }
    public static void printTestAdditionModulo2pow16(ChiffrementABloc cab) {
	int a = (int)(Math.pow(2, 16) -1);
	String ba = Integer.toBinaryString(a);
	System.out.println(ba);
	System.out.println(cab.additionModulo2pow16(ba, 1));
    }
    public static void printBlocs() {
	String m = "1111111100000000";
	System.out.println("1 bloc : " + m);
	System.out.println("2 blocs: " + m+m);
	System.out.println("3 blocs: " + m+m+m);
	System.out.println("4 blocs: " + m+m+m+m);
    }
    public static void printTestDebourrage(String m, ChiffrementABloc cab) {
	printBlocs();
	String m_short1 = "10101000";
	String m_short2 = "1";
	String m_short3 = "1111111000101101101110111101";
	String m_short4 = "1111111000101101101110111101104";
	String m_good = "1010101010101010";
	String m_good1 = "10101010101010101010101010101010";
	System.out.println("m_short1 " + m_short1);
	m_short1 = cab.bourrageBloc16Bits(m_short1);
	System.out.println("bourrage " + m_short1);
	System.out.println("debourra " + cab.debourrageBloc16Bits(m_short1));
	
	System.out.println("m_short2 " + m_short2);
	m_short2 = cab.bourrageBloc16Bits(m_short2);
	System.out.println("bourrage " + m_short2);
	System.out.println("debourra " + cab.debourrageBloc16Bits(m_short2));

	System.out.println("m_short3 " + m_short3);
	m_short3 = cab.bourrageBloc16Bits(m_short3);
	System.out.println("bourrage " + m_short3);
	System.out.println("debourra " + cab.debourrageBloc16Bits(m_short3));

	System.out.println("m_short4 " + m_short4);
	m_short4 = cab.bourrageBloc16Bits(m_short4);
	System.out.println("bourrage " + m_short4);
	System.out.println("debourra " + cab.debourrageBloc16Bits(m_short4));

	System.out.println("m_good : " + m_good);
	m_good = cab.bourrageBloc16Bits(m_good);
	System.out.println("bourrage " + m_good);
	System.out.println("debourra " + cab.debourrageBloc16Bits(m_good));

	System.out.println("m_good1: " + m_good1);
	m_good1 = cab.bourrageBloc16Bits(m_good1);
	System.out.println("bourrage " + m_good1);
	System.out.println("debourra " + cab.debourrageBloc16Bits(m_good1));
    }
    public static void printTestBourrage(String m, ChiffrementABloc cab) {
	System.out.println("1 bloc : " + m);
	System.out.println("2 blocs: " + m+m);
	System.out.println("3 blocs: " + m+m+m);
	System.out.println("4 blocs: " + m+m+m+m);
	String m_short1 = "10101000";
	String m_short2 = "1";
	String m_short3 = "1111111000101101101110111101";
	String m_short4 = "1111111000101101101110111101104";
	String m_good = "1010101010101010";
	String m_good1 = "10101010101010101010101010101010";
	System.out.println("m_short1 " + m_short1);
	System.out.println("bourrage " + cab.bourrageBloc16Bits(m_short1));

	System.out.println("m_short2 " + m_short2);
	System.out.println("bourrage " + cab.bourrageBloc16Bits(m_short2));

	System.out.println("m_short3 " + m_short3);
	System.out.println("bourrage " + cab.bourrageBloc16Bits(m_short3));
	System.out.println("m_short4 " + m_short4);
	System.out.println("bourrage " + cab.bourrageBloc16Bits(m_short4));

	System.out.println("m_good : " + m_good);
	System.out.println("bourrage " + cab.bourrageBloc16Bits(m_good));

	System.out.println("m_good1: " + m_good1);
	System.out.println("bourrage " + cab.bourrageBloc16Bits(m_good1));
    }	
    public static void printTestEncDec(String k, String m, ChiffrementABloc cab) {
	System.out.println("-----k = " + k);
	System.out.println("-----m = " + m);
	String c = cab.Fk_chiffrementABloc(k, m);
	System.out.println("E_k(m) = " + c);
	System.out.println("D_k(c) = " + cab.Fk_dechiffrementABloc(k, c));
    }
    public static void printTestIdentite(String m, KeyGenerator16Bits k, ChiffrementABloc cab) {
	System.out.println("------m = " + m + "\nP(P(m)) = " + cab.permutation(cab.permutation(m)));
	System.out.println("F/T ------x1 = " + k.x1 + "\nF/T S(S(x1)) = " + cab.substitution(cab.substitution(k.x1, false), true));
	System.out.println("T/F ------x1 = " + k.x1 + "\nT/F S(S(x1)) = " + cab.substitution(cab.substitution(k.x1, true), false));
    }
    public static void printTest(KeyGenerator16Bits k, ChiffrementABloc cab) {
	System.out.println("k = " + k.key + " : " + k.key.length());
	System.out.print("k = "+k.x1+" "+k.x2+" "+k.x3+" "+k.x4);

	System.out.println();
	System.out.println("substitution x1 : (x1, S(x1)) = ("+k.x1+", "+cab.substitution(k.x1, false)+")");
	System.out.println("substitution x2 : (x2, S(x2)) = ("+k.x2+", "+cab.substitution(k.x2, false)+")");
	System.out.println("substitution x3 : (x3, S(x3)) = ("+k.x3+", "+cab.substitution(k.x3, false)+")");
	System.out.println("substitution x4 : (x4, S(x4)) = ("+k.x4+", "+cab.substitution(k.x4, false)+")");

	System.out.println("substitution k  : \n\t\t---k = "+k.key
			   +"\n\t\tS(k) = "+ cab.substitution(k.x1, false)
			   + cab.substitution(k.x2, false)
			   + cab.substitution(k.x3, false)
			   + cab.substitution(k.x4, false));
	
	System.out.println();
	System.out.println("------------key = " + k.key);
    	System.out.println("permutation key = " + cab.permutation(k.key));

	System.out.println();
	System.out.println("------------key = " + k.key);
	System.out.println("------switchKey = " + cab.switchKey(k.key, false));
	System.out.println("------switchKey = " + cab.switchKey(cab.switchKey(k.key,false), false));
	System.out.println("------switchKey = " + cab.switchKey(cab.switchKey(cab.switchKey(k.key, false),false),false));
	System.out.println("------switchKey = " + cab.switchKey(cab.switchKey(cab.switchKey(cab.switchKey(k.key, false),false),false),false));

	System.out.println();
	System.out.println("------------key = " + k.key);
	System.out.println("------switchKey = " + cab.switchKey(k.key, true));
	System.out.println("------switchKey = " + cab.switchKey(cab.switchKey(k.key,true), true));
	System.out.println("------switchKey = " + cab.switchKey(cab.switchKey(cab.switchKey(k.key, true),true),true));
	System.out.println("------switchKey = " + cab.switchKey(cab.switchKey(cab.switchKey(cab.switchKey(k.key, true),true),true),true));

	System.out.println();
	System.out.println("------------key = " + k.key);
	System.out.println("switchKey tr/fa = " + cab.switchKey(cab.switchKey(k.key, true), false));

	System.out.println();
	System.out.println("------------key = " + k.key);
	System.out.println("switchKey fa/tr = " + cab.switchKey(cab.switchKey(k.key, false), true));
	
	/******************/
	/*
	System.out.println("k = " + k.key + " : " + k.key.length());
	System.out.println("x1 = " + k.x1 + " : " + k.x1.length());
	System.out.println("x2 = " + k.x2 + " : " + k.x2.length());
	System.out.println("x3 = " + k.x3 + " : " + k.x3.length());
	System.out.println("x4 = " + k.x4 + " : " + k.x4.length());

	System.out.println("substitution x1 = " + substitution(k.x1, false));
	System.out.println("substitution x2 = " + substitution(k.x2, false));
	System.out.println("substitution x3 = " + substitution(k.x3, false));
	System.out.println("substitution x4 = " + substitution(k.x4, false));

	System.out.println("------------key = " + k.key);
    	System.out.println("permutation key = " + permutation(k.key));

	System.out.println("------------key = " + k.key);
	System.out.println("switchKey = " + switchKey(k.key, false));
	System.out.println("switchKey = " + switchKey(switchKey(k.key,false), false));
	System.out.println("switchKey = " + switchKey(switchKey(switchKey(k.key, false),false),false));
	System.out.println("switchKey = " + switchKey(switchKey(switchKey(switchKey(k.key, false),false),false),false));
	*/
    }
    
}
