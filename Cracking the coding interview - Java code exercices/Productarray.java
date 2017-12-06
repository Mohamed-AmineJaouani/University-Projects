public class Productarray {

    public static int[] product(int[] t){
	int[] r = new int[t.length];
	int left =1, right = 1;
	for(int i = 0 ; i < r.length ; i++)
	    r[i] = 1;
	
	for(int i = 0 ; i < t.length-1 ; i++){
	    left *= t[i];
	    r[i+1] *= left;
	}
	for(int i = t.length-1 ; i > 0 ; i--){
	    right *= t[i];
	    r[i-1] *= right;
	}
	
	return r;
    }
    
    public static void main(String[] args){
	int[] t = product(new int[]{1,2,3,4});
	for(int i = 0 ; i < t.length ; i++){
	    System.out.print(t[i]+"|");
	}
	
    }
}