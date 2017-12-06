public class TripleSteps{

    public static int countWays(int n, int[] memo){
	if(n < 0)
	    return 0;
	else if(n == 0)
	    return 1;
	else if(memo[n] != -1)
	    return memo[n];
	memo[n] = countWays(n-1,memo)+countWays(n-2,memo)+countWays(n-3,memo);
	return memo[n];
    }
    
    public static void main(String[] args){
	int n = 4;
	int[] memo = new int[n+1];

	for(int i = 0 ; i < memo.length ; i++){
	    memo[i] = -1;
	}

	System.out.println(countWays(n, memo));
    }
}
