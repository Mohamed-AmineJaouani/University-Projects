import java.util.HashSet;
public class SumSwap {

    public static int sum(int[] tab) {
	int sum = 0;
	for(int i : tab)
	    sum += i;
	return sum;
    }
    
    public static HashSet<Integer[]> sumSwap(int[] A, int[] B) {
	int sommeA = sum(A);
	int sommeB = sum(B);
	
	HashSet<Integer[]> sumList = new HashSet<Integer[]>();
	
	for(int i = 0 ; i < A.length ; i++) {
	    for(int j = 0 ; j < B.length ; j++) {
		int diff = A[i] - B[j];
		if(sommeA - diff == sommeB + diff) {
		    sumList.add(new Integer[]{A[i], B[j]});
		    //return new int[]{A[i], B[j]};
		}
	    }
	}
	return sumList;
    }
    
    public static void main(String[] args){
	int[] A = new int[]{4,1,2,1,1,2};
	int[] B = new int[]{3,6,3,3};
	HashSet<Integer[]> list = sumSwap(A,B);
	for(Integer[] t : list)
	    System.out.println(t[0]+" "+t[1]);
    }
}
