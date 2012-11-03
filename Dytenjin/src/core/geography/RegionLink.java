package core.geography;

public class RegionLink {

	private GeographicalRegion loc1;
	private CardinalDirection dirFrom1;
	private GeographicalRegion loc2;
	private CardinalDirection dirFrom2;
	private int length;
	private int id;
	
	public RegionLink(int id,
			  GeographicalRegion loc1,
			  CardinalDirection dirFrom1,
			  GeographicalRegion loc2,
			  CardinalDirection dirFrom2) {
		this.id = id;
		this.loc1 = loc1;
		this.dirFrom1 = dirFrom1;
		this.loc2 = loc2;
		this.dirFrom2 = dirFrom2;
		this.length = 0;
	}
	
	public RegionLink(int id,
					  GeographicalRegion loc1,
					  CardinalDirection dirFrom1,
					  GeographicalRegion loc2,
					  CardinalDirection dirFrom2,
					  int length) {
		this.id = id;
		this.loc1 = loc1;
		this.dirFrom1 = dirFrom1;
		this.loc2 = loc2;
		this.dirFrom2 = dirFrom2;
		this.length = length;
	}
	
	public CardinalDirection getDirFromGeoReg(GeographicalRegion gL) {
		if (gL.equals(loc1)) {
			return dirFrom1;
		} else if (gL.equals(loc2)) {
			return dirFrom2;
		}
		return CardinalDirection.ERR;
	}
	
	public CardinalDirection getDirToGeoReg(GeographicalRegion gL) {
		if (gL.equals(loc1)) {
			return dirFrom1.opposite();
		} else if (gL.equals(loc2)) {
			return dirFrom2.opposite();
		}
		return CardinalDirection.ERR;
	}

	public CardinalDirection getDirFrom1() {
		return dirFrom1;
	}

	public void setDirFrom1(CardinalDirection dirFrom1) {
		this.dirFrom1 = dirFrom1;
	}

	public CardinalDirection getDirFrom2() {
		return dirFrom2;
	}

	public void setDirFrom2(CardinalDirection dirFrom2) {
		this.dirFrom2 = dirFrom2;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public GeographicalRegion getLoc1() {
		return loc1;
	}

	public GeographicalRegion getLoc2() {
		return loc2;
	}

	public int getId() {
		return id;
	}
}
