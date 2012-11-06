import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;


public class CollisionSimulation implements Widget, MouseMotionListener {

	private PApplet g;

	private ParticleGenerator generator;
	private Nub currentNub;

	private int width;
	private int height;

	private float t;

	private Curve curve;
	private boolean dragging = false;

	CollisionSimulation(PApplet p, int ctrl_pnts) {

		g = p;
		width = g.width;
		height = g.height;
		curve = new Curve(0xFFFFFF00);
		Random r = new Random();
		for (int i = 0; i < ctrl_pnts; i++) {
			Nub next = new Nub(r.nextInt(width/2) + width/4, r.nextInt(height/2) + height/4, r.nextInt(300));
			curve.addControlPoint(next);
		}
		generator = new ParticleGenerator(curve.points.get(0), curve, 30);
	}

	void resampleCurrentCurve() {
		curve.points.clear(); //clear out old points so that resample will subdivide
		curve.points = curve.getNubPositions();
		curve.resample();
	}

	public void mouseMoved(float x, float y) {
		if (g.mousePressed) {

			if (!dragging) currentNub = curve.getClosestControlPoint(Geometry3D.get3DPoint(x, y), Integer.MAX_VALUE);

			if (currentNub != null && g.key == 'm') {
				currentNub.mouseMoved(x, y);
				resampleCurrentCurve();
				dragging = true;
				return;
			}

		}
		
		if (!g.mousePressed) {
			currentNub = null;
			dragging = false;
		}
	}

		public void mouseClicked(float x, float y) {
			if (g.key == 'g') {
				Nub n = new Nub(Geometry3D.get3DPoint(x,y));
				curve.addControlPoint(n);
				return;
			} else if(g.key == 'd'){
				int closeLoc = getClosestNub(curve.controls, Geometry3D.get3DPoint(x,y));
				curve.controls.remove(closeLoc); //remove closest point
				resampleCurrentCurve();
			} 
		}

		public void draw(PApplet c) {

			// get some lights up in here
			c.lights();

			curve.draw(c);
			generator.draw(c);

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
