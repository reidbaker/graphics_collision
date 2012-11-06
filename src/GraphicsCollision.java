import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import processing.core.PApplet;
import processing.opengl.PGraphicsOpenGL;
// load OpenGL libraries and utilities

public class GraphicsCollision extends PApplet {

	private static GraphicsCollision instance;
	public PGraphicsOpenGL pgl;
	public GLU glu;
	public GL gl;

	private Point eye;
	private Point focus;
	private Point up;
	private Point Q, I, J, K;

	float rx, ry, rz;
	//	private Vector I = new Vector(1,0,0), J = new Vector(0,1,0), K = new Vector(0,0,1); // picked surface point Q and screen aligned vectors {I,J,K} set when picked

	final String TITLE = "COLLISION SIMULATOR";

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
	private ArrayList<Widget> widgets = new ArrayList<Widget>();
	private CollisionSimulation simulation;
	public void setup() {

		instance = this;

		if (!isSetup) {
			size(900, 800, OPENGL);
			frameRate(30);
			sphereDetail(6);

			glu = ((PGraphicsOpenGL) g).glu;
			pgl = (PGraphicsOpenGL) g;
			gl = pgl.beginGL();
			pgl.endGL();

			initializeViews();

			// load the fonts for our interface
			//			interfaceFont = loadFont("Roboto-Bold-13.vlw");
			//			textFont(interfaceFont, 13);
			simulation = new CollisionSimulation(this, 7);
			widgets.add(simulation);
			isSetup = true;
		}
	}

	private void initializeViews() {
		focus  = new Point(0,0,0); 
		eye = new Point(0,0,500); 
		up = new Point(0,1,0);
		Q = new Point();
		I = new Point(1,0,0);
		J = new Point(0,1,0);
		K = new Point(0,0,1);
	}

	float i;
	public void draw() {
		camera(); // reset to 2d;
		//drawBackground();
		background(0xFFFFFFFF);
		
		drawTopBar();

		// Geometry3D.setFrame(Q, I, J, K);
		camera(eye.x, eye.y, eye.z, focus.x, focus.y, focus.z, up.x, up.y, up.z); // defines the view : eye, ctr, up

		//		camera(eye.x, eye.y, eye.z, focus.x, focus.y, focus.z, up.x, up.y, up.z); // defines the view : eye, ctr, up
		//		changeViewAndFrame();
		//translate(-width/2, -height/2);

		for (Widget widget: widgets) {
			widget.draw(this);
		}

	}

	float d=300, b=0, a=0; // view parameters: distance, angles, q

	void  changeViewAndFrame() {
		float ca=cos(a), sa=sin(a), cb=cos(b), sb=sin(b); // viewing direction angles
		if (keyPressed&&key=='z') d-=mouseY-pmouseY;  // changes distance form the target to the viewpoint
		eye.x = d*cb*ca;
		eye.y = d*sa;
		eye.z =  d*sb*ca;
		camera(eye.x, eye.y, eye.z, focus.x, focus.y, focus.z, up.x, up.y, up.z); // defines the view : eye, ctr, up
		directionalLight(250, 250, 250, -eye.x, -eye.y+100, -eye.z); // puts a white light above and to the left of the viewer 
	}

	/**
	 * Draws the top information panel.
	 */
	void drawTopBar() {
		noStroke();
		fill(0xFF131D45);
		rect(0, 0, width, 30);
		textAlign(LEFT, TOP);
		textSize(13);
		fill(255,200);
		text(TITLE, 10, 5);
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

	public static GraphicsCollision getInstance() {
		return instance;
	}


	/**
	 * Let's set up some key controls, eh?
	 */
	public void keyPressed() {

		switch(key) {

		}

		if (keyCode == DOWN || key == '[') {
		}
	}

	public void keyReleased() {
		switch(key) {
		case 'z': case 'Z':
			// zDown = false;
			break;
		}
	}

	void rotate() {
		eye=Point.rotatePointAroundPlane(eye, PI*(mouseX-pmouseX)/width,I,K,focus); 
		eye=Point.rotatePointAroundPlane(eye,-PI*(mouseY-pmouseY)/width,J,K,focus);
	}

	void zoom() {
		eye = Point.addScaledVector(eye, K, -(float)(mouseY-pmouseY));
	}

	/**
	 * What happens when we move the mouse.
	 */
	public void mouseMoved() {

		boolean over = false;

		// check to see if we're over a widget,
		// and if we are, update that widget
		for (int i = 0; i < widgets.size(); i++) {
			Widget widget = (Widget)widgets.get(i);
			if (widget.over(mouseX, mouseY)) {
				widget.mouseMoved(mouseX, mouseY);
				over = true;
			}
		}

		//		if (!over) rotate();
	}

	public void mouseDragged() {
		// check to see if we're over a widget,
		// and if we are, update that widget

		if (keyPressed && key == 'r') rotate();
		else if (keyPressed && key == 'z') zoom();
		
		else {
			for (int i = 0; i < widgets.size(); i++) {
				Widget widget = (Widget)widgets.get(i);
				if (widget.over(mouseX, mouseY)) {
					widget.mouseMoved(mouseX, mouseY);
				}
			}
		}
	}

	/**
	 * What happens when we click the mouse.
	 */
	public void mousePressed() {
		// are we clicking on a widget?
		for (int i = 0; i < widgets.size(); i++) {
			Widget widget = (Widget)widgets.get(i);
			if (widget.over(mouseX,mouseY)) {
				widget.mouseClicked(mouseX,mouseY);
			}
		}
	}

}
