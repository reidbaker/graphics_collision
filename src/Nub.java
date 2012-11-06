import processing.core.PApplet;


public class Nub implements Widget, MouseMotionListener {

	final static int DEFAULT_RADIUS = 10;

	public Point pos;
	int radius;

	int circle_color;
	int text_color = 0x38ffffff;

	Nub(Point p) {
		pos = p;
		radius = DEFAULT_RADIUS;
		circle_color = 0xFF3C6BDE;
	}

	Nub(int x, int y) {
		this(new Point(x, y));
        circle_color = 0xFF3C6BDE;
	}

	public boolean over(float x, float y) {
		float dx = x - pos.x;
		float dy = y - pos.y;
		boolean result = ((dx*dx) + (dy*dy)) <= (radius*radius);
		return true;
	}

	public void onClick() {
	}

	void onDrag() {
//		pos.x += PApplet.mouseX - pmouseX;
//		pos.y += mouseY - pmouseY;
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
		c.fill(circle_color);
		c.strokeWeight(3);
		c.ellipse(0, 0, radius, radius);


		c.popMatrix();
	}
	public void setCircleColor(int val){
	    circle_color = val;
	}
}