public class MinMax{

    public int min, max;

    public MinMax(int min, int max){
	this.min = min;
	this.max = max;
    }
    
    public static MinMax minmax(int[] tab, int bg, int bd){
	if(bg == bd)
	    return new MinMax(bg,bg);
	if(bg == bd-1){
	    if(tab[bg] < tab[bd])
		return new MinMax(bg,bd);
	    else
		return new MinMax(bd,bg);
	}
	else{
	    MinMax rep = new MinMax(0,0);

	    int m = (bg+bd)/2;
	    MinMax mm1 = minmax(tab,bg,m);
	    MinMax mm2 = minmax(tab,m+1,bd);
	    if(tab[mm1.min] < tab[mm2.min])
		rep.min = mm1.min;
	    else
		rep.min = mm2.min;
	    if(tab[mm1.max] > tab[mm2.max])
		rep.max = mm1.max;
	    else
		rep.max = mm2.max;
	    return rep;
	    
	}
    }
    public static void main(String[] args){
	int[] tab = {2,1,0,5,9,3,18,17,15,14};
	MinMax mm = minmax(tab,0,tab.length-1);
	System.out.println(mm.min+" "+mm.max);
    }
}
