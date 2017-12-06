public class MissingNumber {
    
    public static int missingNumber(int[] nums) {
		int sum = (nums.length * (nums.length+1)) /2;
		
		System.out.println("s : "+sum);
		
		for(int i = 0 ; i < nums.length ; i++) {
			sum -= nums[i];
		}
		
		return sum;
    }
    
    public static void main(String[] args) {
		int[] t = {0,4,2,1};
		System.out.println(missingNumber(t));
    }
}
