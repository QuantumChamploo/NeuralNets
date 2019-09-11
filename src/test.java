/**
 * Created by neilleonard on 5/12/19. A script just used to test the Neural Netsa=
 */
public class test {

    public static void main(String[] args){

        // below are sample nets being made and run

        //creating an int array to be used in neuralnet 1. Simple net with no hidden layers
        int[] hldr = new int[2];
        hldr[0] = 2;
        hldr[1] = 2;

        // create the net
        nodeNet ndNet1 = new nodeNet(0, hldr);

        //another int array to be used in the nodenet2. 4 layers with sized as defined below
        int[] hldr2 = new int[4];
        hldr2[0] = 5;
        hldr2[1] = 5;
        hldr2[2] = 8;
        hldr2[3] = 6;
        // a third int array for the nodenet3 construction. One hidden layer
        int[] hldr3 = new int[3];
        hldr3[0] = 2;
        hldr3[1] = 2;
        hldr3[2] = 3;

        nodeNet ndNet2 = new nodeNet(2, hldr2);
        nodeNet ndNet3 = new nodeNet(1, hldr3);
        System.out.println(ndNet3.toString5());

        // test inputs and outputs as described in the variables name. Sizes picked to match above neural nets
        double[][] tstin2 = new double[1][2];
        tstin2[0][0] = 1;
        tstin2[0][1] = 0;

        double[][] tstin3 = new double[1][2];
        tstin3[0][0] = 1;
        tstin3[0][1] = 1;

        double[][]  tstout2 = new double[1][2];
        tstout2[0][0] = 0;
        tstout2[0][1] = 1;

        double [][] tstin4 = new double[2][2];
        tstin4[0][0] = 1;
        tstin4[0][1] = 0.01;
        tstin4[1][0] = 0.01;
        tstin4[1][1] = 1;

        double[][] tstout4 = new double[2][2];
        tstout4[0][0] = 0;
        tstout4[0][1] = 1;
        tstout4[1][0] = 1;
        tstout4[1][1] = 0;

        // Below creates a simple neural net and trains it on two pieces of data and then shows that the net
        // can handle two different types of inputs to classify
        for( int i = 0; i < 10000; i++){

            //ndNet1.gradDes(tstin2, tstout2, .02);
            //System.out.println("The complex neural net: " + "\n" + ndNet1 + "\n");


            //ndNet1.gradDes(tstin2, tstout2, .02);
            //System.out.println("The simple neural net: " + "\n" + ndNet1 + "\n");
            ndNet3.gradDes(tstin4, tstout4, .2);
            System.out.println(ndNet3.printAll());
            /**
            ndNet3.onePass(tstin2[0]);
            ndNet3.frwdProp();
            System.out.println(ndNet3.toString());
            ndNet3.bckProp(tstout2[0]);
            System.out.println(ndNet3.printAll());
            ndNet3.updateBias();;
            ndNet3.updateWeights();
            for(int k = 0; k < ndNet3.layers.length; k++){
                ndNet3.layers[k].softReset();
            }

            ndNet3.desBias(1,1);
            ndNet3.desWei(1,1);
            System.out.println(ndNet3.printAll());
             */


        }

        // Now a simple forward pass to inspect how our net does after creation
        ndNet3.onePass(tstin4[0]);
        ndNet3.frwdProp();
        System.out.println(ndNet3.toString());











    }

}
