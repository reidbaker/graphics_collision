import processing.core.PApplet;

public class Particle implements Widget, MouseMotionListener {

	final static int DEFAULT_RADIUS = 25;

	private Curve curve;
	Point pos;
	private int radius;
	Point velocity;
	float mass;
	static double G = 6.7e-11;
	int sphere_color = 0xff00ff00;

	Particle(Curve c, Point p, float mass) {
		curve = c;
		pos = p;
		radius = DEFAULT_RADIUS;
		velocity = new Point();
		this.mass = mass;
	}

    Particle(Curve curve, float x, float y, float z) {
        this(curve, new Point(x, y, z), 1);
    }
    Particle(Curve curve, float x, float y, float z, float mass) {
        this(curve, new Point(x, y, z), mass);
    }

	public boolean over(float x, float y) {
		float dx = x - pos.x;
		float dy = y - pos.y;
		return dx*dx + dy*dy <= radius*radius;
	}

	public void mouseMoved(float x, float y) {
		pos.x = x;
		pos.y = y;
	}

	public void mouseClicked(float x, float y) {

	}
	public static Point force(Particle p, Particle q){
	    float dist = Point.dist(p.pos, q.pos);
	    float f = (float) ((-G * p.mass * q.mass)/(Math.pow(dist,2)));
	    Point out = Point.mult(Point.normalize(Point.sub(q.pos, p.pos)), f);
        return out;
	}

	public static float collision(Particle p, Particle q) {   // computes collision time t and returns it if 0<t<1. Otherwise, returns 2
		Point W = Point.add(Point.mult(p.pos, -1),Point.mult(q.velocity, 1));
		Point D = Point.sub(p.pos,q.pos);
		float a = Point.dot(W,W);
		float b = 2*Point.dot(D,W);
		float c = (float) (Point.dot(D,D)-Math.pow(p.radius+q.radius,2)); // coeffs of quadratic equation

		float d = (float) (Math.pow(b,2)-4*a*c); // discriminant of quadratic equation

		if (d>=0) {
			float t1=(float) ((-b-Math.sqrt(d))/2/a);
			if(t1<0) t1=2;
			float t2=(float) ((-b+Math.sqrt(d))/2/a);
			if(t2<0) t2=2;
			float m=Math.min(t1,t2);
			if ((-0.02<=m)&&(m<=1.02)) return m;
		} //*
		return -1;
	}

	public static void exchangeVelocities(Particle p, Particle q) {
		Point v = p.velocity;
		Point u = q.velocity;
		Point n = Point.sub(v, u);
		n.normalize();

		u.sub(Point.mult(new Point(1,1,1), Point.dot(u, n) + Point.dot(v, n)));
		v.sub(Point.mult(new Point(1,1,1), Point.dot(u, n) + Point.dot(v, n)));
	}

	public void draw(PApplet c) {
		c.pushMatrix();
		c.translate(pos.x, pos.y, pos.z);
		c.noStroke();
		c.fill(sphere_color);
		c.strokeWeight(3);
		c.sphere(radius);
		c.popMatrix();

		Point closest = curve.getClosestPoint(pos);

		if (closest != null) {
			c.stroke(sphere_color);
			c.line(pos.x,pos.y,pos.z,closest.x,closest.y,closest.z);
		}

		// update
		pos.add(velocity);
	}
}