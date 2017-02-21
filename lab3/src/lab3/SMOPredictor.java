package lab3;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import weka.associations.Apriori;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.functions.SMO;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class SMOPredictor {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}

	public static Evaluation classify(Classifier model, Instances trainingSet,
			Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);

		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);

		return evaluation;
	}

	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;

		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
		return 100 * correct / predictions.size();
	}

	public static void main(String[] args) throws Exception {

		String trainPath, testPath;

		trainPath = "weather.txt";
		testPath = "weatherTest.txt";

		BufferedReader trainfile = readDataFile(trainPath);
		BufferedReader testfile = readDataFile(testPath);
		Instances trainData = new Instances(trainfile), testData = new Instances(
				testfile);

		SMO smo = new SMO();

		trainData.setClassIndex(trainData.numAttributes() - 1); // the
		testData.setClassIndex(testData.numAttributes() - 1); // target
		// attribute

		smo.buildClassifier(trainData);

		

		System.out.println("# of training data: " + trainData.numInstances());
		System.out.println("# of testing data: " + testData.numInstances());


		FastVector predictions = new FastVector();

		

		Evaluation validation = classify(smo, trainData, testData);

		predictions.appendElements(validation.predictions());

		// Uncomment to see the summary for each training-testing pair.
		System.out.println(smo.toString());

		// Calculate overall accuracy of current classifier on all splits
		double accuracy = calculateAccuracy(predictions);

		// Print current classifier's name and accuracy in a complicated,
		// but nice-looking way.
		System.out.println("Accuracy of " + smo.getClass().getSimpleName()
				+ ": " + String.format("%.2f%%", accuracy)
				+ "\n---------------------------------");
		
		   
		    
		

	}
}
