

/**
 * Created by neilleonard on 5/27/19.
 */
package packs;



public class packageTest {
    public static void testPacks(){
        System.out.print("testing da packs");
    }
    public static String wordTest(){
        return "word testing!";
    }

    public static double[] labelToVec(int label){
        double [][] vecDict = new double[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(j == i){
                    vecDict[i][j] = 1.0;
                }
                else{
                    vecDict[i][j] = 0.0;
                }
            }
        }
        System.out.println("packages in prep batch");
        return vecDict[label];


    }
  /**  public static double[][] loadMnist(MnistMatrix[] mats){
        int vecSize = 28*28;
        double[][] results = new double[mats.length][vecSize];
        for(int i = 0; i < mats.length; i++){
            results[i] = mats[i].vec;
        }
        return results;
    }
   */
}
