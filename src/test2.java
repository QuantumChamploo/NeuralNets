import java.io.IOException;


/**
 * Will be used as a developing 'main' function.
 * Created by neilleonard on 5/22/19.
 */
public class test2 {
    public static String vecToString(double[] vec){
        String results = "[";
        for(int i = 0; i < vec.length; i++){
            results += vec[i] + ", " ;
        }
        results += "]";
        return results;
    }

    public static double[][] loadMnist(MnistMatrix[] mats){
        int vecSize = 28*28;
        double[][] results = new double[mats.length][vecSize];
        for(int i = 0; i < mats.length; i++){
            results[i] = mats[i].vec;
        }
        return results;
    }

    public static double [][][] prepBatch(MnistMatrix[] mats, int sets, int size){
        int vecSize = 28*28;
        double [][][] vecResults = new double[sets][size][vecSize];
        double [][][] labelResults = new double[sets][size][10];





        double [][] vecs = loadMnist(mats);
        for(int j = 0; j < sets; j++) {
            for (int i = 0; i < size; i++) {
                vecResults[j][i] = vecs[size * j + i];

            }
        }
        return vecResults;

    }


    public static void main(String[] args) throws IOException {
        MnistMatrix[] mnistMatrix = new MnistDataReader().readData("data/train-images.idx3-ubyte", "data/train-labels.idx1-ubyte");
        mnistMatrix[mnistMatrix.length - 1].printMnistMatrix();
        mnistMatrix = new MnistDataReader().readData("data/t10k-images.idx3-ubyte", "data/t10k-labels.idx1-ubyte");
        mnistMatrix[0].printMnistMatrix();
        double[] vec1 = mnistMatrix[0].matToVec();
        System.out.println(vecToString(mnistMatrix[0].vec));
        double[][] allMats = loadMnist(mnistMatrix);
        for(int i = 0; i < allMats.length; i++){
            //System.out.println(vecToString(mnistMatrix[i].vec));
            //System.out.println(allMats.length);
        }
        prepBatch prep1 = new prepBatch(mnistMatrix,100,50);



    }


}
