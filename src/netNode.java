
public class netNode {
	public double actValue;
	public double bias;
	public double[] wArray;
	
	
	public netNode(int wLength){ // general constructor with init weight and bias array size 30
		this.actValue = 0; //should be null?
		
		this.wArray = new double[wLength];
		for(int i = 0; i < wLength; i++){
			this.wArray[i] = 0.0;
		}
		this.bias = 0.0;
		
	}
	
	public netNode(int avalue, int bvalue){// constructor with a starting activation value
		this.actValue = avalue;
		this.bias = bvalue;
		
		this.wArray = new double[30];
	}
	
	public static double sigFun(double input){
		double result = (1 / ( 1 + Math.exp(-1 * input)) );
		return result;
	}
	
	
	
	
	
	
	public static void main(String[] args){
		System.out.println(sigFun(11.0));
		
		
	}
}
