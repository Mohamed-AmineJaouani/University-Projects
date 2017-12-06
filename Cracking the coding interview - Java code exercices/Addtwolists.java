
public class Addtwolists {

    public static void main(String[] args){
	ListNode l1 = new ListNode(2);
	ListNode l1n = new ListNode(4);
	ListNode l1nn = new ListNode(3);
	 
	l1.next = l1n;
	l1n.next = l1nn;

	ListNode l2 = new ListNode(5);
	ListNode l2n = new ListNode(6);
	ListNode l2nn = new ListNode(4);
	 
	l2.next = l2n;
	l2n.next = l2nn;

	ListNode r = l1.f(l2);
	while(r != null){
	    System.out.print(r.val +" -> ");
	    if(r.next != null)
		r = r.next;
	    else break;
	}
	System.out.println();
    }
}
