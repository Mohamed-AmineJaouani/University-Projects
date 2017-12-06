public class LongestPalindromicSubstring {
    
    public static boolean isPalindrome(String s) {
        int first = 0, last = s.length()-1;
	if(s.length() == 0)
	    return false;
        while(first < last) {
            if(s.charAt(first) != s.charAt(last)) {
                return false;
            }
	    first++;
	    last--;
        }
        return true;
    }

    public static void main(String[] args) {
	System.out.println(isPalindrome(""));
    }
}
