# Convex-Hull
Convex Hull Program for APCS



IMPORTANT NOTE - If you want to read from a file, you need to update the file path to data.txt in Display.java's function, readFromFile()


Name: Ethan Nguyen
Period: 1



Features: 

Various modes of input:
Enter "FILE" to read from a file
Enter "RAND" to generate a random set of points
Enter "EXIT" to terminate the program
Enter anything else to read from console

Reading from a file means inputing numbers through data.txt, and is one of the faster ways of inputing data
The random point generator allows you to specify the floor and ceiling of the points as well as the max number of random points
Console input takes x and y values for every point
All inputs only accept integer values, and the program will ask for input again if the user enters any unexpected input

Outputs to the console the optimal set of convex hull points starting from the right-bottom point and then working counter-clockwise

If two or less points are entered, it does not output those points to the console

Can run forever until "EXIT" is entered or window is closed

Program keeps track of the number of runs/uses until closed

Graph scales according to the largest and smallest x and y point values

All points are shown with coordinates listed

Colors of lines, points, background, and sidelines are easily changeable



Organization:

I split my code into three classes: ConvexHull, Display, and Point.

ConvexHull.java initialized the JFrame, Display.java had most of my code, and Point.java was a helper class for Display.

Display.java ran many functions in a loop that asked for input, found the convexhull points, outputed those points to the console, and graphed them on the JFrame.

In askInput(), it either passed control to randomGenerator(), readFromFile(), or continued with console.

Input taken from the user was stored in the ArrayList pointList, a list of Point objects that held an x and y static member variable

pointList was processed in findRightBottom() and findPoints(), and the final convex hull points were added to another Arraylist, finalPointList

finalPointList would then be printed and graphed in printPoints() and paintComponent

All of these methods would run forever in the method, runLoop(), until the user exited the program
