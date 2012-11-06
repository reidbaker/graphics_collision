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
	public final int BG_COLOR = color(48, 52, 88);

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "GraphicsCollision" });
	}
	
	public void setColors() {
	}

	boolean isSetup;
	public void setup() {

		if (!isSetup) {
			size(900, 800, OPENGL);
			frameRate(30);
			sphereDetail(6);

			glu = ((PGraphicsOpenGL) g).glu;
			pgl = (PGraphicsOpenGL) g;
			gl = pgl.beginGL();
			pgl.endGL();

			isSetup = true;
			
			
			// load the fonts for our interface
//			interfaceFont = loadFont("Roboto-Bold-13.vlw");
//			textFont(interfaceFont, 13);
//
//			Window mainWindow = new Window("SIMULATION", 10, 40, 600, 400);
//			strokeSim = new CollisionSimulation(mainWindow, 7, 7);
//
//			widgets.add(mainWindow);
//			widgets.add(new PictureWindow("profile.jpg", 630, 40));
//
//
//			Button smoothen = new Button(630, 200, 120, 40, "SMOOTH (S)");
//			smoothen.addOnClickListener(new WidgetListener() {
//				public void action() {
//					strokeSim.smoothCurrentStroke();
//				}
//			}
//					);
//
//			Button subdivide = new Button(760, 200, 120, 40, "SUBDIVIDE (D)");
//			subdivide.addOnClickListener(new WidgetListener() {
//				public void action() {
//					strokeSim.subdivideCurrentStroke();
//				}
//			}
//					);
//
//
//			Button resetCurve = new Button(630, 350, 250, 40, "RESET STROKES (R)");
//			resetCurve.addOnClickListener(new WidgetListener() {
//				public void action() {
//					strokeSim.resetCurrentStroke();
//				}
//			});
//
//
//
//			widgets.add(smoothen);
//			widgets.add(subdivide);
//			widgets.add(resetCurve);
//
//			// set the framerate to be convenient
//			frameRate(32);
//
//			PointVector p = new PointVector(31,4);
//			println(vSub(p,new PointVector(4,3)));
//			println(PVector.sub(new PVector(31,4),new PVector(4,3)));
		}
	}

	public void draw() {
		line(40,40,mouseX,mouseY);
		drawBackground();
//
//		for (Widget widget: widgets) {
//			widget.draw();
//		}
//
//		drawTopBar();
//
//
//		if (DEBUG) {
//			text(String.format("%d, %d", mouseX, mouseY), width - 50, height - 50);
//		}
	}



	/**
	 * Draws the top information panel.
	 */
	void drawTopBar() {
//		noStroke();
//		fill(#131D45);
//		rect(0, 0, width, 30);
//		textAlign(LEFT, TOP);
//		textSize(13);
//		fill(255,200);
//		text(TITLE, 10, 5);
		// text("MATRIX SIZE: " + size, width/1.18, 10);
	}

	/*
	 * Draws the background grid.
	 */
	void drawBackground() {
		background(BG_COLOR);
		stroke(42,58,146,40);
		strokeWeight(2);

		int gap = 20;
		int x = 0;
		int y = 0;

		while (x < width) {
			line(x, 0, x, height);
			x += gap;
		}
		while (y< height) {
			line(0, y, width, y);
			y+= gap;
		}
	}


	public void mouseReleased(){

	}

	public void drawAxes(){

	}

	public void drawData(){
		// Set colors and draw lines. Use a thicker stroke is possible
	}

	// ===== Mouse Tools from jarak's PV file
	public Point Mouse(){
		// current mouse location
		return Point.P(mouseX,mouseY,0);
	}
	public Point Pmouse(){
		return Point.P(pmouseX,pmouseY,0);
	}
	/*public Vec MouseDrag(){
        // vector representing recent mouse displacement
	    return Vec.V((float)mouseX-pmouseX,(float)mouseY-pmouseY,0f);
	}*/
	

}
