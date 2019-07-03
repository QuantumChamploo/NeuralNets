
public class nodeLayer {

	/**
	 * fields for the nodeLayer class. A reference to the previous layer and next layer.
	 * Nodes are held in an array. We will also keep the weighted input and the error in an
	 * array. Additionally the sum of the partial derivatives will be kept in an array as well
	 */
	public nodeLayer prevLayer;
	public nodeLayer nextLayer;
	public netNode[] nodes;
	public double[] wInput;
	public double[] error;

	public double[] derSumB;


	/**
	 * the constructor for the node layer class. Need to specify the number of nodes and the number
	 * of nodes in the prev layer so the number of weights is correct
	 *
	 * @param length number of nodes
	 * @param wLength number of nodes in prev layer to figure out the number of weights
     */
	public nodeLayer(int length, int wLength){
		this.prevLayer = null;
		this.nextLayer = null;
		this.nodes = new netNode[length];
		this.wInput = new double[length];
		this.error = new double[length];

		this.derSumB = new double[length];

		for(int i = 0; i < length; i++){
			this.nodes[i] = new netNode(wLength);
		}
		// reset the counter fields
		this.resetSums();
	}

	/**
	 * This is the hard reset method. Used between each batch
	 */
	public void resetSums(){
		for(int i = 0; i < this.derSumB.length; i++){
			// make every sum to zero
			this.derSumB[i] = 0.0; // the difference between this and the next method
			this.error[i] = 0.0;
		}
		for(int i = 0; i < this.nodes.length; i++){
			//go into each node and do the same
			this.nodes[i].resetErr();
		}
	}

	/**
	 * this is a soft reset method. Used between each piece of training data qfq
	 * q
	 */
	public void softReset(){
		for(int i = 0; i < this.error.length; i++){
			// make every sum to zero

			this.error[i] = 0.0;
		}

	}

	/**
	 * helper method that calculates the weighted input from one node to another
	 *
	 * @param curr the reciever node int location in the nodes array
	 * @param prev the input node int location in the prev array
     * @return
     */
	public double weightedInput(int curr, int prev){

		double actVal = this.prevLayer.nodes[prev].actValue;
		
		double wVal = this.nodes[curr].wArray[prev];
		//double bVal = this.nodes[curr].bias;

		//System.out.println(((actVal * wVal) + bVal));
		return ((actVal * wVal));

		
	}

	/**
	 * helper method that updates the layers wInput field with the weighted input
	 * to each node
	 */
	public void wResults(){
		// outer loop to go through each node
		for(int i = 0; i < this.nodes.length; i++){
			double results = 0.0;

			// inner loop to add up all the weighted input
			for(int j = 0; j < this.prevLayer.nodes.length; j++){
				if(this.nodes[i] != null){
					results += weightedInput(i, j);

				}
			}
			results += this.nodes[i].bias;
			this.wInput[i] = results;
		}
	}

	/**
	 * Static method to forward prop the layer
	 */
	public void forProp(){
		this.wResults();
		for(int i = 0 ; i < this.nodes.length; i++){
			 
			this.nodes[i].actValue = sigFun(this.wInput[i]);
		}

		
	}

	/**
	 * corner case of back propagation. This is for the last layer, which
	 * is to be compared to answers give
	 *
	 * @param answers an array of correct answers for the datum
     */
	public void bckProp1(double[] answers){
		for(int i = 0; i < this.nodes.length; i++){
			this.error[i] = this.error1(answers[i], i);

		}

	}

	/**
	 * the main version of back propagation, to move error from one layer to
	 * its prev layer
	 */
	public void bckProp2(){
		for(int i = 0; i < this.nodes.length; i++){
			this.error[i] = this.error2(i);


		}
	}

	/**
	 * The sigmoid function method
	 * @param input
	 * @return
     */
	public static double sigFun(double input){
		double result = (1 / ( 1 + Math.exp(-1 * input)) );
		return result;
	}

	/**
	 * the derivative of the sigmoid function, written in a clever way
	 * @param input
	 * @return
     */
	public static double sigPrime(double input){
		double result = sigFun(input)*(1.0-sigFun(input));
		return result;

	}
	/**
	 * error1 is a method to calculate the error of the outer node, which
	 * is compared to knownvalues
	 * @param answer the known answer
	 * @param nloc the int location in the array for the node to be compared
	 * @return the error associated with that node
	 */
	public double error1(double answer, int nloc){
		double result = 0.0;
		result += ((this.nodes[nloc].actValue - answer)) * sigPrime(this.wInput[nloc]);

		return result;
	}
	/**
	 * error2 is a method to calculate the error of the inner layers
	 * @param nloc location in Layer to find the error
	 * @return
	 */
	public double error2(int nloc){
		double result = 0.0;
		for(int i = 0; i < this.nextLayer.error.length; i++){
			result += (this.nextLayer.error[i] * this.nextLayer.nodes[i].wArray[nloc] * sigPrime(this.wInput[nloc]));

		}
		return result;
	}

	/**
	 * toString method for printing a layer in a readable way. The individual numbers represent different
	 * output forms look at the associated method in nodeNet.java for details
	 * @return
     */
	// Print method for actValue
	public String toString(){
		String results = "[";
		for(int i = 0; i < this.nodes.length; i++){
		/**	for(int j = 0; j < this.nodes[i].wArray.length; j++) {
				results += (this.nodes[i].wArray[j] + ", ");
			}*/
			results += (this.nodes[i].actValue + ", ");
		}
		results += "]";
		return results;
	}
	// Print method for weight arrays
	public String toString2(){
		String results = "[";
		for(int i = 0; i < this.nodes.length; i++){
			for(int j = 0; j < this.nodes[i].wArray.length; j++) {
				if(j == 0){
					results += "[";
				}

				results += (this.nodes[i].wArray[j] + ", ");
				if(j == this.nodes[i].wArray.length - 1){
					results += "]";
				}
			}

		}
		results += "]";
		return results;
	}
	// Print method for error array (error for weights)
	public String toString3(){
		String results = "[";
		for(int i = 0; i < this.nodes.length; i++){
			for(int j = 0; j < this.nodes[i].errArray.length; j++) {
				if(j == 0){
					results += "[";
				}
				results += (this.nodes[i].errArray[j] + ", ");
				if(j == this.nodes.length - 1){
					results += "]";
				}
			}
		}
		results += "]";
		return results;
	}
	// Print method for bias
	public String toString4(){
		String results = "[";
		for(int i = 0; i < this.nodes.length; i++){

			results += (this.nodes[i].bias  + ", ");
		}
		results += "]";
		return results;
	}
	// Print method for error (used in calculated error in weights and bias)
	public String toString5(){
		String results = "[";
		for(int i = 0; i < this.error.length; i++){

			results += (this.error[i] + ", ");
		}
		results += "]";
		return results;
	}
	// Print method to see weighted inputs
	public String toString6(){
		String results = "[";
		for(int i = 0; i < this.wInput.length; i++){

			results += (this.wInput[i] + ", ");
		}
		results += "]";
		return results;
	}

}
