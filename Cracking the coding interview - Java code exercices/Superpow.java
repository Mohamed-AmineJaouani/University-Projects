public class Superpow{

    public static int superpoww(int a, int[] b){
	int g = a;
	int[] q = b;
	int y = 1;
	
	while(q != 1){
	
	    if(q%2 == 1) 
		y = g * y;
	    g = (g*g)%1337;
	    q = divise(q, a);
	}
	System.out.println(g+" "+y);
       	return (g*y)%1337;
    }

    

    public static void divise(int[] tab, int a){
	int begin = 0;
	while (tab[begin] == 0) begin++;
	int end = begin;
	int fusion = 0;
	int result = 0;
	int retenu = 0;
	while(begin < tab.length) {
	    fusion = retenu;
	    while (fusion < a && end < tab.length) {
		fusion *= 10;
		fusion += tab[end++];
	    }
	    end --;
	    result = fusion / a;
	    for (int i = end; i>= begin; i--) {
		tab[i] = result % 10;
		result /= 10;
	    }
	    begin = ++end;
	    retenu = fusion % a;
	}
    }
    
    public static void main(String[] args){
	//int[] b = {0, 5};
	int [] b = {4, 3, 5};
	divise(b, 5);
	for (int i=0; i<b.length; i++)
	    System.out.print(b[i]);
	System.out.println();
	//System.out.println(superpoww(2,50));
    }
}
