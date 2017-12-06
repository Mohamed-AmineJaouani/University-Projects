public class FindPeakElement {

    public static int findPeakElement(int[] nums){
	if(nums.length == 1)
	    return 0;
	else if(nums.length > 1){
	    if(nums[1] < nums[0])
		return 0;
	    else if(nums[nums.length-2] < nums[nums.length-1])
		return nums.length-1;
	}
	for(int i = 1 ; i < nums.length-1 ; i++){
	    if(nums[i-1] < nums[i] && nums[i+1] < nums[i])
		return i;
	}
	
	return 0;
    }
    
    public static void main(String[] args){
	int[] nums = {1,2};
	System.out.println(findPeakElement(nums));
    }
}
