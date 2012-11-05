import processing.core.PApplet;
import processing.core.PFont;

public class GraphicsCollision extends PApplet {

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "GraphicsCollision" });
	}


	public void setup() {
		size(800, 600);
		frameRate(30);

	}

	public void draw() {
		// Handle data drawing

		background(0xd9cccc);
	}

	public void mouseReleased() {

	}

	public void drawAxes() {
		// Draw Axes Lines
		stroke(0);
		strokeWeight(3);
		line(50,50,50,450);
		line(50,450,750,450);

		// Draw Labels
	}

	public void drawData() {
		// Set colors and draw lines. Use a thicker stroke is possible
	}

}
