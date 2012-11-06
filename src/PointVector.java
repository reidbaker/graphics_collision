import processing.core.PApplet;

public class PointVector {
	float x, y, z;

	PointVector() {
		this(0,0,0);
	}

	PointVector(float x, float y) {
		this(x,y,0);
	}

	PointVector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float[] getXYZ(){
	    float[] out = {x,y,z};
	    return out;
	}

	void add(PointVector p) {
		x += p.x;
		y += p.y;
		z += p.z;
	}

	void normalize() {
		double magnitude = Math.sqrt(x*x+y*y+z*z);
		x *= 1/magnitude;
		y *= 1/magnitude;
		z *= 1/magnitude;
	}

	//********************
	// GENERAL METHODS
	//********************
	public static PointVector add(PointVector ... vectors) {
	  PointVector p = new PointVector();
	  for (PointVector v: vectors) {
	    p.add(v);
	  }

	  return p;
	}

	public static PointVector sub(PointVector a, PointVector b) {
	    return new PointVector(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static PointVector mult(PointVector a, float s) {
	  return new PointVector(a.x*s, a.y*s, a.z*s);
	}

	public static PointVector div(PointVector a, float s) {
	  return mult(a, 1/s);
	}

	//****************
	// MAGIC METHODS
	//****************

	public static float dist(PointVector a, PointVector b) {
	  return PApplet.dist(a.x,a.y,a.z,b.x,b.y,b.z);
	}

	public static PointVector midPoint(PointVector a, PointVector b) {
		return new PointVector((a.x+b.x)/2,(a.y+b.y)/2, (a.z+b.z)/2);
	}

	public static PointVector lerp(PointVector a, PointVector b, float t) {
	    PointVector d = sub(b, a);
	    return add(a, mult(d, t));
	}

	public static PointVector addScaledVector(PointVector a, PointVector b, float t) {
	    return add(a, mult(b, t));
	}

	public static PointVector rotateVector90(PointVector v) {
	  return new PointVector(-v.y, v.x);
	}


	public static PointVector rotateVector(PointVector v, float angle) {
	  float c=(float) Math.cos(angle), s=(float) Math.sin(angle);
	  return(new PointVector(v.x*c-v.y*s,v.x*s+v.y*c));
	}

	public static float dot(PointVector u, PointVector v) {
	  return u.x*v.x + u.y*v.y + u.z*v.z;
	}

	public static float cross2D(PointVector u, PointVector v) {
	  return dot(u,rotateVector90(v));
	}

	float angle(PointVector a, PointVector b) {
	  return (float) Math.atan2(dot(rotateVector90(a),b),dot(a,b));
	}

	public static PointVector neville(float t, PointVector ... vectors) {
	   if (vectors.length == 1) return vectors[0];
	   if (vectors.length == 2) {
	     return lerp(vectors[0], vectors[1], t);
	   } else {
	     int len = vectors.length - 1;
	     PointVector[] v1 = new PointVector[len], v2 = new PointVector[len];
	     System.arraycopy(vectors,0,v1,0,len);
	     System.arraycopy(vectors,1,v2,0,len);
	     return neville(t/(len),neville(t, v1), neville(t-1,v2));
	   }
	}

	public String toString() {
		return String.format("(%.2f,%.2f,%.2f)", x,y,z);
	}
}
