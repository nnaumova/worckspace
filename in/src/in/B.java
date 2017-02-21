package in;

import java.util.Scanner;

public class B {
	
	static int getMin(int a,int b){
		int min = 0;
		if(a<0 && b<0)
			min = -(a-b);
		if(a>0 && b>0){
			min = b-a;
		}
		else
			min = -a+b;
		return min;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);  
		int n = sc.nextInt();
		int[] arr = new int[n];
		for(int i=0; i<n; i++){
			arr[i] = sc.nextInt();
			 
		  }
		for(int i=0; i<n; i++){
			System.out.println(arr[i]);
		}
		
		System.out.println(getMin(arr[0], arr[1]) + " " + getMin(arr[0], arr[n-1]));
		
		for(int i=1; i<n-1; i++){
		int min;
		int max;
		int temp;
		min = getMin(arr[i], arr[i+1]);
		temp = getMin(arr[i-1], arr[i]);
		
		if(min>temp)min=temp;
		
		max = getMin(arr[i], arr[n-1]);
		temp = getMin(arr[0], arr[i]);
		
		if(max<temp)max=temp;
		System.out.println(min + " " + max);	
		}
		
		System.out.println(getMin(arr[n-2], arr[n-1]) + " " + getMin(arr[0], arr[n-1]));
	

	}

}
