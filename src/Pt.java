// ===== point class
public class Pt {
    float x=0;
    float y=0;
    float z=0;

    public Pt(){
        x=0;
        y=0;
        z=0;
    }
    public Pt (float px, float py, float pz) {
        x = px;
        y = py;
        z = pz;
    }
    public Pt set(float px, float py, float pz) {
        x = px;
        y = py;
        z = pz;
        return this;
    }
    public Pt set(Pt P) {
        x = P.x;
        y = P.y;
        z = P.z;
        return this;
    }
    public Pt add(Pt P) {
        x+=P.x;
        y+=P.y;
        z+=P.z;
        return this;
    }
    public Pt add(Vec V) {
        x+=V.x;
        y+=V.y;
        z+=V.z;
        return this;
    }
    public Pt add(float s, Vec V) {
        x+=s*V.x;
        y+=s*V.y;
        z+=s*V.z;
        return this;
    }
    public Pt add(float dx, float dy, float dz) {
        x+=dx;
        y+=dy;
        z+=dz;
        return this;
    }
    public Pt sub(Pt P) {
        x-=P.x;
        y-=P.y;
        z-=P.z;
        return this;
    }
    public Pt mul(float f) {
        x*=f;
        y*=f;
        z*=f;
        return this;
    }
    public Pt mul(float dx, float dy, float dz) {
        x*=dx;
        y*=dy;
        z*=dz;
        return this;
    }
    public Pt div(float f) {
        x/=f;
        y/=f;
        z/=f;
        return this;
    }
    public Pt div(int f) {
        x/=f;
        y/=f;
        z/=f;
        return this;
    }
    public Pt snap(float r) {
        float f=(float) (r/(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2))));
        x*=f;
        y*=f;
        z*=f;
        return this;
    }
    // =====  point functions
    public static Pt P(){
        // point 0,0,0
        return new Pt();
    }
    public static Pt P(float x, float y, float z){
        // point (x,y,z)
        return new Pt(x,y,z);
    }
    public static Pt P(Pt A) {
        // copy of point P
        return new Pt(A.x,A.y,A.z);
    }
    public static Pt P(Pt A, float s, Pt B) {
        // A+sAB
        return new Pt(A.x+s*(B.x-A.x),A.y+s*(B.y-A.y),A.z+s*(B.z-A.z));
    }
    public static Pt P(Pt A, Pt B) {
        // (A+B)/2
        float newX = (float) ((A.x+B.x)/2.0);
        float newY = (float) ((A.y+B.y)/2.0);
        float newZ = (float) ((A.z+B.z)/2.0);
        return P(newX,newY,newZ);
    }
    public static Pt P(Pt A, Pt B, Pt C) {
        // (A+B+C)/3
        float newX = (float) ((A.x+B.x+C.x)/3.0);
        float newY = (float) ((A.y+B.y+C.y)/3.0);
        float newZ = (float) ((A.z+B.z+C.z)/3.0);
        return new Pt(newX,newY,(newZ));
    }
    public static Pt P(Pt A, Pt B, Pt C, Pt D) {
        // (A+B+C+D)/4
        return P(P(A,B),P(C,D));
    }
    public static Pt P(float s, Pt A) {
        // sA
        return new Pt(s*A.x,s*A.y,s*A.z);
    }
    public static Pt A(Pt A, Pt B) {
        // A+B
        return new Pt(A.x+B.x,A.y+B.y,A.z+B.z);
    }
    public static Pt P(float a, Pt A, float b, Pt B) {
        // aA+bB
        return A(P(a,A),P(b,B));
    }
    public static Pt P(float a, Pt A, float b, Pt B, float c, Pt C) {
        // aA+bB+cC
        return A(P(a,A),P(b,B,c,C));
    }
    public static Pt P(float a, Pt A, float b, Pt B, float c, Pt C, float d, Pt D){
        // aA+bB+cC+dD
        return A(P(a,A,b,B),P(c,C,d,D));
    }
    public static Pt P(Pt P, Vec V) {
        // P+V
        return new Pt(P.x + V.x, P.y + V.y, P.z + V.z);
    }
    public static Pt P(Pt P, float s, Vec V) {
        // P+sV
        return new Pt(P.x+s*V.x,P.y+s*V.y,P.z+s*V.z);
    }
    public static Pt P(Pt O, float x, Vec I, float y, Vec J) {
        // O+xI+yJ
        return P(O.x+x*I.x+y*J.x,O.y+x*I.y+y*J.y,O.z+x*I.z+y*J.z);
    }
    public static Pt P(Pt O, float x, Vec I, float y, Vec J, float z, Vec K) {
        // O+xI+yJ+kZ
        return P(O.x+x*I.x+y*J.x+z*K.x,O.y+x*I.y+y*J.y+z*K.y,O.z+x*I.z+y*J.z+z*K.z);
    }
    public static Pt R(Pt P, float a, Vec I, Vec J, Pt G) {
        // Rotated P by a around G in plane (I,J)
        float x = d(Vec.V(G,P),I);
        float y = d(Vec.V(G,P),J);
        float c = (float) Math.cos(a);
        float s = (float) Math.sin(a);
        return P(P,x*c-x-y*s,I,x*s+y*c-y,J);
    }
    public static void makePts(Pt[] C) { //TODO change return type
        // fills array C with points initialized to (0,0,0)
        for(int i=0; i<C.length; i++){
            C[i]=P();
        }
    }
    public static Pt Predict(Pt A, Pt B, Pt C) {
        // B+AC, parallelogram predictor
        return P(B,Vec.V(A,C));
    }
    /*public static void v(Pt P) { //TODO figure out what this does
        // rendering
        vertex(P.x,P.y,P.z);
    }*/
// ===== measures
    public static float d(Vec U, Vec V) {
        //U*V dot product
        return U.x*V.x+U.y*V.y+U.z*V.z;
    }
    public static float d(Pt P, Pt Q) {
        // ||AB|| distance
        return (float) Math.sqrt(Math.pow(Q.x-P.x,2)+Math.pow(Q.y-P.y,2)+Math.pow(Q.z-P.z,2));
    }
    public static float d2(Pt P, Pt Q) {
        // AB^2 distance squared
        return (float) (Math.pow(Q.x-P.x,2)+Math.pow(Q.y-P.y,2)+Math.pow(Q.z-P.z,2));
    }
    public static float m(Vec U, Vec V, Vec W) {
        // (UxV)*W  mixed product, determinant
        return d(U,Vec.N(V,W));
    }
    public static float m(Pt E, Pt A, Pt B, Pt C) {
        // det (EA EB EC) is >0 when E sees (A,B,C) clockwise
        return m(Vec.V(E,A),Vec.V(E,B),Vec.V(E,C));
    }
    public static float n2(Vec V) {
        // V*V    norm squared
        return (float) (Math.pow(V.x,2)+Math.pow(V.y,2)+Math.pow(V.z,2));
    }
    public static float n(Vec V) {
        // ||V||  norm
        return (float) Math.pow(n2(V), .5);
    }
    public static float area(Pt A, Pt B, Pt C) {
        // area of triangle
        return n(Vec.N(A,B,C))/2;
    }
    public static float volume(Pt A, Pt B, Pt C, Pt D) {
        // volume of tet
        return m(Vec.V(A,B),Vec.V(A,C),Vec.V(A,D))/6;
    }
    public static boolean parallel (Vec U, Vec V) {
        // true if U and V are almost parallel
        return n(Vec.N(U,V))<n(U)*n(V)*0.00001;
    }
    public static float angle(Vec U, Vec V) {
        // angle(U,V)
        return (float) Math.acos(d(U,V)/n(V)/n(U));
    }
    public static boolean cw(Vec U, Vec V, Vec W) {
        // (UxV)*W>0  U,V,W are clockwise
        return m(U,V,W)>=0;
    }
    public static boolean cw(Pt A, Pt B, Pt C, Pt D) {
        // tet is oriented so that A sees B, C, D clockwise
        return volume(A,B,C,D)>=0;
    }
}

/*
// ===== rotate

// ===== render
void normal(vec V) {normal(V.x,V.y,V.z);};                                          // changes normal for smooth shading
void vertex(pt P) {vertex(P.x,P.y,P.z);};                                           // vertex for shading or drawing
void vTextured(pt P, float u, float v) {vertex(P.x,P.y,P.z,u,v);};                          // vertex with texture coordinates
void show(pt P, pt Q) {line(Q.x,Q.y,Q.z,P.x,P.y,P.z); };                       // draws edge (P,Q)
void show(pt P, vec V) {line(P.x,P.y,P.z,P.x+V.x,P.y+V.y,P.z+V.z); };          // shows edge from P to P+V
void show(pt P, float d , vec V) {line(P.x,P.y,P.z,P.x+d*V.x,P.y+d*V.y,P.z+d*V.z); }; // shows edge from P to P+dV
void show(pt A, pt B, pt C) {beginShape(); vertex(A);vertex(B); vertex(C); endShape(CLOSE);};                      // volume of tet
void show(pt A, pt B, pt C, pt D) {beginShape(); vertex(A); vertex(B); vertex(C); vertex(D); endShape(CLOSE);};                      // volume of tet
void show(pt P, float r) {pushMatrix(); translate(P.x,P.y,P.z); sphere(r); popMatrix();}; // render sphere of radius r and center P
void show(pt P, float s, vec I, vec J, vec K) {noStroke(); fill(yellow); show(P,5); stroke(red); show(P,s,I); stroke(green); show(P,s,J); stroke(blue); show(P,s,K); }; // render sphere of radius r and center P
void show(pt P, String s) {text(s, P.x, P.y, P.z); }; // prints string s in 3D at P
void show(pt P, String s, vec D) {text(s, P.x+D.x, P.y+D.y, P.z+D.z);  }; // prints string s in 3D at P+D

// ==== curve
void bezier(pt A, pt B, pt C, pt D) {bezier(A.x,A.y,A.z,B.x,B.y,B.z,C.x,C.y,C.z,D.x,D.y,D.z);} // draws a cubic Bezier curve with control points A, B, C, D
void bezier(pt [] C) {bezier(C[0],C[1],C[2],C[3]);} // draws a cubic Bezier curve with control points A, B, C, D
pt bezierPoint(pt[] C, float t) {return P(bezierPoint(C[0].x,C[1].x,C[2].x,C[3].x,t),bezierPoint(C[0].y,C[1].y,C[2].y,C[3].y,t),bezierPoint(C[0].z,C[1].z,C[2].z,C[3].z,t)); }
vec bezierTangent(pt[] C, float t) {return V(bezierTangent(C[0].x,C[1].x,C[2].x,C[3].x,t),bezierTangent(C[0].y,C[1].y,C[2].y,C[3].y,t),bezierTangent(C[0].z,C[1].z,C[2].z,C[3].z,t)); }
void PT(pt P0, vec T0, pt P1, vec T1) {float d=d(P0,P1)/3;  bezier(P0, P(P0,-d,U(T0)), P(P1,-d,U(T1)), P1);} // draws cubic Bezier interpolating  (P0,T0) and  (P1,T1)
void PTtoBezier(pt P0, vec T0, pt P1, vec T1, pt [] C) {float d=d(P0,P1)/3;  C[0].set(P0); C[1].set(P(P0,-d,U(T0))); C[2].set(P(P1,-d,U(T1))); C[3].set(P1);} // draws cubic Bezier interpolating  (P0,T0) and  (P1,T1)
vec vecToCubic (pt A, pt B, pt C, pt D, pt E) {return V( (-A.x+4*B.x-6*C.x+4*D.x-E.x)/6, (-A.y+4*B.y-6*C.y+4*D.y-E.y)/6, (-A.z+4*B.z-6*C.z+4*D.z-E.z)/6);}
vec vecToProp (pt B, pt C, pt D) {float cb=d(C,B);  float cd=d(C,D); return V(C,P(B,cb/(cb+cd),D)); };

// ==== perspective
pt Pers(pt P, float d) { return P(d*P.x/(d+P.z) , d*P.y/(d+P.z) , d*P.z/(d+P.z) ); };

pt InverserPers(pt P, float d) { return P(d*P.x/(d-P.z) , d*P.y/(d-P.z) , d*P.z/(d-P.z) ); };

// ==== intersection
boolean intersect(pt P, pt Q, pt A, pt B, pt C, pt X)  {return intersect(P,V(P,Q),A,B,C,X); } // if (P,Q) intersects (A,B,C), return true and set X to the intersection point

boolean intersect(pt E, vec T, pt A, pt B, pt C, pt X) { // if ray from E along T intersects triangle (A,B,C), return true and set X to the intersection point
  vec EA=V(E,A), EB=V(E,B), EC=V(E,C), AB=V(A,B), AC=V(A,C);
  boolean s=cw(EA,EB,EC), sA=cw(T,EB,EC), sB=cw(EA,T,EC), sC=cw(EA,EB,T);
  if ( (s==sA) && (s==sB) && (s==sC) ) return false;
  float t = m(EA,AC,AB) / m(T,AC,AB);
  X.set(P(E,t,T));
  return true;
  }

boolean rayIntersectsTriangle(pt E, vec T, pt A, pt B, pt C) { // true if ray from E with direction T hits triangle (A,B,C)
  vec EA=V(E,A), EB=V(E,B), EC=V(E,C);
  boolean s=cw(EA,EB,EC), sA=cw(T,EB,EC), sB=cw(EA,T,EC), sC=cw(EA,EB,T);
  return  (s==sA) && (s==sB) && (s==sC) ;}

boolean edgeIntersectsTriangle(pt P, pt Q, pt A, pt B, pt C)  {
  vec PA=V(P,A), PQ=V(P,Q), PB=V(P,B), PC=V(P,C), QA=V(Q,A), QB=V(Q,B), QC=V(Q,C);
  boolean p=cw(PA,PB,PC), q=cw(QA,QB,QC), a=cw(PQ,PB,PC), b=cw(PA,PQ,PC), c=cw(PQ,PB,PQ);
  return (p!=q) && (p==a) && (p==b) && (p==c);
  }

float rayParameterToIntersection(pt E, vec T, pt A, pt B, pt C) {vec AE=V(A,E), AB=V(A,B), AC=V(A,C); return - m(AE,AC,AB) / m(T,AC,AB);}

float angleDraggedAround(pt G) {  // returns angle in 2D dragged by the mouse around the screen projection of G
   pt S=P(screenX(G.x,G.y,G.z),screenY(G.x,G.y,G.z),0);
   vec T=V(S,Pmouse()); vec U=V(S,Mouse());
   return atan2(d(R(U),T),d(U,T));
   }

float toRad(float a) {return(a*PI/180);}
int toDeg(float a) {return int(a*180/PI);}

void showShrunkOffset(pt A, pt B, pt C, float e, float h) {vec N=U(N(V(A,B),V(A,C))); showShrunk(P(A,h,N),P(B,h,N),P(C,h,N),e);} // offset by h along normal

void showShrunk(pt A, pt B, pt C, float e) { // shrink by e
   vec AB = U(V(A,B)), BC = U(V(B,C)), CA = U(V(C,A));
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
