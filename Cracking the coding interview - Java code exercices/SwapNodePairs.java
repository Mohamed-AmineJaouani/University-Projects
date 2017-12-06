public class SwapNodePairs {

    public static ListNode swapNodePairs(ListNode l){
	if(l == null || l.next == null)
	    return l;
	ListNode tmp;
	ListNode top = l.next;
	ListNode first = l;
	ListNode second = first.next;
	

	do{
	    tmp = second.next;
	    second.next = first;
	    first.next = tmp;

	    first = tmp;
	    if(first!= null && first.next != null){
		System.out.println("test "+first.next);
		second.next.next = first.next;
		second = first.next;
	    }
	}while(first != null && first.next != null );
	
	return top;
    }

   // public static void swap(ListNode l1, ListNode l2){
	
   //}
    
    public static void displayList(ListNode l){
	if(l == null)
	    return;
	System.out.print(l.val+" | ");
	displayList(l.next);
    }
    public static void main(String[] args){
	ListNode l1 = new ListNode(1);
	ListNode l2 = new ListNode(2);
	ListNode l3 = new ListNode(3);
	ListNode l4 = new ListNode(4);

	l1.next = l2;
	//l2.next = l3;
	//l3.next = l4;
	
	displayList(l1);
	System.out.println();
	displayList(swapNodePairs(l1));
	System.out.println();
    }
}
