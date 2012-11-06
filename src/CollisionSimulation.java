import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;


public class CollisionSimulation implements Widget, MouseMotionListener {

	ArrayList<Particle> particles = new ArrayList<Particle>();
	ArrayList<Nub> control_points = new ArrayList<Nub>();
	Nub currentNub;

	int width;
	int height;
	PApplet g;

	float t;
	Shape shape;
	boolean wag;

	int[] stroke_colors = {
			0xff00ff00, 0xffff0000, 0xff00ffff, 0xffff00ff
	};

	Curve[] strokes = new Curve[4];
	Stroke one, two, three, four;
	int currentStroke;
	int keyframe;
	boolean dragging = false;




	CollisionSimulation(PApplet p, int ctrl_pnts, int keyframes) {

		g = p;
		width = g.width;
		height = g.height;

		Random r = new Random();
		for (int i = 0; i < 3; i++) {
			control_points.add(new Nub(r.nextInt(width/2) + width/4, r.nextInt(height/2) + height/4));
			particles.add(new Particle(r.nextInt(width/2) + width/4, r.nextInt(height/2) + height/4));
		}

		for (int i = 0; i < strokes.length; i++) {
			strokes[i] = new Curve(stroke_colors[i]);
		}

		currentStroke = 0;
	}

	void setCurrentStroke(int i) {
		currentStroke = i;
	}

	void smoothCurrentStroke() {
		for (Curve s: strokes) {
			if (s != null) s.smooth();
		}
	}

	void subdivideCurrentStroke() {
		for (Curve s: strokes) {
			if (s != null) s.subdivide();
		}
	}

	void resetCurrentStroke() {
		for (int i = 0; i < strokes.length; i++) {
			strokes[i] = new Curve(stroke_colors[i]);
		}
		currentStroke = 0;
		shape = null;
		wag = false;
	}

	void resampleCurrentStroke() {
		strokes[currentStroke].resample();
	}

	void nextStroke() {
		currentStroke = (currentStroke + 1) % 4;
	}

	public void mouseMoved(float x, float y) {
		if (g.mousePressed) {

			for (Nub k: control_points) {
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

			Curve s = strokes[currentStroke];
			dragging = true;
			//      if (s.size() == 0)  s.addPoint(new PointVector(x, y));
			//      if (s.size() > 0 && dist(s.points[s.length-1], new PointVector(x, y)) > 20)
				//        s.addPoint(new PointVector(x, y));
		}

		if (!g.mousePressed) {
			currentNub = null;
			Curve s = strokes[currentStroke];
			if (dragging) if (dragging && s.size() >= s.points.size()) currentStroke = (currentStroke + 1) % 4;
			dragging = false;
		}
	}

	public void mouseClicked(float x, float y) {
		if (g.key == 'g') {
			Nub n = new Nub(Geometry3D.get3DPoint(x, y));
			strokes[currentStroke].addControlPoint(n);
			control_points.add(n);
			return;
		}else if(g.key == 'd'){
	        int closeLoc = getClosestNub(control_points, Geometry3D.get3DPoint(x, y));
	        control_points.remove(closeLoc);
	        resampleCurrentStroke();
		}
	}

	public void draw(PApplet c) {

		// get some lights up in here
		c.lights();

		c.fill(stroke_colors[currentStroke]);
		c.textSize(13);
		c.textAlign(PApplet.CENTER);
		c.text("CLICK AND DRAG TO CREATE STROKE", c.width/2, c.height/2 + 10);
		if (currentStroke < 3) {
			c.text("PRESS SPACE FOR NEXT STROKE", c.width/2, c.height/2 + 30);
		}
		else {
			c.text("PRESS ENTER TO START ANIMATION", c.width/2, c.height/2 + 30);
		}

		for (Curve s: strokes) {
			s.draw(c);
			// s.drawTranslation(constraints[0].pos, constraints[1].pos);
		}

		for (Particle p: particles) {
			p.draw(c);
		}

        int closeLoc = getClosestNub(control_points, Geometry3D.get3DPoint(c.mouseX, c.mouseY));
        control_points.get(closeLoc).setCircleColor(c.color(250,0,0));
		for (Nub n: control_points) {
			if (n != null){
			    n.draw(c);
			}
		}
		//This is the default circle color
		control_points.get(closeLoc).setCircleColor(0xFF3C6BDE);
		t += .01;
		if (t >= 1) t = 0;
		keyframe++;
		if (keyframe >= strokes[3].size()) keyframe = 0;
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
