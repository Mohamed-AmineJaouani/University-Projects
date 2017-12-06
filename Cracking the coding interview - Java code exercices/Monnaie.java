public class Monnaie{

    public static int[][] monnaie(int[][] t, int[] p){
	for(int i = 1 ; i < p.length ; i++)
	    for(int s = 1 ; s < t[0].length ; s++){
		if(i == 1 && s < p[i])
		    t[i][s] = -1;
		else if(i == 1 && s >= p[i])
		    t[i][s] = 1 + t[i][s-p[1]];
		else if(s < p[i])
		    t[i][s] = t[i-1][s];
		else
		    t[i][s] = Math.min(t[i-1][s], t[i][s-p[i]] + 1 );
	    }
	return t;
    }

    public static void main(String[] args){
	int[][] t = new int[4][9];
	int[] p = {0,1,4,6};
	int[][] r = monnaie(t, p);

	for(int i = 0 ; i < r.length ; i++){
	    for(int j = 0 ; j < r[i].length ; j++)
		System.out.print(r[i][j]+ " | ");
	    System.out.println();
	}
    }
}
