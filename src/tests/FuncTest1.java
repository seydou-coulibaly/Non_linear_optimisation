package tests;

import util.Vector;
import func.RealFunc;

/**
 * Function (x,y) -> x² − xy + y²/2 - y
 * 
 * @author Gilles Chabert
 * 
 */
public class FuncTest1 implements RealFunc {
	
		@Override
		public int dim() { 
			return 2; 
		}

		@Override
		public double eval(Vector x) {
			double x1=x.get(0);
			double x2=x.get(1);
			return Math.pow(x1,2)-x1*x2+0.5*Math.pow(x2,2)-x2;
		}

		@Override
		public Vector grad(Vector x) {
			double x1=x.get(0);
			double x2=x.get(1);
			double[] g = { 2*x1-x2, -x1+x2-1 }; 
			return new Vector(g);
		}

		// ========================================================================================================//
		// the function f1(x0+alpha*d0) is alpha -> 5/2 alpha² -1.
		public static Vector x0=new Vector(new double[] {1,2});
		public static Vector d0=new Vector(new double[] {1,-1});

		// the function f1(x1+alpha*d1) is alpha -> 5/2 alpha² -1.
		public static Vector x1=new Vector(new double[] {1,2});
		public static Vector d1=new Vector(new double[] {2,1});

		// the function f1(x2+alpha*d2) is alpha -> 5/2 alpha² + 5 alpha +3/2.
		public static Vector x2=new Vector(new double[] {2,1});
		public static Vector d2=new Vector(new double[] {-1,1});
		// ========================================================================================================//

}
