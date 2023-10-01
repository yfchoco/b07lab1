public class Polynomial {
	double [] coeffs;

	public Polynomial() {
		double [] zeros = {0};
		coeffs = zeros;
	}

	public Polynomial(double [] c) {
		coeffs = c;
	}

	public Polynomial add(Polynomial pol) {
		if (this.coeffs.length > pol.coeffs.length) {
			for (int i = 0; i < pol.coeffs.length; i++) {
				this.coeffs[i] += pol.coeffs[i];
			}
			return this;
		}
		else {
			for (int i = 0; i < this.coeffs.length; i++) {
				pol.coeffs[i] += this.coeffs[i];
			}
			return pol;
		}
	}

	public double evaluate(double x) {
		double total = 0;
		for (int i = 0; i < this.coeffs.length; i++) {
			double part_sum = 1;
			for (int j = 0; j < i; j++) {
				part_sum *= x;
			}
			total += this.coeffs[i] * part_sum;
		}
		return total;
	}

	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}
}
