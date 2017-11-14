package solve;

import util.Vector;
import util.Matrix;
import func.QuadraForm;
import func.RealFunc;

/**
 * Basic QuasiNewton algorithm for unconstrained minimization problem.
 */


public class QuasiNewton extends Algorithm {
	
	/**
	* Fonction F
	*/
	private RealFunc f;
	/**
	* Delta
	*/
	private double dt;
	/**
	* Matrice Q
	*/
	private Matrix A;
	/**
	* Matrice inverse Q
	*/
	private Matrix H;
	
	/**
	* Initial trust region size .
	*/
	public final static double DELTA_INIT = 1.0;
	/**
	* Ratio by which the region size is either
	* multiplied / divided
	*/
	public final static double DELTA_RATIO = 2.0;
	/**
	* Minimal trust region size .
	*/
	public final static double DELTA_MIN = 1e-10;
	/**
	* Maximal trust region size .
	*/
	public final static double DELTA_MAX = 10;
	/**
	* Ratio of actual / predicted reduction above which
	* adequacy of the quadratic model is considered as good .
	*/
	public final static double GOOD_ADEQUACY = 0.75;
	/**
	* Ratio of actual / predicted reduction under which
	* adequacy of the quadratic model is considered as poor.
	*/
	public final static double POOR_ADEQUACY = 0.25;
	/* *
	* Small curvature : means that the new " measurement " ( dx , dg )
	* is close to be linearly dependent of the n previous ones .
	* When the dot product between dx / dg and e is less than this value ,
	* the corresponding matrix Hk / Ak should not be updated .
	*/
	public final static double SMALL_CURVATURE = 1e-20;
	/* *
	* When the gradient norm is less than this value , the first - order
	* condition are considered to be fulfilled .
	*/
	public final static double GRADIENT_MIN_NORM = 1e-15;
	
	
	/**
	 * Build the algorithm
	 * 
	 * @param f function to minimize
	 * @param s underlying line search algorithm
	 */
	
	public QuasiNewton (RealFunc f) {
		this.f = f;	
	}
	
	/**
	 * Retourne le minimum des deux valeurs
	 */	
	public double min (double alpha, double beta){
		if(alpha >= beta) {return alpha;}
		else {return beta;}
	}
	
	/**
	 * Start the iteration
	 */
	public void start(Vector x0) {
		super.start(x0);
		this.dt = DELTA_INIT;
		this.A = A.identity(x0.size());
		this.H = H.identity(x0.size());		
	}
	
	public void compute_next() throws EndOfIteration {
		QuadraForm Q =  new QuadraForm(A,f.grad(iter_vec))  ;	
		Vector d = H.mult(f.grad(iter_vec)).leftmul(-1);
		Vector xk1 = (iter_vec).add(d);
		
		if(Q.eval(xk1) < Q.eval(iter_vec)){	
			if (xk1.sub(iter_vec).norm()<= dt){
				iter_vec = xk1 ;
			}else{
				d = f.grad(iter_vec).leftmul(-(dt)/(f.grad(iter_vec).norm()));
				double alpha = 0 ;
				double gQg = (f.grad(iter_vec)).scalar(A.mult(f.grad(iter_vec)));
				if(gQg <= 0) {
					alpha = 1 ;
				}else {
					alpha = (Math.pow(f.grad(iter_vec).norm(),3))/(dt * gQg);
					alpha = min (alpha,1);
				}
				xk1 = (iter_vec).add(d.leftmul(alpha));
			}
			
		}else {
			d = f.grad(iter_vec).leftmul(-(dt)/(f.grad(iter_vec).norm()));
			double alpha = 0 ;
			double gQg = (f.grad(iter_vec)).scalar(A.mult(f.grad(iter_vec)));
			if(gQg <= 0) {
				alpha = 1 ;
			}else {
				alpha = (Math.pow(f.grad(iter_vec).norm(),3))/(dt * gQg);
				alpha = min (alpha,1);
			}
			xk1 = (iter_vec).add(d.leftmul(alpha));
		}
		// ----------------------------------------------------------------------------
		
		double adequation = (f.eval(iter_vec)-f.eval(xk1))/(Q.eval(iter_vec)-Q.eval(xk1));
		if(adequation >= GOOD_ADEQUACY && dt < DELTA_MAX) {
			dt = DELTA_RATIO * dt ;			
		}else if(adequation <= POOR_ADEQUACY && dt > DELTA_MIN ) {
			dt = dt / DELTA_RATIO ;
		}
		// ----------------------------------------------------------------------------
		
		//mis a jour de A
		Vector Dx = xk1.sub(iter_vec);
		Vector Dg = f.grad(xk1).sub(f.grad(iter_vec));
		Matrix error ;
		if(Dx.scalar(Dg.sub( A.mult(Dx))) > SMALL_CURVATURE) {
			error = Dg.sub( A.mult(Dx)).mult(Dg.sub( A.mult(Dx))).leftmul(Dx.scalar(Dg.sub( A.mult(Dx)))) ;
			A = A.add(error);
		}
		
		//mis a jour de H
		if(Dg.scalar(Dx.sub(H.mult(Dg))) > SMALL_CURVATURE) {
			error = Dx.sub(H.mult(Dg)).mult(Dx.sub(H.mult(Dg))).leftmul(Dg.scalar(Dx.sub(H.mult(Dg))));
			H = H.add(error);
		}
		
		// ----------------------------------------------------------------------------
		iter_vec = xk1 ;
		if(f.grad(xk1).norm() <= GRADIENT_MIN_NORM || dt <= DELTA_MIN) {
			throw new EndOfIteration();
		}
		
	}
	

}
