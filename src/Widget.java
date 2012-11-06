import processing.core.PApplet;

/**
 * A widget is a UI element that is drawn to screen
 * and can be interacted with.
 */
interface Widget {
	void draw(PApplet c);
	boolean over(float x, float y);
	void onClick();
	void mouseMoved(float x, float y);
	void mouseClicked(float x, float y);
}

/**
 * A listener that performs an action
 * when triggered by a widget.
 */
interface WidgetListener { 
	public void action();
}

interface MouseMotionListener {
	void mouseMoved(float x, float y);
	void mouseClicked(float x, float y);
}

/**
 * A simple button.
 * (Actually, it's not that simple--it has rounded
 * corners and hover states!)
 */
class Button implements Widget {

	float x, y, width, height;
	String string; // the message of the button
	int align = PApplet.CENTER;
	WidgetListener listener;
	WidgetListener hoverListener;

	int textColor = 0xFF646464;
	int backColor = 0xC8FFFFFF;
	int hoverColor = 0xFF7FC757;
	int hoverTextColor = 0xFFFFFFFF;

	Button(float x, float y, float width, float height, String string) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.string = string;
	}

	Button(float x, float y, float width, float height) {
		this(x,y,width,height, null);
	}
	
	/**
	 * Draws the button to the screen.
	 */
	public void draw(PApplet c) {

		// draw the button
		
		c.noStroke();
		if (over(c.mouseX, c.mouseY)) {
			c.fill(hoverColor);
		} else {
			c.fill(backColor);
		}
		
		roundedRect(c, x, y, width, height, 10, 10);

		// draw the button text
		if (string != null) {
			float startX = x + 10;
			float startY = y;
			if (align == PApplet.CENTER) {
				startX = x + width/2;
			}

			if (over(c.mouseX,c.mouseY)) {
				c.fill(hoverTextColor);
			} else {
				c.fill(textColor);
			}

			c.textAlign(align, PApplet.CENTER);
			c.textSize(13);
			c.text(string, startX, y + height/2 -3); 
			c.textAlign(PApplet.LEFT, PApplet.TOP);
		}
	}

	public void mouseMoved(float x, float y) {}
	public void mouseClicked(float x, float y) {}

	void setAlign(int align) {
		this.align = align;
	}

	/**
	 * Determines whether or not the mouse is hovering over this object.
	 */
	public boolean over(float mx, float my) {
		return mx > x && mx < x + width && my > y && my < y + height;
	}

	void addOnClickListener(WidgetListener listener) {
		this.listener = listener;
	}

	void addHoverListener(WidgetListener listener) {
		this.hoverListener = listener;
	}

	public void onClick() {
		if (listener != null) {
			listener.action();
		}
	}

	void onHover() {
		if (hoverListener != null) {
			hoverListener.action();
		}
	}
	/**
	 * Draws a pretty sweet rounded rectangle.
	 * Taken from here: http://forum.processing.org/topic/rounded-rectangle
	 */
	void roundedRect(PApplet canvas, float x, float y, float w, float h, float rx, float ry) {
		canvas.beginShape();
		canvas.vertex(x,y+ry); //top of left side 
		canvas.bezierVertex(x,y,x,y,x+rx,y); //top left corner

		canvas.vertex(x+w-rx,y); //right of top side 
		canvas.bezierVertex(x+w,y,x+w,y,x+w,y+ry); //top right corner

		canvas.vertex(x+w,y+h-ry); //bottom of right side
		canvas.bezierVertex(x+w,y+h,x+w,y+h,x+w-rx,y+h); //bottom right corner

		canvas.vertex(x+rx,y+h); //left of bottom side
		canvas.bezierVertex(x,y+h,x,y+h,x,y+h-ry); //bottom left corner

		canvas.endShape(PApplet.CLOSE);
	}
}

