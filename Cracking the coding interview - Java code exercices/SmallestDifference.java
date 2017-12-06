import java.util.Arrays;
public class SmallestDifference {

    public static int smallest1(int[] array1, int[] array2){
	if(array1.length == 0 || array2.length == 0)
	    return -1;
	int min = Integer.MAX_VALUE;
	for(int i = 0 ; i < array1.length ; i++)
	    for(int j = 0 ; j < array2.length ; j++)
		if(Math.abs(array1[i] - array2[j]) < min)
		    min = Math.abs(array1[i] - array2[j]);
	return min;
    }
    
    public static int smallest2(int[] array1, int[] array2){
	Arrays.sort(array1);
	Arrays.sort(array2);
	int a = 0, b = 0;
	int min = Integer.MAX_VALUE;

	while(a < array1.length && b < array2.length){
	    if(Math.abs(array1[a] - array2[b]) < min)
		min = Math.abs(array1[a] - array2[b]);
	    if(array1[a] < array2[b])
		a++;
	    else
		b++;
	}
	return min;
    }
    
    public static void main(String[] args){
	int[] a1 = new int[]{1,3,5,11,12};
	int[] a2 = new int[]{23,127,235,19,8};

	System.out.println(smallest2(a1,a2));
    }
}
