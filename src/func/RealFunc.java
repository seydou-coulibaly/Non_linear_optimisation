package func;
import util.Vector;

/**
 * 
 * Interface for real functions.
 * 
 * @author Gilles Chabert
 *
 */
public interface RealFunc {

	/**
	 * @return the dimension of x (i.e., the number of variables)
	 */
	public int dim();
	
	/**
	 * @return f(x).
	 */
	public double eval(Vector x) ;

	/**
	 * @return the gradient of f at x.
	 */
	public Vector grad(Vector x);

}
