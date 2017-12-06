public class ContainsDuplicate2 {

    public static boolean containsDuplicate2(int[] nums, int k){
	if(k==0 || nums.length == 1)
	    return false;
	if(nums.length == 2)
	    if(nums[0] == nums[1])
		return true;
	for(int i = 0 ; i < nums.length-1 ; i++)
	    for(int j = i+1 ; j < nums.length ; j++){
		if(nums[i] == nums[j] && j-i <= k)
		    return true;
		if(i+k <= j)
		    break;
	    }
	return false;
    }
    
    public static void main(String[] args){
	int[] nums = {0,1,2,3,4,5,0};
	System.out.println(containsDuplicate2(nums,6));
    }
}
