/*
--------------------------------------------------------------
Point.java: 	
	Definition of Point object

    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------
*/


public class Point {
	private double x;
	private double y;
	
	public Point (double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public String toString(){
		return "(" + this.x + ","  +this.y + ")";
	}
	
}
