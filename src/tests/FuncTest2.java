package tests;

import util.Matrix;
import util.Singularity;
import util.Vector;
import func.RealFunc;

/**
 * Function x ->  x² if x<=-0.5 
 *                (1.5-x)-1.75  otherwise (!! linear function !!)
 * 
 * @author Gilles Chabert
 *
 */
public class FuncTest2 implements RealFunc {

	@Override
	public int dim() { 
		return 1; 
	}

	@Override
	public double eval(Vector x) {
		if (x.get(0)<=-0.5) return Math.pow(x.get(0),2);
		else return (1.5-x.get(0))-1.75;
	}

	@Override
	public Vector grad(Vector x) {
		if (x.get(0)<=-0.5) 
			return new Vector(new double[] {2*x.get(0)});
		else 
			return  new Vector(new double[] {-1});
	}	

	public static Vector x=new Vector(new double[] {-2});
	public static Vector d=new Vector(new double[] {1});

	// The value of alpha obtained after the 1st iteration of SlopeIter
	// applied on f2 with (x,d).
	public static double alpha2() {
		// Armijo applied with x and d gives 16.
		// The slope y=a*x+b satisfies
		//   -4 = a*(-2)+b
		//   -1 = a*(-2+16)+b
		double[][] M= {{-2,1},{14,1}};
		double x2=0; 
		try {
			// ab is the coefficients a and b of the line
			Vector ab = new Matrix(M).inverse().mult(new Vector(new double[] {-4,-1}));
			// x2 is the point where the line crosses 0, i.e., -b/a:
			x2=-ab.get(1)/ab.get(0);
		} catch (Singularity e) { }
		// So the second iteration should give (x2+2) 
		return x2+2.0;
	}

}
