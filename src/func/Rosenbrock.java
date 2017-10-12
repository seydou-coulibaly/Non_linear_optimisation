package func;

import util.Vector;

public class Rosenbrock implements RealFunc {


	public double eval(Vector v) {
		return Math.pow((1-v.get(0)),2) + 100 * Math.pow( (v.get(1)-(Math.pow(v.get(0),2))),2);
		
	}
	
	public Vector grad(Vector v) {
		
		double x = 400 * (Math.pow(v.get(0),3)) + 2 * v.get(0) -400 * v.get(1)-2 ;
		double y = 200 * v.get(1) - 200 *  (Math.pow(v.get(0),2));		
		return new Vector(new double[] {x,y});
	}
	
	@Override
	public int dim() {
		return 2;
	}

	
}
