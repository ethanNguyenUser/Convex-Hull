package ethanNguyen;
 
import javax.swing.JFrame;
 
public class ConvexHull {
	
	public final static int DISPLAY_WIDTH = 900;
	public final static int DISPLAY_HEIGHT = 900;
	private final static boolean IS_RESIZABLE = false;
	
	
	public static void main(String[] args) {
		
		JFrame f = new JFrame();
		f.setLayout(null);
		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Ethan Nguyen's Convex Hull");
		Display display = new Display(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.add(display);
		f.setResizable(IS_RESIZABLE);
		f.setVisible(true);
		display.runLoop();
	}
}
