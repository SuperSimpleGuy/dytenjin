package core.geography;

public enum CardinalDirection {
	N(0), NNE(1), NE(2), NEE(3), E(4), SEE(5), SE(6), SSE(7), S(8), 
	SSW(9), SW(10), SWW(11), W(12), NWW(13), NW(14), NNW(15), ERR(-1);
	
	private int dir;
	
	private CardinalDirection(int dir) {
		this.dir = dir;
	}
	
	public int getDir() {
		return dir;
	}
	
	public CardinalDirection leftSixteenth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 15) % 16];
	}
	
	public CardinalDirection rightSixteenth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 1) % 16];
	}
	
	public CardinalDirection leftEigth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 14) % 16];
	}
	
	public CardinalDirection rightEigth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 2) % 16];
	}
	
	public CardinalDirection leftFourth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 12) % 16];
	}
	
	public CardinalDirection rightFourth() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 4) % 16];
	}
	
	public CardinalDirection opposite() {
		if (dir < 0 || dir >= 16) {
			return ERR;
		}
		return CardinalDirection.values()[(dir + 8) % 16];
	}
}
