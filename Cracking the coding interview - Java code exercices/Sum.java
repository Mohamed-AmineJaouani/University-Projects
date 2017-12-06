public class Sum {

    public static int summ(int a, int b){
	for(int i = 0 ; i < Math.abs(b) ; i++){
	    if(b>0){
		System.out.println("+");
		a++;
	    }
	    else if(b<0){
		System.out.println("-");
		a--;
	    }
	    
	    
	}
	return a;
    }

    public static void main(String[] args){
	System.out.println(summ(2147483647, -2147483648));
    }
}





























    public static int soluce(int a, int b){
	boolean retenue = false; // y a t il une retenue pour la prochaine etape
	int result = 0;
	int bit = 1; // bit actuel a regarder
	int abit, bbit;
	while (bit != 0) {
	    abit = a & bit;
	    bbit = b & bit;
	    if (retenue)
		result |= abit ^ bbit ^ bit;
	    else
		result |= abit ^ bbit;

	    retenue = ((abit != bbit) && retenue) || ((abit != 0 && bbit != 0));
	    bit <<= 1;
	}
	return result;
    }
}
