package lab3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.associations.Apriori;
import weka.associations.FPGrowth;
import weka.associations.PredictiveApriori;
import weka.core.Instances;

public class Rules {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}
	public static void main(String[] args) throws Exception {
		
		
		Apriori apriori = new Apriori();
		PredictiveApriori predictiveApriori = new PredictiveApriori();
		
		String file = "weather.nominal.arff";

		BufferedReader data = readDataFile(file);
		Instances dat = new Instances(data);
		
		  apriori.buildAssociations(dat);
		  System.out.println(apriori);
		  
		  
		  predictiveApriori.setClassIndex(-1);
		  predictiveApriori.setCar(true); 
		  predictiveApriori.setNumRules(10); 
		  predictiveApriori.buildAssociations(dat);
		  System.out.println(predictiveApriori.toString());
		  
		
		  
	}
}


