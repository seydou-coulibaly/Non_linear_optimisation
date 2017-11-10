package solve;

import util.Vector;
import util.Matrix;
import line.LineSearch;
import func.QuadraForm;
import func.RealFunc;

/**
 * Basic QuasiNewton algorithm for unconstrained minimization problem.
 */


public class QuasiNewton extends Algorithm {
	

	private LineSearch s;
	
	private RealFunc f;
	private double dt;
	private Matrix A;
	private Matrix H;
	
	
	/**
	 * Build the algorithm
	 * 
	 * @param f function to minimize
	 * @param s underlying line search algorithm
	 */
	
	public QuasiNewton (RealFunc f, LineSearch s) {
		this.f = f;
		this.s = s;
	
	}
	
	/**
	 * Start the iteration
	 */
	public void start(Vector x0, double dt0) {
		super.start(x0);
		this.dt = dt;
		this.A = A.identity(x0.size());
		this.H = H.identity(x0.size());		
	}
	
	public void compute_next() throws EndOfIteration {
		QuadraForm Q =  new QuadraForm(A,f.grad(iter_vec))  ;	
		Vector d = H.mult(f.grad(iter_vec)).leftmul(-1);
		Vector xk1 = (iter_vec).add(d);
		
		if(f.eval(xk1) < f.eval(iter_vec)){
			// Deux cas de figures
			
			//a) on est dans la region de confiance
			if (xk1.sub(iter_vec).norm()< dt){
				//mis a jour de iter_vec
				iter_vec = xk1 ;
			}
			//b) on calcule le point de Cauchy
			else{
				
			}
			
		}
		
		//mis a jour de H
		//mis a jour de A
	}
	

}
