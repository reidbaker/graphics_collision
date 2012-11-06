import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import processing.core.PApplet;
import processing.opengl.PGraphicsOpenGL;

public class Geometry3D {
	
	public static PointVector[] getScreenRay(float mouseX, float mouseY) {
		
		GraphicsCollision applet = GraphicsCollision.getInstance();
		((PGraphicsOpenGL)applet.g).beginGL(); // let's start doing some gl stuff
		int viewport[] = new int[4];
		double[] projection = new double[16];
		double[] model = new double[16];
		double[] rayPos = new double[4];

		applet.gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);	// load our viewport
		applet.gl.glGetDoublev (GL.GL_MODELVIEW_MATRIX, model, 0);  //lood our modelview matrix
		applet.gl.glGetDoublev (GL.GL_PROJECTION_MATRIX, projection, 0); // load our projection matrix

		double x = mouseX;
		double y = applet.height - mouseY;


		// we're using a floatbuffer for performance reasons. if we were using C, it wouldn't be necessary.
		// more info on why here: http://stackoverflow.com/questions/10697161/why-floatbuffer-instead-of-float
		// float[] fb;

		FloatBuffer fb=ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asFloatBuffer();

		// this is so we can get the Z component
		applet.gl.glReadPixels((int)x, (int)y, 1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, fb);

		applet.glu.gluUnProject (x, y, (double)0.0, model, 0, projection, 0, viewport, 0, rayPos, 0);
		PointVector p1 = new PointVector ( (float) rayPos[0], (float) rayPos[1], (float) rayPos[2]);
		applet.glu.gluUnProject (x, y, (double)1.0, model, 0, projection, 0, viewport, 0, rayPos, 0);
		PointVector p2= new PointVector ( (float) rayPos[0], (float) rayPos[1], (float) rayPos[2]);

		((PGraphicsOpenGL)applet.g).endGL(); // we're done!

		return new PointVector[] {
				p1, p2
		};
	}

	public static PointVector getClosestPoint(PointVector[] points, float mouseX, float mouseY, float threshold) {

		PointVector closest = null;

		PointVector[] ray = getScreenRay(mouseX, mouseY);

		PointVector raySlope = PointVector.sub(ray[1], ray[0]);
		raySlope.normalize();

		for (PointVector p: points) {
			PointVector slope = PointVector.sub(ray[0], p);
			slope.normalize();

			float dist = PointVector.dist(raySlope, slope);
			if ( dist < threshold) {
				threshold = dist;
				closest = p;
			}
		}

		return closest;
	}

	public static PointVector get3DPoint(float mouseX, float mouseY) {

		GraphicsCollision applet = GraphicsCollision.getInstance();
		((PGraphicsOpenGL)applet.g).beginGL(); // let's start doing some gl stuff
		int viewport[] = new int[4];
		double[] projection = new double[16];
		double[] model = new double[16];
		double[] mousePos = new double[4];

		applet.gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);	// load our viewport
		applet.gl.glGetDoublev (GL.GL_MODELVIEW_MATRIX, model, 0);  //lood our modelview matrix
		applet.gl.glGetDoublev (GL.GL_PROJECTION_MATRIX, projection, 0); // load our projection matrix

		double x = mouseX;
		double y = applet.height - mouseY;


		// we're using a floatbuffer for performance reasons. if we were using C, it wouldn't be necessary.
		// more info on why here: http://stackoverflow.com/questions/10697161/why-floatbuffer-instead-of-float
		// float[] fb;

		FloatBuffer fb=ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asFloatBuffer();

		// this is so we can get the Z component
		applet.gl.glReadPixels((int)x, (int)y, 1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, fb);
		fb.rewind(); // reset the buffer position

		applet.glu.gluUnProject (x, y, (double)fb.get(0), model, 0, projection, 0, viewport, 0, mousePos, 0);

		((PGraphicsOpenGL)applet.g).endGL(); // we're done!

		return new PointVector ( (float) mousePos[0], (float) mousePos[1], (float) mousePos[2]);
	}
}
