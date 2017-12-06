class ReverseVowel {
    
    public static boolean isVowel(char c){
        return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
    }
    
    public static String arrayToString(char[] t){
        StringBuilder b = new StringBuilder();
        for(int i = 0 ; i < t.length ; i++)
            b.append(t[i]);
        return b.toString();
    }

    public static char[] StringToCharArray(String s){
	char[] t = new char[s.length()];
	for(int i = 0 ; i < s.length() ; i++)
	    t[i] = s.charAt(i);
	return t;
    }
    
    public static String reverseVowels(String s) {
        if(s.length() == 0) return s;        
        
        char[] t = StringToCharArray(s);

        int first = 0, last = s.length()-1;
        while(first < last){
            if(isVowel(Character.toLowerCase(t[first]))){
                if(isVowel(Character.toLowerCase(t[last]))){
                    char swap = t[last];
                    t[last] = t[first];                           
                    t[first] = swap;
		    first++;
		    last--;
		    continue;
                }
                else
                    last--;
            }
            else 
                first++;
        }
        return arrayToString(t);
    }

    public static void main(String[] args){
	if(args.length == 1)
	    System.out.println(reverseVowels(args[0]));
	else
	    System.out.println("Entrez un mot");
    }
}
