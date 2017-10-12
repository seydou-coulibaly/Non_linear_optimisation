package util;

/**
 * Thrown when one tries to invert a singular (not invertible) matrix
 * or to derivate a non differentiable function, etc. 
 * 
 * @author Gilles Chabert
 *
 */
public class Singularity extends Exception {

	private static final long serialVersionUID = 1L;

}
