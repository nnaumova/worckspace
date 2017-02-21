package kate;

import java.util.ArrayList;

public class TrainStrategy {
    ArrayList<DataSetPattern> traindata; 
    ArrayList<DataSetPattern> mapdata;
    ArrayList<DataSetPattern> instances;
    int patterns;
    int classCounts;
    int pCounts;
    int inputLayerModel;
    int hiddenLayerModel;
    
    public String[] MPL_testing(String readfile, int uinputLayerModel, int uhiddenLayerModel, int outputLayerModel, int numHiddenLayersModel){
        String[] results = new String[9];
        
        for(int p = 10; p < 100; p += 10){
            for(int i = 0; i< instances.size(); i++){
                instances.get(i).set_busy(false);
            }
            int numPatterns = (int)Math.round((patterns/100)*p);
            MultiLayerPerceptron mlp_bloodDonate = new MultiLayerPerceptron(inputLayerModel, hiddenLayerModel, outputLayerModel, numHiddenLayersModel, 1.0);
            traindata = new ArrayList<DataSetPattern>();
            mapdata = new ArrayList<DataSetPattern>();

            int countComplete = numPatterns;
            while(countComplete > 0){
                for(int i = 0; i < instances.size(); i++){
                    if((Math.random() > 0.5) && (countComplete != 0)) {
                        if( instances.get(i).get_busy() == false) {
                            traindata.add(instances.get(i));
                            instances.get(i).set_busy(true);
                            countComplete--;
                        }
                    }
                }
            }

            int[] classver = new int[patterns - numPatterns];
            int j = 0;
            for(int i = 0; i < instances.size(); i++){
                 if (instances.get(i).get_busy() == false) {
                      mapdata.add(instances.get(i));
                      classver[j] = i;
                      j++;
                 }  
            }

            for (int iter = 0; iter < 1000; iter ++){
                    for(int i = 0; i < traindata.size(); i++){
                        double[] input = new double[pCounts];
                        for(int k = 0; k < pCounts; k++){
                            input[k] = traindata.get(i).get(k);
                        }
                        double[] target = new double[2];
                        if(traindata.get(i).get_fclass() == 0){
                            target[0] = 1;
                            target[1] = 0;
                        } else {
                            target[0] = 0;
                            target[1] = 1;  
                        }
                        mlp_bloodDonate.train(input, target);
                    }
                }

            double testing = 0;
             for(int i = 0; i < mapdata.size(); i++){
                    double[] instance = new double[pCounts];
                    for (int k = 0; k < pCounts; k++){
                        instance[k] = mapdata.get(i).get(k);
                    }
                    double[] cl = mlp_bloodDonate.classify(instance);

                    double resClass1 = cl[0];
                    double resClass2 = cl[1];
                    double resClass = 0.0;
                    if(resClass1 > resClass2) { 
                       resClass = 0.0;
                    } else {
                       resClass = 1.0; 
                    }
                    if (resClass == instances.get(classver[i]).get_fclass()){ testing++; }
                    System.out.println("Classified class1: "+ resClass1);
                    System.out.println("Classified class2: "+ resClass2);

             }
             results[(p - 10)/10] = String.valueOf(1 - (testing/(patterns - numPatterns)));
        }
        return results;
    }
}
