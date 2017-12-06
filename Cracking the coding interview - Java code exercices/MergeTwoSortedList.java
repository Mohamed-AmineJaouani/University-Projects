public class MergeTwoSortedList{
    //Peut etre amelioré
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2){
	if(l1 == null)
	    return l2;
	if(l2 == null)
	    return l1;

	ListNode list;

	if(l1.val <= l2.val){
	    list = new ListNode(l1.val);
	    l1 = l1.next;
	}
	else{
	    list = new ListNode(l2.val);
	    l2 = l2.next;
	}
	
	ListNode p = list;	

	while(l1 != null && l2 != null){
	    
	    if(l1.val <= l2.val){
		
		p.next = new ListNode(l1.val);
		l1 = l1.next;
	    }
	    else{
		p.next = new ListNode(l2.val);
		l2 = l2.next;
	    }
	    p = p.next;
	}
	while(l1 != null){
	    p.next = new ListNode(l1.val);
	    l1 = l1.next;
	    p = p.next;
	}
	
	while(l2 != null){
	    p.next = new ListNode(l2.val);
	    l2 = l2.next;
	    p = p.next;
	}
	return list;
    }

    public static void displayList(ListNode l){
	if(l == null)
	    return;
	System.out.print(l.val+" | ");
	displayList(l.next);

    }
    
    public static void main(String[] args){
	ListNode l1 = new ListNode(1);
	ListNode l3 = new ListNode(3);
	ListNode l5 = new ListNode(5);
	ListNode l7 = new ListNode(7);

	l1.next = l3;
	l3.next = l5;
	l5.next = l7;
	
	ListNode l2 = new ListNode(2);
	ListNode l4 = new ListNode(4);
	ListNode l6 = new ListNode(6);
	ListNode l8 = new ListNode(8);

	l2.next = l4;
	l4.next = l6;
	l6.next = l8;

	displayList(l1);

	System.out.println();
	displayList(l2);
	
	System.out.println();
	displayList(mergeTwoLists(l1,l2));
    }
}
