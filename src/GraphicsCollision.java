import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.opengl.*;                // load OpenGL libraries and utilities
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.nio.*;

public class GraphicsCollision extends PApplet {

    private PGraphicsOpenGL pgl;
    private GLU glu;
    private GL gl;
    //Colors
    int RED = color(250,0,0);
    int DRED = color(150,0,0);
    int MAGENTA = color(250,0,250);
    int DMAGENTA = color(150,0,150);
    int BLUE = color(0,0,250);
    int DBLUE = color(0,0,150);
    int CYAN = color(0,250,250);
    int DCYAN = color(0,150,150);
    int GREEN = color(0,250,0);
    int DGREEN = color(0,150,0);
    int YELLOW = color(250,250,0);
    int DYELLOW = color(150,150,0);
    int ORANGE = color(250,150,0);
    int DORANGE = color(150,50,0);
    int BROWN = color(150,150,0);
    int DBROWN = color(50,50,0);
    int WHITE = color(255,255,255);
    int BLACK = color(0,0,0);
    int GREY = color(100,100,100);
    int METAL = color(150,150,250);

    public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "GraphicsCollision" });
	}
    public void setColors() {
       }
    public void setup() {
	  frameRate(30);
	  size(800, 800, OPENGL);
	  sphereDetail(6);

	  // ***************** OpenGL and View setup
	  glu = ((PGraphicsOpenGL) g).glu;
	  pgl = (PGraphicsOpenGL) g;
	  gl = pgl.beginGL();
	  pgl.endGL();
	  //initView(); // declares the local frames for 3D GUI

	  // ***************** Load Curve
	  //C.loadPts();

	  // ***************** Set view
	}

	public void draw() {
	  pgl.beginDraw();
	  pgl.background(102);
	  pgl.stroke(255);
	  pgl.line(40, 40, 80, 80);
	  pgl.endDraw();
	}


	public void mouseReleased() {

	}

	public void drawAxes() {

	}

	public void drawData() {
		// Set colors and draw lines. Use a thicker stroke is possible
	}

}
