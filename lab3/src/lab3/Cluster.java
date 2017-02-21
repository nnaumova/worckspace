package lab3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.Instances;

public class Cluster {
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
			
			String file = "DATA_NEW Ч копи€.arff";

			BufferedReader data = readDataFile(file);
			Instances dat = new Instances(data);
		
	String[] options = new String[2];
	 options[0] = "-I";                 // max. iterations
	 options[1] = "100";
	 EM clusterer = new EM();   // new instance of clusterer
	 clusterer.setOptions(options);     // set the options
	 clusterer.buildClusterer(dat);    // build the clusterer
	 System.out.println(clusterer.toString());
	 ClusterEvaluation eval = new ClusterEvaluation();
	 eval.setClusterer(clusterer);                                   // the cluster to evaluate
	 eval.evaluateClusterer(dat);                                // data to evaluate the clusterer on
	 System.out.println("# of clusters: " + eval.getNumClusters());  // output # of clusters
}
}