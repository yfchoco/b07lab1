import java.io.*;
import java.util.*;

public class Driver{
    public static void main(String[] args){
        double[] c1 = {6,5};
        int[] pow1 = {0,3};
        Polynomial p1 = new Polynomial(c1, pow1);
        double[] c2 = {-2,-9};
        int[] pow2 = {1,4};
        Polynomial p2 = new Polynomial(c2, pow2);
        double[] c3 = {1,-1};
        int[] pow3 = {0,1};
        Polynomial p3 = new Polynomial(c3, pow3);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1)){
            System.out.println("1 is a root of s");
        }
        else{
            System.out.println("1 is not a root of s");
        }
        Polynomial m = p1.multiply(p3);
        System.out.println("m(2) = " + m.evaluate(2));
        if(m.hasRoot(1)){
            System.out.println("1 is a root of m");
        }
        else{
            System.out.println("1 is not a root of m");
        }

        File pol_file = new File("/Users/yfchoco/b07lab1/test_pol.txt");
        Polynomial p4 = new Polynomial(pol_file);
        System.out.println("p4(1) = " + p4.evaluate(1));

        String fname = "/Users/yfchoco/b07lab1/test_output.txt";
        m.saveToFile(fname);
    }
}