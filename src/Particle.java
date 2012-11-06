import processing.core.PApplet;

public class Particle implements Widget, MouseMotionListener {

  final static int DEFAULT_RADIUS = 5;

  Curve curve;
  
  Point pos;
  int radius;

  int sphere_color = 0xff555555;

  Particle(Curve c, Point p) {
	curve = c;
    pos = p;
    radius = DEFAULT_RADIUS;
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
  }
}