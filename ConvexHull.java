package ethanNguyen;
 
import javax.swing.JFrame;
 
public class ConvexHull {
	
	public static int DISPLAY_WIDTH = 1000;
	public static int DISPLAY_HEIGHT = 1000;
	
	public static void main(String[] args) {
		
		JFrame f = new JFrame();
		f.setLayout(null);
		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Ethan Nguyen's Convex Hull");
		Display display = new Display(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.add(display);
		f.setVisible(true);
		display.runLoop();
	}
}