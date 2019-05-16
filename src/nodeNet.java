
public class nodeNet {
	public nodeLayer first;
	public nodeLayer last;
	public nodeLayer[] layers;
	public double mut;
	
	
	/**
	 * Constructor for the node Net. Using the convention that nodeNum[0] is input
	 * length, nodeNum[1] is output length, and nodeNum[i] represents the ith layer
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

	/**
	 *  Uses the nodeLayer forProp method on each layer. Note the order it is done in, reflecting the
	 *  ordering convention in our layers field
	 */
	public void frwdProp(){
		
		for(int i = 2; i < this.layers.length; i++){
			this.layers[i].forProp();
		}
		this.last.forProp();
	}

	public void bckProp(double[] answers){
		this.last.bckProp1(answers);
		for(int i = this.layers.length - 1; i > 1 ; i--){
			this.layers[i].bckProp2();
		}
	}

	/**
	 * the update sum of the errors in the weights and bias. Used in between each piece of training data
	 * in a batch
	 */

	public void updateBias(){
		for(int i = 0; i < this.layers.length; i++){
			for(int j = 0; j < this.layers[i].nodes.length; j++){
				this.layers[i].derSumB[j] += this.layers[i].error[j];
			}
		}
	}

	/**
	 * Same as above but for the weights
	 */
	public void updateWeights(){
		for(int i = 1; i < this.layers.length; i++){
			for(int j = 0; j < this.layers[i].nodes.length; j++){
				for(int k = 0; k < this.layers[i].nodes[j].wArray.length; k++){
					this.layers[i].nodes[j].errArray[k] += (this.layers[i].error[j] * this.layers[i].prevLayer.nodes[k].actValue);
				}
			}
		}
	}

	/**
	 * the 'des' functions will be used when implementing training batches. Applies the corrections
	 * gathered during a training batch
	 * @param mut a variable that is a scalar for how far we want to mutate the weights and bias
	 * @param batchSize size of the batch of data we used
     */
	public void desBias(double mut, int batchSize){
		for(int i = 1; i < this.layers.length; i++){
			for(int j = 0; j < this.layers[i].nodes.length; j++){
				this.layers[i].nodes[j].bias += ((-1 * mut / batchSize) * this.layers[i].derSumB[j]);
			}
		}
	}

	/**
	 * Same as above but for weights
	 * @param mut
	 * @param batchSize
     */
	public void desWei(double mut, int batchSize){
		for(int i = 1; i < this.layers.length; i++){
			for(int j = 0; j < this.layers[i].nodes.length; j++){
				for(int k = 0; k < this.layers[i].nodes[j].wArray.length; k++){
					this.layers[i].nodes[j].wArray[k] += ((-1 * mut / batchSize) * this.layers[i].nodes[j].errArray[k]);
				}
			}
		}
	}

	/**
	 * the main function. Pretty simple as it basically uses all the helper functions
	 *
	 *
	 * @param in
	 * @param out
	 * @param mut
     */

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



			for(int k = 0; k < this.layers.length; k++){
				this.layers[k].softReset();
			}
		}

		this.desBias(mut, in.length);
		this.desWei(mut, in.length);

	}

	/**
	 * Helper function used to put a single piece of training data into the front of the net, 'loading it'.
	 * Note that this just puts the values into the input later, and does not forward propagate
	 * @param in
     */
	public void onePass(double[] in) {


		for(int j = 0; j < in.length; j++){
			this.layers[0].nodes[j].actValue = in[j];
		}
	}
	// Below are a collection of toString methods for various printing options. Useful for debugging
	/**
	 *
	 * @return text rep of nets activation values
	 */
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

	/**
	 * A second to string method that shows the nets weights
	 * @return
	 */
	public String toString2(){
		String results = "";
		results += this.layers[0].toString();
		results += "\n";
		for(int i = 2; i < this.layers.length; i++){
			results += this.layers[i].toString2();
			results += "\n";
		}
		results += this.layers[1].toString2();
		return results;

	}
	/**
	 * A second to string method that shows the nets error
	 * @return
	 */
	public String toString3(){
		String results = "";
		results += this.layers[0].toString();
		results += "\n";
		for(int i = 2; i < this.layers.length; i++){
			results += this.layers[i].toString3();
			results += "\n";
		}
		results += this.layers[1].toString3();
		return results;

	}

	/**
	 * A second to string method that shows the nets bias
	 * @return
	 */
	public String toString4(){
		String results = "";
		results += this.layers[0].toString();
		results += "\n";
		for(int i = 2; i < this.layers.length; i++){
			results += this.layers[i].toString4();
			results += "\n";
		}
		results += this.layers[1].toString4();
		return results;

	}

	/**
	 * A second to string method that shows the nets error
	 * @return
	 */
	public String toString5(){
		String results = "";
		results += this.layers[0].toString();
		results += "\n";
		for(int i = 2; i < this.layers.length; i++){
			results += this.layers[i].toString5();
			results += "\n";
		}
		results += this.layers[1].toString5();
		return results;

	}
	/**
	 * A second to string method that shows the nets wInputs
	 * @return
	 */
	public String toString6(){
		String results = "";
		results += this.layers[0].toString();
		results += "\n";
		for(int i = 2; i < this.layers.length; i++){
			results += this.layers[i].toString6();
			results += "\n";
		}
		results += this.layers[1].toString6();
		return results;

	}
	public String printAll(){
		String results = "";
		results += "The Neural net looks like \n" + this.toString() + "\n" + "with weights \n"  + this.toString2();
		results += "\n" + "With bias \n" + this.toString4() + "\n" + "With errors \n" + this.toString5();
		return results;
	}
	
	
	public static void main(String[] args){
	}

}
