import java.util.ArrayList;

public class BreakSpace{
    
    public static String breakSpaceTwo(String[] dico, String sentence){
	for(int i = 1 ; i < sentence.length() ; i++){
	    String left = sentence.substring(0, i);
	    String right = sentence.substring(i, sentence.length());
	    if(contains(dico, left) && contains(dico, right))
		return left + " " + right;
	}
	return sentence;
    }


    public static String breakSpace(ArrayList<String> dicoList, String sentence){
	String result="";
	for(int i = 0 ; i < sentence.length() ; i++)
	    for(int j = i ; j < sentence.length()+1 ; j++){
		String left = sentence.substring(i, j);
		System.out.println(left);
		if(dicoList.contains(left) || sameWord(dicoList, left)){
		    result += left + " ";
		    i = j;
		}
	    }
	return result;
    }
    
    
    public static boolean contains(String[] dico, String s){
	for(int i = 0 ; i < dico.length ; i++)
	    if(dico[i].equals(s))
		return true;
	return false;
    }

    public static boolean sameWord(ArrayList<String> dico, String word){
	int counter = 0;
	for(String s : dico){
	    for(int i = 0 ; i < word.length() ; i++){
		if(i >= s.length())
		    break;
		if(s.charAt(i) == word.charAt(i))
		    counter++;
	    }
	}
	System.out.println(counter);
	if(counter == word.length() - 1)
	    return true;
	return false;
    }
    
    public static void main(String[] args){
	String[] dico = {"peanut", "butter", "pea"};
    	ArrayList<String> dicoList = new ArrayList<String>();
	dicoList.add("peanut");
	dicoList.add("butter");
	dicoList.add("i");
	dicoList.add("am");
	dicoList.add("doing");
	dicoList.add("that");
	System.out.println(breakSpace(dicoList, "iamdoingbuter"));
    }
}
