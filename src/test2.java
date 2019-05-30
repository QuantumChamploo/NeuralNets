import java.io.IOException;
import packs.*;


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
        //System.out.println(vecToString(mnistMatrix[0].vec));
        double[][] allMats = loadMnist(mnistMatrix);
        for(int i = 0; i < allMats.length; i++){
            //System.out.println(vecToString(mnistMatrix[i].vec));
            //System.out.println(allMats.length);
        }

        prepBatch prep1 = new prepBatch(mnistMatrix,100,50);

        /**
        packageTest.printDouble(prep1.inVecs[0][0]);
        System.out.println("here");
        System.out.println("here");
        packageTest.printDouble(prep1.inVecs[0][1]);
        System.out.println(prep1.inVecs.length);
        for(int i = 0; i < 50; i++){
            System.out.println(prep1.inVecs[88][34].length);
            System.out.println(mnistMatrix[i].getLabel());
            System.out.println(mnistMatrix[i].getLabel());
            packageTest.printDouble(prep1.outVecs[0][i]);
        }
        System.out.println(mnistMatrix[0].getLabel());
        packageTest.printDouble(prep1.outVecs[0][0]);
         */
        int[] hld = new int[3];
        hld[0] = 784;
        hld[1] = 10;
        hld[2] = 90;
        nodeNet ndNet = new nodeNet(1, hld);
        for(int j = 0; j < 1; j++) {
            for (int i = 0; i < prep1.inVecs.length; i++) {
                ndNet.gradDes(prep1.inVecs[i], prep1.outVecs[i], 1.0);
            }
        }
        packageTest.printDouble(mnistMatrix[4999].matToVec());
        System.out.println(mnistMatrix[4999].getLabel());
        System.out.println(ndNet.toString());
        ndNet.onePass(prep1.inVecs[0][0]);
        System.out.println(ndNet.toString());
        System.out.println(mnistMatrix[0].getLabel());
        ndNet.onePass(prep1.inVecs[0][1]);
        System.out.println(ndNet.toString());
        System.out.println(mnistMatrix[1].getLabel());
        ndNet.onePass(prep1.inVecs[0][2]);
        System.out.println(ndNet.toString());
        System.out.println(mnistMatrix[2].getLabel());





    }


}
