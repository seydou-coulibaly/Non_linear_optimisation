package tests;

import org.junit.Test;
import util.Vector;
import line.LineSearch;

public class TestLineSearch {

	public static double round_error = 1e-10;
	

	
	// A line search that does nothing
	public static LineSearch s = new LineSearch(new FuncTest1()) {
		public void compute_next() { }
	};

	@Test
	public void testLineSearch01() {
		Vector x=new Vector(new double[] {0,0});
		Vector d=new Vector(new double[] {0,1});
		double alpha = 1.0;
		
		org.junit.Assert.assertEquals("checking LineSearch.eval:", -0.5, s.eval(x, d, alpha), round_error);
	}
	
	@Test
	public void testLineSearch02() {
		org.junit.Assert.assertEquals("checking LineSearch.eval:", 1.5, s.eval(FuncTest1.x0, FuncTest1.d0, 1.0), round_error);
	}
	
	@Test
	public void testLineSearch03() {
		org.junit.Assert.assertEquals("checking LineSearch.eval:", 1.5, s.eval(FuncTest1.x1, FuncTest1.d1, 1.0), round_error);
	}

	@Test
	public void testLineSearch04() {
		org.junit.Assert.assertEquals("checking LineSearch.derivative:", 5, s.derivative(FuncTest1.x0, FuncTest1.d0, 1.0), round_error);
	}
	
	@Test
	public void testLineSearch05() {
		org.junit.Assert.assertEquals("checking LineSearch.derivative:", 5, s.derivative(FuncTest1.x1, FuncTest1.d1, 1.0), round_error);
	}
	
	@Test
	public void testLineSearch06() {
		org.junit.Assert.assertEquals("checking LineSearch.derivative:", -5, s.derivative(FuncTest1.x2, FuncTest1.d2, 0), round_error);
	}
	
}
