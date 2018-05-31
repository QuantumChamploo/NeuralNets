
public class netNode {
	// fields for each node. Act value and bias per node
	// the wArray contains weights to PREVIOUS arrays
	// the errArray is the error counter for back prop and gradient descent
	public double actValue;
	public double bias;
	public double[] wArray;
	public double[] errArray;
	
	
	public netNode(int wLength){ // general constructor with init weight and bias array size 30
		this.actValue = 0; //should be null?
		
		this.wArray = new double[wLength];
		this.errArray = new double[wLength];
		for(int i = 0; i < wLength; i++){
			this.wArray[i] = 1.0;
		}
		for(int i = 0; i < wLength; i++){
			this.errArray[i] = 0.0;
		}
		this.bias = 0.0;
		
	}
	
	public netNode(int avalue, int bvalue){// constructor with a starting activation value
		this.actValue = avalue;
		this.bias = bvalue;
		
		this.wArray = new double[30];
	}
	// resets the error for each data run
	public void resetErr(){
		for(int i = 0; i < this.errArray.length; i++){
			this.errArray[i] = 0.0;
		}
	}
	// I believe unnecessary
	public static double sigFun(double input){
		double result = (1 / ( 1 + Math.exp(-1 * input)) );
		return result;
	}
	
	
	
	
	
	
	public static void main(String[] args){
		System.out.println(sigFun(11.0));
		
		
	}
}
