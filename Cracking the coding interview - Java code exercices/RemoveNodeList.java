public class RemoveNodeList {

    public static ListNode removeNodeList(ListNode head, int n){
	if(head == null){
	    return null;
	}
	ListNode p = head;
	int size = 1;
	while(p.next != null){
	    p = p.next;
	    size++;
	}

	if(size < n)
	    return head;
	if(size == n)
	    return head.next;

	p = head;
	for(int i = 0 ; i < size - n-1 ; i++){
	    p = p.next;
	}
	if(p.next.next == null)
	    p.next = null;
	else
	    p.next = p.next.next;
	return head;
    }
    
    public static void displayList(ListNode l){
	if(l == null)
	    return;
	System.out.print(l.val+" | ");
	displayList(l.next);

    }
    public static void main(String[] args){
	ListNode l1 = new ListNode(1);
	ListNode l3 = new ListNode(2);
	ListNode l5 = new ListNode(3);
	ListNode l7 = new ListNode(4);
	ListNode l9 = new ListNode(5);
	
	l1.next = l3;
	l3.next = l5;
	l5.next = l7;
	l7.next = l9;


	ListNode l2 = new ListNode(1);
	ListNode l4 = new ListNode(2);
	l2.next = l4;
	
	displayList(l2);
	System.out.println();
	displayList(removeNodeList(l2,2));
	System.out.println();
    }
}
