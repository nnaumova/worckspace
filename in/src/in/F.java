package in;

import java.util.Scanner;

public class F {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);  
		int candl     = sc.nextInt();
		int num   = sc.nextInt(); 
		int summ = 0;
		int ost = 0;
		while (candl/num!=0){
			
			
			ost = candl%num;
			summ= summ+candl-ost;
			candl = candl/num + ost;
			
			System.out.println( "остаток" + ost);
			System.out.println("свечи" +candl);
			System.out.println("сумма" +summ);
		}
		System.out.println(ost);
		System.out.println(candl);
		System.out.println(summ+candl);
		
		

	}

}
 
