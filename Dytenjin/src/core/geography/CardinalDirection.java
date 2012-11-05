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

package core.geography;

/**
 * Represents one of the sixteen cardinal directions, in combinations
 * of north, south, east, and west.
 * @author SuperSimpleGuy
 */
public enum CardinalDirection {
	N(0), NNE(1), NE(2), NEE(3), E(4), SEE(5), SE(6), SSE(7), S(8), 
	SSW(9), SW(10), SWW(11), W(12), NWW(13), NW(14), NNW(15), ERR(-1), SAME(16);
	
	private int dir;
	
	/**
	 * Creates a Cardinal Direction as defined in this enum
	 * @param dir the directional value of this Cardinal Direction
	 */
	private CardinalDirection(int dir) {
		this.dir = dir;
	}
	
	/**
	 * Gets the direction of the Cardinal Direction
	 * @return the direction of this cardinal direction
	 */
	public int getDir() {
		return dir;
	}
	
	/**
	 * Returns the cardinal direction when turning left
	 * a sixteenth of a circle from this direction
	 * @return the new direction
	 */
	public CardinalDirection leftSixteenth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 15) % 16];
	}
	
	/**
	 * Returns the cardinal direction when turning right
	 * a sixteenth of a circle from this direction
	 * @return the new direction
	 */
	public CardinalDirection rightSixteenth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 1) % 16];
	}
	
	/**
	 * Returns the cardinal direction when turning left
	 * an eighth of a circle from this direction
	 * @return the new direction
	 */
	public CardinalDirection leftEighth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 14) % 16];
	}
	
	/**
	 * Returns the cardinal direction when turning right
	 * an eighth of a circle from this direction
	 * @return the new direction
	 */
	public CardinalDirection rightEighth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 2) % 16];
	}
	
	/**
	 * Returns the cardinal direction when turning left
	 * a fourth of a circle from this direction
	 * @return the new direction
	 */
	public CardinalDirection leftFourth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 12) % 16];
	}
	
	/**
	 * Returns the cardinal direction when turning right
	 * a fourth of a circle from this direction
	 * @return the new direction
	 */
	public CardinalDirection rightFourth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 4) % 16];
	}
	
	/**
	 * Returns the cardinal direction when turning around
	 * from this direction
	 * @return the new direction
	 */
	public CardinalDirection opposite() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 8) % 16];
	}
	
	/**
	 * Returns the cardinal direction from a set of x and y coordinates
	 * representing two points
	 * @param xFrom the x position of the point from where the new
	 * 				direction is determined
	 * @param yFrom the y position of the point from where the new 
	 * 				direction is determined
	 * @param xTo the x position of the point towards where the new 
	 * 				direction is determined
	 * @param yTo the y position of the point towards where the new 
	 * 				direction is determined
	 * @return the new cardinal direction
	 */
	public static CardinalDirection getDirFromCoords(int xFrom, int yFrom, int xTo, int yTo) {
		int deltaX = xTo - xFrom;
		int deltaY = yTo - yFrom;
		if (deltaX == 0 && deltaY ==0) {
			return SAME;
		}
		//Math.atan returns -pi/2 to pi/2
		double angle = Math.atan(deltaY/(double)deltaX);
		int dir = (int)Math.round(Math.abs(angle) / ((Math.PI/2)*(1/4.0)));
		if (angle <= 0) {
			dir += 4;
		} else {
			dir = Math.abs(dir - 4);
		}
		if (deltaX < 0) {
			dir += 8;
		}
		return CardinalDirection.values()[dir % 16];
	}
}
