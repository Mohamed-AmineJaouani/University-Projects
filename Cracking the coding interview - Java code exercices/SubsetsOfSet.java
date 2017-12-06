import java.util.HashSet;
public class SubsetsOfSet {
    
    public static HashSet<HashSet<Integer>> subsetsOfSet(HashSet<Integer> set){
	HashSet<HashSet<Integer>> result = new HashSet<HashSet<Integer>>();
	if(set.isEmpty())
	    return null;
	
	result.add(new HashSet<Integer>()); //empty set
	
	for(int i : set){
	    HashSet<HashSet<Integer>> l = new HashSet<HashSet<Integer>>();
	    for(HashSet<Integer> list : result){
		HashSet<Integer> tmp = new HashSet<Integer>();
		tmp.addAll(list);
		tmp.add(i);
		l.add(tmp);
	    }
	    result.addAll(l);
	    
	    /*	    print(l);
	    System.out.println(i+" ---------------------------");
	    */
	}
	return result;
    }

    public static void print(HashSet<HashSet<Integer>> set){
	for(HashSet<Integer> ll : set){
	    if(ll.isEmpty())
		System.out.print("vide");
	    for(Integer i : ll){

		System.out.print(i + " | ");
	    }
	    System.out.println();
	}
    }
    public static void main(String[] args){
	HashSet<Integer> l = new HashSet<Integer>();
	l.add(1);
	l.add(2);
	l.add(3);
	//l.add(4);
	//l.add(5);

	HashSet<HashSet<Integer>> set = subsetsOfSet(l);

	print(set);
    }
}

/*
{}
{},{a1}
{}, {a1}, {a2}, {a1,a2}


 */
