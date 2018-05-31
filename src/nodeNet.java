
public class nodeNet {
	public nodeLayer first;
	public nodeLayer last;
	public nodeLayer[] layers;
	public double mut;
	
	
	/**
	 * Constructor for the node Net. Using the convention that nodeNum[0] is input
	 * length, nodeNum[1] is output lenght, and nodeNum[i] represents the ith layer
	 * length
	 * @param numLay
	 * @param nodeNum
	 */
	public nodeNet(int numLay, int[] nodeNum){
		this.layers = new nodeLayer[numLay + 2];
		this.first = new nodeLayer(nodeNum[0], 0);
		if(nodeNum.length == 2){
			this.last = new nodeLayer(nodeNum[1], nodeNum[0]);
		}
		else{
			this.last = new nodeLayer(nodeNum[1], nodeNum[numLay + 1]);
		}
		
		this.first.nextLayer = this.last;
		this.last.prevLayer = this.first;
		this.layers[0] = this.first;
		this.layers[1] = this.last;
		
		for(int i = 0; i < numLay; i++){
			nodeLayer newLayer;
			if(i == 0){
				 newLayer = new nodeLayer(nodeNum[i + 2], nodeNum[0]);
			}
			else{
				newLayer = new nodeLayer(nodeNum[i + 2], nodeNum[i + 1]);
			}
			
			nodeLayer hld = this.last.prevLayer;
			hld.nextLayer = newLayer;
			newLayer.prevLayer = hld;
			this.last.prevLayer = newLayer;
			newLayer.nextLayer = this.last;
			this.layers[i + 2] = newLayer;
		}
	}
	
	public void frwdProp(){
		
		for(int i = 2; i < this.layers.length; i++){
			this.layers[i].forProp();
		}
		this.last.forProp();
	}

	public void bckProp(double[] answers){
		this.last.bckProp1(answers);
		for(int i = 2; i < this.layers.length; i++){
			this.layers[i].bckProp2();

		}


	}
	
	public String toString(){
		String results = "";
		results += this.layers[0].toString();
		results += "\n";
		for(int i = 2; i < this.layers.length; i++){
			results += this.layers[i].toString();
			results += "\n";
		}
		results += this.layers[1].toString();
		return results;
	}

	public void updateBias(){
		for(int i = 0; i < this.layers.length; i++){
			for(int j = 0; j < this.layers[i].nodes.length; j++){
				this.layers[i].derSumB[j] += this.layers[i].error[j];
			}
		}
	}
	public void updateWeights(){
		for(int i = 1; i < this.layers.length; i++){
			for(int j = 0; j < this.layers[i].nodes.length; j++){
				for(int k = 0; k < this.layers[i].nodes[j].wArray.length; k++){
					this.layers[i].nodes[j].errArray[k] += (this.layers[i].error[j] * this.layers[i].prevLayer.nodes[k].actValue);
				}
			}
		}
	}

	public void desBias(double mut, int batchSize){
		for(int i = 1; i < this.layers.length; i++){
			for(int j = 0; j < this.layers[i].nodes.length; j++){
				this.layers[i].nodes[j].bias += ((-1 * mut / batchSize) * this.layers[i].derSumB[j]);
			}
		}
	}

	public void desWei(double mut, int batchSize){
		for(int i = 1; i < this.layers.length; i++){
			for(int j = 0; j < this.layers[i].nodes.length; j++){
				for(int k = 0; k < this.layers[i].nodes[j].wArray.length; k++){
					this.layers[i].nodes[j].wArray[k] += ((-1 * mut / batchSize) * this.layers[i].nodes[j].errArray[k]);
				}
			}
		}
	}

	public void gradDes(double[][] in, double[][] out, double mut){
		for(int i = 0; i < this.layers.length; i++){
			this.layers[i].resetSums();
		}
		for(int i = 0; i < in.length; i++){
			for(int j = 0; j < in[i].length; j++){
				this.layers[0].nodes[j].actValue = in[i][j];
			}
			this.frwdProp();
			this.bckProp(out[i]);
			this.updateBias();
			this.updateWeights();




		}

		this.desBias(mut, in.length);
		this.desWei(mut, in.length);


	}
	
	
	
	public static void main(String[] args){
		
		 
		int[] hld = new int[4];
		hld[0] = 3;
		hld[1] = 4;
		hld[2] = 5;
		hld[3] = 7;
		
		nodeNet ndNet1 = new nodeNet(2, hld);
		
		System.out.println(ndNet1.layers[0].toString());
		System.out.println(ndNet1.layers[1].nodes[0].wArray.length);
		System.out.println(ndNet1.layers[1]);
		System.out.println(ndNet1.layers[2].nodes[0].wArray.length);
		System.out.println(ndNet1.layers[2]);
		System.out.println(ndNet1.layers[3].nodes[0].wArray.length);
		System.out.println(ndNet1.layers[3]);
		
		
		int[] hldr = new int[4];
		hldr[0] = 5;
		hldr[1] = 5;
		hldr[2] = 8;
		hldr[3] = 6;

		int[] hldr3 = new int[4];
		hldr3[0] = 5;
		hldr3[1] = 5;
		hldr3[2] = 3;
		hldr3[3] = 3;

		nodeNet ndNet2 = new nodeNet(2, hldr);
		nodeNet ndNet3 = new nodeNet(2, hldr3);
		//System.out.println(ndNet2.toString());
		//ndNet2.layers[0].nodes[0].actValue = 1;
		//ndNet2.frwdProp();
		//ndNet2.frwdProp();
		//System.out.println(ndNet2.toString());

		double[] answers = new double[2];
		answers[0] = 0.0;
		answers[1] = 1.0;


		System.out.println("here");
		//ndNet2.bckProp(answers);

		double[][] tstin = new double[2][2];
		tstin[0][0] = 0;
		tstin[0][1] = 1;
		tstin[1][0] = 1;
		tstin[1][1] = 1;
		double[][] tstout = new double[2][2];
		tstout[0][0] = 1;
		tstout[0][1] = 0;
		tstout[1][0] = 0;
		tstout[1][1] = 1;

		double[][] tstin2 = new double[1][5];
		tstin2[0][0] = 1;
		tstin2[0][1] = 0;
		tstin2[0][2] = 0;
		tstin2[0][3] = 0;
		tstin2[0][4] = 0;


		double[][]  tstout2 = new double[1][5];
		tstout2[0][0] = 0;
		tstout2[0][1] = 0;
		tstout2[0][2] = 0;
		tstout2[0][3] = 0;
		tstout2[0][4] = 1;


		System.out.println(ndNet2);
		for( int i = 0; i < 1000; i++){

			ndNet2.gradDes(tstin2, tstout2, .2);
			System.out.println("The complex neural net: " + "\n" + ndNet2 + "\n");


			ndNet3.gradDes(tstin2, tstout2, .2);
			System.out.println("The simple neural net: " + "\n" + ndNet3 + "\n");


		}


		// asdfasdf
	}


	

}
