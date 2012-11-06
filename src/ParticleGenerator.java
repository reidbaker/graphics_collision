import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;


public class ParticleGenerator  implements Widget, MouseMotionListener  {
	
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private Curve curve;

	private Point pos;
	private float radius;
	private int particlesPerSecond;
	
	private int t;
	
	
	public ParticleGenerator(Point p, Curve c, int r) {
		pos = p;
		radius = r;
		curve = c;
	}

	@Override
	public void draw(PApplet c) {
		
		c.pushMatrix();
		c.translate(pos.x,  pos.y, pos.z);
		c.sphere(radius);
		c.popMatrix();
		
		for (Particle p: particles) {
			p.draw(c);
		}
		
		if (++t >= 300) {
			Random r = new Random();
			particles.add(new Particle(curve, r.nextInt(50) + pos.x, r.nextInt(50) + pos.y,  r.nextInt(50) + pos.z));
		}
		
	}

	@Override
	public boolean over(float x, float y) {
		return false;
	}

	@Override
	public void mouseMoved(float x, float y) {
		
	}

	@Override
	public void mouseClicked(float x, float y) {
		
	}

}
