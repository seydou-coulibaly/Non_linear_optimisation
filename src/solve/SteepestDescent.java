package solve;
//import line.Dichotomy;
import line.LineSearch;
import func.RealFunc;
import util.Vector;

/**
 * Basic steepest descent algorithm for unconstrained minimization problem.
 * 
 * @author Gilles Chabert
 */

public class SteepestDescent extends Algorithm {
	
	private RealFunc f;
	private LineSearch s;
	//private Dichotomy dicho;
	
	
	/**
	 * Build the algorithm
	 * 
	 * @param f function to minimize
	 * @param s underlying line search algorithm
	 */
	public SteepestDescent(RealFunc f, LineSearch s) {
		this.f = f;
		this.s = s;
		//this.dicho = new Dichotomy(f);
	}

	
	/**
	 * Calculate the next iterate.
	 * 
	 */
	public void compute_next() throws EndOfIteration {
		
		// 1-calculer le gradient de f au point xk
		Vector xk = iter_vec;
		Vector d = f.grad(xk).leftmul(-1);
		
		// 3-calcul du pas alphaK par une methode de recherche en ligne suivant
		// la direction opposée au gradient
		double alpha = s.search(xk,d);
		
		// calcul du nouvel itéré xk+1
		Vector xk1 = xk.add(d.leftmul(alpha));
		// Mis à jour pas optimal
		iter_vec=xk1;
	}
	
}
