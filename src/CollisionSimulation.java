import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;


public class CollisionSimulation implements Widget, MouseMotionListener {

	private PApplet g;

	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private ParticleGenerator generator;
	private Nub currentNub;

	private int width;
	private int height;

	private float t;

	private Curve curve;
	private boolean dragging = false;

	private float a = 0;

	CollisionSimulation(PApplet p, int ctrl_pnts) {

		g = p;
		width = g.width;
		height = g.height;
		curve = new Curve(0xFFFFFF00);
		Random r = new Random();
		for (int i = 0; i < ctrl_pnts; i++) {
		    Nub next = new Nub(r.nextInt(width/2) + width/4, r.nextInt(height/2) + height/4, 15);
		    curve.addControlPoint(next);
			particles.add(new Particle(curve, r.nextInt(width/2) + width/4, r.nextInt(height/2) + height/4, 0));
		}
		generator = new ParticleGenerator(curve.points.get(0), curve, 15);
	}

	void resampleCurrentCurve() {
        curve.points.clear(); //clear out old points so that resample will subdivide
        curve.points = curve.getNubPositions();
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

//		if (currentNub == null) GraphicsCollision.getInstance().rotate();

		if (!g.mousePressed) {
			currentNub = null;
			dragging = false;
		}
	}

	public void mouseClicked(float x, float y) {
        if (g.key == 'a') {
			Nub n = new Nub(Geometry3D.get3DPoint(x, y));
			curve.addControlPoint(n);
			return;
		}else if(g.key == 'd'){
            int closeLoc = getClosestNub(curve.controls, Geometry3D.get3DPoint(x, y));
	        curve.controls.remove(closeLoc); //remove closest point
            resampleCurrentCurve();
		}else if(g.key == 'm'){
            int closeLoc = getClosestNub(curve.controls, Geometry3D.get3DPoint(x, y));
            curve.controls.get(closeLoc).pos = Geometry3D.get3DPoint(x, y);
            resampleCurrentCurve();
		}
	}

	public void keyPressed(){
	    if (g.key == 's') {
            try {
                curve.writeCurve();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if (g.key == 'r') {
	        try {
	            curve.readCurve();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	}

	public void draw(PApplet c) {

		// get some lights up in here
		c.lights();

		curve.draw(c);
		for (Particle p: particles) {
			p.draw(c);
		}

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
		World w = new World(c, 300, 300, 359);
		w.draw(c, a);
        a+= 0.01;
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
