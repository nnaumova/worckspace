import java.util.Random;


public class Money {
	

	public static void main(String[] args) {
		int numTry = 10000; //10,100,1000
		int choises [] = new int [2];
		int sum = 0, sum1 = 0, sum2 = 0, sum3 = 0;
		
		for (;numTry>0; numTry--){
			
		Random r = new Random();
		int a = r.nextInt(100000);
		int b = a*2;
		if(r.nextInt(2)==0) {
			choises[0] = a ;
			choises[1] = b;
		}
			else{
				choises[0] = b ;
				choises[1] = a;
			}
		
			sum += choises [0];
		sum1 += choises [1];
		sum2 += choises [r.nextInt(2)];
		if( numTry%5==0)
		sum3 += choises [0];
		else sum3 += choises [1];
			
		}
		
System.out.println(sum + " "+ sum1 + " "+ sum2 + " "+ sum3);
	}
	
}
