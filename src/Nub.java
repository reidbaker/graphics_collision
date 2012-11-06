import processing.core.PApplet;


public class Nub implements Widget, MouseMotionListener {

	final static int DEFAULT_RADIUS = 10;

	PointVector pos;
	int radius;

	int circle_color = 0xFF3C6BDE;
	int text_color = 0x38ffffff;

	Nub(PointVector p) {
		pos = p;
		radius = DEFAULT_RADIUS;
	}

	Nub(int x, int y) {
		this(new PointVector(x, y));
	}

	public boolean over(float x, float y) {
		float dx = x - pos.x;
		float dy = y - pos.y;
		return dx*dx + dy*dy <= radius*radius;
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
}