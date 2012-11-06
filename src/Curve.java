import java.util.ArrayList;

import processing.core.PApplet;

class Curve {

	final int MAX_PointVectorS = 5000; // max number of PointVectors in the stroke
	ArrayList<Nub> controls = new ArrayList<Nub>();
	ArrayList<PointVector> points = new ArrayList<PointVector>();

	int stroke_color;
	int PointVector_color;
	int PointVector_size = 3;

	int transformed_stroke_color = 0xffff00;

	int traveler = 0;
	boolean animate = false;

	Curve(int colour) {
		stroke_color = PointVector_color = colour;
	}

	Curve() {
		this(0xffffff);
	}

	Curve(int colour, ArrayList<PointVector> PointVectors) {
		this(colour);
		this.points = PointVectors;
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
			subdivide2();
		}

		//    ArrayList<PointVector> newPointVectors = new ArrayList<PointVector>();
		//    int k = 0;
		//    for (int i = 0; i < size; i++) {
		//      newPointVectors.add(PointVectors.get(i * (PointVectors.size()/size)));
		//    } 

		// PointVectors = newPointVectors;;
	}

	private void smooth(float s) {
		PointVector[] avg = new PointVector[points.size()];
		//    LinkedList<PointVector> avg = new LinkedList<PointVector>();

		for (int i = 1; i < points.size()-1; i++) {
			PointVector mid = PointVector.midPoint(points.get(i-1), points.get(i+1));
			avg[i] = (PointVector.addScaledVector(points.get(i), PointVector.sub(mid, points.get(i)), s));
		}

		for (int i = 1; i < points.size()-1; i++) {
			points.set(i, avg[i]);
		}
	}

	void draw(PApplet c) {
		drawPointVectors(c);
		// showNextSubDiv();
		// drawTranslation(strokeSim.constraints[0].pos, strokeSim.constraints[1].pos);
		if (animate) {
			PointVector p = points.get(traveler);
			if (p != null) {
				c.fill(255); 
				c.noStroke();
				c.ellipse(p.x, p.y, 15, 15);
				traveler++;
				if (traveler >= points.size()) traveler = 0;
			}

			if (traveler > 0 && traveler < points.size() - 1) {
				PointVector a = PointVector.addScaledVector(p, PointVector.add(PointVector.sub(points.get(traveler-1), p), PointVector.sub(points.get(traveler+1), p)), 2);
				c.fill(0xffff0000);
				c.ellipse(a.x, a.y, 15, 15);
			}
		}
	}

	void drawPointVectors(PApplet c) {
		c.beginShape();

		c.stroke(stroke_color);
		c.fill(PointVector_color);

		for (int i = 0; i < points.size()-1; i++) {
			PointVector p1 = points.get(i);
			PointVector p2 = points.get(i+1);
			c.ellipse(p1.x, p1.y, PointVector_size, PointVector_size);
			c.line(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
		}
	}

	PointVector getLocalFromEdge(PointVector p, PointVector a, PointVector b) {
		PointVector i = PointVector.sub(b, a);
		PointVector j = PointVector.rotateVector90(i); // may not need to add
		return globalToBary(p, i, j, a);
	}

	PointVector globalToBary(PointVector p, PointVector i, PointVector j, PointVector o) {
		PointVector op = PointVector.sub(p, o);
		float x = PointVector.cross2D(op, j)/PointVector.cross2D(i, j);
		float y = PointVector.cross2D(op, i)/PointVector.cross2D(j, i);
		return new PointVector(x, y);
	}

	PointVector baryToGlobal(PointVector p, PointVector i, PointVector j, PointVector o) {
		return PointVector.add(o, PointVector.mult(i, p.x), PointVector.mult(j, p.y));
	}

	void subdivide2() {
		if (points.size()*2 >= MAX_PointVectorS) {
			System.out.println("Cannot subdivide; curve over max PointVectors");
			return;
		} 
		PointVector[] newPointVectors = new PointVector[points.size()];
		int pos = 0;

		int length = this.points.size();
		if (points.size() > 2) {
			PointVector[] PointVectors = this.points.toArray(new PointVector[0]);
			newPointVectors[pos++] = subdivide(PointVectors[0], PointVectors[1], PointVectors[2]);

			for (int i = 0; i < length - 3; i++) {
				newPointVectors[pos++] = subdivide(PointVectors[i], PointVectors[i+1], PointVectors[i+2], PointVectors[i+3]);
			}
			newPointVectors[pos++] = subdivide(PointVectors[length-1], PointVectors[length-2], PointVectors[length-3]);
		}

		ArrayList<PointVector> PointVectors2 = new ArrayList<PointVector>();
		int k = 0;
		for (int i = 0; i < length; i++) {
			PointVectors2.add(points.get(i));
			if (i < pos) PointVectors2.add(newPointVectors[i]);
		}

		points = PointVectors2;
	} 

	void showNextSubDiv(PApplet c) {

		if (points.size() < 2) return;    
		c.stroke(0);


		c.translate(0, -30);
		c.beginShape();
		int length = points.size();
		PointVector[] PointVectors = this.points.toArray(new PointVector[0]);
		if (length > 2) {
			PointVector p;
			p = subdivide(PointVectors[0], PointVectors[1], PointVectors[2]);
			c.vertex(p.x, p.y, p.z);
			c.ellipse(p.x, p.y, 5, 5);
			for (int i = 0; i < length - 3; i++) {
				p = subdivide(PointVectors[i], PointVectors[i+1], PointVectors[i+2], PointVectors[i+3]);
				c.vertex(p.x, p.y, p.z);
				c.ellipse(p.x, p.y, 5, 5);
			}
			p = subdivide(PointVectors[length-1], PointVectors[length-2], PointVectors[length-3]);
			c.vertex(p.x, p.y, p.z);
			c.ellipse(p.x, p.y, 5, 5);
		}
		c.endShape();
		c.translate(0, 30);
	}

	PointVector subdivide(PointVector ... p) {
		PointVector newPointVector;
		if (p.length == 3) {
			newPointVector = PointVector.neville(.5f, p[0], p[1], p[2]);
		} 
		else {
			newPointVector = PointVector.midPoint(PointVector.neville(1, p[0], p[1], p[2]), PointVector.neville(1, p[3], p[2], p[1]));
		}
		return newPointVector;
	}

	void addPointVector(PointVector pt) {
		if (points.size() < MAX_PointVectorS) {
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
		if (controls.size() > 3) {
		    Point p = Point.subdivision(points.get(0), points.get(1), points.get(2), points.get(3));
		    points.add(new PointVector(p.x, p.y, p.z));
		}
	}

	PointVector getClosestPointVector(PointVector p) {
		PointVector closest = null;
		float threshold = 200;
		for (PointVector PointVector: points) {
			float dist = PointVector.dist(p, PointVector);
			if ( dist < threshold) {
				threshold = dist;
				closest = PointVector;
			}
		}
		return closest;
	}
}
