import java.util.HashSet;
public class LengthOfLongestSubstringWithoutRepeating {
    
    public static int lengthOfLongestSubstring(String s) {
        if(s.length() == 0) 
            return 0;
        int max = 0;
        HashSet<Character> letterSet = new HashSet<Character>();
        
        for(int i = 0 ; i < s.length() ; i++) {
	    letterSet.add(s.charAt(i));
	    for(int j = i+1 ; j < s.length() ; j++) {
		if(!letterSet.contains(s.charAt(j)))
		    letterSet.add(s.charAt(j));
		else {
		    break;
		}
	    }
	    if(letterSet.size() > max) 
		max = letterSet.size();
	    letterSet.clear();
	}
        return max;
    }

    public static void main(String[] args){
	System.out.println(lengthOfLongestSubstring("dqirnnnchguccmkluloyzunslxhgxjyazitnxgreplhrzreuessdofxacgicpgcpqyengvrvjamitscxk"));
    }
}
