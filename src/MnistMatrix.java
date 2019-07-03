/**
 * Created by neilleonard on 5/22/19.
 */
public class MnistMatrix {

    private int [][] data;

    private int nRows;
    private int nCols;

    private int label;

    public double [] vec;

    public MnistMatrix(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;

        data = new int[nRows][nCols];

    }

    public int getValue(int r, int c) {
        return data[r][c];
    }

    public void setValue(int row, int col, int value) {
        data[row][col] = value;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getNumberOfRows() {
        return nRows;
    }

    public int getNumberOfColumns() {
        return nCols;
    }

    public void setVec(){
        vec = this.matToVec();
    }



    public void printMnistMatrix() {
        System.out.println("label: " + this.getLabel());
        for (int r = 0; r < this.getNumberOfRows(); r++ ) {
            for (int c = 0; c < this.getNumberOfColumns(); c++) {
                System.out.print(this.getValue(r, c) + " ");
            }
            System.out.println();
        }
    }

    public double[] matToVec(){
        // The matrix is a 28 by 28 sized square

        int vecSize = 28*28;
        double[] vec = new double[vecSize];
        for(int i = 0; i < vecSize; i++){
            int mod = i%28;
            int mult = (i - mod)/28;
            double datum = this.getValue(mult,mod);
            vec[i] = datum/256;
        }

        return vec;
    }

}
