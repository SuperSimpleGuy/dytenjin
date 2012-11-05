/*
 *  Dytenjin is an engine for making dynamic text-based java games.
 *  Copyright (C) 2012 SuperSimpleGuy
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package core.geography.test;

import static org.junit.Assert.*;

import org.junit.Test;

import core.geography.CardinalDirection;

/**
 * Tests the CardinalDirection enums to ensure geography is
 * going to be handled correctly
 * @author SuperSimpleGuy
 */
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
		assertTrue(e.leftEighth() == CardinalDirection.NW);
	}
	
	@Test
	public void testRightEigthNNW() {
		CardinalDirection e = CardinalDirection.NNW;
		assertTrue(e.rightEighth() == CardinalDirection.NNE);
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
		assertTrue(e.leftEighth() == CardinalDirection.ERR);
	}
	
	@Test
	public void testRightEigthERR() {
		CardinalDirection e = CardinalDirection.ERR;
		assertTrue(e.rightEighth() == CardinalDirection.ERR);
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
	
	@Test
	public void testGetDirFromCoordsSAME() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, 0, 0);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.SAME);
	}
	
	@Test
	public void testGetDirFromCoordsN() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, 0, 1);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.N);
	}
	
	@Test
	public void testGetDirFromCoordsNNE() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, 1, 4);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.NNE);
	}
	
	@Test
	public void testGetDirFromCoordsNE() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, 1, 1);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.NE);
	}
	
	@Test
	public void testGetDirFromCoordsNEE() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, 4, 1);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.NEE);
	}
	
	@Test
	public void testGetDirFromCoordsS() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, 0, -1);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.S);
	}
	
	@Test
	public void testGetDirFromCoordsSSW() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, -1, -4);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.SSW);
	}
	
	@Test
	public void testGetDirFromCoordsSW() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, -1, -1);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.SW);
	}
	
	@Test
	public void testGetDirFromCoordsSWW() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, -4, -1);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.SWW);
	}
	
	@Test
	public void testGetDirFromCoordsW() {
		CardinalDirection e = CardinalDirection.getDirFromCoords(0, 0, -1, 0);
		assertTrue("e.getDir = "+e.getDir(), e == CardinalDirection.W);
	}

}
