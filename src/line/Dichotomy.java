package line;

import solve.EndOfIteration;
import util.Vector;
import func.RealFunc;

/**
 * Perform a dichotomic line search (minimize xk+alpha*d).
 * 
 * The search starts from ALPHA_INIT and divide it by DICHO_RATIO at each step.
 * 
 * If alpha<=MIN_STEP, the descent is impossible: raise an error.
 */

public class Dichotomy extends LineSearch {

	final static double ALPHA_INIT=0.1;
	final static double MIN_STEP=1e-20;
	final static double DICHO_RATIO=1.1;
	
	/**
	 * Build the dichotomy for a function f.
	 */
	public Dichotomy(RealFunc f) {
		super(f);
	}
	
	@Override
	public void start(Vector x, Vector d) {
		super.start(x,d);
		iter_vec.set(0,ALPHA_INIT); 
	}
	
	@Override
	public void compute_next() throws EndOfIteration {
		double alpha=iter_vec.get(0);
		if (alpha<MIN_STEP) {
			throw new EndOfIteration();
		}
		else if (f.eval(x0.add(d.leftmul(alpha)))<f.eval(x0)) {		
			throw new EndOfIteration();
		}
		else {
			alpha=alpha/DICHO_RATIO;
			iter_vec.set(0,alpha);
		}
	}
	
}
