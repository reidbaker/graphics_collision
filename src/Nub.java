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
    Nub(String s) {
        //s.replace('(', ' ').replace(')', ' ').replace(',', ' ')
        int fComma = s.indexOf(',');
        int sComma = s.indexOf(',', fComma+1);
        float x = Float.parseFloat(s.substring(s.indexOf('(')+1,fComma));
        float y = Float.parseFloat(s.substring(fComma+1,sComma));
        float z = Float.parseFloat(s.substring(sComma+1, s.indexOf(')')));
        pos = new Point(x,y,z);
        radius = DEFAULT_RADIUS;
        circle_color = 0xFF3C6BDE;
    }

	Nub(float x, float y, float z) {
		this(new Point(x, y, z));
        circle_color = 0xFF3C6BDE;
	}

	public boolean over(float x, float y) {
		return true;
	}

	public void onClick() {
	}

	public void mouseMoved(float x, float y) {
		Point p = Geometry3D.get3DPoint(x, y);
		pos.x = p.x;    
		pos.y = p.y;
		pos.z = p.z;
	}

	public void mouseClicked(float x, float y) {

	}

	public void draw(PApplet c) {

		c.pushMatrix();
		c.translate(pos.x, pos.y, pos.z);

		c.noStroke();
		c.fill(circle_color);
		c.strokeWeight(3);
		c.sphere(radius);

		c.popMatrix();
	}
	
	public void setCircleColor(int val){
	    circle_color = val;
	}
}