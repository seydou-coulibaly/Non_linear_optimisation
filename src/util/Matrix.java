package util;


/**
 * Matrix (noted "M" in comments)
 * 
 * @author Gilles Chabert
 *
 */
public class Matrix {
	
	private int m;         // number of rows
	private int n;         // number of columns
	private Vector[] rows; // vector of rows

	/**
	 * Build a m*n matrix filled with zeros.
	 */
	public Matrix(int m, int n) {
		this.n = n;
		this.m = m;
		this.rows=new Vector[m];

		for (int i=0; i<m; i++)
			rows[i]=new Vector(n);
	}

	/**
	 * Build a matrix from a double array of values.
	 * vals[i] represents the ith row.
	 */
	public Matrix(double[][] vals) {
		this.m = vals.length;
		this.n = vals[0].length;
		this.rows = new Vector[m];

		for (int i = 0; i < m; i++) {
			assert(vals[i].length==n);
			this.rows[i] = new Vector(vals[i]);
		}
	}

	/**
	 * @return the number of rows
	 */
	public int nb_rows() {
		return m;
	}

	/**
	 * @return the number of columns
	 */
	public int nb_cols() {
		return n;
	}

	/**
	 * Copy constructur
	 * @param mat - The matrix to be copied
	 */
	public Matrix(Matrix mat) {
		this.n = mat.nb_cols();
		this.m = mat.nb_rows();
		this.rows=new Vector[m];

		for (int i=0; i<this.m; i++)
			rows[i]=new Vector(mat.rows[i]);
	}

	/**
	 * @return M[i,j]
	 */
	public double get(int i, int j) {
		return rows[i].get(j);
	}

	/**
	 * @return M[r,:]
	 */
	public Vector get_row(int r) {
		assert(r>=0 && r<nb_rows());
		return new Vector(rows[r]);
	}

	/**
	 * @return M[:,c]
	 */
	public Vector get_col(int c) {
		assert(c>=0 && c<nb_cols());
		Vector v=new Vector(nb_rows());
		for (int i=0; i<nb_rows(); i++) v.set(i,get(i,c));
		return v;
	}

	/**
	 * M[i,j]:=d
	 */
	public void set(int i, int j, double d) {
		rows[i].set(j,d);
	}

	/**
	 * M[r,:]:=v
	 */	
	public void set_row(int r, Vector v) {
		assert(r>=0 && r<nb_rows());
		assert(nb_cols()==v.size());

		rows[r] = new Vector(v);
	}

	/**
	 * M[:,c]:=v
	 */	
	public void set_col(int c, Vector v) {
		assert(c>=0 && c<nb_cols());
		assert(nb_rows()==v.size());

		for (int i=0; i<nb_rows(); i++)
			set(i,c,v.get(i));
	}

	/**
	 * @return M*v
	 */
	public Vector mult(Vector v) {
		assert(v.size()==n);

		Vector v2=new Vector(m);
		for (int i=0; i<m; i++)
			v2.set(i, rows[i].scalar(v));
		return v2;
	}
	
	/**
	 * @return alpha * M
	 */
	public Matrix leftmul(double alpha) {
		Matrix v= new Matrix(m,n);
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++) {
				v.set(i,j,(alpha * get(i,j)));
			}
		}
		return v;
	}

	/**
	 * Build the nxn identity matrix.
	 */
	public static Matrix identity(int n){
		Matrix x = new Matrix(n,n);
		for (int i = 0; i < n; i++) {
			x.set(i,i,1);
		}
		return x;
	}

	/**
	 * @return M^T
	 */
	public Matrix transpose() {

		Matrix x = new Matrix(n,m);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				x.set(i,j,get(j,i));
			}
		}
		return x;
	}

	/**
	 * @return M^{-1}
	 * @throws Singularity
	 */
	public Matrix inverse() throws Singularity {
		Matrix LU = new Matrix(n,n);
		Matrix invA = new Matrix(n,n);
		int[] p= new int[n];

		real_LU(LU, p);

		Vector b=new Vector(n);
		Vector x=new Vector(n);

		for (int i=0; i<n; i++) {
			b.set(i,1);
			real_LU_solve(LU, p, b, x);
			invA.set_col(i, x);
			b.set(i,0);
		}
		return invA;
	}

	/**
	 * @return -M
	 */
	public Matrix minus() {
		Matrix x=new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			x.set_row(i,get_row(i).minus());
		}
		return x;
	}

	/**
	 * @return M+M2
	 */
	public Matrix add(Matrix M2) {
		assert(M2.nb_rows()==nb_rows());
		assert(M2.nb_cols()==nb_cols());

		Matrix x=new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x.set(i,j,get(i,j)+M2.get(i,j));
			}
		}
		return x;
	}

	/**
	 * @return M-M2
	 */	
	public Matrix sub(Matrix M2) {
		assert(M2.nb_rows()==nb_rows());
		assert(M2.nb_cols()==nb_cols());

		Matrix x=new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x.set(i,j,get(i,j)-M2.get(i,j));
			}
		}
		return x;
	}

	/**
	 * @return M*M2
	 */
	public Matrix mult(Matrix M2) {
		int n2=M2.nb_cols();
		assert(M2.nb_rows()==nb_cols());

		Matrix x=new Matrix(m,n2);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n2; j++) {
				double xij=0;
				for (int k = 0; k < n; k++) {
					xij+=get(i,k)*M2.get(k,j);
				}
				x.set(i,j,xij);
			}
		}
		return x;	
	}

	/**
	 * @return M as a string
	 */
	public String toString() {
		String res="(";
		for (int i=0; i<m; i++)
			res+=rows[i]+(i<m-1?"\n":"");
		return res+")";
	}

	/*
	 * Build a LU decomposition of M.
	 * 
	 * @param LU - a n*n matrix (to be filled)
	 * @param p  - a n-sized permutation vector (to be filled)
	 * @throws SingularMatrixException if M is not invertible
	 */
	private void real_LU(Matrix LU, int p[]) throws Singularity {

		double TOO_LARGE = 1e30;

		assert(m==n);
		assert(n == (LU.nb_rows()) && n == (LU.nb_cols()));
		assert(n == p.length);

		// check the matrix has no "infinite" values
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (Math.abs(get(i,j))>=TOO_LARGE) throw new Singularity();
				LU.set(i, j, get(i,j));
			}
		}

		for (int i=0; i<n; i++) p[i]=i;

		// LU computation
		double pivot;

		for (int i=0; i<n; i++) {
			// pivot search
			int swap = i;
			pivot = LU.get(p[i],i);
			for (int j=i+1; j<n; j++) {
				double tmp=LU.get(p[j],i);
				if (Math.abs(tmp)>Math.abs(LU.get(p[swap],i))) swap=j;
			}
			int tmp = p[i];
			p[i] = p[swap];
			p[swap] = tmp;
			// --------------------------------------------------

			pivot = LU.get(p[i],i);
			if (pivot==0.0) throw new Singularity();
			if (Math.abs(1/pivot)>=TOO_LARGE) throw new Singularity();

			for (int j=i+1; j<n; j++) {
				for (int k=i+1; k<n; k++) {
					//cout << LU.get(p[j]][k] << " " << LU.get(p[j]][i] << " " << pivot << endl;
					LU.set(p[j],k,LU.get(p[j],k)-LU.get(p[j],i)/pivot*LU.get(p[i],k));
				}
				LU.set(p[j],i,LU.get(p[j],i)/pivot);
			}
		}
	}

	/*
	 * Solve LU x = b with permutation matrix P.
	 * @throws SingularMatrixException
	 */
	private void real_LU_solve(Matrix LU, int[] p, Vector b, Vector x) throws Singularity {
		//cout << "LU=" << LU << " b=" << b << endl;

		double TOO_SMALL=1e-10;

		int n = (LU.nb_rows());
		assert(n == (LU.nb_cols())); // throw NotSquareMatrixException();
		assert(n == (b.size()) && n == (x.size()));

		// solve Lx=b
		x.set(0,b.get(p[0]));
		for (int i=1; i<n; i++) {
			x.set(i,b.get(p[i]));
			for (int j=0; j<i; j++) {
				x.set(i,x.get(i)-LU.get(p[i],j)*x.get(j));
			}
		}
		//cout << " x1=" << x << endl;

		// solve Uy=x
		if (Math.abs(LU.get(p[n-1],n-1))<=TOO_SMALL) throw new Singularity();
		x.set(n-1, x.get(n-1)/ LU.get(p[n-1],n-1));

		for (int i=n-2; i>=0; i--) {
			for (int j=i+1; j<n; j++) {
				x.set(i, x.get(i)-LU.get(p[i],j)*x.get(j));
			}
			if (Math.abs(LU.get(p[i],i))<=TOO_SMALL) throw new Singularity();
			x.set(i, x.get(i)/LU.get(p[i],i));
		}
		//cout << " x2=" << x << endl;
	}


	public static void main(String[] args) {
		Matrix A = new Matrix(new double[][]{{1,2,3},{2,-1,-1}});
		Matrix B = new Matrix(new double[][]{{1,2},{1,-1},{1,1}});
		Matrix C=A.mult(B);
		System.out.println("A="+A);
		System.out.println("B="+B);
		System.out.println("B.getCol(1)="+B.get_col(1));
		System.out.println("C="+C);
		try {
			Matrix iC=C.inverse();
			System.out.println("inv(C)*C="+iC.mult(C));
		} catch(Singularity e) {

		}
		System.out.println(A.transpose());

	}
}
