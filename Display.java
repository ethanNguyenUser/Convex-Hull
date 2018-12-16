package ethanNguyen;
 
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JComponent;
import java.util.concurrent.ThreadLocalRandom;

public class Display extends JComponent{

	private static final long serialVersionUID = 1L;
	private final static String LINE = "---------------------------------------------------------------------";
	private final static String STOP_INPUT = "q";
	private final static String EXIT_PROGRAM = "EXIT";
	private final static String READ_FROM_FILE = "FILE";
	private final static String RANDOM_GENERATOR = "RAND";
	private final static int GRAPH_WIDTH = 800;
	private final static int GRAPH_HEIGHT = 800;
	private final static int Y_MESSAGE_OFFSET = 20;
	private final static int DEFAULT_SMALLEST_X = -10;
	private final static int DEFAULT_LARGEST_X = 10;
	private final static int DEFAULT_SMALLEST_Y = -10;
	private final static int DEFAULT_LARGEST_Y = 10;
	private final static int POINT_SIZE = 8;
	private final static Color LINE_COLOR= new Color(255, 245, 75);
	private final static Color POINT_COLOR = new Color(255, 80, 20);

	private static Scanner input = new Scanner(System.in);
	private static int programUsageCounter = 1;
	private static ArrayList<Point> pointList = new ArrayList<Point>();
	private static ArrayList<Point> finalPointList = new ArrayList<Point>();
	private static Point firstIndex = new Point(0, 0);
	
	public static Point getFirstIndex() { return firstIndex; }
	
	public Display(int width, int height) { //constructor
		setSize(width, height);
		System.out.println(LINE);
		System.out.println("Ethan Nguyen's Convex Hull Program");
		System.out.println(LINE);
	}
	
	public void runLoop() { //runs the loop for the entire program
		while(askInput()) {
			findRightLowPoint();
			findPoints();
			printPoints();
			repaint();
			programUsageCounter++;
		}
	}
 
	private static boolean askInput() { //asks for input from user through file, random generator, or console
		
		String inputString = "";
		int x = 0; //x value
		int y = 0; //y value
		
		System.out.println("Run " + programUsageCounter);
		System.out.println(LINE);
		System.out.println("Enter \"" + READ_FROM_FILE + "\" to read from a file");
		System.out.println("Enter \"" + RANDOM_GENERATOR + "\" to generate a random set of points");
		System.out.println("Enter \"" + EXIT_PROGRAM + "\" to terminate the program");
		System.out.println("Enter anything else to read from console");
		System.out.println(LINE);
		
		inputString = input.nextLine();
		
		if(inputString.equals(READ_FROM_FILE)) {
			System.out.println(LINE);
			readFromFile();
			System.out.println(LINE);
			return true;
		}
		
		if(inputString.equals(RANDOM_GENERATOR)) {
			System.out.println(LINE);
			randomGenerator();
			System.out.println(LINE);
			return true;
		}
		
		if(inputString.equals(EXIT_PROGRAM)) {
			System.out.println(LINE);
			System.out.println("Thank you for using Ethan Nguyen's Convex Hull Program");
			System.out.println(LINE);
			return false;
		}
		
		System.out.println(LINE);
		System.out.println("Enter your coordinates");
		System.out.println("Enter \"" + STOP_INPUT + "\" to stop input");
		System.out.println(LINE);
		
		for(int k = 1;;k++) {
			
			System.out.println("Input " + k);
			System.out.print("x-value: ");
			inputString = input.nextLine();
			
			if(inputString.equals(STOP_INPUT)) {
				System.out.println(LINE);
				System.out.println("Input Closed");
				System.out.println(LINE);
				break;
			}
			
			try {
			x = Integer.parseInt(inputString);
			} catch(NumberFormatException e) {
				System.out.println("Unexpected Input " + e.getMessage());
				System.out.println(LINE);
				k--;
				continue;
			}
			
			System.out.print("y-value: ");
			inputString = input.nextLine();
			
			if(inputString.equals(STOP_INPUT)) {
				System.out.println(LINE);
				System.out.println("Input Closed");
				System.out.println(LINE);
				break;
			}
			
			try {
				y = Integer.parseInt(inputString);
			} catch(NumberFormatException e) {
				System.out.println("Unexpected Input " + e.getMessage());
				System.out.println(LINE);
				k--;
				continue;
			}
			
			pointList.add(new Point(x, y));
			
			System.out.println(LINE);
		}
		
		return true;
	}

	private static void readFromFile() { //reads from a file "data.txt"
		
		int numcoords = 0;

	    try (
	        Scanner sc = new Scanner(new BufferedReader(new FileReader("C:\\Users\\chris\\eclipse-workspace\\ConvexHull\\src\\ethanNguyen\\data"))); //file path
	        ) {
	        while(sc.hasNextLine()) {
	            //  this file read pass gets total number of coordinates
	            String[] l1 = sc.nextLine().split(",");
	            if (l1.length > 1) {
	               //  without this check, blank lines will throw an exception
	               numcoords++;
	            }
	        }
	    }
	    catch(Exception e) {
	        System.out.println("Problem reading coordinates from data.txt file");
	        //  e.printStackTrace();
	    }
	    System.out.println("File contains " + numcoords + " coordinate sets");

	    try (
	        Scanner sc = new Scanner(new BufferedReader(new FileReader("C:\\Users\\chris\\eclipse-workspace\\ConvexHull\\src\\ethanNguyen\\data"))); //file path
	        ) {
	        int i = 0;
	        int [] xx = new int[numcoords];  //  allocate array, we know
	        int [] yy = new int[numcoords];  //  how many coords are in file

	        while(sc.hasNextLine()) {
	        //  String line = sc.nextLine();

	            String[] line = sc.nextLine().split(",");
	            if (line.length > 1) {
	               //  without this check, blank lines will thorw an exception
	               xx[i] = Integer.parseInt(line[0].trim());
	               yy[i] = Integer.parseInt(line[1].trim());
	               i++;
	            }
	        }
	       System.out.println("x: " + Arrays.toString(xx));
	       System.out.println("y: " + Arrays.toString(yy));
	       
	       for(int k = 0; k < xx.length; k++) {
	    	   pointList.add(new Point(xx[k], yy[k]));
	       }

	    }
	    catch(Exception e) {
	       System.out.println("Problem reading coordinates from data.txt file");
	       //  e.printStackTrace();
	    }
	}

	private static void randomGenerator() { //generates random points given a floor, ceiling, and max points
		
		String inputString = "";
		int xCeiling = 0;
		int xFloor = 0;
		int yCeiling = 0;
		int yFloor = 0;
		int maxPoints = 0;
		
		
		System.out.print("Enter x-value floor: ");
		inputString = input.nextLine();
		try { xFloor = Integer.parseInt(inputString); }
		catch(NumberFormatException e) { 
			randomGeneratorError();
			return;
		}
		
		System.out.print("Enter x-value ceiling: ");
		inputString = input.nextLine();
		try { xCeiling = Integer.parseInt(inputString); }
		catch(NumberFormatException e) { 
			randomGeneratorError(); 
			return;
		}
		
		System.out.print("Enter y-value floor: ");
		inputString = input.nextLine();
		try { yFloor = Integer.parseInt(inputString); }
		catch(NumberFormatException e) { 
			randomGeneratorError(); 
			return;
		}
		
		System.out.print("Enter y-value ceiling: ");
		inputString = input.nextLine();
		try { yCeiling = Integer.parseInt(inputString); }
		catch(NumberFormatException e) { 
			randomGeneratorError(); 
			return;
		}
		
		System.out.print("Enter maximum number of points: ");
		inputString = input.nextLine();
		try { maxPoints = Integer.parseInt(inputString); }
		catch(NumberFormatException e) { 
			randomGeneratorError(); 
			return;
		}
		
		for(int k = 1; k <= maxPoints; k++)
			pointList.add(new Point(ThreadLocalRandom.current().nextInt(xFloor, xCeiling + 1), ThreadLocalRandom.current().nextInt(yFloor, yCeiling + 1)));
		
	}
	
	private static void randomGeneratorError() { //error message for random generator
		System.out.println(LINE);
		System.out.println("Unexpected Input");
		System.out.println(LINE);
		randomGenerator();
	}
	
	private void findRightLowPoint() { //finds the right most point, then bottom point if some points are in line
		
		int rightLowIndex = 0;
		
		for(int k = 0; k < pointList.size(); k++) {
			if(pointList.get(rightLowIndex).getX() < pointList.get(k).getX() //if tested point is right of current point
					|| pointList.get(rightLowIndex).getX() == pointList.get(k).getX() //or (they are equal on x-axis
							&& pointList.get(rightLowIndex).getY() > pointList.get(k).getY()) //and tested point is lower than current point)
				rightLowIndex = k;
		}
		
		//System.out.println("Right Low Index: (" + pointList.get(rightLowIndex).getX() + ", " + pointList.get(rightLowIndex).getY() + ")");
		
		try {
		firstIndex = pointList.get(rightLowIndex);
		}
		catch(IndexOutOfBoundsException e) {
		}
	}
	
	private void findPoints() { //finds the convex hull using Graham scan
		
		if(pointList.size() < 3) { //has to be at least three points
			return;
		}
		
		pointList.sort(Point::compareTo);


		//adds points to finalPointList which acts as a stack
		for(int k = 0; k < pointList.size(); k++) { //for all points
			while(finalPointList.size() >= 2 && //while stack size >= 2 and stack points are clockwise
					isCW(finalPointList.get(finalPointList.size() - 2), finalPointList.get(finalPointList.size() - 1), pointList.get(k)))
				finalPointList.remove(finalPointList.size() - 1); //pop stack
			finalPointList.add(pointList.get(k)); //push next largest polar angle point in sorted pointList
		}
		
		if(finalPointList.size() <= 2) { //in case all points are linear
			finalPointList.clear();
		}
	}
	
	private void printPoints() { //outputs convex hull points to console
		System.out.println("Convex Hull Points:");
		for (int k = 0; k < finalPointList.size(); k++)
				System.out.println("Point " + (k + 1) + ": ("+ finalPointList.get(k).getX() +", "+ finalPointList.get(k).getY() +")");
		System.out.println(LINE);
	}
		
	private boolean isCW(Point a, Point b, Point c) { //returns true if counter clockwise turn (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
		return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX()) <= 0;
	}
	
	public void paintComponent(Graphics g) { //repaints graph every call
		
		int smallestX = DEFAULT_SMALLEST_X;
		int largestX = DEFAULT_LARGEST_X;
		int smallestY = DEFAULT_SMALLEST_Y;
		int largestY = DEFAULT_LARGEST_Y;
		
		try { //attempts to update smallest and largest x and y values based on convex hull points
			smallestX = finalPointList.get(0).getX();
			largestX = finalPointList.get(0).getX();
			smallestY = finalPointList.get(0).getY();
			largestY = finalPointList.get(0).getY();

			for(int k = 0; k < finalPointList.size(); k++) {
				smallestX = Math.min(smallestX, finalPointList.get(k).getX());
				largestX = Math.max(largestX, finalPointList.get(k).getX());
				smallestY = Math.min(smallestY, finalPointList.get(k).getY());
				largestY = Math.max(largestY, finalPointList.get(k).getY());
			}
			
		}
		catch(IndexOutOfBoundsException e) {
		}
		
		int xRange = largestX - smallestX;
		int yRange = largestY - smallestY;
		
		//draw rectangle background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, ConvexHull.DISPLAY_WIDTH, ConvexHull.DISPLAY_HEIGHT);
		
		//draw side lines
	    g.setColor(Color.WHITE);
	    g.drawLine(GRAPH_WIDTH, GRAPH_HEIGHT, GRAPH_WIDTH, 0);
	    g.drawLine(GRAPH_WIDTH, 0, 0, 0);
	    g.drawLine(0, 0, 0, GRAPH_HEIGHT);
	    g.drawLine(0, GRAPH_HEIGHT, GRAPH_WIDTH, GRAPH_HEIGHT);
	 
	    //draw text
	    g.drawString("(" + largestX + ", " + smallestY + ")", GRAPH_WIDTH, GRAPH_HEIGHT + Y_MESSAGE_OFFSET);
	    g.drawString("(" + largestX + ", " + largestY + ")", GRAPH_WIDTH, Y_MESSAGE_OFFSET);
	    g.drawString("(" + smallestX + ", " + largestY + ")", 0, Y_MESSAGE_OFFSET);
	    g.drawString("(" + smallestX + ", " + smallestY + ")", 0, GRAPH_HEIGHT + Y_MESSAGE_OFFSET);
	    
	    //draw convex hull
	    g.setColor(LINE_COLOR);
	    
	    try {
	    	
	    	finalPointList.add(new Point(finalPointList.get(0).getX(), finalPointList.get(0).getY()));
	    	
	    	for(int k = 0; k < finalPointList.size() - 1; k++) {
		    	int xPoint1 = (int) (GRAPH_WIDTH * (finalPointList.get(k).getX() - smallestX) / xRange);
		    	int yPoint1 = (int) (GRAPH_HEIGHT - (GRAPH_HEIGHT * (finalPointList.get(k).getY() - smallestY) / yRange));  //checks weird y-flipping
		    	int xPoint2 = (int) (GRAPH_WIDTH * (finalPointList.get(k + 1).getX() - smallestX) / xRange);
		    	int yPoint2 = (int) (GRAPH_HEIGHT - (GRAPH_HEIGHT * (finalPointList.get(k + 1).getY() - smallestY) / yRange));  //checks weird y-flipping
		    	//System.out.println("index " + k + ", x value " + xPoint + ", y value " + yPoint);
		    	g.drawLine(xPoint1, yPoint1, xPoint2, yPoint2);
		    }
		    
		    finalPointList.remove(finalPointList.size() - 1);
	    }
	    catch(IndexOutOfBoundsException e) { //nothing in finalPointList
	    }
	    
	    //draw points
	    g.setColor(POINT_COLOR);
	    for(int k = 0; k < pointList.size(); k++) {
	    	int xPoint = (int) (GRAPH_WIDTH * (pointList.get(k).getX() - smallestX) / xRange);
	    	int yPoint = (int) (GRAPH_HEIGHT - (GRAPH_HEIGHT * (pointList.get(k).getY() - smallestY) / yRange));  //checks weird y-flipping
	    	//System.out.println("index " + k + ", x value " + xPoint + ", y value " + yPoint);
	    	g.fillOval((int) (xPoint - 0.5 * POINT_SIZE), (int) (yPoint - 0.5 * POINT_SIZE), POINT_SIZE, POINT_SIZE);
	    	g.drawString("(" + pointList.get(k).getX() + ", " + pointList.get(k).getY() + ")", xPoint, yPoint + Y_MESSAGE_OFFSET);
	    }
	    
	    pointList.clear();
		finalPointList.clear();
	  }

}
