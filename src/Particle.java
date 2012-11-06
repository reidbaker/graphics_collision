import processing.core.PApplet;

public class Particle implements Widget, MouseMotionListener {

  final static int DEFAULT_RADIUS = 5;

  Point pos;
  int radius;

  int sphere_color = 0xff55ff55;
  int text_color = 0x38ffffff;

  Particle(Point p) {
    pos = p;
    radius = DEFAULT_RADIUS;
  }

  Particle(int x, int y) {
    this(new Point(x, y));
  }

  public boolean over(float x, float y) {
    float dx = x - pos.x;
    float dy = y - pos.y;
    return dx*dx + dy*dy <= radius*radius;
  }

  public void onClick() {
  }

  void onDrag() {
//    pos.x += mouseX - pmouseX;
//    pos.y += mouseY - pmouseY;
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
  }
}