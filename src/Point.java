import java.util.ArrayList;

import processing.core.PApplet;


// ===== point class
public class Point {
	float x, y, z;

	public Point(){
		this(0,0,0);
	}

	public Point(float x, float y){
		this(0,0,0);
	}

	Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float[] getXYZ(){
		float[] out = {x,y,z};
		return out;
	}

	public void add(Point p) {
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
	public static Point add(Point ... vectors) {
		Point p = new Point();
		for (Point v: vectors) {
			p.add(v);
		}
		return p;
	}

	public static Point sub(Point a, Point b) {
		return new Point(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static Point mult(Point a, float s) {
		return new Point(a.x*s, a.y*s, a.z*s);
	}

	public static Point div(Point a, float s) {
		return mult(a, 1/s);
	}

	//****************
	// MAGIC METHODS
	//****************

	public static float dist(Point a, Point b) {
		return PApplet.dist(a.x,a.y,a.z,b.x,b.y,b.z);
	}

	public static Point midPoint(Point a, Point b) {
		return new Point((a.x+b.x)/2,(a.y+b.y)/2, (a.z+b.z)/2);
	}

	public static Point lerp(Point a, float t, Point b) {
		Point d = sub(b, a);
		return add(a, mult(d, t));
	}

	public static Point addScaledVector(Point a, Point b, float t) {
		return add(a, mult(b, t));
	}

	public static Point rotateVector90(Point v) {
		return new Point(-v.y, v.x);
	}
	
	public static Vector rotatePointAroundPlane(Point p, float a, Vector I, Vector J, Point g) {
		float x = dist(Point.sub(g,p),I), 
			  y = dist(Point.sub(g,p),J);
		
		float c=(float) Math.cos(a), s=(float) Math.sin(a); 
		return Vector.add(Vector.mult(I,x*c-x-y*s),Vector.mult(J,x*s+y*c-y));
	};


	public static Point rotateVector(Point v, float angle) {
		float c=(float) Math.cos(angle), s=(float) Math.sin(angle);
		return(new Point(v.x*c-v.y*s,v.x*s+v.y*c));
	}
	
	// Rotated V by a parallel to plane (I,J)
	public static Point rotateVectorParallelToPlane(Point v, float angle, Point I, Point J) {
		float x=dist(v,I), y=dist(v,J);
		float c=(float) Math.cos(angle), s=(float) Math.sin(angle);
		return Point.add(v,Point.add(Point.mult(I,x*c-x-y*s),Point.mult(J,x*s+y*c-y))); 
	}; 

	public static float dot(Point u, Point v) {
		return u.x*v.x + u.y*v.y + u.z*v.z;
	}

	public static float cross2D(Point u, Point v) {
		return dot(u,rotateVector90(v));
	}

	float angle(Point a, Point b) {
		return (float) Math.atan2(dot(rotateVector90(a),b),dot(a,b));
	}

	public static Point neville(float t, Point ... vectors) {
		if (vectors.length == 1) return vectors[0];
		if (vectors.length == 2) {
			return lerp(vectors[0], t, vectors[1]);
		} else {
			int len = vectors.length - 1;
			Point[] v1 = new Point[len], v2 = new Point[len];
			System.arraycopy(vectors,0,v1,0,len);
			System.arraycopy(vectors,1,v2,0,len);
			return neville(t/(len),neville(t, v1), neville(t-1,v2));
		}
	}

	public static ArrayList<Point> subdivide(ArrayList<Point> points) {

		Point[] newPoints = new Point[points.size()];
		int pos = 0;

		int length = points.size();
		if (points.size() > 3) {
			newPoints[pos++] = fourPointSubdivide(points.get(0), points.get(1), points.get(2), points.get(3));

			for (int i = 0; i < length - 3; i++) {
				newPoints[pos++] = fourPointSubdivide(points.get(i), points.get(i+1), points.get(i+2), points.get(i+3));
			}
			
			ArrayList<Point> points2 = new ArrayList<Point>();
			points2.add(points.get(0));
			for (int i = 1; i < length; i++) {
				points2.add(points.get(i));
				if (i < pos) points2.add(newPoints[i]);
			}
			
			points = points2;
		} else {
			System.out.println("Not enough points to do four point subdivision");
		}
		
		return points;
	} 

	public static Point fourPointSubdivide(Point p0, Point p1, Point p2, Point p3){
		//s( s(A,9/8,B),.5, s(E,9/8,D)) from the notes
		Point one = Point.lerp(p0, 9.0f/8.0f, p1);
		Point two = Point.lerp(p3, 9.0f/8.0f, p2);
		return Point.lerp(one, (float).5, two);
	}
	//	
	//    public static Point subdivision(PointVector p0, PointVector p1, PointVector p2, PointVector p3){
	//        float[] p0xyz = p0.getXYZ();
	//        Point newP0 = new Point(p0xyz[0],p0xyz[1],p0xyz[2]);
	//
	//        float[] p1xyz = p1.getXYZ();
	//        Point newP1 = new Point(p1xyz[0],p1xyz[1],p1xyz[2]);
	//
	//        float[] p2xyz = p2.getXYZ();
	//        Point newP2 = new Point(p2xyz[0],p2xyz[1],p2xyz[2]);
	//
	//        float[] p3xyz = p3.getXYZ();
	//        Point newP3 = new Point(p3xyz[0],p3xyz[1],p3xyz[2]);
	//        return subdivision(newP0,newP1,newP2,newP3);
	//
	//    }
	//
	//	public Point (float px, float py, float pz) {
	//		x = px;
	//		y = py;
	//		z = pz;
	//	}
	//
	//	public Point add(Point P) {
	//		x+=P.x;
	//		y+=P.y;
	//		z+=P.z;
	//		return this;
	//	}
	//	public Point add(Vector V) {
	//		x+=V.x;
	//		y+=V.y;
	//		z+=V.z;
	//		return this;
	//	}
	//	public Point add(float s, Vector V) {
	//		x+=s*V.x;
	//		y+=s*V.y;
	//		z+=s*V.z;
	//		return this;
	//	}
	//	public Point add(float dx, float dy, float dz) {
	//		x+=dx;
	//		y+=dy;
	//		z+=dz;
	//		return this;
	//	}
	//
	//	public static Point sub(Point a, Point b) {
	//	    return new Point(a.x - b.x, a.y - b.y, a.z - b.z);
	//	}
	//
	//	public Point sub(Point P) {
	//		x-=P.x;
	//		y-=P.y;
	//		z-=P.z;
	//		return this;
	//	}
	//	public Point mul(float f) {
	//		x*=f;
	//		y*=f;
	//		z*=f;
	//		return this;
	//	}
	//	public Point mul(float dx, float dy, float dz) {
	//		x*=dx;
	//		y*=dy;
	//		z*=dz;
	//		return this;
	//	}
	//	public Point div(float f) {
	//		x/=f;
	//		y/=f;
	//		z/=f;
	//		return this;
	//	}
	//	public Point div(int f) {
	//		x/=f;
	//		y/=f;
	//		z/=f;
	//		return this;
	//	}
	//	public Point snap(float r) {
	//		float f=(float) (r/(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2))));
	//		x*=f;
	//		y*=f;
	//		z*=f;
	//		return this;
	//	}
	//	// =====  point functions
	//			public static Point P(){
	//		// point 0,0,0
	//		return new Point();
	//	}
	//	public static Point P(float x, float y, float z){
	//		// point (x,y,z)
	//		return new Point(x,y,z);
	//	}
	//	public static Point P(Point A) {
	//		// copy of point P
	//		return new Point(A.x,A.y,A.z);
	//	}
	//	public static Point lerp(Point A, float s, Point B) {
	//		// A+sAB
	//	    float newX = A.x+s*(B.x-A.x);
	//	    float newY = A.y+s*(B.y-A.y);
	//	    float newZ = A.z+s*(B.z-A.z);
	//		return new Point(newX,newY,newZ);
	//	}
	//	public static Point P(Point A, Point B) {
	//		// (A+B)/2
	//		float newX = (float) ((A.x+B.x)/2.0);
	//		float newY = (float) ((A.y+B.y)/2.0);
	//		float newZ = (float) ((A.z+B.z)/2.0);
	//		return P(newX,newY,newZ);
	//	}
	//	public static Point P(Point A, Point B, Point C) {
	//		// (A+B+C)/3
	//		float newX = (float) ((A.x+B.x+C.x)/3.0);
	//		float newY = (float) ((A.y+B.y+C.y)/3.0);
	//		float newZ = (float) ((A.z+B.z+C.z)/3.0);
	//		return new Point(newX,newY,(newZ));
	//	}
	//	public static Point P(Point A, Point B, Point C, Point D) {
	//		// (A+B+C+D)/4
	//		return P(P(A,B),P(C,D));
	//	}
	//	public static Point P(float s, Point A) {
	//		// sA
	//		return new Point(s*A.x,s*A.y,s*A.z);
	//	}
	//	public static Point A(Point A, Point B) {
	//		// A+B
	//		return new Point(A.x+B.x,A.y+B.y,A.z+B.z);
	//	}
	//	public static Point P(float a, Point A, float b, Point B) {
	//		// aA+bB
	//		return A(P(a,A),P(b,B));
	//	}
	//	public static Point P(float a, Point A, float b, Point B, float c, Point C) {
	//		// aA+bB+cC
	//		return A(P(a,A),P(b,B,c,C));
	//	}
	//	public static Point P(float a, Point A, float b, Point B, float c, Point C, float d, Point D){
	//		// aA+bB+cC+dD
	//		return A(P(a,A,b,B),P(c,C,d,D));
	//	}
	//	public static Point P(Point P, Vector V) {
	//		// P+V
	//		return new Point(P.x + V.x, P.y + V.y, P.z + V.z);
	//	}
	//	public static Point P(Point P, float s, Vector V) {
	//		// P+sV
	//		return new Point(P.x+s*V.x,P.y+s*V.y,P.z+s*V.z);
	//	}
	//	public static Point P(Point O, float x, Vector I, float y, Vector J) {
	//		// O+xI+yJ
	//		return P(O.x+x*I.x+y*J.x,O.y+x*I.y+y*J.y,O.z+x*I.z+y*J.z);
	//	}
	//	public static Point P(Point O, float x, Vector I, float y, Vector J, float z, Vector K) {
	//		// O+xI+yJ+kZ
	//		return P(O.x+x*I.x+y*J.x+z*K.x,O.y+x*I.y+y*J.y+z*K.y,O.z+x*I.z+y*J.z+z*K.z);
	//	}
	//	public static Point R(Point P, float a, Vector I, Vector J, Point G) {
	//		// Rotated P by a around G in plane (I,J)
	//		float x = d(Vector.V(G,P),I);
	//		float y = d(Vector.V(G,P),J);
	//		float c = (float) Math.cos(a);
	//		float s = (float) Math.sin(a);
	//		return P(P,x*c-x-y*s,I,x*s+y*c-y,J);
	//	}
	//	public static void makePts(Point[] C) { //TODO change return type
	//		// fills array C with points initialized to (0,0,0)
	//		for(int i=0; i<C.length; i++){
	//			C[i]=P();
	//		}
	//	}
	//
	//	public static Point Predict(Point A, Point B, Point C) {
	//		// B+AC, parallelogram predictor
	//		return P(B,Vector.V(A,C));
	//	}
	//	/*public static void v(Pt P) { //TODO figure out what this does
	//        // rendering
	//        vertex(P.x,P.y,P.z);
	//    }*/
	//	// ===== measures
	//	public static float d(Vector U, Vector V) {
	//		//U*V dot product
	//		return U.x*V.x+U.y*V.y+U.z*V.z;
	//	}
	//	public static float d(Point P, Point Q) {
	//		// ||AB|| distance
	//		return (float) Math.sqrt(Math.pow(Q.x-P.x,2)+Math.pow(Q.y-P.y,2)+Math.pow(Q.z-P.z,2));
	//	}
	//	public static float d2(Point P, Point Q) {
	//		// AB^2 distance squared
	//		return (float) (Math.pow(Q.x-P.x,2)+Math.pow(Q.y-P.y,2)+Math.pow(Q.z-P.z,2));
	//	}
	//	public static float m(Vector U, Vector V, Vector W) {
	//		// (UxV)*W  mixed product, determinant
	//		return d(U,Vector.N(V,W));
	//	}
	//	public static float m(Point E, Point A, Point B, Point C) {
	//		// det (EA EB EC) is >0 when E sees (A,B,C) clockwise
	//		return m(Vector.V(E,A),Vector.V(E,B),Vector.V(E,C));
	//	}
	//	public static float n2(Vector V) {
	//		// V*V    norm squared
	//		return (float) (Math.pow(V.x,2)+Math.pow(V.y,2)+Math.pow(V.z,2));
	//	}
	//	public static float n(Vector V) {
	//		// ||V||  norm
	//		return (float) Math.pow(n2(V), .5);
	//	}
	//	public static float area(Point A, Point B, Point C) {
	//		// area of triangle
	//		return n(Vector.N(A,B,C))/2;
	//	}
	//	public static float volume(Point A, Point B, Point C, Point D) {
	//		// volume of tet
	//		return m(Vector.V(A,B),Vector.V(A,C),Vector.V(A,D))/6;
	//	}
	//	public static boolean parallel (Vector U, Vector V) {
	//		// true if U and V are almost parallel
	//		return n(Vector.N(U,V))<n(U)*n(V)*0.00001;
	//	}
	//	public static float angle(Vector U, Vector V) {
	//		// angle(U,V)
	//		return (float) Math.acos(d(U,V)/n(V)/n(U));
	//	}
	//	public static boolean cw(Vector U, Vector V, Vector W) {
	//		// (UxV)*W>0  U,V,W are clockwise
	//		return m(U,V,W)>=0;
	//	}
	//	public static boolean cw(Point A, Point B, Point C, Point D) {
	//		// tet is oriented so that A sees B, C, D clockwise
	//		return volume(A,B,C,D)>=0;
	//	}

	public String toString() {
		return String.format("(%.2f,%.2f,%.2f)", x,y,z);
	}

}

/*
// ===== rotate

// ===== render
void normal(Vector V) {normal(V.x,V.y,V.z);};                                          // changes normal for smooth shading
void vertex(pt P) {vertex(P.x,P.y,P.z);};                                           // vertex for shading or drawing
void vTextured(pt P, float u, float v) {vertex(P.x,P.y,P.z,u,v);};                          // vertex with texture coordinates
void show(pt P, pt Q) {line(Q.x,Q.y,Q.z,P.x,P.y,P.z); };                       // draws edge (P,Q)
void show(pt P, Vector V) {line(P.x,P.y,P.z,P.x+V.x,P.y+V.y,P.z+V.z); };          // shows edge from P to P+V
void show(pt P, float d , Vector V) {line(P.x,P.y,P.z,P.x+d*V.x,P.y+d*V.y,P.z+d*V.z); }; // shows edge from P to P+dV
void show(pt A, pt B, pt C) {beginShape(); vertex(A);vertex(B); vertex(C); endShape(CLOSE);};                      // volume of tet
void show(pt A, pt B, pt C, pt D) {beginShape(); vertex(A); vertex(B); vertex(C); vertex(D); endShape(CLOSE);};                      // volume of tet
void show(pt P, float r) {pushMatrix(); translate(P.x,P.y,P.z); sphere(r); popMatrix();}; // render sphere of radius r and center P
void show(pt P, float s, Vector I, Vector J, Vector K) {noStroke(); fill(yellow); show(P,5); stroke(red); show(P,s,I); stroke(green); show(P,s,J); stroke(blue); show(P,s,K); }; // render sphere of radius r and center P
void show(pt P, String s) {text(s, P.x, P.y, P.z); }; // prints string s in 3D at P
void show(pt P, String s, Vector D) {text(s, P.x+D.x, P.y+D.y, P.z+D.z);  }; // prints string s in 3D at P+D

// ==== curve
void bezier(pt A, pt B, pt C, pt D) {bezier(A.x,A.y,A.z,B.x,B.y,B.z,C.x,C.y,C.z,D.x,D.y,D.z);} // draws a cubic Bezier curve with control points A, B, C, D
void bezier(pt [] C) {bezier(C[0],C[1],C[2],C[3]);} // draws a cubic Bezier curve with control points A, B, C, D
pt bezierPoint(pt[] C, float t) {return P(bezierPoint(C[0].x,C[1].x,C[2].x,C[3].x,t),bezierPoint(C[0].y,C[1].y,C[2].y,C[3].y,t),bezierPoint(C[0].z,C[1].z,C[2].z,C[3].z,t)); }
Vector bezierTangent(pt[] C, float t) {return V(bezierTangent(C[0].x,C[1].x,C[2].x,C[3].x,t),bezierTangent(C[0].y,C[1].y,C[2].y,C[3].y,t),bezierTangent(C[0].z,C[1].z,C[2].z,C[3].z,t)); }
void PT(pt P0, Vector T0, pt P1, Vector T1) {float d=d(P0,P1)/3;  bezier(P0, P(P0,-d,U(T0)), P(P1,-d,U(T1)), P1);} // draws cubic Bezier interpolating  (P0,T0) and  (P1,T1)
void PTtoBezier(pt P0, Vector T0, pt P1, Vector T1, pt [] C) {float d=d(P0,P1)/3;  C[0].set(P0); C[1].set(P(P0,-d,U(T0))); C[2].set(P(P1,-d,U(T1))); C[3].set(P1);} // draws cubic Bezier interpolating  (P0,T0) and  (P1,T1)
Vector vecToCubic (pt A, pt B, pt C, pt D, pt E) {return V( (-A.x+4*B.x-6*C.x+4*D.x-E.x)/6, (-A.y+4*B.y-6*C.y+4*D.y-E.y)/6, (-A.z+4*B.z-6*C.z+4*D.z-E.z)/6);}
Vector vecToProp (pt B, pt C, pt D) {float cb=d(C,B);  float cd=d(C,D); return V(C,P(B,cb/(cb+cd),D)); };

// ==== perspective
pt Pers(pt P, float d) { return P(d*P.x/(d+P.z) , d*P.y/(d+P.z) , d*P.z/(d+P.z) ); };

pt InverserPers(pt P, float d) { return P(d*P.x/(d-P.z) , d*P.y/(d-P.z) , d*P.z/(d-P.z) ); };

// ==== intersection
boolean intersect(pt P, pt Q, pt A, pt B, pt C, pt X)  {return intersect(P,V(P,Q),A,B,C,X); } // if (P,Q) intersects (A,B,C), return true and set X to the intersection point

boolean intersect(pt E, Vector T, pt A, pt B, pt C, pt X) { // if ray from E along T intersects triangle (A,B,C), return true and set X to the intersection point
  Vector EA=V(E,A), EB=V(E,B), EC=V(E,C), AB=V(A,B), AC=V(A,C);
  boolean s=cw(EA,EB,EC), sA=cw(T,EB,EC), sB=cw(EA,T,EC), sC=cw(EA,EB,T);
  if ( (s==sA) && (s==sB) && (s==sC) ) return false;
  float t = m(EA,AC,AB) / m(T,AC,AB);
  X.set(P(E,t,T));
  return true;
  }

boolean rayIntersectsTriangle(pt E, Vector T, pt A, pt B, pt C) { // true if ray from E with direction T hits triangle (A,B,C)
  Vector EA=V(E,A), EB=V(E,B), EC=V(E,C);
  boolean s=cw(EA,EB,EC), sA=cw(T,EB,EC), sB=cw(EA,T,EC), sC=cw(EA,EB,T);
  return  (s==sA) && (s==sB) && (s==sC) ;}

boolean edgeIntersectsTriangle(pt P, pt Q, pt A, pt B, pt C)  {
  Vector PA=V(P,A), PQ=V(P,Q), PB=V(P,B), PC=V(P,C), QA=V(Q,A), QB=V(Q,B), QC=V(Q,C);
  boolean p=cw(PA,PB,PC), q=cw(QA,QB,QC), a=cw(PQ,PB,PC), b=cw(PA,PQ,PC), c=cw(PQ,PB,PQ);
  return (p!=q) && (p==a) && (p==b) && (p==c);
  }

float rayParameterToIntersection(pt E, Vector T, pt A, pt B, pt C) {Vector AE=V(A,E), AB=V(A,B), AC=V(A,C); return - m(AE,AC,AB) / m(T,AC,AB);}

float angleDraggedAround(pt G) {  // returns angle in 2D dragged by the mouse around the screen projection of G
   pt S=P(screenX(G.x,G.y,G.z),screenY(G.x,G.y,G.z),0);
   Vector T=V(S,Pmouse()); Vector U=V(S,Mouse());
   return atan2(d(R(U),T),d(U,T));
   }

float toRad(float a) {return(a*PI/180);}
int toDeg(float a) {return int(a*180/PI);}

void showShrunkOffset(pt A, pt B, pt C, float e, float h) {Vector N=U(N(V(A,B),V(A,C))); showShrunk(P(A,h,N),P(B,h,N),P(C,h,N),e);} // offset by h along normal

void showShrunk(pt A, pt B, pt C, float e) { // shrink by e
   Vector AB = U(V(A,B)), BC = U(V(B,C)), CA = U(V(C,A));
   float a = e/n(N(CA,AB)), b = e/n(N(AB,BC)), c = e/n(N(BC,CA));
   float d = max(d(A,B)/3,d(B,C)/3,d(C,A)/3);
   a=min(a,d); b=min(b,d); c=min(c,d);
   pt As=P(A,a,AB,-a,CA), Bs=P(B,b,BC,-b,AB), Cs=P(C,c,CA,-c,BC);
   beginShape(); vertex(As); vertex(Bs); vertex(Cs); endShape(CLOSE);
   }

float scaleDraggedFrom(pt G) {pt S=P(screenX(G.x,G.y,G.z),screenY(G.x,G.y,G.z),0); return d(S,Mouse())/d(S,Pmouse()); }

// INTERPOLATING CURVE
void drawCurve(pt A, pt B, pt C, pt D) {float d=d(A,B)+d(B,C)+d(C,D); beginShape(); for(float t=0; t<=1; t+=0.025) vertex(P(A,B,C,D,t*d)); endShape(); }
void drawSamplesOnCurve(pt A, pt B, pt C, pt D, float r) {float d=d(A,B)+d(B,C)+d(C,D); for(float t=0; t<=1; t+=0.025) show(P(A,B,C,D,t*d),r);}
pt P(pt A, pt B, pt C, pt D, float t) {return P(0,A,d(A,B),B,d(A,B)+d(B,C),C,d(A,B)+d(B,C)+d(C,D),D,t);}
pt P(float a, pt A, float b, pt B, float c, pt C, float d, pt D, float t) {
   pt E = P(A,(t-a)/(b-a),B), F = P(B,(t-b)/(c-b),C), G = P(C,(t-c)/(d-c),D),
                 H = P(E,(t-a)/(c-a),F), I = P(F,(t-b)/(d-b),G);
                            return P(H,(t-a)/(d-a),I);
  }

pt NUBS(float a, pt A, float b, pt B, float c, pt C, float d, pt D, float e, float t) {
  pt E = P(A,(a+b+t*c)/(a+b+c),B), F = P(B,(b+t*c)/(b+c+d),C), G = P(C,(t*c)/(c+d+e),D),
                 H = P(E,(b+t*c)/(b+c),F),         I = P(F,(t*c)/(c+d),G),
                            J = P(H,t,I);
  return J;
  }

// Linear
pt L(pt A, pt B, float t) {return P(A.x+t*(B.x-A.x),A.y+t*(B.y-A.y),A.z+t*(B.z-A.z));}

// Interpolation non-uniform (Neville's algorithm)
pt I(float a, pt A, float b, pt B, float t) {return L(A,B,(t-a)/(b-a));}                               // P(a)=A, P(b)=B
pt I(float a, pt A, float b, pt B, float c, pt C, float t) {pt P=I(a,A,b,B,t); pt Q=I(b,B,c,C,t); return I(a,P,c,Q,t);} // P(a)=A, P(b)=B, P(c)=C
pt I(float a, pt A, float b, pt B, float c, pt C, float d, pt D, float t) {pt P=I(a,A,b,B,c,C,t); pt Q=I(b,B,c,C,d,D,t); return I(a,P,d,Q,t);} // P(a)=A, P(b)=B, P(c)=C, P(d)=D
pt I(float a, pt A, float b, pt B, float c, pt C, float d, pt D, float e, pt E, float t) {pt P=I(a,A,b,B,c,C,d,D,t); pt Q=I(b,B,c,C,d,D,e,E,t); return I(a,P,e,Q,t);}
// Interpolation proportional to Distance
pt D(pt A, pt B, pt C, float t) {float a=0, b=d(A,B), c=b+d(B,C); return I(a,A,b,B,c,C,a+t*(c-a));}
pt D(pt A, pt B, pt C, pt D, float t) {float a=0, b=d(A,B), c=b+d(B,C), d=c+d(C,D); return I(a,A,b,B,c,C,d,D,a+t*(d-a));}
pt D(pt A, pt B, pt C, pt D, pt E, float t) {float a=0, b=d(A,B), c=b+d(B,C), d=c+d(C,D), e=d+d(D,E); return I(a,A,b,B,c,C,d,D,e,E,a+t*(e-a));}
 */
