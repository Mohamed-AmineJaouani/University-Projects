import java.util.ArrayList;
import java.util.List;
public class SummaryRanges {
    
    public static List<String> summaryRanges(int[] nums){
	List<String> list = new ArrayList<String>();
	if(nums.length == 0)
	    return list;
	if(nums.length == 1){
	    list.add(nums[0]+"");
	    return list;
	}

	int left = nums[0];
	for(int i = 0 ; i < nums.length-1 ; i++){
	    if(nums[i] != nums[i+1]-1){
		if(left == nums[i])
		    list.add(left+"");
		else
		    list.add(left+"->"+nums[i]);
		left = nums[i+1];
	    }
	    if(i == nums.length-2 && nums[i] == nums[i+1]-1)
		list.add(left+"->"+nums[i+1]);
	    else if( i == nums.length-2 && nums[i] != nums[i+1]-1)
		list.add(nums[i+1]+"");
	}
	return list;
    }
    
    public static void main(String[] args){
	int[] nums = {5,8};
	List<String> list = summaryRanges(nums);

	for(int i = 0 ; i < list.size() ; i++)
	    System.out.print(list.get(i) + " | ");
    }
}
