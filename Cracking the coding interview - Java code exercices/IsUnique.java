import java.util.HashSet;
public class IsUnique {

    public static boolean isUnique1(String s){
	HashSet<Character> set = new HashSet<Character>();
	for(int i = 0 ; i < s.length(); i++)
	    if(!set.add(s.charAt(i)))
		return false;
	return true;
    }

    public static boolean isUnique2(String s){
	int checker = 0;
	int val;
	for(int i = 0 ; i < s.length() ; i++){
	    val = s.charAt(i) - 'a';
	    if((checker & (1 << val)) > 0) return false;
	    checker |= 1 << val;
	}
	return true;
    }
    
    public static void main(String[] args){
	String s = "abonjaur";
	System.out.println(isUnique2(s));
    }
}
