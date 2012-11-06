// ===== vector class
class Vector { 
    float x;
    float y;
    float z;
    
    public Vector () {
        x = 0;
        y = 0;
        z = 0;
    }
    public Vector(float px, float py, float pz) {
        x = px; y = py; z = pz;
    }
    public Vector set(float px, float py, float pz) {
        x = px; y = py; z = pz; return this;
    } 
    public Vector set (Vector V) {
        x = V.x; y = V.y; z = V.z; return this;
    } 
    public Vector add(Vector V) {
        x+=V.x; y+=V.y; z+=V.z; return this;
        }
    public Vector add(float s, Vector V) {
        x+=s*V.x; y+=s*V.y; z+=s*V.z; return this;
    }
    public Vector sub(Vector V) {
        x-=V.x; y-=V.y; z-=V.z; return this;
    }
    public Vector mul(float f) {
        x*=f; y*=f; z*=f; return this;
    }
    public Vector div(float f) {
        x/=f; y/=f; z/=f; return this;
    }
    public Vector div(int f) {
        x/=f; y/=f; z/=f; return this;
    }
    public Vector rev() {
        x=-x; y=-y; z=-z; return this;
    }
    public float norm() {
        return (float) (Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)));
    } 
    public Vector normalize() {
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
    public static Vector V(Vector V) {
     // make copy of vector V
        return new Vector(V.x,V.y,V.z); 
    }
    
    public static Vector A(Vector A, Vector B) {
        // A+B    
        return new Vector(A.x+B.x,A.y+B.y,A.z+B.z);
    }
    public static Vector A(Vector U, float s, Vector V) {
        // U+sV
        return new Vector(U.x+s*V.x,U.y+s*V.y,U.z+s*V.z);
    }
    public static Vector M(Vector U, Vector V) {
        // U-V
        return new Vector(U.x-V.x,U.y-V.y,U.z-V.z);
    }
    public static Vector M(Vector V) {
        // -V
        return new Vector(-V.x,-V.y,-V.z);
    }
    public static Vector V(Vector A, Vector B) {
        // (A+B)/2
        float newX = (float) ((A.x+B.x)/2.0);
        float newY = (float) ((A.y+B.y)/2.0);
        float newZ = (float) ((A.z+B.z)/2.0);
        return new Vector(newX,newY,newZ); 
    }
    public static Vector V(Vector A, float s, Vector B) {
     // (1-s)A+sB
        return new Vector(A.x+s*(B.x-A.x),A.y+s*(B.y-A.y),A.z+s*(B.z-A.z));
    }
    public static Vector V(Vector A, Vector B, Vector C) {
        // (A+B+C)/3
        float newX = (float) ((A.x+B.x+C.x)/3.0);
        float newY = (float) ((A.y+B.y+C.y)/3.0);
        float newZ = (float) ((A.z+B.z+C.z)/3.0);
        return new Vector(newX,newY,newZ);
    }
    public static Vector V(Vector A, Vector B, Vector C, Vector D) {
        // (A+B+C+D)/4
        return V(V(A,B),V(C,D));
    }
    public static Vector V(float s, Vector A) {
        // sA
        return new Vector(s*A.x,s*A.y,s*A.z);
    }
    public static Vector V(float a, Vector A, float b, Vector B) {
        // aA+bB 
        return A(V(a,A),V(b,B));
    }
    public static Vector V(float a, Vector A, float b, Vector B, float c, Vector C) {
        // aA+bB+cC
        return A(V(a,A,b,B),V(c,C));
    }
    public static Vector V(Point P, Point Q) {
        // PQ
        return new Vector(Q.x-P.x,Q.y-P.y,Q.z-P.z);
    }
    public static Vector U(Vector V) {
        // V/||V||
        float n = V.norm();
        if (n<0.000001){
            return new Vector(0, 0, 0);
        }else{
            return V((float)(1./n),V);
        }
    }
    /*public static Vec U(pt A, pt B) {
        //TODO implement point
        return U(V(A,B));
    }*/
    public static Vector N(Vector U, Vector V) {
        // UxV CROSS PRODUCT (normal to both)
        return new Vector( U.y*V.z-U.z*V.y, U.z*V.x-U.x*V.z, U.x*V.y-U.y*V.x);
    }
    public static Vector N(Point A, Point B, Point C) {
        // normal to triangle (A,B,C), not normalized (proportional to area)
        return N(V(A,B),V(A,C));
    }
    Vector B(Vector U, Vector V) {
        // (UxV)xV unit normal to U in the plane UV
        return U(N(N(U,V),U));
    }
    Vector R(Vector V) {
        // rotated 90 degrees in XY plane
        return new Vector(-V.y,V.x,V.z);
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

