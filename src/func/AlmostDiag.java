package func;
import util.Matrix;
//import util.Vector;


public class AlmostDiag extends QuadraForm {

	private static Matrix almostDiag(int n) {
		Matrix Q=new Matrix(n,n);
		
		for (int i=0; i<n; i++) {
			for (int j=0;j<n; j++) {
				if(i==j) {Q.set(i,j,1);}
				else {Q.set(i,j,((double)-1/n));}				
			}
		}
		return Q;	
	}
	
	public AlmostDiag(int n) {
		super(almostDiag(n));
	}
	
}
