public class Fibonacci {

    public static int fiboNaif(int n){
	if(n == 0)
	    return 0;
	if(n == 1)
	    return 1;
	return fiboNaif(n-1)+fiboNaif(n-2);
    }

    public static int fiboCache(int n, int[] memo){
	if(n == 0)
	    return 0;
	if(n == 1)
	    return 1;
	if(n >= 2 && memo[n] == 0)
	    memo[n] = fiboCache(n-1, memo) + fiboCache(n-2, memo);
	return memo[n];
    }
    
    public static int fiboBottomUp(int n){
	if(n == 0) return 0;
	int[] t = new int[n+1];
	t[1] = 1;
	for(int i = 2 ; i <= n ; i++)
	    t[i] = t[i-1] + t[i-2];
	return t[n];
    }

    public static int fiboOpt(int n){
	if(n == 0) return 0;
	else if(n == 1) return 1;
	else{
	    int a=0, b=1, tmp;
	    for(int i = 2 ; i < n ; i++){
		tmp = a;
		a = b;
		b += tmp;
	    }
	    return a+b;
	}
    }

    public static void main(String[] args){
	System.out.println(fiboNaif(6));

	int taille = 16;
	int[] memo = new int[taille];
	for(int i = 0 ; i < taille ; i++)
	    System.out.println(fiboOpt(i));
	
	
    }
}
