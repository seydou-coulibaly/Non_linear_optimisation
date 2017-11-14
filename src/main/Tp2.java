package main;
import solve.Algorithm;
import solve.QuasiNewton;
import util.Vector;
import util.Plot;

import java.util.ArrayList;

import func.AlmostDiag;
import func.Hilbert;
import func.RealFunc;
import func.Rosenbrock;

public class Tp2 {
	public final static int N=10000;
	public final static double PREC=1e-8;

	/**
	 * Run an unconstrained local search algorithm on a problem and plot
	 * the convergence behavior.
	 *  
	 * @param f          the function to minimize
	 * @param xopt       the optimizer x*
	 * @param x0         the initial x
	 * @param algo       the algorithm
	 * @param plotTitle  title of the output plot
	 * @param fileName   name of the output plot file
	 */
	private static void run(RealFunc f, Vector xopt, Vector x0, Algorithm algo, String plotTitle, String fileName) {

		/* Store the iteration number k:  k=1, 2... */
		double[] it=new double[N];
		/* store the logarithm of the distance between f(x_k) and f(x*). */ 
		double[] gap=new double[N];
		ArrayList<Vector> xkplus = new ArrayList<Vector>();
	
		algo.start(x0);
		algo.search(xkplus);
		xopt = algo.current_vector();
		int nbi = algo.current_iteration();
		
		//faire gaffe à ce que nbi soit plus petit que N
		System.out.println("iterations number : "+nbi);
		
		//Calcule de gap[i] et son affichage
		System.out.println("iteration	GAP[iteration]");		
	    for (int i=0; i<nbi; i++) {
	    	it[i]  = i ;
			gap[i] = Math.log(f.eval(xkplus.get(i))-(f.eval(xopt)));
			System.out.println(i+"	"+gap[i]);
	    }
	    System.out.println("iterations number : "+nbi);
	    System.out.println("Xopt :  "+xopt.toString());
	    
		
		/* Generate the graphic */
		new Plot(plotTitle,"#iter","log(x-x*)",fileName,it,gap);
	}

	public static void QuasiNewtonAlmostDiag() {
		AlmostDiag almostDiag5=new AlmostDiag(5);
		Vector zero5 = new Vector(new double[] {0,0,0,0,0});
		Vector x0 = new Vector(new double[] {1,-2,-1,3,1});

		run(almostDiag5, 
			zero5,
			x0,
			new QuasiNewton(almostDiag5), "QuasiNewton - AlmostDiag","QuasiNewton_almostdiag.jpg");
	}
	
	public static void QuasiNewtonHilbert() {
		RealFunc hilbert5=new Hilbert(5);
		Vector zero5 = new Vector(new double[] {0,0,0,0,0});
		Vector x0 = new Vector(new double[] {1,2,-1,3,1});

		run(hilbert5, 
			zero5,
			x0,
			new QuasiNewton(hilbert5), "QuasiNewton - Hilbert","QuasiNewton.jpg");
	}
	
	public static void QuasiNewtonRosenbrock() {
		RealFunc rosenbrock  = new Rosenbrock();
		Vector xopt          = new Vector(new double[] {1,1});
		Vector x0            = new Vector(new double[] {1,2});

		run(rosenbrock, 
			xopt,
			x0,
			new QuasiNewton(rosenbrock), "QuasiNewton - Rosenbrock","QuasiNewton_rosenbrock.jpg");
	}
	
	
	public static void main(String[] args) {
	
		QuasiNewtonAlmostDiag();
	
//		QuasiNewtonHilbert();
		
//		QuasiNewtonRosenbrock();

		
	}

}
