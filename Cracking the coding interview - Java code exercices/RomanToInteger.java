public class RomanToInteger {
    public static int romanToInt(String s) {
        int res = 0;
        for(int i = 0 ; i < s.length() ; i++){
            switch(s.charAt(i)){
                case 'I': res++;
                    break;
                case 'V': if(i != 0 && s.charAt(i-1) == 'I') res += 3; else res += 5;
                    break;
                case 'X': if(i != 0 && s.charAt(i-1) == 'I') res += 8; else res += 10;
                    break;
                case 'L': if(i != 0 && s.charAt(i-1) == 'I') res += 48; 
                          else if(i != 0 && s.charAt(i-1) == 'X') res += 30; 
                          else res+= 50; //
                    break;
                case 'C': if(i != 0 && s.charAt(i-1) == 'I') res += 98;
                          else if(i != 0 && s.charAt(i-1) == 'X') res += 80;
                          else res+= 100;
                    break;
                case 'D': if(i != 0 && s.charAt(i-1) == 'I') res += 498;
                          else if(i != 0 && s.charAt(i-1) == 'X') res += 480;
                          else if(i != 0 && s.charAt(i-1) == 'C') res += 300;
                          else res+= 500;
                    break;
                case 'M': if(i != 0 && s.charAt(i-1) == 'I') res += 998;
                          else if(i != 0 && s.charAt(i-1) == 'X') res += 980;
                          else if(i != 0 && s.charAt(i-1) == 'C') res += 800;
                          else res+= 1000;
                    break;
                default:
                    System.out.println("error");
            }
        }
        return res;
    }


    public static void main(String[] args){
	System.out.println(romanToInt("DCXXI"));
    }
}
