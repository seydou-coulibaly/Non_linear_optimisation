package line;

import solve.EndOfIteration;
import util.Vector;
import func.RealFunc;

/**
 * Slope iteration for non-linear line search.
 *
 * It is a two-step methods, so the first iteration 
 * has a specific behavior (Armijo)
 * 
 * @author Gilles Chabert
 */
public class SlopeIter extends LineSearch {
	
	public final static double EPS_ARMIJO=0.2;
	public final static double ETA_ARMIJO=2;
	public final static double H0_ARMIJO=1;
	public final static double HMIN_ARMIJO=1e-20;
	public final static double HMAX_ARMIJO=10;
	
	/**
	 * Value of alpha_{k-1}.
	 */
	protected double alpha1; 
	
	/**
	 * Slope of alpha_{k-1}
	 */
	protected double slope1;
	
	/**
	 * Slope of alpha_k
	 */
	protected double slope;
	
	/**
	 * Build the slope iteration algorithm for a function f.
	 */
	public SlopeIter(RealFunc f) {
		super(f);
	}

	/**
	 * Important precondition: the slope must be negative at alpha=0.
	 *  
	 */
	public double armijo(Vector x, Vector d) {
		double h=H0_ARMIJO;
		double f0=eval(x,d,0);
		double fp0=derivative(x,d,0);
				
		while (eval(x,d,h)>f0+fp0*EPS_ARMIJO*h && h>HMIN_ARMIJO) {
			h = h / ETA_ARMIJO;
		}
		 
		if (h==H0_ARMIJO) {
			
			while (eval(x,d,ETA_ARMIJO*h)<f0+fp0*EPS_ARMIJO*ETA_ARMIJO*h && h<HMAX_ARMIJO) {
				h = h * ETA_ARMIJO;
			}
		}
		
		return h;
	}
		
	/**
	 * See Algorithm
	 */
	@Override
	public void compute_next() throws EndOfIteration {
		/*
		 * In a line search, the vector iter_vec
		 * has only one component (alpha).
		 */
		double alpha = iter_vec.get(0); // get the current value of alpha
		
		if (current_iteration()==0) {
			alpha1=alpha;
			slope1=derivative(x0,d,alpha1);
			
			if (Math.abs(slope1)<getEpsilon()) {
				if (log) System.out.println("[slopeiter] exit: initial slope~0.");
				throw new EndOfIteration();
			}
			
			// warning! a line search is oriented (it is a half-line search)
			if (slope1>0) {
				if (log) System.out.println("[slopeiter] abort: initial slope>0.");
				throw new EndOfIteration();
			}

			alpha=armijo(x0, d);

			if (alpha<0) {
				if (log) System.out.println("[slopeiter] abort: Armijo moves backward.");
				throw new EndOfIteration();
			} 
			
			iter_vec.set(0,alpha);  // set the new value of alpha
		}
		else {
			slope=derivative(x0,d,alpha);

			if (Math.abs(slope)<getEpsilon()) {
				if (log) System.out.println("[slopeiter] normal exit: slope~0.");
				throw new EndOfIteration();
			}

			if (Math.abs(slope1-slope)<getEpsilon()) {
				if (log) System.out.println("[slopeiter] abort: too closed slopes");
				throw new EndOfIteration();
			}

			// new value for alpha
			double alpha2=alpha1-(alpha1-alpha)/(slope1-slope)*slope1;

			if (Double.isNaN(alpha2)) {
				if (log) System.out.println("[slopeiter] abort: NaN");
				throw new EndOfIteration();
			}
			
			alpha1=alpha;
			slope1=slope;
			iter_vec.set(0,alpha2);  // set the new value of alpha
		}
	}
}
