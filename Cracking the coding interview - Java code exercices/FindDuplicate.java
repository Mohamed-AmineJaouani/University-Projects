public class FindDuplicate {

    public static int findDuplicate(int[] nums){
	int slow = nums[0], fast=nums[nums[0]];

	while(slow != fast){
	    slow = nums[slow];
	    fast = nums[nums[fast]];
	}

	fast = 0;
	while(slow != fast){
	    slow = nums[slow];
	    fast = nums[fast];
	}			    
	return slow;
    }

    /* public static int findDuplicate(int[] nums){
	for(int i = 0 ; i < nums.length-1 ; i++)
	    for(int j = i+1 ; j < nums.length; j++)
		if(nums[i] == nums[j])
		    return nums[i];
	return -1;
	}*/
    
    public static void main(String[] args){
	int[] t = {2,5,9,6,9,3,8,9,7,1};
	System.out.println(findDuplicate(t));
    }
}
// 2 9
// 9 5
// 1 6
// 5 7
// 3 1
// 6 3
// 8 8

// 8 0
// 7 2
// 9 9
