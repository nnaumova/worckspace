package kate;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class prepareClassiffFiles {
    
    public void Normalize(String readFile, String writeFile, String className1, String className2){
        try{
            CsvWriter writer = new CsvWriter(new FileWriter(writeFile, false), ';');
            CsvReader reader = new CsvReader(readFile);
            
            reader.setDelimiter(';');
            reader.readHeaders();
            
            ArrayList<Stocks> instances = new ArrayList<Stocks>();
            
            for(int i = 0; i < 20; i++){
                instances.add(new Stocks());
            }
            while (reader.readRecord()){
                for(int i = 0; i < 20 ; i++){
                    instances.get(i).setValue(Double.valueOf(reader.get("int"+ i)));
                }
            }
            reader.close();
            
            ArrayList<Double> m = new ArrayList<Double>();
            ArrayList<Double> max = new ArrayList<Double>();
            
            for(int i = 0; i < 20; i++){
                double mtmp = instances.get(i).ArgValue(0, instances.get(i).getValuesSize() - 1);
                m.add(mtmp);
                double maxtmp = instances.get(i).maxValue(0, instances.get(i).getValuesSize() - 1);
                max.add(maxtmp);
            }
            
            for(int j = 0; j < instances.get(0).getValuesSize(); j++){
                for(int i = 0 ; i < 20 ; i++){
                    double val = 0;
                    if( max.get(i) != 0){
                        val = (instances.get(i).getValue(j) - m.get(i))/max.get(i);
                    } 
                    writer.write(String.valueOf(val));
                }
                writer.write(className1);
                writer.write(className2);
                writer.endRecord();
            }
            writer.close();
               
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    
    public void sp500sortString(String readFile, String writeFile){
        try{
            CsvReader reader = new CsvReader("company_list_sp500.csv");
            ArrayList<String> tickers = new ArrayList<String>(); 
            reader.setDelimiter(';');
            reader.readHeaders();
            while (reader.readRecord()){
                tickers.add(reader.get("Symbol"));
            }
            reader.close();
            
            CsvWriter writer = new CsvWriter(new FileWriter(writeFile, false), ';');
            writer.write("Company");
            writer.write("Date");
            writer.write("Open");
            writer.write("High");
            writer.write("Close");
            writer.write("Volume");
            writer.endRecord();
            for(int j = 1 ; j < 10; j++) {
                reader = new CsvReader(j + "_" + readFile);
                reader.setDelimiter(';');
                reader.readHeaders();
                while (reader.readRecord()){
                    for(int i = 0; i < tickers.size(); i++){
                        if(tickers.get(i).equals(reader.get("TICKER"))){
                            writer.write(reader.get("TICKER"));
                            writer.write(reader.get("DATE"));
                            writer.write(reader.get("OPEN"));
                            writer.write(reader.get("HIGH"));
                            writer.write(reader.get("PRICE"));
                            writer.write(reader.get("VOLUME"));
                            writer.endRecord();
                        }
                    }
                }
                reader.close();
             }
             writer.close();
            
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    private static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		
		StringBuffer result = new StringBuffer();
		
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		 }
		 result.append(str.substring(s));
		 
	     return result.toString();
   }
    
    public void vectorCorr(String readFile, int count_files, int count_stocks, String classname, String writeFile, double threshold){
        try{
            CsvWriter writer = new CsvWriter(new FileWriter(writeFile, false), ';');
            for (int i = 0 ; i < 20; i++){
                 writer.write("int" + i);
            }
            writer.write("class");
            writer.endRecord();
            
           for (int i = 0; i < count_files; i++){
                CsvReader reader = new CsvReader(i + readFile);
                reader.setDelimiter(';');
                reader.readHeaders();
                ArrayList<Stocks> corr = new ArrayList<Stocks>(); 
                double value = 0.00000000000;
                int index = 0;
                int iden = 0;
                corr.add(new Stocks());
                reader.readRecord();
                value  = Double.parseDouble(replace(reader.get("Correlation"),",","."));
                if (value < threshold){
                    corr.get(iden).setValue(0.00000000000);                  
                } else {
                    corr.get(iden).setValue(value);
                 }
                while (reader.readRecord()){
                    value  = Double.parseDouble(replace(reader.get("Correlation"),",","."));
                    
                    if(index < count_stocks - 1){
                        if (value < threshold){
                            corr.get(iden).setValue(0.00000000000);                  
                        } else {
                            corr.get(iden).setValue(value);
                        }
                        index ++;
                    } else {
                        index = 0;
                        iden ++;
                        corr.add(new Stocks());
                        if (value < threshold){
                            corr.get(iden).setValue(0.00000000000);                  
                        } else {
                            corr.get(iden).setValue(value);
                        }
                    }
                }
                reader.close();
                
             
             int c = 0;
             for(int j = 0; j < corr.size(); j++){
                 for(int m = 0; m < corr.size(); m++){
                     double interval  = 0;
                     double val = corr.get(j).getValue(m);
                     for(int k = 0; k < 20 ; k++){
                         double end = interval + 0.05;
                         if((val >= interval) && (val < end)){
                                c = corr.get(j).getVecValue(k);
                                c++;
                                corr.get(j).setVecValue(k, c);
                                k = 20;
                            }
                            if(val == 1.0000000000){
                                c = corr.get(j).getVecValue(19);
                                c++;
                                corr.get(j).setVecValue(19, c);
                                k = 20;
                            }
                        interval = end;
                     }
                 }
             }
             
             ArrayList<Integer> vec = new ArrayList<Integer>();
             for(int j = 0; j < 20; j++){
                 vec.add(0);
             }
             
             int cc = 0;
             for(int j = 0; j < corr.size(); j++){
                 for(int m = 0; m < corr.get(j).getVecSize(); m++){
                     if(corr.get(j).getVecValue(m) > 0){
                         cc = vec.get(m);
                         cc ++;
                         vec.set(m, cc);
                     }
                 }
             }
             
             for(int k = 0; k < 20; k++){
                 writer.write(String.valueOf(vec.get(k)));
             }
             writer.write(classname);
             writer.endRecord();
           }
            writer.close();
            
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
 
    
    public void timeScalesUSA(String dateFile, String readFile, String writeFile, int interval){
        try{
            CsvReader reader = new CsvReader(dateFile);
            ArrayList<String> dates = new ArrayList<String>(); 
            reader.readHeaders();
            while (reader.readRecord()){
                dates.add(reader.get("Date"));
            }
            reader.close();
            
            CsvWriter writer = new CsvWriter(new FileWriter(writeFile, false), ';');
            
            int delimtmp = dates.size() - interval;
            for(int i = 0; i < delimtmp; i++){
            writer = new CsvWriter(new FileWriter(i + "_" + interval + "_" + writeFile, false), ';');
            writer.write("Company");
            writer.write("Date");
            writer.write("Price");
            writer.write("Volume");
            writer.endRecord();
           
            int delim = i + interval;
            
                reader = new CsvReader(readFile);
                reader.setDelimiter(';');
                reader.readHeaders();
                while (reader.readRecord()){
                    for(int j = i; j < delim; j++){
                    if(dates.get(j).equals(reader.get("Date"))){
                        writer.write(reader.get("Company"));
                        writer.write(reader.get("Date"));
                        writer.write(reader.get("Close"));
                        writer.write(reader.get("Volume"));
                        writer.endRecord();
                    } 
                }
                
            }
            reader.close();
            writer.close();
            }
            
            
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    public void calcCorrelationMarket(String readFile, String writeFile){
        try{
            CsvReader reader = new CsvReader(readFile);
            ArrayList<Stocks> stocks = new ArrayList<Stocks>(); 
            
            reader.setDelimiter(';');
            reader.readHeaders();
            
            int index = 0;
            reader.readRecord();
            String ticker = reader.get("Company");
            stocks.add(new Stocks());
            stocks.get(index).setSecid(ticker);
            stocks.get(index).setPrice(Double.parseDouble(reader.get("Price")));
            
            while (reader.readRecord()){
               if(ticker.equals(reader.get("Company"))){ 
                    stocks.get(index).setPrice(Double.parseDouble(reader.get("Price")));
               } else {
                    index++;
                    ticker = reader.get("Company");
                    stocks.add(new Stocks());
                    stocks.get(index).setSecid(ticker);
                    stocks.get(index).setPrice(Double.parseDouble(reader.get("Price")));
               }
            }
            reader.close();
            
            CsvWriter writer = new CsvWriter(new FileWriter(writeFile, false), ';');
            writer.write("Correlation");
            writer.endRecord();
            calcCorrelations ct = new calcCorrelations();
            double corr = 0;
            for(int i = 0; i < stocks.size(); i++){
                for (int j = 0; j < stocks.size(); j++){
                    corr = ct.calcCorr(stocks.get(i), stocks.get(j), stocks.get(i).getPricesCount(), -1.0);
                    writer.write(ct.FormaterEX(corr));
                    writer.endRecord();
                }
            }
            writer.close();
            
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    public void sortSP500Files(String dataFile, String readFile, String writeFile){
        try{
            CsvReader reader = new CsvReader(dataFile);
            ArrayList<String> dates = new ArrayList<String>(); 
            reader.readHeaders();
            while (reader.readRecord()){
                dates.add(reader.get("Date"));
            }
            reader.close();
            
            CsvWriter writer = new CsvWriter(new FileWriter(writeFile, false), ';');
            writer.write("Company");
            writer.write("Date");
            writer.write("Open");
            writer.write("High");
            writer.write("Close");
            writer.write("Volume");
            writer.endRecord();
            
            for(int i = 0; i < dates.size(); i++){
            reader = new CsvReader(readFile);
            reader.setDelimiter(';');
            reader.readHeaders();
            
            while (reader.readRecord()){
                if((dates.get(i).equals(reader.get("Date")))){
                    writer.write(reader.get("Company"));
                    writer.write(reader.get("Date"));
                    writer.write(reader.get("Open"));
                    writer.write(reader.get("High"));
                    writer.write(reader.get("Close"));
                    writer.write(reader.get("Volume"));
                    writer.endRecord();
                } 
            }
            reader.close();
            }
            writer.close();
            
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    

    
    public void prepSP500Files(String dataFile, String readFile, String writeFile){
        try{
            CsvReader reader = new CsvReader(dataFile);
            ArrayList<String> dates = new ArrayList<String>(); 
            reader.readHeaders();
            while (reader.readRecord()){
                dates.add(reader.get("Date"));
            }
            reader.close();
            
            CsvWriter writer = new CsvWriter(new FileWriter(writeFile, false), ';');
            writer.write("Company");
            writer.write("Date");
            writer.write("Open");
            writer.write("High");
            writer.write("Close");
            writer.write("Volume");
            writer.endRecord();
            
            reader = new CsvReader(readFile);
            reader.setDelimiter(';');
            reader.readHeaders();
            
             while (reader.readRecord()){
             for(int i = 0; i < dates.size(); i++){
                if((dates.get(i).equals(reader.get("Date")))){
                    writer.write(reader.get("Company"));
                    writer.write(reader.get("Date"));
                    writer.write(replace(reader.get("Open"),",","."));
                    writer.write(replace(reader.get("High"),",","."));
                    writer.write(replace(reader.get("Close"),",","."));
                    writer.write(reader.get("Volume"));
                    writer.endRecord();
                }
            }
            }
            writer.close();
            reader.close();
            
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    public void clearData(String readFile, String writeFile, int days){
      try{
          CsvReader reader = new CsvReader(readFile);
          CsvWriter writer = new CsvWriter(new FileWriter(writeFile, false), ';');
          reader.setDelimiter(';');
          reader.readHeaders();
          writer.write("Company");
          writer.write("Date");
          writer.write("Open");
          writer.write("High");
          writer.write("Close");
          writer.write("Volume");
          writer.endRecord();
          while (reader.readRecord()){
            if((Double.parseDouble(replace(reader.get("Volume"),",",".")) > 0) && (Double.parseDouble(replace(reader.get("Close"), ",", ".")) > 0)){ 
                writer.write(reader.get("Company"));
                writer.write(reader.get("Date"));
                writer.write(reader.get("Open"));
                writer.write(reader.get("High"));
                writer.write(reader.get("Close"));
                writer.write(reader.get("Volume"));
                writer.endRecord();
            }
          }
          writer.close();
          reader.close();
          
          reader = new CsvReader(writeFile);
          reader.setDelimiter(';');
          reader.readHeaders();
          reader.readRecord();
          String ticker = reader.get("Company");
          int count = 1;
          int index = 0;
          while (reader.readRecord()){
             if(ticker.equals(reader.get("Company"))){
                 count++;
             } else {
                 if(count > days){
                     System.out.println(index + " " + ticker + " : " + count);
                     index++;
                 }
                 ticker = reader.get("Company");
                 count = 1;
             }
          }
          reader.close();
          System.out.println("Tickers: " + index);
          
       } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
        }
    }
}

