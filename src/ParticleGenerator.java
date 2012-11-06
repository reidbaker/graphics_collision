import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;


public class ParticleGenerator  implements Widget, MouseMotionListener  {
	
	private static final int MAX_PARTICLES = 5000;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private Curve curve;

	private Point pos;
	private float radius;
	
	private int t;
	private World w;
	
	public ParticleGenerator(Point p, Curve c, int r) {
		pos = p;
		radius = r;
		curve = c;
        w = new World((float)200, (float)200, (float)0);
        particles.add(w);
	}

	@Override
	public void draw(PApplet c) {
        w.draw(c);
		
		c.pushMatrix();
		c.translate(pos.x,  pos.y, pos.z);
		c.sphere(radius);
		c.popMatrix();
		
		for (int i = particles.size() - 1; i >= 0; i--) {
			
			Particle p = particles.get(i);
			
			Point closest = curve.getClosestPoint(p.pos);
		    
		    if (closest != null) {
		    	p.velocity = curve.getTangent(closest);
		    	p.velocity.normalize();
		    	p.velocity.mult(.2f*Point.dist(closest, p.pos));
		    	
		    	if (curve.isLastPoint(closest)) {
		    		particles.remove(p);
		    	}
		    	
		    	for (Particle q: particles) {
		    		if (!q.equals(p)) {
		    			float t = 1/GraphicsCollision.getInstance().frameRate;
		    			while (t > 0) {
		    				float m = Particle.collision(p,q); 
		    				if (m > 0) {
//		    					Particle.exchangeVelocities(p, q);
		    					t -= m; 
		    				} else {
		    					break;
		    				}
		    			}
		    		}
		    	}
		    }
		    
		    // update
		    p.pos.add(p.velocity);
		    p.draw(c);
		}
		
		if (++t >= 30 && particles.size() < MAX_PARTICLES) {
			Random r = new Random();
			particles.add(new Particle(curve, radius + pos.x + r.nextInt(10), radius + pos.x + pos.y + r.nextInt(10),  radius + pos.z + r.nextInt(10)));
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
