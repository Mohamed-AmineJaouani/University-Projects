public class Card{

    public int val;
    public String color;

    public Card(int val, String color){
	if(val > 13 || val < 1){
	    System.out.println("Invalid Number");
	    return;
	}
	this.val = val;
	this.color = color;
    }
}
