import java.util.HashMap;
//import java.util.LinkedList;
public class RemoveDups{

    public static Node removeDups(Node head){
	HashMap<Integer, Boolean> map = new HashMap<Integer,Boolean>();
	Node n = head;
	
	if(n != null)
	    map.put(n.data,true);
	else
	    return head;

	while(n.next != null){
	    if(map.get(n.next.data) == null)
		map.put(n.next.data, true);
	    else{
		n.next = n.next.next;
	    }
	    n = n.next;
	}
	return head;
    }
    
    public static void main(String[] args){
	Node head = new Node(1);
	
	head.appendToTail(2);
	head.appendToTail(2);
	head.appendToTail(3);
	head.appendToTail(4);
	head.appendToTail(4);
	head.appendToTail(5);

	System.out.println(head);
	System.out.println(removeDups(head));
    }
}
