package line;

import util.Vector;
import func.RealFunc;
import solve.Algorithm;

/**
 * Line search algorithm.
 * 
 * Minimizes f(x+alpha*d).
 * 
 * @author Gilles Chabert
 */
public abstract class LineSearch extends Algorithm {

	/**
	 * The function to minimize.
	 */
	protected RealFunc f;
	
	/**
	 * Point of the current search (given by "start")
	 */
	protected Vector x0;
	
	/**
	 * Direction of the current search (given by "start")
	 */
	protected Vector d;
	
	/**
	 * Build a new line search algorithm
	 * for the function f
	 */
	public LineSearch(RealFunc f) {
		this.f = f;
	}
	
	/**
	 * Start a new search
	 */
	public void start(Vector x, Vector d) {
		this.x0=x;
		this.d=d;
		double alpha0 = 0;
		super.start(new Vector(new double[]{alpha0}));
	}
	
	
	/**
	 * Start and run the iteration until fixpoint.
	 * @return the best alpha
	 */
	public double search(Vector x, Vector d) {
		start(x,d);
		return search().get(0);
	}
	
	/**
	 * Return the value of f(x+alpha*d).
	 */
	public double eval(Vector x, Vector d, double alpha) {		
		return f.eval(x.add(d.leftmul(alpha)));
	}
	
	/**
	 * Return the derivative of the restriction of the function
	 * to the line passing by x and directed by d, at x, that is:
	 * 
	 * g'(alpha) with g(alpha)=f(x+alpha*d).
	 */
	public double derivative(Vector x, Vector d, double alpha) {
		return f.grad(x.add(d.leftmul(alpha))).scalar(d);
	}

}
