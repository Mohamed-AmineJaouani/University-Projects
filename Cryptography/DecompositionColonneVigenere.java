public class DecompositionColonneVigenere {

	public static void main(String[] args) {
		String texte = "XAUNM EESYI EDTLL FGSNB WQUFX PQTYO RUTYI INUMQ IEULS MFAFX GUTYB XXAGB HMIFI IMUMQ IDEKR IFRIR ZQUHI ENOOO IGRML YETYO VQRYS IXEOK IYPYO IGRFB WPIYR BQURJ IYEMJ IGRYK XYACP PQSPB VESIR ZQRUF REDYJ IGRYK XBLOP JARNP UGEFB WMILX MZSMZ YXPNB PUMYZ MEEFB UGENL RDEPB JXONQ EZTMB WOEFI IPAHP PQBFL GDEMF WFAHQ";
		String[] tab = texte.split(" ");
		String L1 = "";
		String L2 = "";
		String L3 = "";
		String L4 = "";
		String L5 = "";

		for(int i=0 ; i<tab.length ; i++) {
			L1 = L1 + tab[i].charAt(0);
			L2 = L2 + tab[i].charAt(1);
			L3 = L3 + tab[i].charAt(2);
			L4 = L4 + tab[i].charAt(3);
			L5 = L5 + tab[i].charAt(4);
		}

		System.out.println("Texte=" + texte);
		System.out.println("L1=" + L1);
		System.out.println("L2=" + L2);
		System.out.println("L3=" + L3);
		System.out.println("L4=" + L4);
		System.out.println("L5=" + L5);

	}

}
