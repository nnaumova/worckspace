import java.util.List;


public class Corr3 {

	
	public double E(double X[]){
		double e = 0;
		for(int i =0; i < X.length; i++){
			e += X[i];
		}
		return e/X.length;
	}
	public double E(double X[], double Y[]){
		double e = 0;
		for(int i =0; i < X.length; i++){
			e += X[i]*Y[i];
		}
		return e/X.length;
	}
	
	public double var(double X[]){
		double var = 0;
		double Ex = E(X); 
		for(int i =0; i < X.length; i++){
			var += (X[i]-Ex)*(X[i]-Ex);
		}
		return var/X.length;
	}
	
	public  double result(List <Double> a, List <Double> b){
		double X [] = new double [a.size()];
		double Y []  = new double [a.size()];
		for(int i = 0; i<a.size(); i++){
			X[i] = a.get(i);
			Y[i] = b.get(i);
		}
		System.out.println(X.toString());
		System.out.println(Y.toString());
		double result = (E(X,Y) -E(X)*E(Y))/(Math.sqrt(var(X)*var(Y)));
		return result;
	}
	
	
	
	/*public static void main(String[] args) {
		double X [] = {19,32,33,44,28,35,39,39,44,44,24,37,29,40,42,32,48,42,33,47};
		double Y [] = {17,7,17,28,27,31,20,17,35,43,10,28,13,43,45,24,45,26,16,26};
		Corr3 check = new Corr3();
		System.out.println(check.result(X, Y));
	}*/
}
