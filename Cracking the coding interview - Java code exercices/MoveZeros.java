public class MoveZeros{

    public static void moveZeros(int[] nums){
	for(int i = 0 ; i < nums.length ; i++){
	    if(nums[i] == 0){
		for(int j = i+1 ; j < nums.length ; j++)
		    if(nums[j] != 0){
			echanger(nums,i,j);
			break;
		    }
	    }
	}
    }

    public static void echanger(int[] nums, int i, int j){
	int tmp = nums[j];
	nums[j] = nums[i];
	nums[i] = tmp;
    }

    public static void main(String[] args){
	int[] t = {0,1,0,3,0,0,4,0,8,12};
	//echanger(t,0,2);
	moveZeros(t);
	for(int i = 0 ; i < t.length ; i++)
	System.out.print(t[i] +" | ");
    }
}
