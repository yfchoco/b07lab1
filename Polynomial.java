class Polynomial {
    double[] polynomial;

    Polynomial() {
        double[] zero = {0};
        polynomial = zero;
    }
    Polynomial(double[] pol) {
        polynomial = pol;
    }
    Polynomial add(Polynomial pol){
        int longer = Math.max(this.polynomial.length, pol.polynomial.length);
        double[] zeros = new double[longer];
        Polynomial newpol = new Polynomial(zeros);
        for(int i=0; i< this.polynomial.length; i++){
            newpol.polynomial[i] += this.polynomial[i];
        }
        for(int i=0; i< pol.polynomial.length; i++){
            newpol.polynomial[i] += pol.polynomial[i];
        }
        return newpol;
    }

    double evaluate(double x){
        double total = 0;
        for(int i=0; i< this.polynomial.length; i++){
            total += this.polynomial[i]*(Math.pow(x,i));
        }
        return total;
    }

    boolean hasRoot(double root){
        return this.evaluate(root) == 0;
    }
}