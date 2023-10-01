import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


public class Polynomial {
	double[] coeffs;
	int[] exponents;

	public Polynomial() {
		double[] zerosDouble = {0};
		int[] zerosInt = {0};
		coeffs = zerosDouble;
		exponents = zerosInt;
	}

	public Polynomial(double[] c, int[] e) {
		coeffs = c;
		exponents = e;
	}

	public Polynomial(File f) {
		char[] polArr = new char[100];
		String polStr = "";

		try {
			Scanner myReader = new Scanner(f);
			while (myReader.hasNextLine()) {
				polStr = myReader.nextLine();
			}
     		myReader.close();

     		String[] polStrSpl = polStr.split("(?=-|\\+)");
			double[] c = new double[polStrSpl.length];
			int[] e = new int[polStrSpl.length];

			for (int i = 0; i < polStrSpl.length; i++) {
				String[] coeffAndExp = polStrSpl[i].split("x");

				if (coeffAndExp.length == 2) {
					c[i] = Double.parseDouble(coeffAndExp[0]);
					e[i] = Integer.parseInt(coeffAndExp[1]);
				}
				else {
					c[i] = Double.parseDouble(coeffAndExp[0]);
					e[i] = 0;
				}
			}
			coeffs = c;
			exponents = e;
    	} 
    	catch (FileNotFoundException er) {
      		System.out.println("File error");
      		er.printStackTrace();
    	}
	}


	public boolean inExponents(int exp) {
		// return true if exp is in this.exponents
		for (int i = 0; i < this.exponents.length; i++) {
			if (this.exponents[i] == exp) {
				return true;
			}
		}
		return false;
	}

	public void addExp(Polynomial pol, List<Integer> list) {
		// combine the exponents in this and pol and add to list
		for (int i = 0; i < pol.exponents.length; i++) {
			if (! this.inExponents(pol.exponents[i])){
				// add if only in pol.exponents
				list.add(pol.exponents[i]);
			}
		}
		for (int i = 0; i < this.exponents.length; i++) {
			// add everything in this.exponents
			list.add(this.exponents[i]);
		}
	}

	public double coeffFromExp(int exp) {
		// return the coef that corresponds to exp or 0 if dne
		for (int i = 0; i < this.exponents.length; i++) {
			if (this.exponents[i] == exp) {
				return this.coeffs[i];
			}
		}
		return 0;
	}

	public Polynomial add(Polynomial pol) {
		List<Integer> expList = new ArrayList<>();
		this.addExp(pol, expList);
		int[] expArray = new int [expList.size()];
		double[] coeffArray = new double [expArray.length];

		// convert list into array and add coeffs to corresponding exponent
        for (int i = 0; i < expList.size(); i++) {
            expArray[i] = expList.get(i);
            coeffArray[i] += this.coeffFromExp(expArray[i]);
			coeffArray[i] += pol.coeffFromExp(expArray[i]);
        }

		Polynomial sumPol = new Polynomial(coeffArray, expArray);
		return sumPol;
	}

	public int findExp(List<Integer> list, int exp) {
		// return the index of exp if it is in list and -1 otherwise
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == exp) {
				return i;
			}
		}
		return -1;
	}

	public Polynomial multiply(Polynomial pol) {
		List<Double> coeffList = new ArrayList<>();
		List<Integer> expList = new ArrayList<>();
		double coeffProd = 0;
		int expProd = 0;
		int expFound = 0;

		for (int i = 0; i < this.exponents.length; i++) {
			for (int j = 0; j < pol.exponents.length; j++) {
				coeffProd = this.coeffs[i] * pol.coeffs[j];
				expProd = this.exponents[i] + pol.exponents[j];
				expFound = findExp(expList, expProd);
				if (expFound == -1) {
					coeffList.add(coeffProd);
					expList.add(expProd);
				}
				else {
					coeffProd += coeffList.get(expFound);
					coeffList.set(expFound, coeffProd);
				}
			}
		}

		int[] expArray = new int [expList.size()];
		double[] coeffArray = new double [expArray.length];

		for (int i = 0; i < expList.size(); i++) {
            expArray[i] = expList.get(i);
            coeffArray[i] = coeffList.get(i);
        }
        Polynomial prodPol = new Polynomial(coeffArray, expArray);
		return prodPol;
	}

	public double evaluate(double x) {
		double total = 0;
		for (int i = 0; i < this.coeffs.length; i++) {
			double part_sum = 1;
			for (int j = 0; j < this.exponents[i]; j++) {
				part_sum *= x;
			}
			total += this.coeffs[i] * part_sum;
		}
		return total;
	}

	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}

	public void saveToFile(String fName) throws IOException {
		String polStr = "";
		for (int i = 0; i < this.exponents.length; i++) {
			if (this.exponents[i] != 0) {
				if ((this.coeffs[i] > 0) && (i != 0)) {
					polStr += "+";
				}
				polStr += String.format("%.1fx%d", this.coeffs[i], this.exponents[i]);
			}
			else {
				if ((i != 0) && (this.coeffs[i] > 0)) {
					polStr += "+";
				}
				polStr += String.format("%.1f", this.coeffs[i]);
			}
		}

		File file = new File(fName);
		FileWriter wr = new FileWriter(file);
		wr.write(polStr);
		wr.flush();
		wr.close();
	}
}