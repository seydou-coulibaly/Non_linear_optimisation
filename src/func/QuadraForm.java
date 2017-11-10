package func;
import util.Matrix;
import util.Vector;

public class QuadraForm implements RealFunc {

	public Matrix Q;
	public Vector b;
	
	public QuadraForm(Matrix Q) {
		this.Q=new Matrix(Q);
	}
	
	public QuadraForm(Matrix Q, Vector b) {
		this.Q=new Matrix(Q);
		this.b =new Vector(b);
	}
	
	@Override
	public double eval(Vector x){
		return  0.5 * x.scalar(Q.mult(x))- b.scalar(x);
	}
	
		
	@Override	
	public Vector grad(Vector x){
		return Q.mult(x).sub(b);
	}
	
	@Override
	public int dim() {
		return Q.nb_cols();
	}

}
