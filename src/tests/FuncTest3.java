package tests;

import util.Vector;
import func.RealFunc;

/**
 * Function x ->  -sin(x)-x^11
 */
public class FuncTest3 implements RealFunc {
	
	@Override
	public int dim() { 
		return 1; 
	}

	@Override
	public double eval(Vector x) {
		return Math.sin(-x.get(0)) - Math.pow(x.get(0),11);
	}

	@Override
	public Vector grad(Vector x) {
		return new Vector(new double[] {-Math.cos(x.get(0))-11*Math.pow(x.get(0),10)});
	}	
	
	public static Vector x=new Vector(new double[] {-2});
	public static Vector d=new Vector(new double[] {1});

};