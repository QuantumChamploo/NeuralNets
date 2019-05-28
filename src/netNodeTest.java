import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class netNodeTest {
	
	netNode node;

	@Before
	public void setUp() throws Exception {
		node = new netNode(3);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor() {
		
		assertEquals(node.actValue, 0, 5);
		assertEquals(node.bias, 0, 5);
		for(int i = 0; i < node.errArray.length; i++) {
			assertEquals(node.errArray[i], 0, 0.0001);
		}
	}
	
	@Test
	public void testReset() {
		for(int i = 0; i < node.errArray.length; i++) {
			node.errArray[i] = 1.0;
		}
		
		node.resetErr();
		
		for(int i = 0; i < node.errArray.length; i++) {
			assertEquals(node.errArray[i], 0, 0.0001);
		}
	}
	
	@Test
	public void testSigFunc() {
		assertEquals(netNode.sigFun(0), 0.5, 0.0001);
		assertEquals(netNode.sigFun(10), 1, 0.0001);
		assertEquals(netNode.sigFun(-10), 0, 0.0001);
	}

}
