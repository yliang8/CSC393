/*
--------------------------------------------------------------
Party2.java:     
    Helper class that includes helpers methods for finding 
    convex hull using Jarvis algorithm.

    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------
*/


public class Jarvis {
    
    public boolean CCW(Point p, Point q, Point r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());
        if (val >= 0)
            return false;
        return true;
    }
    
    // Check if there are at least three points in one's collection
    /** Problem: in the real practice, we only need 3 points from Alice and Bob's collections in total **/
    public boolean check(Point[] pointArray){
        if (pointArray.length < 3) 
            return false;
        return true;
    }
    
    // Finds the point which has the smallest x-coordinate in a collection of points
    public Point getLeftMost(Point[] pointArray){
		Point leftMost = pointArray[0];
		for (Point p : pointArray){
			if (p.getX() < leftMost.getX()){
				leftMost = p;
			}
		}
		return leftMost;
	}
	
	// Compares the x-coordinate of two points
	public Point getLeftMost(Point A, Point B){
		double xi = A.getX();
		double wj = B.getX();
		
		if (wj < xi) return B;
		else return A;
	}
    
	// Determines the next point by finding that point that makes the smallest angle with vertical drawn at last point
	/** Idea from : http://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/ **/
    public Point getNextPoint(Point[] pointArray, Point leftMost, Point lastPoint) {
        int n = pointArray.length;
        Point p = lastPoint;
        int q = 0;
        if (pointArray[q].getX() == p.getX() && pointArray[q].getY() == p.getY()) q = n-1;// q can be anything other than p itself
        /** iterate till p becomes leftMost **/
        for (int i = 1; i < n; i++){
        	// System.out.println(q);
        	if (CCW(p, pointArray[i], pointArray[q])){
        		q = i;
        	}	
        }
        p = pointArray[q]; 
        return p;
    }
    
	// Compares the angles to the last point of two points
	public Point getNextPoint (Point A, Point B, Point lastPoint){
		if (CCW (lastPoint,B,A)) return B; 
		return A;
	}
    
	public Point toPoint(String str){
		// (x,y)
		char[] strs = str.toCharArray();
		String xStr = "";
		String yStr = "";
		int comma = 0;
		for (int i = 1; i< strs.length-1 ; i++){
			if (strs[i] == ',') {
				comma = i;
				break;
			}
			xStr += strs[i];
		}
		for (int i = comma + 1; i< strs.length-1 ; i++){
			yStr += strs[i];
		}
		double x = Double.parseDouble(xStr);
		double y = Double.parseDouble(yStr);

		Point p = new Point (x,y);
		return p;
	}
    
}