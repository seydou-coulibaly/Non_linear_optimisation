package tests;

import util.Vector;

import org.junit.Test;

import util.Matrix;
import func.AlmostDiag;
import func.Hilbert;
import func.QuadraForm;

public class TestQuadraForm {

	static Matrix Q=new Matrix(new double[][] {{2,-1},{-1,3}});
	static Vector x=new Vector(new double[] {-3,-3});
	static QuadraForm q = new QuadraForm(Q);
	
	@Test 
	public void testEval() {
		org.junit.Assert.assertEquals(q.eval(x),13.5,0.0);
	}
	
	@Test 
	public void testGrad() {
		org.junit.Assert.assertTrue(q.grad(x).equals(new Vector(new double[] {-3,-6})));
	}

	@Test 
	public void testDim() {
		org.junit.Assert.assertEquals(q.dim(),2);
	}

	@Test
	public void testAlmostDiag() {
		AlmostDiag a = new AlmostDiag(3);
		org.junit.Assert.assertEquals(1.0,    a.Q.get(0,0), 1e-20);
		org.junit.Assert.assertEquals(-1.0/3, a.Q.get(0,1), 1e-20);
		org.junit.Assert.assertEquals(-1.0/3, a.Q.get(0,2), 1e-20);
		org.junit.Assert.assertEquals(-1.0/3, a.Q.get(1,0), 1e-20);
		org.junit.Assert.assertEquals(1.0,    a.Q.get(1,1), 1e-20);
		org.junit.Assert.assertEquals(-1.0/3, a.Q.get(1,2), 1e-20);
		org.junit.Assert.assertEquals(-1.0/3, a.Q.get(2,0), 1e-20);
		org.junit.Assert.assertEquals(-1.0/3, a.Q.get(2,1), 1e-20);
		org.junit.Assert.assertEquals(1.0,    a.Q.get(2,2), 1e-20);
	}
	
	@Test
	public void testHilberg() {
		Hilbert h = new Hilbert(3);
		org.junit.Assert.assertEquals(1.0,   h.Q.get(0,0), 1e-20);
		org.junit.Assert.assertEquals(1.0/2, h.Q.get(0,1), 1e-20);
		org.junit.Assert.assertEquals(1.0/3, h.Q.get(0,2), 1e-20);
		org.junit.Assert.assertEquals(1.0/2, h.Q.get(1,0), 1e-20);
		org.junit.Assert.assertEquals(1.0/3, h.Q.get(1,1), 1e-20);
		org.junit.Assert.assertEquals(1.0/4, h.Q.get(1,2), 1e-20);
		org.junit.Assert.assertEquals(1.0/3, h.Q.get(2,0), 1e-20);
		org.junit.Assert.assertEquals(1.0/4, h.Q.get(2,1), 1e-20);
		org.junit.Assert.assertEquals(1.0/5, h.Q.get(2,2), 1e-20);
	}
	
}
