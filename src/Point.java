import java.util.ArrayList;

import processing.core.PApplet;


// ===== point class
public class Point {
	float x, y, z;

	public Point(){
		this(0,0,0);
	}

	Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float[] getXYZ(){
		float[] out = {x,y,z};
		return out;
	}

	public void add(Point p) {
		x += p.x;
		y += p.y;
		z += p.z;
	}
	
	public void sub(Point p) {
		x -= p.x;
		y -= p.y;
		z -= p.z;
	}
	
	public void mult(float s) {
		x *= s;
		y *= s;
		z *= s;
	}

	void normalize() {
		double magnitude = Math.sqrt(x*x+y*y+z*z);
		x *= 1/magnitude;
		y *= 1/magnitude;
		z *= 1/magnitude;
	}


	//********************
	// GENERAL METHODS
	//********************
	public static Point add(Point ... vectors) {
		Point p = new Point();
		for (Point v: vectors) {
			p.add(v);
		}
		return p;
	}

	public static Point sub(Point a, Point b) {
		return new Point(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static Point mult(Point a, float s) {
		return new Point(a.x*s, a.y*s, a.z*s);
	}

	public static Point div(Point a, float s) {
		return mult(a, 1/s);
	}

	//****************
	// MAGIC METHODS
	//****************

	public static float dist(Point a, Point b) {
		return PApplet.dist(a.x,a.y,a.z,b.x,b.y,b.z);
	}

	public static Point midPoint(Point a, Point b) {
		return new Point((a.x+b.x)/2,(a.y+b.y)/2, (a.z+b.z)/2);
	}

	public static Point lerp(Point a, float t, Point b) {
		Point d = sub(b, a);
		return add(a, mult(d, t));
	}

	public static Point addScaledVector(Point a, Point b, float t) {
		return add(a, mult(b, t));
	}

	public static Point rotateVector90(Point v) {
		return new Point(-v.y, v.x, 0);
	}
	
	public static Point rotatePointAroundPlane(Point p, float a, Point I, Point J, Point g) {
		float x = dist(Point.sub(g,p),I), 
			  y = dist(Point.sub(g,p),J);
		
		float c=(float) Math.cos(a), s=(float) Math.sin(a); 
		return Point.add(Point.mult(I,x*c-x-y*s),Point.mult(J,x*s+y*c-y));
	};


	public static Point rotateVector(Point v, float angle) {
		float c=(float) Math.cos(angle), s=(float) Math.sin(angle);
		return(new Point(v.x*c-v.y*s,v.x*s+v.y*c, 0));
	}
	
	// Rotated V by a parallel to plane (I,J)
	public static Point rotateVectorParallelToPlane(Point v, float angle, Point I, Point J) {
		float x=dist(v,I), y=dist(v,J);
		float c=(float) Math.cos(angle), s=(float) Math.sin(angle);
		return Point.add(v,Point.add(Point.mult(I,x*c-x-y*s),Point.mult(J,x*s+y*c-y))); 
	}; 

	public static float dot(Point u, Point v) {
		return u.x*v.x + u.y*v.y + u.z*v.z;
	}

	public static float cross2D(Point u, Point v) {
		return dot(u,rotateVector90(v));
	}

	float angle(Point a, Point b) {
		return (float) Math.atan2(dot(rotateVector90(a),b),dot(a,b));
	}

	public static Point neville(float t, Point ... vectors) {
		if (vectors.length == 1) return vectors[0];
		if (vectors.length == 2) {
			return lerp(vectors[0], t, vectors[1]);
		} else {
			int len = vectors.length - 1;
			Point[] v1 = new Point[len], v2 = new Point[len];
			System.arraycopy(vectors,0,v1,0,len);
			System.arraycopy(vectors,1,v2,0,len);
			return neville(t/(len),neville(t, v1), neville(t-1,v2));
		}
	}
	
	
	void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static ArrayList<Point> subdivide(ArrayList<Point> points) {

		Point[] newPoints = new Point[points.size()];
		int pos = 0;

		int length = points.size();
		if (points.size() > 3) {
			newPoints[pos++] = fourPointSubdivide(points.get(0), points.get(1), points.get(2), points.get(3));

			for (int i = 0; i < length - 3; i++) {
				newPoints[pos++] = fourPointSubdivide(points.get(i), points.get(i+1), points.get(i+2), points.get(i+3));
			}
			
			ArrayList<Point> points2 = new ArrayList<Point>();
			points2.add(points.get(0));
			for (int i = 1; i < length; i++) {
				points2.add(points.get(i));
				if (i < pos) points2.add(newPoints[i]);
			}
			
			points = points2;
		} else {
			System.out.println("Not enough points to do four point subdivision");
		}
		
		return points;
	} 

	public static Point fourPointSubdivide(Point p0, Point p1, Point p2, Point p3){
		//s( s(A,9/8,B),.5, s(E,9/8,D)) from the notes
		Point one = Point.lerp(p0, 9.0f/8.0f, p1);
		Point two = Point.lerp(p3, 9.0f/8.0f, p2);
		return Point.lerp(one, (float).5, two);
	}

	public String toString() {
		return String.format("(%.2f,%.2f,%.2f)", x,y,z);
	}

}