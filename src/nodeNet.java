
public class nodeNet {
	public nodeLayer first;
	public nodeLayer last;
	public nodeLayer[] layers;
	
	
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
		
		
		int[] hldr = new int[3];
		hldr[0] = 2;
		hldr[1] = 2;
		hldr[2] = 3;
		
		nodeNet ndNet2 = new nodeNet(1, hldr);
		System.out.println(ndNet2.toString());
		ndNet2.layers[0].nodes[0].actValue = 1;
		ndNet2.frwdProp();
		System.out.println(ndNet2.toString());
		
		// asdfasdf
	}

	

}
