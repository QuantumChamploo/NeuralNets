import java.util.*;
import packs.*;


/**
 * Created by neilleonard on 5/27/19.
 */



public class prepBatch {
    public double [][][] inVecs;
    public double [][][] outVecs;
    public MnistMatrix asdf;






    public  prepBatch(MnistMatrix[] mats, int sets, int size){
        int vecSize = 28*28;
        double [][][] vecResults = new double[sets][size][vecSize];
        double [][][] labelResults = new double[sets][size][10];


        double [][] vecs = loadMnist(mats);
        for(int j = 0; j < sets; j++) {
            for (int i = 0; i < size; i++) {
                vecResults[j][i] = vecs[size * j + i];
                labelResults[j][i] =packageTest.labelToVec(mats[size * j + i].getLabel());
                //Please god be on my git

            }
        }


        packageTest.labelToVec(3);
        packageTest.printDouble(packageTest.labelToVec(3));




    }
    public static double[][] loadMnist(MnistMatrix[] mats){
        int vecSize = 28*28;
        double[][] results = new double[mats.length][vecSize];
        for(int i = 0; i < mats.length; i++){
            results[i] = mats[i].vec;
        }
        return results;
    }


}
