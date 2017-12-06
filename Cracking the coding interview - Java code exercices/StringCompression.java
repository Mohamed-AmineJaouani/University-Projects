public class StringCompression{

    public static String stringCompression(String s){
	StringBuilder content = new StringBuilder();
	int c = 1;
	for(int i = 0 ; i < s.length() ; i++){
	    if(i == s.length()-1 || s.charAt(i) != s.charAt(i+1)){
		content.append(s.charAt(i));
		content.append(c);
		c = 1;
	    }
	    else
		c++;
	}
	return ((content.toString().length() < s.length())? content.toString() : s);
    }
    
    public static void main(String[] args){
	String s = "aabcccccaaa";
	System.out.println(stringCompression(s));
    }
}
