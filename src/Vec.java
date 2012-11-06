// ===== vector class
class Vec {
    float x;
    float y;
    float z;

    public Vec () {
        x = 0;
        y = 0;
        z = 0;
    }
    public Vec(float px, float py, float pz) {
        x = px; y = py; z = pz;
    }
    public Vec set(float px, float py, float pz) {
        x = px; y = py; z = pz; return this;
    }
    public Vec set (Vec V) {
        x = V.x; y = V.y; z = V.z; return this;
    }
    public Vec add(Vec V) {
        x+=V.x; y+=V.y; z+=V.z; return this;
        }
    public Vec add(float s, Vec V) {
        x+=s*V.x; y+=s*V.y; z+=s*V.z; return this;
    }
    public Vec sub(Vec V) {
        x-=V.x; y-=V.y; z-=V.z; return this;
    }

    public static Vector sub(Pt a, Pt b) {
	    return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
	}

    public Vec mul(float f) {
        x*=f; y*=f; z*=f; return this;
    }
    public Vec div(float f) {
        x/=f; y/=f; z/=f; return this;
    }
    public Vec div(int f) {
        x/=f; y/=f; z/=f; return this;
    }
    public Vec rev() {
        x=-x; y=-y; z=-z; return this;
    }
    public float norm() {
        return (float) (Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)));
    }
    public Vec normalize() {
        float n=norm();
        if (n>0.000001) {
            div(n);
        }
        return this;
    }
    /*public Vec rotate(float a, Vec I, Vec J) {
        // Rotate by a in plane (I,J)
        float x=d(this,I); //TODO Make dot product
        float y=d(this,J); //TODO Make dot product
        float c=(float) Math.cos(a);
        float s=(float) Math.sin(a);
        add(x*c-x-y*s,I);
        add(x*s+y*c-y,J);
        return this;
    }*/

    //Vector functions
    public static Vec V(Vec V) {
     // make copy of vector V
        return new Vec(V.x,V.y,V.z);
    }

    public static Vec A(Vec A, Vec B) {
        // A+B
        return new Vec(A.x+B.x,A.y+B.y,A.z+B.z);
    }
    public static Vec A(Vec U, float s, Vec V) {
        // U+sV
        return new Vec(U.x+s*V.x,U.y+s*V.y,U.z+s*V.z);
    }
    public static Vec M(Vec U, Vec V) {
        // U-V
        return new Vec(U.x-V.x,U.y-V.y,U.z-V.z);
    }
    public static Vec M(Vec V) {
        // -V
        return new Vec(-V.x,-V.y,-V.z);
    }
    public static Vec V(Vec A, Vec B) {
        // (A+B)/2
        float newX = (float) ((A.x+B.x)/2.0);
        float newY = (float) ((A.y+B.y)/2.0);
        float newZ = (float) ((A.z+B.z)/2.0);
        return new Vec(newX,newY,newZ);
    }
    public static Vec V(Vec A, float s, Vec B) {
     // (1-s)A+sB
        return new Vec(A.x+s*(B.x-A.x),A.y+s*(B.y-A.y),A.z+s*(B.z-A.z));
    }
    public static Vec V(Vec A, Vec B, Vec C) {
        // (A+B+C)/3
        float newX = (float) ((A.x+B.x+C.x)/3.0);
        float newY = (float) ((A.y+B.y+C.y)/3.0);
        float newZ = (float) ((A.z+B.z+C.z)/3.0);
        return new Vec(newX,newY,newZ);
    }
    public static Vec V(Vec A, Vec B, Vec C, Vec D) {
        // (A+B+C+D)/4
        return V(V(A,B),V(C,D));
    }
    public static Vec V(float s, Vec A) {
        // sA
        return new Vec(s*A.x,s*A.y,s*A.z);
    }
    public static Vec V(float a, Vec A, float b, Vec B) {
        // aA+bB
        return A(V(a,A),V(b,B));
    }
    public static Vec V(float a, Vec A, float b, Vec B, float c, Vec C) {
        // aA+bB+cC
        return A(V(a,A,b,B),V(c,C));
    }
    public static Vec V(Pt P, Pt Q) {
        // PQ
        return new Vec(Q.x-P.x,Q.y-P.y,Q.z-P.z);
    }
    public static Vec U(Vec V) {
        // V/||V||
        float n = V.norm();
        if (n<0.000001){
            return new Vec(0, 0, 0);
        }else{
            return V((float)(1./n),V);
        }
    }
    /*public static Vec U(pt A, pt B) {
        //TODO implement Pt
        return U(V(A,B));
    }*/
    public static Vec N(Vec U, Vec V) {
        // UxV CROSS PRODUCT (normal to both)
        return new Vec( U.y*V.z-U.z*V.y, U.z*V.x-U.x*V.z, U.x*V.y-U.y*V.x);
    }
    public static Vec N(Pt A, Pt B, Pt C) {
        // normal to triangle (A,B,C), not normalized (proportional to area)
        return N(V(A,B),V(A,C));
    }
    Vec B(Vec U, Vec V) {
        // (UxV)xV unit normal to U in the plane UV
        return U(N(N(U,V),U));
    }
    Vec R(Vec V) {
        // rotated 90 degrees in XY plane
        return new Vec(-V.y,V.x,V.z);
    }
    /*Vec R(Vec V, float a, Vec I, Vec J) {
     // Rotated V by a parallel to plane (I,J)
        float x=d(V,I); //TODO implement dot product
        float y=d(V,J);
        float c=Math.cos(a);
        float s=Math.sin(a);
        return A(V,V(x*c-x-y*s,I,x*s+y*c-y,J));
    }*/
}
