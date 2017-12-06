public class Bit{

    public static boolean getBit(int num, int i){
	return ((num >> i) & 1) == 1;
	//return ((num & (1 << i)) != 0;
    }

    public static int setBit(int num, int i){
	return num | (1 << i);
    }
    
    public static void main(String[] args){
	int n = 6;
	n = setBit(n,0);
	System.out.println(n);
    }
}
