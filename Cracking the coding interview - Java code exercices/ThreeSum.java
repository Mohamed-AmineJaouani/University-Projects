import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
public class ThreeSum {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> reponses = new ArrayList<List<Integer>>();
        for(int i = 0 ; i < nums.length-2 ; i++)
            for(int j = i+1 ; j < nums.length-1 ; j++)
                for(int k = j+1 ; k < nums.length ; k++){
                    if(nums[i]+nums[j]+nums[k] == 0 && !contains(reponses, Arrays.asList(nums[i], nums[j], nums[k]))){
			reponses.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                }
        return reponses;
    }

    public static boolean contains(List<List<Integer>> list, List<Integer> l){
	for(List<Integer> elt : list)
	    if(equals(elt,l))
		return true;
	return false;
    }

    public static boolean equals(List<Integer> l1, List<Integer> l2){
	Collections.sort(l1);
	Collections.sort(l2);
	return l1.equals(l2);
    }
    
    public static void main(String[] args){
	System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
	List<Integer> l1 = new ArrayList<Integer>();
	List<Integer> l2 = new ArrayList<Integer>();

	l1.add(-1);
	l1.add(0);
	l1.add(1);

	l2.add(0);
	l2.add(1);
	l2.add(-1);

	System.out.println(equals(l1,l2));
	
    }
}
