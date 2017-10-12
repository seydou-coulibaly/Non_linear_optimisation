package solve;
import line.Dichotomy;
import line.LineSearch;
import func.RealFunc;
import util.Vector;

/**
 * Non-linear variant of the conjugate gradients.
 * 
 * @author Gilles Chabert
 *
 */
public class ConjugateGradients extends Algorithm {

	/**
	 * Build the algorithm for a given function and
	 * with an underlying line search technique.
	 * 
	 * @param f the function
	 * @param s the line search algorithm
	 */
	public ConjugateGradients(RealFunc f, LineSearch s) {

		/* TODO */
	
	}
	
	/**
	 * Start the iteration
	 */
	public void start(Vector x0) {
		super.start(x0);

		/* TODO */
		
	}
	
	/**
	 * Calculate the next iterate.
	 * 
	 * (update iter_vec).
	 */
	public void compute_next() throws EndOfIteration {

		/* TODO */
		
		iter_vec=null;
	}
}
