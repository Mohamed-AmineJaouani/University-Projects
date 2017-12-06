import java.util.*;

public class lengthOfLongestSubstring {
    
    public static int f(String s){
	int cpt = 0;
	for(int i = 0 ; i <= s.length() ; i++)
	    for(int j = i+1 ; j <= s.length() ; j++)
		if(allUnique(s.substring(i,j)))
		    cpt = Math.max(cpt, j-i);
	return cpt;
    }
    
    public static boolean allUnique(String s){
	HashSet<Character> chars = new HashSet<Character>();
	for(int i = 0; i < s.length() ; i++){
	    if(chars.contains(s.charAt(i)))
		return false;
	    chars.add(s.charAt(i));
	}
	return true;
    }
    
    public static void main(String[] args){
	String s = "bbbazebc";
	System.out.println(s.substring(0,8));
	System.out.println(f(s));
    }
}