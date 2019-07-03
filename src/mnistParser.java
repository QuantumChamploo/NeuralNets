

/**
 * Created by neilleonard on 5/23/19.
 */
public class mnistParser {
    public float[] matToVec(MnistMatrix mat){
        // The matrix is a 28 by 28 sized square

        int vecSize = 28*28;
        float[] vec = new float[vecSize];
        for(int i = 0; i < vecSize; i++){
            int mod = i%28;
            int mult = (i - mod)/28;
            float datum = mat.getValue(mult,mod);
            vec[i] = datum;
        }

        return vec;
    }

}
