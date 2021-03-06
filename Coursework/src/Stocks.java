import java.io.IOException;
import java.util.*;

public class Stocks {

	List<String> date;
	List<Double> open;
	List<Double> close;
	List<Double> yield;

	Stocks() {
		date = new ArrayList<String>();
		open = new ArrayList<Double>();
		close = new ArrayList<Double>();
		yield = new ArrayList<Double>();
	}

	public Double getcorrVal(int i) {
		return yield.get(i);
	}

	public int getSize() {
		return date.size();
	}

	public String getDate(int i) {
		return date.get(i);
	}

	public Double getOpen(int i) {
		return open.get(i);
	}

	public Double getClose(int i) {
		return close.get(i);
	}

	void setDate(String d) {
		date.add(d);
	}

	void setOpen(Double p) {
		open.add(p);
	}

	void setClose(Double p) {
		close.add(p);
	}

	void setCorrVal(Double p) {
		yield.add(p);
	}

	public void smooth() {

		int flag = 0;
		int temp = 0;
		for (int t = 0; t < close.size(); t++) {
			if (close.get(t) < 0.001 || close.get(t) > 9999999.9
					|| close.get(t).isInfinite())
				close.set(t, 0.0);

		}
		/*for (; temp < close.size() && close.get(temp) == 0.0; temp++) {
		}

		// System.out.println("������ " + temp);

		for (int i = temp + 1; i < close.size(); i++) {
			// System.out.println("i= " + i + " ���� " + flag);
			if (close.get(i) == 0.0) {
				close.set(i, close.get(i - 1));
				flag++;
				if (flag > 10)
					for (; i < close.size() - 2
							&& (close.get(i + 1) == 0.0 || close.get(i + 2) == 0.0); i++) {
					}
			} else
				flag = 0;
		}*/
	}

		/*
		 * for(int i = 1; i < close.size()-1; i++){ if(close.get(i)==0.0 &&
		 * close.get(i-1)!=0.0 && close.get(i+1)!=0.0){ close.set(i,
		 * (close.get(i-1)+close.get(i-1))/2); }
		 * 
		 * 
		 * }
		 */

	

	public void calcYield() {
		
		
		for (int i = 0; i < getSize() - 1; i++) {
			
			yield.add(Math.log(close.get(i + 1) / close.get(i)));
			//System.out.println(i + " " + yield.get(i));
		}
		
		for (int i = 0; i < getSize() - 1; i++) {
			if(yield.get(i).isInfinite()){
				yield.set(i, 0.0);
							}}
			int temp = 0;
			int flag = 0;
			for (; temp < yield.size() && yield.get(temp) == 0.0; temp++) {
			}

			// System.out.println("������ " + temp);

			for (int i = temp + 1; i < yield.size(); i++) {
				// System.out.println("i= " + i + " ���� " + flag);
				if (yield.get(i) == 0.0) {
					yield.set(i, yield.get(i - 1));
					flag++;
					if (flag > 10)
						for (; i < yield.size() - 2
								&& (yield.get(i + 1) == 0.0 || yield.get(i + 2) == 0.0); i++) {
						}
				} else
					flag = 0;
			}
			for (int i = 0; i < getSize() - 1; i++){
				if(yield.get(i).isNaN()){
					yield.set(i, 0.0);
				}
			}
			//System.out.println(printYield());
			
		}
		
	

	/*public List<Double> getYield(int a, int b) {
		// System.out.println(yield.size() + " ����� �����������");

		return yield.subList(a, b);

	}*/
	public double[] getYield(int a, int b) {
		
		List <Double> c =  yield.subList(a, b);
		double res[] = new double[c.size()];
		for(int i = 0; i<c.size(); i++){
			res[i]=c.get(i);
			
		}

		return res;

	}

	public double get1Yield(int a) {
		// System.out.println(yield.size() + " ����� �����������");

		return yield.get(a);

	}

	public String printYield() {
		// System.out.println(yield.size() + " ����� �����������");

		return yield.toString();

	}

	public String printPrice() {
		// System.out.println(yield.size() + " ����� �����������");

		return close.toString();

	}

	public boolean hasZeroYield(int a, int b) {
		if(yield.subList(a, b).contains(0.0))
		return true;
		else return false;
	}

}
