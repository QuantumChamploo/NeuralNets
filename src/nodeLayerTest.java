import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class nodeLayerTest {
	nodeLayer nl;
	nodeLayer nl2;

	@Before
	public void setUp() throws Exception {
		nl = new nodeLayer(3, 3);
		nl2 = new nodeLayer(3, 3);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor() {
		for(int i = 0; i < nl.error.length; i++) {
			assertEquals(nl.error[i], 0, 0.001);
			assertEquals(nl.derSumB[i], 0, 0.001);
		}
	}
	
	@Test
	public void testSigFunc() {
		assertEquals(nodeLayer.sigFun(0), 0.5, 0.0001);
		assertEquals(nodeLayer.sigFun(10), 1, 0.0001);
		assertEquals(nodeLayer.sigFun(-10), 0, 0.0001);
	}
	
	@Test
	public void testSigPrime() {
		assertEquals(nodeLayer.sigPrime(0), 0.25, 0.0001);
		assertEquals(nodeLayer.sigPrime(10), 0, 0.0001);
		assertEquals(nodeLayer.sigPrime(-10), 0, 0.0001);
	}

}
