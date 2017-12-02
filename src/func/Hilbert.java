package func;
import util.Matrix;


public class Hilbert extends QuadraForm {

	private static Matrix _hilbert(int n) {
		Matrix Q=new Matrix(n,n);

		for (int i=0; i<n; i++) {
			for (int j=0;j<n; j++) {
				Q.set(i,j,((double)1/(i+j+1)));				
			}
		}
		System.out.println("Hilbet=\n");
		System.out.println(Q);
		System.out.println("===============================================");
		return Q;	
	}
	
	public Hilbert(int n) {
		super(_hilbert(n));
	}
	
}
