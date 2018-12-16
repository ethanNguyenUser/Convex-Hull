package ethanNguyen;
 
public class Point implements Comparable<Point> {
	
	private int x;
	private int y;
	
	Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {	return x; }
	
	public int getY() { return y; }
	
	@Override
    public int compareTo(Point other) {
		return Double.compare(this.getPolarOrder(), other.getPolarOrder());
    }
	
	private double getPolarOrder() {
		
		Point first = Display.getFirstIndex(); //right-bottom point
		double polarAngle = Math.toDegrees(Math.atan2(this.y - first.y, this.x - first.x)); //gets polar angle of tested point with right-bottom point acting as origin
		polarAngle = (polarAngle < 0) ? polarAngle + 360: polarAngle; //if negative, adds 360 to polar angle
		
		return polarAngle;
	}
}
