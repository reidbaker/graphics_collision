import processing.core.PApplet;

// ===== vector class
public class Vector extends Point  {

	float x, y, z;

	public Vector(){
		this(0,0,0);
	}

	public Vector(float x, float y){
		this(0,0,0);
	}

	Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float[] getXYZ(){
		float[] out = {x,y,z};
		return out;
	}

	public void add(Vector p) {
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
	public static Vector add(Vector ... vectors) {
		Vector p = new Vector();
		for (Vector v: vectors) {
			p.add(v);
		}
		return p;
	}

	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static Vector mult(Vector a, float s) {
		return new Vector(a.x*s, a.y*s, a.z*s);
	}

	public static Vector div(Vector a, float s) {
		return mult(a, 1/s);
	}

	//****************
	// MAGIC METHODS
	//****************

	public static float dist(Vector a, Vector b) {
		return PApplet.dist(a.x,a.y,a.z,b.x,b.y,b.z);
	}

	public static Vector midVector(Vector a, Vector b) {
		return new Vector((a.x+b.x)/2,(a.y+b.y)/2, (a.z+b.z)/2);
	}

	public static Vector lerp(Vector a, float t, Vector b) {
		Vector d = sub(b, a);
		return add(a, mult(d, t));
	}

	public static Vector addScaledVector(Vector a, Vector b, float t) {
		return add(a, mult(b, t));
	}

	public static Vector rotateVector90(Vector v) {
		return new Vector(-v.y, v.x);
	}


	public static Vector rotateVector(Vector v, float angle) {
		float c=(float) Math.cos(angle), s=(float) Math.sin(angle);
		return(new Vector(v.x*c-v.y*s,v.x*s+v.y*c));
	}

	
	public static float dot(Vector u, Vector v) {
		return u.x*v.x + u.y*v.y + u.z*v.z;
	}

	public static float cross2D(Vector u, Vector v) {
		return dot(u,rotateVector90(v));
	}

	float angle(Vector a, Vector b) {
		return (float) Math.atan2(dot(rotateVector90(a),b),dot(a,b));
	}

	public static Vector neville(float t, Vector ... vectors) {
		if (vectors.length == 1) return vectors[0];
		if (vectors.length == 2) {
			return lerp(vectors[0], t, vectors[1]);
		} else {
			int len = vectors.length - 1;
			Vector[] v1 = new Vector[len], v2 = new Vector[len];
			System.arraycopy(vectors,0,v1,0,len);
			System.arraycopy(vectors,1,v2,0,len);
			return neville(t/(len),neville(t, v1), neville(t-1,v2));
		}
	}


	//   
	//    public Vector(float px, float py, float pz) {
	//        x = px; y = py; z = pz;
	//    }
	//    public Vector set(float px, float py, float pz) {
	//        x = px; y = py; z = pz; return this;
	//    }
	//    public Vector set (Vector V) {
	//        x = V.x; y = V.y; z = V.z; return this;
	//    }
	//    public Vector add(Vector V) {
	//        x+=V.x; y+=V.y; z+=V.z; return this;
	//        }
	//    public Vector add(float s, Vector V) {
	//        x+=s*V.x; y+=s*V.y; z+=s*V.z; return this;
	//    }
	//    public Vector sub(Vector V) {
	//        x-=V.x; y-=V.y; z-=V.z; return this;
	//    }
	//    public Vector mul(float f) {
	//        x*=f; y*=f; z*=f; return this;
	//    }
	//    public Vector div(float f) {
	//        x/=f; y/=f; z/=f; return this;
	//    }
	//    public Vector div(int f) {
	//        x/=f; y/=f; z/=f; return this;
	//    }
	//    public Vector rev() {
	//        x=-x; y=-y; z=-z; return this;
	//    }
	//    public float norm() {
	//        return (float) (Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)));
	//    }
	//    public Vector normalize() {
	//        float n=norm();
	//        if (n>0.000001) {
	//            div(n);
	//        }
	//        return this;
	//    }
	//    /*public Vec rotate(float a, Vec I, Vec J) {
	//        // Rotate by a in plane (I,J)
	//        float x=d(this,I); //TODO Make dot product
	//        float y=d(this,J); //TODO Make dot product
	//        float c=(float) Math.cos(a);
	//        float s=(float) Math.sin(a);
	//        add(x*c-x-y*s,I);
	//        add(x*s+y*c-y,J);
	//        return this;
	//    }*/
	//
	//    //Vector functions
	//    public static Vector V(Vector V) {
	//     // make copy of vector V
	//        return new Vector(V.x,V.y,V.z);
	//    }
	//
	//    public static Vector A(Vector A, Vector B) {
	//        // A+B
	//        return new Vector(A.x+B.x,A.y+B.y,A.z+B.z);
	//    }
	//    public static Vector A(Vector U, float s, Vector V) {
	//        // U+sV
	//        return new Vector(U.x+s*V.x,U.y+s*V.y,U.z+s*V.z);
	//    }
	//    public static Vector M(Vector U, Vector V) {
	//        // U-V
	//        return new Vector(U.x-V.x,U.y-V.y,U.z-V.z);
	//    }
	//    public static Vector M(Vector V) {
	//        // -V
	//        return new Vector(-V.x,-V.y,-V.z);
	//    }
	//    public static Vector V(Vector A, Vector B) {
	//        // (A+B)/2
	//        float newX = (float) ((A.x+B.x)/2.0);
	//        float newY = (float) ((A.y+B.y)/2.0);
	//        float newZ = (float) ((A.z+B.z)/2.0);
	//        return new Vector(newX,newY,newZ);
	//    }
	//    public static Vector V(Vector A, float s, Vector B) {
	//     // (1-s)A+sB
	//        return new Vector(A.x+s*(B.x-A.x),A.y+s*(B.y-A.y),A.z+s*(B.z-A.z));
	//    }
	//    public static Vector V(Vector A, Vector B, Vector C) {
	//        // (A+B+C)/3
	//        float newX = (float) ((A.x+B.x+C.x)/3.0);
	//        float newY = (float) ((A.y+B.y+C.y)/3.0);
	//        float newZ = (float) ((A.z+B.z+C.z)/3.0);
	//        return new Vector(newX,newY,newZ);
	//    }
	//    public static Vector V(Vector A, Vector B, Vector C, Vector D) {
	//        // (A+B+C+D)/4
	//        return V(V(A,B),V(C,D));
	//    }
	//    public static Vector V(float s, Vector A) {
	//        // sA
	//        return new Vector(s*A.x,s*A.y,s*A.z);
	//    }
	//    public static Vector V(float a, Vector A, float b, Vector B) {
	//        // aA+bB
	//        return A(V(a,A),V(b,B));
	//    }
	//    public static Vector V(float a, Vector A, float b, Vector B, float c, Vector C) {
	//        // aA+bB+cC
	//        return A(V(a,A,b,B),V(c,C));
	//    }
	//    public static Vector V(Vector P, Vector Q) {
	//        // PQ
	//        return new Vector(Q.x-P.x,Q.y-P.y,Q.z-P.z);
	//    }
	//    public static Vector U(Vector V) {
	//        // V/||V||
	//        float n = V.norm();
	//        if (n<0.000001){
	//            return new Vector(0, 0, 0);
	//        }else{
	//            return V((float)(1./n),V);
	//        }
	//    }
	//    /*public static Vec U(pt A, pt B) {
	//        //TODO implement Vector
	//        return U(V(A,B));
	//    }*/
	//    public static Vector N(Vector U, Vector V) {
	//        // UxV CROSS PRODUCT (normal to both)
	//        return new Vector( U.y*V.z-U.z*V.y, U.z*V.x-U.x*V.z, U.x*V.y-U.y*V.x);
	//    }
	//    public static Vector N(Vector A, Vector B, Vector C) {
	//        // normal to triangle (A,B,C), not normalized (proportional to area)
	//        return N(V(A,B),V(A,C));
	//    }
	//    Vector B(Vector U, Vector V) {
	//        // (UxV)xV unit normal to U in the plane UV
	//        return U(N(N(U,V),U));
	//    }
	//    Vector R(Vector V) {
	//        // rotated 90 degrees in XY plane
	//        return new Vector(-V.y,V.x,V.z);
	//    }
	//    /*Vec R(Vec V, float a, Vec I, Vec J) {
	//     // Rotated V by a parallel to plane (I,J)
	//        float x=d(V,I); //TODO implement dot product
	//        float y=d(V,J);
	//        float c=Math.cos(a);
	//        float s=Math.sin(a);
	//        return A(V,V(x*c-x-y*s,I,x*s+y*c-y,J));
	//    }*/
}
