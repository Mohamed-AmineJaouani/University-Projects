import java.util.HashMap;
import java.util.ArrayList;
public class EquationCube {


    public static void equationCube(int n){
	for(int a = 1 ; a < n ; a++)
	    for(int b = 1 ; b < n ; b++)
		for(int c = 1 ; c < n ; c++)
		    for(int d = 1 ; d < n ; d++)
			if(Math.pow(a,3) + Math.pow(b,3) == Math.pow(c,3) + Math.pow(d,3)){
			    System.out.println(a + " " + b + " " + c + " " + d);
			    break;
			}
    }

    public static void equationCubeOpt(int n){
	int d;
	for(int a = 1 ; a < n ; a++)
	    for(int b = 1 ; b < n ; b++)
		for(int c = 1 ; c < n ; c++){
		    d = (int)Math.pow(Math.pow(a,3) + Math.pow(b,3) - Math.pow(c,3),1/3);
		    if(Math.pow(a,3) + Math.pow(b,3) == Math.pow(c,3) + Math.pow(d,3))
			System.out.println(a + " " + b + " " + c + " " + d);
		}
    }

    public static void equationCubeMap(int n){
	HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	int result;
	ArrayList<Integer> list = new ArrayList<Integer>();
	for(int c = 1 ; c < n ; c++)
	    for(int d = 1 ; d < n ; d++){
		result = (int)(Math.pow(c,3) + Math.pow(d,3));
		list = map.get(result);
		if(list == null){
		    list = new ArrayList<Integer>();
		    list.add(c);
		    list.add(d);
		    map.put(result,list);
		}
		else{
		    list.add(c);
		    list.add(d);
		}
	    }
	for(int a = 1 ; a < n ; a++)
	    for(int b = 1 ; b < n ; b++){
		result = (int)(Math.pow(a,3) + Math.pow(b,3));
		list = map.get(result);
		if(list != null)
		    for(int i = 0 ; i < list.size()-1 ; i++)
			System.out.println(a + " " + b + " " + list.get(i) + " " + list.get(i+1));
	    }
    }


    public static void main(String[] args){
	equationCubeMap(500);
    }
}
