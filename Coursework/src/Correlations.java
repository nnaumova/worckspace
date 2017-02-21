

class Correlations {

	public double relativePriceChange(double Pt, double Pt1) {
		//double val = Math.log(Pt / Pt1);
		double val =(Pt-Pt1)/Pt1; //Ѕез разницы как считать
		//val = ((double) Math.round(val * 10000000000L)) / 10000000000L;
		System.out.println("val= "+val);
		return val;
	}

	public double expectancy(Stocks P, int n) {
		double e = 0;
		for (int i = 1; i < n; i++) {
			e = e + relativePriceChange(P.getPrice(i), P.getPrice(i - 1));
		}
		//e = ((double) Math.round(e * 10000000000L)) / 10000000000L;
		System.out.println("e/n= "+ e / n);
		return e / (n-1);
	}

	public double var(Stocks P, int n) {
		double var = 0;
		double expP = expectancy(P, n);
		for (int i = 1; i < n; i++) {
			var = var
					+ (Math.pow(
							(relativePriceChange(P.getPrice(i),
									P.getPrice(i - 1)) - expP), 2));
		}
		//var = ((double) Math.round(var * 10000000000L)) / 10000000000L;
		System.out.println("var = "+ var);
		return var/(n-1); // тут было деление на n? ?
	}

	public double calcCorr(Stocks Pi, Stocks Pj, int n, double threshold) {
		double corr = 0.0000000000;
		Stocks Pe = new Stocks();
		//Pe.setPrice(0.1);
		for (int i = 1; i < n; i++) {
			double newRelPrice = relativePriceChange(Pi.getPrice(i),Pi.getPrice(i - 1))* relativePriceChange(Pj.getPrice(i), Pj.getPrice(i - 1));
			Pe.setPrice(newRelPrice);
		}

		double cov = expectancy(Pe, n-1) - (expectancy(Pi, n) * expectancy(Pj, n));
		System.out.println( cov + " " +  (Math.sqrt(var(Pi, n)*Math.sqrt(var(Pj, n)))));
		corr = cov / (Math.sqrt(var(Pi, n)*Math.sqrt(var(Pj, n))));
		//corr = ((double) Math.round(corr * 10000000000L)) / 10000000000L;
		//System.out.println("corr"+ ((double)(Math.round(corr* 10000000000L))/ 10000000000L));

		//if (corr < threshold) {
		//	corr = 0.0000000000;
		//}
		return corr;
	}

	public static void main(String[] args) {
		Correlations c = new Correlations();
		Stocks a = new Stocks();
		Stocks b = new Stocks();
		for(int i =0; i<10; i++){
			a.setPrice((double) (i+1));
			b.setPrice((double) (i+2));
		}
		for(int i =0; i<10; i++){
			System.out.println(a.getPrice(i));
		}
		
		System.out.println(c.calcCorr(a,b,3,0.00000000001));
		double e = 0.0;
		for(int i = 1; i<3; i++){
			
				e = e + c.relativePriceChange(a.getPrice(i), a.getPrice(i - 1));
					}
	System.out.println(e);
	}
	
}
