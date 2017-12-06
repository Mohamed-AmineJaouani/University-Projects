import java.util.Stack;
public class SimplifyPath {

    public static String simplify(String s){
	String r = "";

	Stack<String> stack = new Stack<String>();
	Stack<String> stackreverse = new Stack<String>();

	String[] t = s.split("/");

	for(int i = 0 ; i < t.length ; i++){
	    
	    System.out.print(" -"+t[i]+"- | ");
	    switch(t[i]){
	    case "":
		if(!stack.empty() && stack.peek().equals("/"))
		    break;
		else
		    stack.push("/");
		break;
		
	    case ".":
		break;
	    case "..":
		if(!stack.empty())
		    stack.pop();
		else
		    stack.push("/");
		if(!stack.empty())
		    stack.pop();
		else{
		    stack.push("/");
		    if(i == t.length)
			stack.push("/");
		}
		break;
	    default:
		stack.push(t[i]);
		stack.push("/");
		break;
	    }
	}
	
	if(stack.size() > 1 && stack.peek().equals("/"))
	    stack.pop();
	else if(stack.size() != 1)
	    stack.push("/");

	System.out.println();

	while(!stack.empty())
	    stackreverse.push(stack.pop());

	while(!stackreverse.empty())
	    r += stackreverse.pop();
	
	System.out.println();
	return r;
    }

    

    public static void main(String[] args){
	System.out.println(simplify("/a/./b///../c/../././../d/..//../e/./f/./g/././//.//h///././/..///"));
	
    }
}

/* 

"/../ -> /"
"/./ -> /."
"/home//b/ -> /home/b"
*/