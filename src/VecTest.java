import static org.junit.Assert.*;

import org.junit.Test;


public class VecTest {
    double EPS = .00000000000001; 

    @Test
    public void testVec() {
        Vec v = new Vec();
        assertEquals(v.x, 0, EPS);
        assertEquals(v.y, 0, EPS);
        assertEquals(v.z, 0, EPS);
    }

    @Test
    public void testVecFloatFloatFloat() {
        Vec v = new Vec(1.0f, 2.0f,3.0f);
        assertEquals(v.x, 1.0f, EPS);
        assertEquals(v.y, 2.0f, EPS);
        assertEquals(v.z, 3.0f, EPS);
    }

    @Test
    public void testSetFloatFloatFloat() {
        Vec v = new Vec(1.0f, 2.0f,3.0f);
        v.set(3.5f, 2.5f, 1.5f);
        assertEquals(v.x, 3.5f, EPS);
        assertEquals(v.y, 2.5f, EPS);
        assertEquals(v.z, 1.5f, EPS);
    }

    @Test
    public void testSetVec() {
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
    public void testSub() {
        fail("Not yet implemented");
    }

    @Test
    public void testMul() {
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
    public void testRev() {
        fail("Not yet implemented");
    }

    @Test
    public void testNorm() {
        fail("Not yet implemented");
    }

    @Test
    public void testNormalize() {
        fail("Not yet implemented");
    }

    @Test
    public void testVVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testAVecVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testAVecFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testMVecVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testMVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testVVecVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testVVecFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testVVecVecVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testVVecVecVecVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testVFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testVFloatVecFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testVFloatVecFloatVecFloatVec() {
        fail("Not yet implemented");
    }

    @Test
    public void testU() {
        fail("Not yet implemented");
    }

    @Test
    public void testN() {
        fail("Not yet implemented");
    }

    @Test
    public void testB() {
        fail("Not yet implemented");
    }

    @Test
    public void testR() {
        fail("Not yet implemented");
    }

}
