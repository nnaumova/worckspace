package kate;

public class calcCorrelations {
	
	public double calcCorr(Stocks Pi, Stocks Pj, int n, double threshold){
		double corr = 0.0000000000;
		Stocks Pe = new Stocks();
		for (int i = 1; i < n; i++){
                        double value = Yield(Pi.getPrice(i,i),Pi.getPrice(i - 1,i -1))*Yield(Pj.getPrice(i,i),Pj.getPrice(i - 1,i -1));
			Pe.setPrice(value);
		}
		if (n != 1){
                        double cov = ExpP(Pe, n) - (Expectancy(Pi, n)*Expectancy(Pj, n));
			corr = (cov)/(Math.sqrt(Var(Pi, n))*Math.sqrt(Var(Pj, n)));
                        corr = ((double)Math.round(corr * 10000000000L))/ 10000000000L;
		} else {
			corr = 0;
		}
		if (corr < threshold){
			corr = 0.0000000000;
                }
		return corr;
	}
        
        public double ExpP(Stocks P, int n){
                double e = 0.0000000000;
		for (int i = 0; i < n -1; i++){
			e = e + P.getPrice(i,i);
		}
		return e/n;
        }
	
	private double Yield(double Pt, double Pt1){
                double val = Math.log(Pt/Pt1);
                 val = ((double)Math.round(val * 10000000000L))/ 10000000000L;
		return val;
	}
	
	private double Expectancy(Stocks P, int n){
		double e = 0.0000000000;
		for (int i = 1; i < n; i++){
			e = e + Yield(P.getPrice(i,i),P.getPrice(i - 1,i - 1));
		}
                e = ((double)Math.round(e * 10000000000L))/ 10000000000L;
		return e/n;
	}
	
	private double Var(Stocks P, int n){
		double var = 0.0000000000;
                double expP = Expectancy(P, n);
		for (int i = 1; i < n; i++){
			var = var + (Math.pow((Yield(P.getPrice(i,i),P.getPrice(i - 1,i - 1)) - expP), 2)); 
		}
                var = ((double)Math.round(var * 10000000000L))/ 10000000000L;
		return var/n;
	}
        
	public String FormaterEX(double value){
            value = ((double)Math.round(value * 10000000000L))/ 10000000000L;
            if(value > 1){
               value = 1.00000000000;
            }
	    String formatedDouble = String.format("%.10f", value);
		return formatedDouble;
	}
	
}

