import static org.junit.Assert.*;

import org.junit.Test;


public class PtTest {
    double EPS = .00000000000001;

    @Test
    public void testPt() {
        Pt p = new Pt();
        assertEquals(p.x, 0, EPS);
        assertEquals(p.y, 0, EPS);
        assertEquals(p.z, 0, EPS);
    }

    @Test
    public void testPtFloatFloatFloat() {
        Pt p = new Pt(1.0f, 2.0f, 3.0f);
        assertEquals(p.x, 1.0f, EPS);
        assertEquals(p.y, 2.0f, EPS);
        assertEquals(p.z, 3.0f, EPS);
    }

    @Test
    public void testSetFloatFloatFloat() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddPt() {
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
    public void testPPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPtFloatPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPtPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPtPtPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPtPtPtPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testA() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatPtFloatPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatPtFloatPtFloatPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testPFloatPtFloatPtFloatPtFloatPt() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPtVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPtFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPtFloatVecFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testPPtFloatVecFloatVecFloatVec() {
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
    public void testDPtPt() {
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
    public void testMPtPtPtPt() {
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
    public void testCwPtPtPtPt() {
        fail("Not yet implemented");
    }

}
