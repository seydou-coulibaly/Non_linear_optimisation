package main;

import line.SlopeIter;
import solve.Algorithm;
import solve.SteepestDescent;
import solve.ConjugateGradients;

import util.Vector;
import util.Plot;

import java.util.ArrayList;

import func.AlmostDiag;
import func.Hilbert;
import func.RealFunc;
import func.Rosenbrock;

public class Tp1 {
	
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


	public static void steepestAlmostDiag1() {
		AlmostDiag almostDiag5=new AlmostDiag(5);
		Vector zero5 = new Vector(new double[] {0,0,0,0,0});
		Vector x0 = new Vector(new double[] {1,2,-1,3,1});

		run(almostDiag5, 
			zero5,
			x0,
			new SteepestDescent(almostDiag5, 
			new SlopeIter(almostDiag5)), "Steepest descent - AlmostDiag","steep_almostdiag.jpg");
	}
	
	public static void steepestAlmostDiag2() {
		RealFunc almostDiag5=new AlmostDiag(5);
		Vector zero5 = new Vector(new double[] {0,0,0,0,0});
		
		Vector x0 = new Vector(new double[] {0.74536,1.21120,1.21120,1.21120,1.21120});
		
		run(almostDiag5, 
			zero5,
			x0,
			new SteepestDescent(almostDiag5, 
			new SlopeIter(almostDiag5)), "Steepest descent - AlmostDiag (worst case)","steep_almostdiag_worst_case.jpg");
	}
	
	public static void steepestHilbert() {
		RealFunc hilbert5=new Hilbert(5);
		Vector zero5 = new Vector(new double[] {0,0,0,0,0});
		Vector x0 = new Vector(new double[] {1,2,-1,3,1});

		run(hilbert5, 
			zero5,
			x0,
			new SteepestDescent(hilbert5, 
			new SlopeIter(hilbert5)), "Steepest descent - Hilbert","steep_hilbert.jpg");
	}
	
	public static void steepestRosenbrock() {
		RealFunc rosenbrock  = new Rosenbrock();
		Vector xopt          = new Vector(new double[] {1,1});
		Vector x0            = new Vector(new double[] {1,2});

		run(rosenbrock, 
			xopt,
			x0,
			new SteepestDescent(rosenbrock, 
			new SlopeIter(rosenbrock)), "Steepest descent - Rosenbrock","steep_rosenbrock.jpg");
	}
	
	public static void conjgradAlmostDiag() {
		AlmostDiag almostDiag5=new AlmostDiag(5);
		Vector zero5 = new Vector(new double[] {0,0,0,0,0});
		Vector x0 = new Vector(new double[] {1,-2,-1,3,1});

		run(almostDiag5, 
			zero5,
			x0,
			new ConjugateGradients(almostDiag5, 
			new SlopeIter(almostDiag5)), "Conjugate gradients - AlmostDiag","conjgrad_almostdiag.jpg");
	}
	
	public static void conjgradHilbert() {
		RealFunc hilbert5=new Hilbert(5);
		Vector zero5 = new Vector(new double[] {0,0,0,0,0});
		Vector x0 = new Vector(new double[] {1,2,-1,3,1});

		run(hilbert5, 
			zero5,
			x0,
			new ConjugateGradients(hilbert5, 
			new SlopeIter(hilbert5)), "Conjugate gradients - Hilbert","conjgrad_hilbert.jpg");
	}
	
	public static void conjgradRosenbrock() {
		RealFunc rosenbrock  = new Rosenbrock();
		Vector xopt          = new Vector(new double[] {1,1});
		Vector x0            = new Vector(new double[] {1,2});

		run(rosenbrock, 
			xopt,
			x0,
			new ConjugateGradients(rosenbrock, 
			new SlopeIter(rosenbrock)), "Conjugate gradients - Rosenbrock","conjgrad_rosenbrock.jpg");
	}
	
	
	public static void main(String[] args) {
		
//		steepestAlmostDiag1();
		
//		steepestAlmostDiag2();
		
//		steepestHilbert();
		
		steepestRosenbrock();
//		
//		conjgradAlmostDiag();
//		
//		conjgradHilbert();
//		
//		conjgradRosenbrock();

		
	}
}
