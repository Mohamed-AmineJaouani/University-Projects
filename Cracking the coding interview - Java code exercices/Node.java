class Node {
    Node next = null;
    int data;
    
    public Node(int d) {	
	data = d;
    }
    
    void appendToTail(int d){
	Node end = new Node(d);
	Node n = this;
	
	while(n.next != null)
	    n = n.next;
	n.next = end;
    }

    @Override
    public String toString(){
	String s = "[";
	Node n = this;
	while(n.next != null){
	    s+= n.data+",";
	    n = n.next;
	}
	s += n.data+"";
	return s+"]";
    }
}


