import processing.core.PApplet;

public class Particle implements Widget, MouseMotionListener {

  final static int DEFAULT_RADIUS = 5;

  private Curve curve;
  Point pos;
  private int radius;
  Point velocity;

  int sphere_color = 0xff555555;

  Particle(Curve c, Point p) {
	curve = c;
    pos = p;
    radius = DEFAULT_RADIUS;
    velocity = new Point();
  }

  Particle(Curve curve, float x, float y, float z) {
    this(curve, new Point(x, y, z));
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
  
//  public float collision(BALL A, BALL B) {   // computes collision time t and returns it if 0<t<1. Otherwise, returns 2
//	  vec W=M(-1,A.V,1,B.V); vec D=V(A.C,B.C); float a=dot(W,W); float b=2*dot(D,W); float c=dot(D,D)-sq(A.r+B.r); // coeffs of quadratic equation
//	  float d=sq(b)-4*a*c; // discriminant of quadratic equation
//	  if (d>=0) {float t1=(-b-sqrt(d))/2/a; if(t1<0) t1=2; float t2=(-b+sqrt(d))/2/a; if(t2<0) t2=2; float m=min(t1,t2); 
//	              if ((-0.02<=m)&&(m<=1.02)) return m; } //* 
//	  return 2; }
  
  public boolean collides(Particle p) {
	  return false;
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