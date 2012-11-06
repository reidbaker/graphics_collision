import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import processing.core.PApplet;
import processing.opengl.PGraphicsOpenGL;
// load OpenGL libraries and utilities

public class GraphicsCollision extends PApplet {

	private PGraphicsOpenGL pgl;
	private GLU glu;
	private GL gl;

	//Colors
	public final int RED = color(250,0,0);
	public final int DRED = color(150,0,0);
	public final int MAGENTA = color(250,0,250);
	public final int DMAGENTA = color(150,0,150);
	public final int BLUE = color(0,0,250);
	public final int DBLUE = color(0,0,150);
	public final int CYAN = color(0,250,250);
	public final int DCYAN = color(0,150,150);
	public final int GREEN = color(0,250,0);
	public final int DGREEN = color(0,150,0);
	public final int YELLOW = color(250,250,0);
	public final int DYELLOW = color(150,150,0);
	public final int ORANGE = color(250,150,0);
	public final int DORANGE = color(150,50,0);
	public final int BROWN = color(150,150,0);
	public final int DBROWN = color(50,50,0);
	public final int WHITE = color(255,255,255);
	public final int BLACK = color(0,0,0);
	public final int GREY = color(100,100,100);
	public final int METAL = color(150,150,250);

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "GraphicsCollision" });
	}
	public void setColors() {
	}

	boolean isSetup;
	public void setup() {

		if (!isSetup) {
			size(800, 800, OPENGL);
			frameRate(30);
			sphereDetail(6);

			glu = ((PGraphicsOpenGL) g).glu;
			pgl = (PGraphicsOpenGL) g;
			gl = pgl.beginGL();
			pgl.endGL();

			isSetup = true;
		}
	}

	public void draw(){
		//pgl.beginDraw();
		//pgl.background(102);
		//pgl.stroke(255);
		//pgl.line(40, 40, mouseX, mouseY);
		line(40,40,mouseX,mouseY);
		// pgl.endDraw();
	}


	public void mouseReleased(){

	}

	public void drawAxes(){

	}

	public void drawData(){
		// Set colors and draw lines. Use a thicker stroke is possible
	}

	// ===== Mouse Tools from jarak's PV file
	public Pt Mouse(){
		// current mouse location
		return Pt.P(mouseX,mouseY,0);
	}
	public Pt Pmouse(){
		return Pt.P(pmouseX,pmouseY,0);
	}
	/*public Vec MouseDrag(){
        // vector representing recent mouse displacement
	    return Vec.V((float)mouseX-pmouseX,(float)mouseY-pmouseY,0f);
	}*/

}
