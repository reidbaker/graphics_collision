import java.util.ArrayList;

import processing.core.PApplet;

class Curve {

	ArrayList<Nub> controls = new ArrayList<Nub>();
	ArrayList<Point> points = new ArrayList<Point>();

	int stroke_color;

	Curve(int colour) {
		stroke_color = colour;
	}

	Curve() {
		this(0xffffff);
	}

	Curve(int colour, ArrayList<Point> points) {
		this(colour);
		this.points = points;
	}

	int size() {
		return points.size();
	}

	void resample() {

		int size = controls.size()*16;
		if (points.size() < 1) {
		    return;
		}

		while (points.size () < size) {
			subdivide();
		}
	}

	void draw(PApplet c) {
		drawPoints(c);
	}

	void drawPoints(PApplet c) {
		if (points.size() > 0) {
			c.beginShape();
			c.stroke(stroke_color);
			c.noFill();
			for (int i = 0; i < points.size()-1; i++) {
				Point p = points.get(i);
				c.vertex(p.x,p.y,p.z);
			}

			Point p = points.get(points.size()-1);
			c.vertex(p.x,p.y,p.z);
			c.endShape();

			c.fill(stroke_color);
			for (int i = 0; i < points.size()-1; i++) {
				p = points.get(i);
				c.pushMatrix();
				c.translate(p.x,p.y,p.z);
				c.sphere(10);
				c.popMatrix();
			}
		}
	}

	public void subdivide() {
		points = Point.subdivide(points);
	}

	public void addControlPoint(Nub nub) {
		controls.add(nub);
		recalculate();
	}

	void recalculate() {
		points.clear();
		for (Nub n: controls) {
			points.add(n.pos);
		}

		if (points.size() > 3) {
			resample();
		}
	}

	public Point getClosestPoint(Point p) {
		Point closest = null;
		float threshold = Integer.MAX_VALUE;
		for (Point point: points) {
			float dist = Point.dist(p, point);
			if ( dist < threshold) {
				threshold = dist;
				closest = point;
			}
		}
		return closest;
	}
	
	public Nub getClosetControlPoint(Point p) {
		return getClosestControlPoint(p, Integer.MAX_VALUE);
	}
	
	public Nub getClosestControlPoint(Point p, float threshold) {
		Nub closest = null;
		for (Nub point: controls) {
			float dist = Point.dist(p, point.pos);
			if ( dist < threshold) {
				threshold = dist;
				closest = point;
			}
		}
		return closest;
	}
	
	public ArrayList<Point> getNubPositions(){
	    ArrayList<Point> positions = new ArrayList<Point>();
	    for (int i = 0; i < controls.size(); i++) {
            positions.add(controls.get(i).pos);
        }
	    return positions;
	}
	
	public Point getTangent(Point p){
	    int index = findPoint(p);
	    if(index == 0 || index == points.size()-1){
	        return new Point();
	    } else {
	        float dx = points.get(index+1).x - points.get(index-1).x;
	        float dy = points.get(index+1).y - points.get(index-1).y;
	        float dz = points.get(index+1).z - points.get(index-1).z;
	        
	        return new Point(dx, dy, dz);
	    }
	}

	private int findPoint(Point p){
	    for (int i = 0; i < points.size(); i++) {
            if(points.get(i).equals(p)){
                return i;
            }
        }
	    System.out.println("Point didnt match any points");
	    return -1;
	}
	
	boolean isLastPoint(Point p) {
		return points.get(points.size()-1).equals(p);
	}
}
