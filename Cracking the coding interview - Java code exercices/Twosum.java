public class Twosum {
    
    public static int[] ftwosum(int[] nums, int target){
	int res[] = new int[2];
	for(int i = 0 ; i < nums.length ; i++){
	    for(int j = i+1 ; j < nums.length ; j++){

		if(nums[i] + nums[j] == target){
		    res[0] = i;
		    res[1] = j;
		}
	    }
	}
	return res;
    }
    
    public static void main(String[] args){
	int[] nums = {15,11,7,2};
	int[] a = ftwosum(nums,9);
	for(int i = 0 ; i < a.length ; i++)
	    System.out.print(a[i]+ " ");
    }
}
