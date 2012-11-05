import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.*;                // load OpenGL libraries and utilities
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.nio.*;

public class GraphicsCollision extends PApplet {

    private PGraphics pg;

    public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "GraphicsCollision" });
	}

    public void setup() {
	  size(800, 600);
	  frameRate(30);
	  pg = createGraphics(800, 600, OPENGL);
	}

	public void draw() {
	  pg.beginDraw();
	  pg.background(102);
	  pg.stroke(255);
	  pg.line(40, 40, 80, 80);
	  pg.endDraw();
	}


	public void mouseReleased() {

	}

	public void drawAxes() {

	}

	public void drawData() {
		// Set colors and draw lines. Use a thicker stroke is possible
	}

}
