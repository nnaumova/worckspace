package in;

import java.util.Scanner;

public class E {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);  
		int n = sc.nextInt();
		int[] arr = new int[n];
		for(int i=0; i<n; i++){
			arr[i] = sc.nextInt();
			 
		  }
		for(int i = arr.length-1 ; i > 0 ; i--){
	        for(int j = 0 ; j < i ; j++){
	                if( arr[j] > arr[j+1] ){
	                int tmp = arr[j];
	                arr[j] = arr[j+1];
	                arr[j+1] = tmp;
	            }
	        }
	    }
		for(int i=0; i<n-1; i++){
			
			System.out.print(arr[i] + " ");
		}
		System.out.print(arr[n-1]);

	}

}
