import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;


public class CollisionSimulation implements Widget, MouseMotionListener {

	ArrayList<Particle> particles = new ArrayList<Particle>();
//	ArrayList<Nub> control_points = new ArrayList<Nub>();
	Nub currentNub;

	int width;
	int height;
	PApplet g;

	float t;
	Shape shape;

	Curve curve;
	Stroke one, two, three, four;
	int keyframe;
	boolean dragging = false;

	CollisionSimulation(PApplet p, int ctrl_pnts, int keyframes) {

		g = p;
		width = g.width;
		height = g.height;

		curve = new Curve(0xFFFF0000);

		Random r = new Random();
		for (int i = 0; i < 3; i++) {
			// control_points.add(new Nub(r.nextInt(width/2) + width/4, r.nextInt(height/2) + height/4));
			curve.addControlPoint(new Nub(r.nextInt(width/2) + width/4, r.nextInt(height/2) + height/4));
			particles.add(new Particle(curve, r.nextInt(width/2) + width/4, r.nextInt(height/2) + height/4, 0));
		}


	}




	void resampleCurrentStroke() {
		curve.resample();
	}


	public void mouseMoved(float x, float y) {
		if (g.mousePressed) {

			for (Nub k: curve.controls) {
				if (k != null && k.over(x, y)) {
					if (currentNub == null) {
						currentNub = k;
					}
				}
			}

			if (currentNub != null) {
				if (x > 20 && x < width-20 && y > 20 && y < height - 20) {
					currentNub.mouseMoved(x, y);
				}
				return;
			}

			dragging = true;
		}

		if (!g.mousePressed) {
			currentNub = null;
			dragging = false;
		}
	}

	public void mouseClicked(float x, float y) {
		if (g.key == 'g') {
			Nub n = new Nub(Geometry3D.get3DPoint(x, y));
			curve.addControlPoint(n);
			return;
		}else if(g.key == 'd'){
	        int closeLoc = getClosestNub(curve.controls, Geometry3D.get3DPoint(x, y));
	        curve.controls.remove(closeLoc); //remove closest point
	        curve.points.clear(); //clear out old points so that resample will subdivide
	        curve.points = curve.getNubPositions();
	        resampleCurrentStroke();
		}
	}

	public void draw(PApplet c) {

		// get some lights up in here
		c.lights();

		c.fill(curve.stroke_color);
		c.textSize(13);
		c.textAlign(PApplet.CENTER);

		curve.draw(c);

		for (Particle p: particles) {
			p.draw(c);
		}

        int closeLoc = getClosestNub(curve.controls, Geometry3D.get3DPoint(c.mouseX, c.mouseY));
        curve.controls.get(closeLoc).setCircleColor(c.color(250,0,0));
		for (Nub n: curve.controls) {
			if (n != null){
			    n.draw(c);
			}
		}

		//This is the default circle color
		curve.controls.get(closeLoc).setCircleColor(0xFF3C6BDE);
//		t += .01;
//		if (t >= 1) t = 0;
//		keyframe++;
//		if (keyframe >= strokes[3].size()) keyframe = 0;
	}

	public int getClosestNub(ArrayList<Nub> nubs, Point p){
	    float lastDist;
	    float minDist = Integer.MAX_VALUE;
	    int minIndex = -1;
	    for(int i = 0; i < nubs.size(); i++) {
	        lastDist = Point.dist(p, nubs.get(i).pos);
	        if(lastDist < minDist){
	            minDist = lastDist;
	            minIndex = i;
	        }
        }
	    return minIndex;
	}

	@Override
	public boolean over(float x, float y) {
		return true;
	}
}
