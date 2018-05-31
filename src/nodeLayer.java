
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

	public void resetSums(){
		for(int i = 0; i < this.derSumB.length; i++){
			// make every sum to zero
			this.derSumB[i] = 0.0;
		}
		for(int i = 0; i < this.nodes.length; i++){
			//go into each node and do the same
			this.nodes[i].resetErr();
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
		double bVal = this.nodes[curr].bias;
		//System.out.println((actVal * wVal) + bVal);
		return ((actVal * wVal) + bVal);
		
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
	 * the derivative of the sigmoid function
	 * @param input
	 * @return
     */
	public static double sigPrime(double input){
		double result = (Math.exp(-1 * input) / ( 1 + Math.exp(-1 * input)) );
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
		result += ((this.nodes[nloc].actValue - answer) * sigPrime(this.wInput[nloc]));
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
	 * toString method for printing a layer in a readable way
	 * @return
     */
	public String toString(){
		String results = "[";
		for(int i = 0; i < this.nodes.length; i++){
			results += (this.nodes[i].actValue + ", ");
		}
		results += "]";
		return results;
	}
	
	public static void main(String[] args){
		nodeLayer layer1 = new nodeLayer(3, 0);
		layer1.nodes[0].actValue = 1;
		layer1.nodes[1].actValue = 0;
		layer1.nodes[2].actValue = 0;
		nodeLayer layer2 = new nodeLayer(3,3);
		layer2.nodes[0].wArray[0] = 2.0;
		layer2.nodes[0].wArray[1] = 1.0;
		layer2.nodes[0].wArray[2] = 0.5;
		layer2.nodes[1].wArray[0] = .6;
		layer2.nodes[1].wArray[1] = .4;
		layer2.nodes[1].wArray[2] = .3;
		layer2.nodes[2].wArray[0] = 2.6;
		layer2.nodes[2].wArray[1] = 1.3;
		layer2.nodes[2].wArray[2] = 5.5;
		nodeLayer layer3 = new nodeLayer(2, 3);
		layer3.nodes[0].wArray[0] = .9;
		layer3.nodes[0].wArray[1] = 4.9;
		layer3.nodes[0].wArray[2] = 8.9;
		layer3.nodes[1].wArray[0] = 9.9;
		layer3.nodes[1].wArray[1] = 2.9;
		layer3.nodes[1].wArray[2] = 1.9;
		layer1.nextLayer = layer2;
		layer2.nextLayer = layer3;
		layer2.prevLayer = layer1;
		layer3.prevLayer = layer2;
		System.out.println(layer1.toString());
		System.out.println(layer2.toString());
		layer2.forProp();
		System.out.println(layer2.toString());
		layer2.forProp();
		System.out.println(layer2.toString());
		layer3.forProp();
		System.out.println(layer3.toString());
		for(int i = 0; i < layer3.wInput.length; i++){
			System.out.println(layer3.wInput[i]);
		}
		layer3.error[0] = layer3.error1(1, 0);
		layer3.error[1] = layer3.error1(0, 1);
		System.out.println(layer3.error[0]);
		System.out.println(layer3.error[1]);
		layer2.error[0] = layer2.error2(0);
		layer2.error[1] = layer2.error2(1);
		layer2.error[2] = layer2.error2(2);
		System.out.println(layer2.error[0]);
		System.out.println(layer2.error[1]);
		System.out.println(layer2.error[2]);
	}
}
