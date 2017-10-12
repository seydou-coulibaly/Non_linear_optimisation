package util;

/**
 * Class representing a vector x of R^n
 * 
 * @author Gilles Chabert
 *
 */
public class Vector {
	private int n;
	private double[] tab;
	
	/**
	 * Build x as an uninitialized vector of R^n.
	 */
	public Vector(int n) {
		this.n = n;
		this.tab=new double[n];
		for (int i=0; i<n; i++)
			tab[i]=0;
	}
	
	/**
	 * Build x as a copy of v.
	 */
	public Vector(Vector v) {
		this(new Vector[]{v});
	}
	
	/**
	 * Create a vector by concatenation of two vectors
	 */
	public Vector(Vector v1, Vector v2) {
		this(new Vector[]{v1,v2});
	}
	
	/**
	 * Create a vector by concatenation of three vectors
	 */
	public Vector(Vector v1, Vector v2, Vector v3) {
		this(new Vector[]{v1,v2,v3});
	}
	
	/**
	 * Create a vector by concatenation of several vectors
	 */
	public Vector(Vector v[]) {
		// calculate total size
		int n=0;
		for (int i=0; i<v.length; i++) 
			n+=v[i].size();
		
		this.n = n;
		this.tab=new double[n];

		// fill the vector
		int k=0; // index in this vector
		for (int i=0; i<v.length; i++) {
			for (int j=0; j<v[i].size(); j++) {
				tab[k++]=v[i].get(j);
			}
		}
	}
	
	/**
	 * Build x from an array of double.
	 */
	public Vector(double[] x) {
		this.n = x.length;
		this.tab=new double[n];
		for (int i=0; i<n; i++)
			set(i,x[i]);
	}
	

	/**
	 * Get the sub-vector (x[start],...,x[end])
	 */
	public Vector subvector(int start, int end) {
		assert(start>=0);
		assert(end>=start);
		
		int n=end-start+1;
		Vector x=new Vector(n);
		
		for (int i=0; i<n; i++)
			x.set(i,get(start+i));
		
		return x;
	}
	
	
	
	/**
	 * Return the size (n) of x.
	 */
	public int size() {
		return n;
	}
	
	/**
	 * Return x[i].
	 */
	public double get(int i) {
		return tab[i];
	}
	
	/**
	 * Set x[i].
	 */
	public void set(int i, double d) {
		tab[i]=d;
	}
	
	/**
	 * Set x to y.
	 */
	public void set(Vector y) {
		for (int i=0; i<n; i++)
			set(i,y.get(i));
	}

	/**
	 * Return -x.
	 */
	public Vector minus() {
		Vector v2=new Vector(n);
		for (int i=0; i<n; i++)
			v2.set(i,-get(i));
		return v2;
	}
	
	/**
	 * Return x+v.
	 */
	public Vector add(Vector v) {
		assert(n==v.size());
		Vector v2=new Vector(n);
		for (int i=0; i<n; i++)
			v2.set(i,get(i)+v.get(i));
		return v2;
	}
	
	/**
	 * Return x-v.
	 */
	public Vector sub(Vector v) {
		return add(v.minus());
	}
	
	/**
	 * Return lambda*x.
	 */
	public Vector leftmul(double lambda) {
		Vector v=new Vector(n);
		for (int i=0; i<n; i++)
			v.set(i,lambda*get(i));
		return v;
	}
	
	/**
	 * Return <x,v>.
	 */
	public double scalar(Vector v) {
		assert(n==v.size());
		double s=0;
		for (int i=0; i<n; i++)
			s=s+get(i)*v.get(i);
		return s;
	}
	
	/**
	 * Return ||x||.
	 */
	public double norm() {
		double ret=0;
		for (int i=0; i<n; i++)
			ret=ret+Math.pow(get(i),2);
		return Math.sqrt(ret);
	}
	
	/**
	 * Return x as a string.
	 */
	public String toString() {
		String res="(";
		for (int i=0; i<n; i++) {
			res+=get(i)+(i<n-1?", ":"");
			//if (i==4) res+="\n\t";
		}
//		if (n>5) res+=",...";
		return res+")";
	}
	
	/**
	 * Check if components are exactyl the same
	 */
	public boolean equals(Vector x) {
		for (int i=0; i<n; i++) {
			if (get(i)!=x.get(i)) return false;
		}
		return true;
	}
}
 