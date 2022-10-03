import java.io.*;
import java.util.*;

class Polynomial {
    double[] polynomial_coef;
    int[] polynomial_pow;

    Polynomial() {
        double[] zero_d = {0};
        int[] zero_i = {0};
        polynomial_coef = zero_d;
        polynomial_pow = zero_i;
    }
    Polynomial(double[] pol_coef, int[] pol_pow) {
        polynomial_coef = pol_coef;
        polynomial_pow = pol_pow;
    }

    static int[] get_pow(String[] pol_s, int index){
        int[] val_a_idx = new int[2];
        int i=1;
        String st = pol_s[index];
        if(pol_s[index].equals("x")){
            st = "";
        }
        //if x and the next is + or - then return 1
        if(pol_s[index].equals("x") && index+1 == pol_s.length){
            val_a_idx[0] = 1;
            val_a_idx[1] = index+1;
        }
        else if(pol_s[index].equals("x") && (pol_s[index+1].equals("+") || pol_s[index+1].equals("-"))){
            val_a_idx[0] = 1;
            val_a_idx[1] = index+1;
        }
        else{
            while((index+i) < pol_s.length && !((pol_s[index+i].equals("-") || pol_s[index+i].equals("+")))){
                st += pol_s[index+i];
                i++;
            }
            int val;
            val = Integer.parseInt(st);
            val_a_idx[0] = val;
            val_a_idx[1] = index+i;
        }
        return val_a_idx;
    }

    static double[] get_coef(String[] pol_s, int index){
        double[] val_a_idx = new double[2];
        int i=1;
        String st = pol_s[index];
        if(pol_s[index].equals("+")){
            if((index+1)<pol_s.length && pol_s[index+1].equals("x")){
                st = "1";
            }
            else if((index+1)<pol_s.length && !(pol_s[index+1].equals("x"))){
                st = pol_s[index+1];
                i++;
            }
        }
        while((index+i) < pol_s.length && !((pol_s[index+i].equals("-") || pol_s[index+i].equals("+") || pol_s[index+i].equals("x")))){
            st += pol_s[index+i];
            i++;
        }
        if(st.equals("-")){//when we find -1
            st = "-1";
        }
        double val;
        val = Double.parseDouble(st);
        val_a_idx[0] = val;
        val_a_idx[1] = index+i;

        return val_a_idx;
    }

    Polynomial(File pol_file){
        String pol = "";
        try{
            Scanner scanner = new Scanner(pol_file);
            while(scanner.hasNext()) {
                pol += scanner.next();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        String[] pol_parts = pol.split("");
        double[] coefs = new double[pol_parts.length];
        int[] pows = new int[pol_parts.length];
        int idx=0;
        int count_coef=0;  
        int count_pow=0;
        int[] pow_a_idx = new int[2];
        double[] coef_a_idx = new double[2];

        while(idx<pol_parts.length){
            if(!pol_parts[idx].equals("x")){
                coef_a_idx = get_coef(pol_parts, idx);
                coefs[count_coef] = coef_a_idx[0];
                count_coef++;
                if((int)coef_a_idx[1] < pol_parts.length && !pol_parts[(int)coef_a_idx[1]].equals("x")){
                    pows[count_pow] = 0;
                    count_pow++;
                }
                else if((int)coef_a_idx[1] == pol_parts.length){
                    pows[count_pow] = 0;
                }
                idx = (int)coef_a_idx[1];
            }
            else{
                if(idx==0){
                    coefs[count_coef] = 1;
                    count_coef++;
                }
                pow_a_idx = get_pow(pol_parts, idx);
                pows[count_pow] = pow_a_idx[0];
                count_pow++;
                idx = (int)pow_a_idx[1];
            }
        }
        polynomial_coef = coefs;
        polynomial_pow = pows;
    }



    int notin(int[] a, int[] b){
        int count=0;
        for(int i=0; i<b.length; i++){
            if(findidx(a, b[i]) == -1){
                count++;
            }
        }
        return count;
    }

    int findidx(int[] ar, int key){
        for(int i=0; i<ar.length; i++){
            if(ar[i] == key){
                return i;
            }
        }
        return -1;
    }

    Polynomial add(Polynomial pol){
        int diff= notin(this.polynomial_pow, pol.polynomial_pow);//in pol but not in this

        int[] pows = new int[this.polynomial_pow.length+diff];
        double[] coefs = new double[this.polynomial_pow.length+diff];

        for(int i=0; i< this.polynomial_pow.length; i++){
            pows[i] = this.polynomial_pow[i];
            coefs[i]+= this.polynomial_coef[i];
        }
        int count = 0;
        for(int i=0; i< pol.polynomial_pow.length; i++){
            int idx = findidx(pows, pol.polynomial_pow[i]);
            if(idx == -1){
                pows[this.polynomial_pow.length + count] = pol.polynomial_pow[i];
                coefs[this.polynomial_pow.length + count] = pol.polynomial_coef[i];
                count++;
            }
            else{
                coefs[idx] += pol.polynomial_coef[i];
            }
        }
        Polynomial newpol = new Polynomial(coefs, pows);

        return newpol;
    }

    double evaluate(double x){
        double total = 0;
        for(int i=0; i< this.polynomial_coef.length; i++){
            total += this.polynomial_coef[i]*(Math.pow(x,this.polynomial_pow[i]));
        }
        return total;
    }
 
    boolean hasRoot(double root){
        return this.evaluate(root) == 0;
    }

    int num_duplicate(int[] ar, int key){
        int count = -1;
        for(int i=0; i< ar.length; i++){
            if(ar[i] == key){
                count++;
            }
        }
        return count;
    }

    double[] drop_second_zero(int[] pow, double[] coef){
        int count = 0;
        for(int i=0; i< pow.length; i++){
            if(pow[i] == 0){
                count++;
                if(count > 1){
                    coef[i] = 0;
                }
            }
        }
        return coef;
    }


    Polynomial multiply(Polynomial pol){
        int len = this.polynomial_coef.length * pol.polynomial_coef.length;
        double[] temp_coef = new double[len];
        int[] temp_pow = new int[len];
        int count=0;
        for(int i=0; i< this.polynomial_coef.length; i++){
            for(int j=0; j< pol.polynomial_coef.length; j++){
                temp_coef[count] = this.polynomial_coef[i]*pol.polynomial_coef[j];
                temp_pow[count] = this.polynomial_pow[i]+pol.polynomial_pow[j];
                count++;
            }
        }
        int[] new_pow = new int[len];
        count = 0;
        for(int i=0; i<temp_coef.length; i++){
            if(findidx(new_pow, temp_pow[i]) == -1){
                new_pow[count] = temp_pow[i];
                count++;
            }
        }
        double[] new_coef = new double[len];
        for(int i=0; i<new_pow.length; i++){
            for(int j=0; j<temp_coef.length; j++){
                if(new_pow[i] == temp_pow[j]){
                    new_coef[i] += temp_coef[j]; 
                }
            }
        }
        new_coef = drop_second_zero(new_pow, new_coef);

        Polynomial mult_pol = new Polynomial(new_coef, new_pow);
        return mult_pol;
    }

    void saveToFile(String file_name){
        String pol_string = "";
        double[] coef = this.polynomial_coef;
        int[] pow = this.polynomial_pow;
        int i=0;
        while(i<coef.length){
            if(coef[i]>0 && i!=0){
                pol_string += "+";
            }
            if(coef[i] != 1){
                pol_string += String.valueOf(coef[i]);
            }
            if(pow[i]==0){
                i++;
            }
            else if(pow[i]==1){
                pol_string += "x";
                i++;
            }
            else{
                pol_string += "x";
                pol_string += String.valueOf(pow[i]);
                i++;
            }
        }
        try{
            File f = new File(file_name);
            FileWriter output = new FileWriter(f, false);
            output.write(pol_string);
            output.close();
        }
        catch (IOException e) {
    	    e.printStackTrace();
    	}
    }

    public static void main(String[] args){
        File pol_file = new File("/Users/yfchoco/Downloads/test_pol.txt");
        String pol = "";
        try{
            Scanner scanner = new Scanner(pol_file);
            while(scanner.hasNext()) {
                pol += scanner.next();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        //System.out.println(pol_test);

        //String pol = "-4.0+x+50x-3x10+10x20+x2-210x+3";
        String[] pol_parts = pol.split("");
        String x = pol_parts[1];
        String plus = pol_parts[2];
        String minus = pol_parts[6];

        
        double[] coefs = new double[pol_parts.length];
        int[] pows = new int[pol_parts.length];
        int idx=0;
        int count_coef=0;  
        int count_pow=0;
        int[] pow_a_idx = new int[2];
        double[] coef_a_idx = new double[2];

        while(idx<pol_parts.length){
            if(!pol_parts[idx].equals("x")){
                coef_a_idx = get_coef(pol_parts, idx);
                coefs[count_coef] = coef_a_idx[0];
                count_coef++;
                if((int)coef_a_idx[1] < pol_parts.length && !pol_parts[(int)coef_a_idx[1]].equals("x")){
                    pows[count_pow] = 0;
                    count_pow++;
                }
                else if((int)coef_a_idx[1] == pol_parts.length){
                    pows[count_pow] = 0;
                }
                idx = (int)coef_a_idx[1];
            }
            else{
                if(idx==0){
                    coefs[count_coef] = 1;
                    count_coef++;
                }
                pow_a_idx = get_pow(pol_parts, idx);
                pows[count_pow] = pow_a_idx[0];
                count_pow++;
                idx = (int)pow_a_idx[1];
            }
        }
        for(int i=0; i<pows.length; i++){
            System.out.println(pows[i]);
            System.out.println(coefs[i]);
        }

        String pol_string = "";
        double[] coef = {-4,1,50,-3,10,1,-210,3};
        int[] pow = {0,1,1,10,20,2,1,0};
        int i=0;
        while(i<coef.length){
            if(coef[i]>0 && i!=0){
                pol_string += "+";
            }
            if(coef[i] != 1){
                pol_string += String.valueOf(coef[i]);
            }
            if(pow[i]==0){
                i++;
            }
            else if(pow[i]==1){
                pol_string += "x";
                i++;
            }
            else{
                pol_string += "x";
                pol_string += String.valueOf(pow[i]);
                i++;
            }
        }
        System.out.println(pol_string);

        Polynomial p = new Polynomial(pol_file);
        double tot = p.evaluate(2);
        System.out.println(tot);
        double[] c = {0,1,2,3,4};
        int[] po = {0,1,2,3,4};
        Polynomial p2 = new Polynomial(c, po);
        p2.saveToFile("/Users/yfchoco/Downloads/test_pol.txt");
    }
}

    

    

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









