public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {val = x;}
    

    public ListNode f(ListNode l2){
	ListNode res = new ListNode(0);
	ListNode p = this, q = l2;
	ListNode curr = res;
	int retenue = 0;
	while(p != null || q != null ){
	    int x = (p != null)? p.val : 0;
	    int y = (q != null)? q.val : 0;
	    int sum = retenue+x+y;
	    retenue = sum / 10;
	    curr.next = new ListNode(sum % 10);
	    curr = curr.next;
	    if(p!=null) p = p.next;
	    if(q!=null) q = q.next;
	}
	if(retenue > 0)
	    curr.next = new ListNode(retenue);
	return res.next;
    }
}