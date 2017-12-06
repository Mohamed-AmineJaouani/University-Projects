public class URLify {

    public static String urlify(String s, int length){
	StringBuilder content = new StringBuilder();
	for(int i = 0 ; i < length ; i++){
	    if(s.charAt(i) == ' ')
		content.append("%20");
	    else
		content.append(s.charAt(i));
	}
	return content.toString();
    }
    
    public static void main(String[] args){
	String s = "Mr John Smith    ";
	System.out.println(urlify(s,13));
    }
}
