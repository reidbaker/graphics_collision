import static org.junit.Assert.*;

import org.junit.Test;


public class PointTest {
    double EPS = .00000000000001;

    @Test
    public void testPoint() {
        Point p = new Point();
        assertEquals(p.x, 0, EPS);
        assertEquals(p.y, 0, EPS);
        assertEquals(p.z, 0, EPS);
    }

    @Test
    public void testSubdivision() {
        Point p0 = new Point(0.0f, 0.0f, 0.0f);
        Point p1 = new Point(1.0f, 0.0f, 0.0f);
        Point p2 = new Point(2.0f, 0.0f, 0.0f);
        Point p3 = new Point(3.0f, 0.0f, 0.0f);
        Point out = Point.subdivision(p0, p1, p2, p3);
        assertEquals(out.x, 1.5f, EPS);
        assertEquals(out.y, 0.0f, EPS);
        assertEquals(out.z, 0.0f, EPS);
    }

    @Test
    public void testPointFloatFloatFloat() {
        Point p = new Point(1.0f, 2.0f, 3.0f);
        assertEquals(p.x, 1.0f, EPS);
        assertEquals(p.y, 2.0f, EPS);
        assertEquals(p.z, 3.0f, EPS);
    }

    @Test
    public void testAddPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddFloatFloatFloat() {
        fail("Not yet implemented");
    }

    @Test
    public void testSub() {
        fail("Not yet implemented");
    }

    @Test
    public void testMulFloat() {
        fail("Not yet implemented");
    }

    @Test
    public void testMulFloatFloatFloat() {
        fail("Not yet implemented");
    }

    @Test
    public void testDivFloat() {
        fail("Not yet implemented");
    }

    @Test
    public void testDivInt() {
        fail("Not yet implemented");
    }

    @Test
    public void testSnap() {
        fail("Not yet implemented");
    }

    @Test
    public void testP() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatFloatFloat() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testMidPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPointPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPointPointPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPointPointPointPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testA() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatPointFloatPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatPointFloatPointFloatPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatPointFloatPointFloatPointFloatPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPointVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPointFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPointFloatVecFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPointFloatVecFloatVecFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testR() {
        fail("Not yet implemented");
    }

    @Test
    public void testMakePts() {
        fail("Not yet implemented");
    }

    @Test
    public void testPredict() {
        fail("Not yet implemented");
    }

    @Test
    public void testDVecVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testDPointPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testD2() {
        fail("Not yet implemented");
    }

    @Test
    public void testMVecVecVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testMPointPointPointPoint() {
        fail("Not yet implemented");
    }

    @Test
    public void testN2() {
        fail("Not yet implemented");
    }

    @Test
    public void testN() {
        fail("Not yet implemented");
    }

    @Test
    public void testArea() {
        fail("Not yet implemented");
    }

    @Test
    public void testVolume() {
        fail("Not yet implemented");
    }

    @Test
    public void testParallel() {
        fail("Not yet implemented");
    }

    @Test
    public void testAngle() {
        fail("Not yet implemented");
    }

    @Test
    public void testCwVecVecVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testCwPointPointPointPoint() {
        fail("Not yet implemented");
    }

}
