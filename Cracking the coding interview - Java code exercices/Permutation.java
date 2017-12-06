import java.util.Arrays;
public class Permutation {

    public static String sort(String s){
	char[] content = s.toCharArray();
	java.util.Arrays.sort(content);
	return new String(content);
    }
    

    public static boolean permutation1(String s, String t){
	if(s.length() != t.length())
	    return false;
	return sort(s).equals(sort(t));
    }

    public static boolean permutation2(String s, String t){
	if(s.length() != t.length())
	    return false;
	int[] tab = new int[26];

	for(int i = 0 ; i < s.length() ; i++)
	    tab[s.charAt(i) - 'a']++;
	
	for(int i = 0 ; i < t.length() ; i++)
	    if(--tab[t.charAt(i) -'a'] < 0)
		return false;
	return true;
    }
    
    public static void main(String[] args){
	String s = "hello", t = "ohellz";

	System.out.println(permutation2(s,t));
    }
}
