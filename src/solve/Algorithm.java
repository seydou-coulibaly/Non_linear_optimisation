package solve;

import java.util.ArrayList;

import util.Vector;

/**
 * Iterative local search algorithm.
 * 
 * Generates a sequence of vectors of R^n from a starting point.
 *
 * @author Gilles Chabert
 *
 */
public abstract class Algorithm {

	/**
	 * Log flag
	 */
	public boolean log=false;

	/**
	 * Current vector
	 */
	protected Vector iter_vec;
	
	/**
	 * First stop criterion: when distance between two vectors
	 * are less than "eps", convergence is assumed to be reached).
	 */
	public final static double DEFAULT_EPS=1e-20;
	private double eps=DEFAULT_EPS;

	/**
	 * Second stop criterion: when the number of iterations
	 * exceeds maxIteration, convergence is assumed to be reached.
	 */
	public final static int DEFAULT_MAX_ITERATION=10000;
	private int maxIteration = DEFAULT_MAX_ITERATION;

	/**
	 * Indicates when hasNext has been called (the next
	 * vector is then already calculated)
	 */
	private boolean just_computed=false;
	
	/**
	 * Iteration counter
	 */
	private int iter_nb;

	/**
	 * Calculate the next iterate.
	 * 
	 * Precondition:  the current iterate is stored in "iter_vec".
	 * 
	 * Postcondition: "iter_vec" is a reference to the next iterate. It can 
	 *                be obtained by calling current_vector(). If there is no 
	 *                more iterate, an exception "EndOfIteration" is thrown.
	 */
	protected abstract void compute_next() throws EndOfIteration;
	
	/**
	 * Return (a copy of) the current iterate (the last computed).
	 */
	public Vector current_vector() {
		return new Vector(iter_vec);
	}
	
	/**
	 * Return the current number of iterations.
	 */
	public int current_iteration() {
		return iter_nb;
	}
	
	/**
	 * Set the value of the stop criterion.
	 * 
	 * @param new_eps
	 */
	public void setEpsilon(double new_eps) {
		eps = new_eps;
	}
	
	/**
	 * Get the value of the stop criterion.
	 * 
	 */
	public double getEpsilon() {
		return eps;
	}
	
	/**
	 * Set the maximal number of iterations.
	 * 
	 * @param maxIteration
	 */
	public void setMaxIteration(int maxIteration) {
		this.maxIteration = maxIteration;
	}

	/**
	 * Get the maximal number of iterations.
	 *  
	 */
	public int getMaxIteration() {
		return maxIteration;
	}
	
	/**
	 * Start the iteration from init_vec (can be overloaded by 
	 * subclasses if additional data needs to
	 * be initialized)
	 */
	public void start(Vector init_vec) {
		iter_vec=init_vec;
		iter_nb=0;
		just_computed=false;
	}
		
	/**
	 * @return true iff the iteration is not over
	 */
	public boolean hasNext() {
		just_computed = (next()!=null);
		
		return just_computed;
	}
	
	/**
	 * Calculate and return the next iterate.
	 * and increment the number of iterations.
	 *
	 * Return null if iteration is over.
	 */
	public Vector next() {
		if (just_computed) {
			just_computed=false;
			return current_vector();
		}
		
		if (iter_nb==maxIteration) {
			if (log) System.out.println("[algorithm] abort: number of iterations exceeds maximum");
			return null;
		}
		try {
			Vector old_vec=current_vector(); // copy
			
			compute_next();
			
			if (iter_vec.sub(old_vec).norm()<eps) throw new EndOfIteration();
			
			iter_nb++;
			
			return current_vector();	
		} catch(EndOfIteration e) { // can be raised also by compute_next
			return null;
		}

	}
	
	/**
	 * Run the iteration until fix-point 
	 * and return the last iterate.
	 * 
	 * Usually preceded by a call to "start".
	 */
	public Vector search() {
		search(null);
		return current_vector();
	}
	
	/**
	 * Run the iteration until fix-point 
	 * and store all the iterates in an ArrayList.
	 *
	 * Usually preceded by a call to "start".
	 */
	public void search(ArrayList<Vector> a) {
		
		if (a!=null) a.add(current_vector());
		
		while (hasNext()) {
			Vector tmp=next();
			if (a!=null) a.add(tmp);
		}		
	}
}
