import java.util.ArrayList;
import java.util.List;


public class Corr {

	public double relativePriceChange(double Pt, double Pt1) {
		double val = Math.log(Pt / Pt1);
		//double val =(Pt-Pt1)/Pt1; //Без разницы как считать
		//val = ((double) Math.round(val * 10000000000L)) / 10000000000L;
		System.out.println("val= "+val);
		return val;
	}
	
	public  double pirsonCorr(List <Double> X, List <Double> Y) {
		double corr = 0;
		double xSum=0;
		double ySum=0;
		double xxSum=0;
		double yySum=0;
		double xySum=0;
		int n = X.size();
		
		for(int i = 0; i<n; i++){
			xSum += X.get(i);
			ySum += Y.get(i);
			xxSum += X.get(i)*X.get(i);
			yySum += Y.get(i)*Y.get(i);
			xySum += X.get(i)*Y.get(i);
			
		}
		//System.out.println("+xSum " +xSum + " ySum"  +ySum+ "xxSum "+ xxSum+ "yySum"+ yySum +"xySum" + xySum);
		corr = (n *  xySum - xSum*ySum)/(Math.sqrt((n*xxSum - xSum*xSum)*(n*yySum - ySum*ySum)));
		return corr;
	}
	public double pirsonCorr2(double X[], double Y[]) {
		double corr = 0;
		double xAv=0;
		double yAv=0;
		double xy = 0;
		
		double x2 = 0;
		double y2 =0;
		int n = X.length;
		for(int i = 0; i<n; i++){
			xAv += X[i];
			yAv += Y[i];
		}
		xAv =xAv/n;
		yAv =yAv/n;
		for(int i = 0; i<n; i++){
			xy += (X[i]-xAv)*(Y[i]-yAv);
			x2 += (X[i]-xAv)*(X[i]-xAv);
			y2 += (Y[i]-yAv)*(Y[i]-yAv);
		}
		corr = xy/(Math.sqrt(x2*y2));
		return corr;
	}
	
	
	
	
	public static void main(String[] args) {
		double q [] = {19,32,33,44,28,35,39,39,44,44,24,37,29,40,42,32,48,42,33,47};
		double w [] = {17,7,17,28,27,31,20,17,35,43,10,28,13,43,45,24,45,26,16,26};
		double r [] = {27,9,16,18,17,21,21,18,31,23,20,38,33,45,35,14,44,56,26,36};
		double t [] = {37,2,18,38,24,37,10,11,15,42,12,23,23,73,40,20,25,16,19,24};
		Corr calk = new Corr();
		//System.out.println(check.pirsonCorr2(X, Y));
		int a = 0;
		int b = 4;
		int interval = 5;
		ArrayList <ArrayList <Double>> allCorrelations = new ArrayList <ArrayList <Double>>();
		ArrayList <ArrayList <Double>> allStocks = new ArrayList <ArrayList <Double>>();
		for(int i = 0; i<4;i++){
			allStocks.add(new ArrayList <Double>());
			allCorrelations.add(new ArrayList <Double>());
		}
		
			for(int j=0; j<q.length;j++){
				allStocks.get(0).add(q[j]);
				allStocks.get(1).add(w[j]);
				allStocks.get(2).add(r[j]);
				allStocks.get(3).add(t[j]);
				
			}
			
		
		for(int i = 0; i<allStocks.size()-1;i++){
			for(int j = i+1; j<allStocks.size();j++){
				List <Double> X = allStocks.get(i).subList(a, b);
				List <Double> Y = allStocks.get(j).subList(a, b);
				Double correlation = calk.pirsonCorr(X, Y);
				allCorrelations.get(i).add(correlation);
				allCorrelations.get(j).add(correlation);
								
			}
			
		}
		for(int i = 0; i< allCorrelations.size();i++){
			for(int j = 0; j< allCorrelations.get(i).size();j++){
				System.out.print(allCorrelations.get(i).get(j) + " ");
			}
			System.out.println();
		}
		
		for(int i = 0; i< allCorrelations.size();i++){
			int [] numberCorInInterval = new int[40];
			int n = 0;
			for(double o = -1; o<1; o+=0.05){
				
			for(int j = 0; j< allCorrelations.get(i).size();j++){
				double corr = allCorrelations.get(i).get(j);
				if(corr<o+0.05 && corr>o) numberCorInInterval[n]+=1;
					
		
				}
			n++;
		}
			for(int u =0;u<40; u++)
			System.out.print(numberCorInInterval[u] + " ");	
			System.out.println();
	}
		
	}
}

