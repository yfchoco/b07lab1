import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial(); 
		System.out.println(p.evaluate(3));
		double [] c1 = {2,-8,10,3};
		int [] e1 = {2,1,0,3};
		Polynomial p1 = new Polynomial(c1, e1);
		double [] c2 = {-1,2,-1,-3};
		int [] e2 = {2,1,0,3};
		Polynomial p2 = new Polynomial(c2, e2); 
		Polynomial s = p1.add(p2); 
		Polynomial prod = p1.multiply(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		System.out.println("prod(-2.37775) = " + prod.evaluate(-2.37775));
		System.out.println("length of prod is " + prod.exponents.length);
		if(s.hasRoot(3)) {
			System.out.println("3 is a root of s");
		}
		else{
			System.out.println("3 is not a root of s");
		}

		if(prod.hasRoot(3)) {
			System.out.println("3 is a root of prod");
		}
		else{
			System.out.println("3 is not a root of prod");
		}

		File file = new File("polynomial.txt");
		Polynomial p4 = new Polynomial(file);
		System.out.println("p4(5) = " + p4.evaluate(6));

		try {
    		prod.saveToFile("prod.txt");
		} 
		catch (IOException e) {
    	// Handle the exception, e.g., print an error message
    		e.printStackTrace();
		}
		try {
    		p4.saveToFile("p4.txt");
		} 
		catch (IOException e) {
    	// Handle the exception, e.g., print an error message
    		e.printStackTrace();
		}
	} 
}
