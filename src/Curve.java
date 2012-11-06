import java.util.ArrayList;

import processing.core.PApplet;

class Curve {

	final int MAX_POINTS = 5000; // max number of Points in the stroke
	ArrayList<Nub> controls = new ArrayList<Nub>();
	ArrayList<Point> points = new ArrayList<Point>();

	int stroke_color;
	int Point_color;
	int Point_size = 3;

	int transformed_stroke_color = 0xffff00;

	int traveler = 0;
	boolean animate = false;

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

		//    ArrayList<Point> newPoints = new ArrayList<Point>();
		//    int k = 0;
		//    for (int i = 0; i < size; i++) {
		//      newPoints.add(Points.get(i * (Points.size()/size)));
		//    } 

		// Points = newPoints;;
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
		// showNextSubDiv();
		// drawTranslation(strokeSim.constraints[0].pos, strokeSim.constraints[1].pos);
		if (animate) {
			Point p = points.get(traveler);
			if (p != null) {
				c.fill(255); 
				c.noStroke();
				c.ellipse(p.x, p.y, 15, 15);
				traveler++;
				if (traveler >= points.size()) traveler = 0;
			}

			if (traveler > 0 && traveler < points.size() - 1) {
				Point a = Point.addScaledVector(p, Point.add(Point.sub(points.get(traveler-1), p), Point.sub(points.get(traveler+1), p)), 2);
				c.fill(0xffff0000);
				c.ellipse(a.x, a.y, 15, 15);
			}
		}
	}

	void drawPoints(PApplet c) {
		c.beginShape();

		c.stroke(stroke_color);
		c.fill(Point_color);

		for (int i = 0; i < points.size()-1; i++) {
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			c.ellipse(p1.x, p1.y, Point_size, Point_size);
			c.line(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
		}
	}

	Point getLocalFromEdge(Point p, Point a, Point b) {
		Point i = Point.sub(b, a);
		Point j = Point.rotateVector90(i); // may not need to add
		return globalToBary(p, i, j, a);
	}

	Point globalToBary(Point p, Point i, Point j, Point o) {
		Point op = Point.sub(p, o);
		float x = Point.cross2D(op, j)/Point.cross2D(i, j);
		float y = Point.cross2D(op, i)/Point.cross2D(j, i);
		return new Point(x, y);
	}

	Point baryToGlobal(Point p, Point i, Point j, Point o) {
		return Point.add(o, Point.mult(i, p.x), Point.mult(j, p.y));
	}

	public void subdivide() {
		points = Point.subdivide(points);
	}

	void showNextSubDiv(PApplet c) {

		if (points.size() < 2) return;    
		c.stroke(0);


		c.translate(0, -30);
		c.beginShape();
		int length = points.size();
		Point[] Points = this.points.toArray(new Point[0]);
		if (length > 3) {
			Point p;
			p = subdivide(Points[0], Points[1], Points[2], Points[3]);
			c.vertex(p.x, p.y, p.z);
			c.ellipse(p.x, p.y, 5, 5);
			for (int i = 0; i < length - 3; i++) {
				p = subdivide(Points[i], Points[i+1], Points[i+2], Points[i+3]);
				c.vertex(p.x, p.y, p.z);
				c.ellipse(p.x, p.y, 5, 5);
			}
			p = subdivide(Points[length-1], Points[length-2], Points[length-3], Points[length - 4]);
			c.vertex(p.x, p.y, p.z);
			c.ellipse(p.x, p.y, 5, 5);
		}
		c.endShape();
		c.translate(0, 30);
	}

	Point subdivide(Point ... p) {
		Point newPoint;
		if (p.length == 3) {
			newPoint = Point.neville(.5f, p[0], p[1], p[2]);
		} 
		else {
			newPoint = Point.midPoint(Point.neville(1, p[0], p[1], p[2]), Point.neville(1, p[3], p[2], p[1]));
		}
		return newPoint;
	}

	void addPoint(Point pt) {
		if (points.size() < MAX_POINTS) {
			points.add(pt);
		}
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

	Point getClosestPoint(Point p) {
		Point closest = null;
		float threshold = 200;
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
