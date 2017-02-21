package kate;
import java.util.List;
import java.util.ArrayList;

public class Stocks {
        String secid;
	List<String> date;
	List<Double> values;
	List<Integer> numtrades; 
	int count = 0;
	int scale = 0;
	List<Double> prices;
        List<Integer> vector;
	
	public Stocks(){
		numtrades = new ArrayList<Integer>();
		date = new ArrayList<String>();
		values = new ArrayList<Double>();
		prices = new ArrayList<Double>();
                vector = new ArrayList<Integer>();
                for(int i = 0; i < 20; i++){
                    vector.add(0);
                }
	}
        
        public void setVecValue(int index, int value){
            vector.set(index, value);
        }
        
        public int getVecValue(int index){
            return vector.get(index);
        }
        
        public int getVecSize(){
            return vector.size();
        }
        
        public void setDateValue(int index, String val){
            date.set(index, val);
        }
        
        public void setPriceValue(int index, Double val){
            prices.set(index, val);
        }
	
	public void setDate(String dat){
		date.add(dat);
	}
	
	public int getDateCount(){
		return date.size();
	}
        
        public int getValuesSize(){
            return values.size();
        }
	
	public String getDate(int index){
		return date.get(index);
	}
	
	public void setScale(int i){
		scale = i;
	}
	
	public int getScale(){
		return scale;
	}
	
	public void setVal(int c){
		count = c;
	}
	
	public int getVal(){
		return count;
	}
	
	public int lenght(){
		return values.toArray().length;
	}
	
	public void setNumTrades(int num){
		numtrades.add(num);
	}
	
	public int getNumTrades(int begin, int end){
		int res = 0;
		for(int i = begin; i < end; i++){
			res = res + numtrades.get(i);
		}
		return res;
	}
	
	public void setPrice(Double price){
		prices.add(price);
	}
	
	public int getPricesCount(){
		return prices.toArray().length;
	}
	
	public Double get(int begin, int end){
		double res = 0;
		if(end - begin == 0){
			res = prices.get(end);
		} else {
			double val1 = prices.get(end - 1);
			double val2 = prices.get(end - 2);
			res = Math.log(val1) - Math.log(val2);
		}
		return res;
	}
        
        public Double getPrice(int begin, int end){
            return prices.get(begin);
        }
	
	public void setSecid(String secVal){
		secid = secVal;
	}
	
	public String getSecid(){
		return secid;
	}
	
	public Double minValue(int begin, int end) {
		double min = values.get(begin);
		for(int i = begin; i < end; i++){
			if(values.get(i) < min) { min = values.get(i); }
		}
		return min;
	}
	
	public Double maxValue(int begin, int end) {
		double max = values.get(begin);
		for(int i = begin; i < end; i++){
			if(values.get(i) > max) { max = values.get(i); }
		}
		return max;
	}
	
	public Double ArgValue(int begin, int end) {
		double argvalue = 0;
		int c = 0;
		for(int i = begin; i < end; i++){
			argvalue = argvalue + values.get(i);
		}
		if ( begin == end ) { c = 1; }
		else { c = end - begin; }
		return (argvalue/c);
	}
	
	public void setValue(Double val){
		values.add(val);
	}
	
	public Double getValue(int i){
		return values.get(i);
	}
}
