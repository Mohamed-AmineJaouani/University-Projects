public class Jugs {
    public static boolean canMeasureWater(int x, int y, int z) {
	if(x+y == z) return true;
	if(y - (2*x-y) == z){
            return true;
        }
	if(2*y-x == z) return true;
	if(y - x%y  == z-y) return true;
        else{
            return false;
        }
    }

    public static void main(String[] args){
	System.out.println(canMeasureWater(34,5,6));
    }
}
