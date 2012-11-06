import java.util.ArrayList;

import processing.core.PApplet;

class Curve {

	ArrayList<Nub> controls = new ArrayList<Nub>();
	ArrayList<Point> points = new ArrayList<Point>();

	int stroke_color;
	int Point_color;
	int Point_size = 3;

	Curve(int colour) {
		stroke_color = Point_color = colour;
	}

	Curve() {
		this(0xffffff);
	}

	Curve(int colour, ArrayList<Point> points) {
		this(colour);
		this.points = points;
	}

	void smooth() {
		smooth(.5f);
		smooth(-.5f);
	}

	int size() {
		return points.size();
	}

	void resample() {

		int size = controls.size()*16;
		if (points.size() < 1) return;

		while (points.size () < size) {
			subdivide();
		}
	}

	private void smooth(float s) {
		Point[] avg = new Point[points.size()];
		//    LinkedList<Point> avg = new LinkedList<Point>();

		for (int i = 1; i < points.size()-1; i++) {
			Point mid = Point.midPoint(points.get(i-1), points.get(i+1));
			avg[i] = (Point.addScaledVector(points.get(i), Point.sub(mid, points.get(i)), s));
		}

		for (int i = 1; i < points.size()-1; i++) {
			points.set(i, avg[i]);
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
}
