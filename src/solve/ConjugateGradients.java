package solve;
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
	
	Vector dk ;
	Vector xk ;
	
	private RealFunc f;
	private LineSearch s;

	/**
	 * Build the algorithm for a given function and
	 * with an underlying line search technique.
	 * 
	 * @param f the function
	 * @param s the line search algorithm
	 */
	public ConjugateGradients(RealFunc f, LineSearch s) {

		/* TODO */
		this.f = f;
		this.s = s;
	
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
		
		xk = iter_vec;		
		if(current_iteration()==0){
			dk = f.grad(xk).leftmul(-1);
		}
		
		if(f.grad(xk).norm()<= getEpsilon()) {
			if (log) System.out.println("[ConjugateGradients] exit: Trop proche du pas optimal pour itérer");
			throw new EndOfIteration();
		}
		
		double alpha = s.search(xk,dk);
		Vector xk1 = xk.add(dk.leftmul(alpha));
		//Mettre à jour xk et dk
		double beta = ((Math.pow(f.grad(xk1).norm(), 2)) / (Math.pow(f.grad(xk).norm(), 2))) ;
		dk = f.grad(xk1).leftmul(-1).add(dk.leftmul(beta));
		iter_vec=xk1;
	}
}
