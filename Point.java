package ethanNguyen;
 
public class Point implements Comparable<Point> {
	
	private int x;
	private int y;
	private static final double SMALLEST_DOUBLE = -2147483648;
	
	Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {	return x; }
	
	public int getY() { return y; }
	
	@Override
    public int compareTo(Point other) {
		return Double.compare(this.getSlope(), other.getSlope());
    }
	
	private double getSlope() {
		Point first = Display.getFirstIndex(); //right-bottom point
		if(this.x - first.x == 0) //prevent / 0 error
			return SMALLEST_DOUBLE;
		double slope = (double) (this.y - first.y) / (this.x - first.x);
		return slope;
	}
}
