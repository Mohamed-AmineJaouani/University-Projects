import java.util.Random;

public class KeyGenerator16Bits {

    public String key;
    public String x1;
    public String x2;
    public String x3;
    public String x4;
    
    public KeyGenerator16Bits() {
	Random random = new Random();
	key = Integer.toBinaryString(random.nextInt((int)Math.pow(2, 16)));
	while(key.length() < 16) {
	    key = "0" + key;
	}
	x1 = key.substring(0, 4);
	x2 = key.substring(4, 8);
	x3 = key.substring(8, 12);
	x4 = key.substring(12);
    }

    public static void main(String[] args) {
	KeyGenerator16Bits k = new KeyGenerator16Bits();
	System.out.println("k = " + k.key + " : " + k.key.length());
	System.out.println("x1 = " + k.x1 + " : " + k.x1.length());
	System.out.println("x2 = " + k.x2 + " : " + k.x2.length());
	System.out.println("x3 = " + k.x3 + " : " + k.x3.length());
	System.out.println("x4 = " + k.x4 + " : " + k.x4.length());
	
    }
}
