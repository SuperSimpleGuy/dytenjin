package core.geography.test;

import static org.junit.Assert.*;

import org.junit.Test;

import core.geography.CardinalDirection;

public class CardinalDirectionTest {
	
	@Test
	public void testOpposite() {
		CardinalDirection e = CardinalDirection.E;
		assertTrue(e.opposite() == CardinalDirection.W);
	}
	
	@Test
	public void testGetDir() {
		CardinalDirection e = CardinalDirection.E;
		assertTrue(e.getDir() == 4);
	}
	
	@Test
	public void testGetDirError() {
		CardinalDirection e = CardinalDirection.ERR;
		assertTrue(e.getDir() == -1);
	}
	
	@Test
	public void testLeftSixteenthN() {
		CardinalDirection e = CardinalDirection.N;
		assertTrue(e.leftSixteenth() == CardinalDirection.NNW);
	}
	
	@Test
	public void testRightSixteenthNNW() {
		CardinalDirection e = CardinalDirection.NNW;
		assertTrue(e.rightSixteenth() == CardinalDirection.N);
	}
	
	@Test
	public void testLeftEigthN() {
		CardinalDirection e = CardinalDirection.N;
		assertTrue(e.leftEigth() == CardinalDirection.NW);
	}
	
	@Test
	public void testRightEigthNNW() {
		CardinalDirection e = CardinalDirection.NNW;
		assertTrue(e.rightEigth() == CardinalDirection.NNE);
	}
	
	@Test
	public void testLeftFourthN() {
		CardinalDirection e = CardinalDirection.N;
		assertTrue(e.leftFourth() == CardinalDirection.W);
	}
	
	@Test
	public void testRightFourthNNW() {
		CardinalDirection e = CardinalDirection.NNW;
		assertTrue(e.rightFourth() == CardinalDirection.NEE);
	}
	
	@Test
	public void testLeftSixteenthERR() {
		CardinalDirection e = CardinalDirection.ERR;
		assertTrue(e.leftSixteenth() == CardinalDirection.ERR);
	}
	
	@Test
	public void testRightSixteenthERR() {
		CardinalDirection e = CardinalDirection.ERR;
		assertTrue(e.rightSixteenth() == CardinalDirection.ERR);
	}
	
	@Test
	public void testLeftEigthERR() {
		CardinalDirection e = CardinalDirection.ERR;
		assertTrue(e.leftEigth() == CardinalDirection.ERR);
	}
	
	@Test
	public void testRightEigthERR() {
		CardinalDirection e = CardinalDirection.ERR;
		assertTrue(e.rightEigth() == CardinalDirection.ERR);
	}
	
	@Test
	public void testLeftFourthERR() {
		CardinalDirection e = CardinalDirection.ERR;
		assertTrue(e.leftFourth() == CardinalDirection.ERR);
	}
	
	@Test
	public void testRightFourthERR() {
		CardinalDirection e = CardinalDirection.ERR;
		assertTrue(e.rightFourth() == CardinalDirection.ERR);
	}

}
