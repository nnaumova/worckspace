package intel;

import java.util.Scanner;

public class A {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);  
		String n     = sc.nextInt();
		String s   = sc.nextLine(); 
		
				
		System.out.println(s);
		
		String str = s.replaceAll("01", "");
		String str1 =  str.replaceAll("01", "");
		
		System.out.println(str);
		
        char [] myCharArray = str1.toCharArray ();
        System.out.println(myCharArray.length);
        
       

	}

}
