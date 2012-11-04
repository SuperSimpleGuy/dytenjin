package core.geography;

import java.util.HashMap;

import core.management.individual.AspectManager;

public abstract class LocationLink extends GeographicalLocation {

	private GeographicalLocation loc1;
	private CardinalDirection dirFrom1;
	private GeographicalLocation loc2;
	private CardinalDirection dirFrom2;
	private int length;
	
	private HashMap<Integer, CardinalDirection> dirFromPath;
	
	public LocationLink(String name,
				int id,
				int xCoord,
				int yCoord,
				AspectManager asp,
				GeographicalRegion parent,
				GeographicalLocation loc1,
				GeographicalLocation loc2,
				CardinalDirection dirFrom1,
				CardinalDirection dirFrom2) {
		super(name, id, xCoord, yCoord, asp, parent);
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.dirFrom1 = dirFrom1;
		this.dirFrom2 = dirFrom2;
		this.dirFromPath = new HashMap<Integer, CardinalDirection>();
		this.length = 0;
	}
	
	public LocationLink(String name,
			int id,
			int xCoord,
			int yCoord,
			AspectManager asp,
			GeographicalRegion parent,
			GeographicalLocation loc1,
			GeographicalLocation loc2,
			CardinalDirection dirFrom1,
			CardinalDirection dirFrom2,
			int length) {
		super(name, id, xCoord, yCoord, asp, parent);
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.dirFrom1 = dirFrom1;
		this.dirFrom2 = dirFrom2;
		this.dirFromPath = new HashMap<Integer, CardinalDirection>();
		this.length = length;
	}
	
	public CardinalDirection getDirFromPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null && dirFromPath.containsKey(temp.getId())) {
			return dirFromPath.get(temp.getId());
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	public CardinalDirection getDirToPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null && dirFromPath.containsKey(temp.getId())) {
			return dirFromPath.get(temp.getId()).opposite();
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	public CardinalDirection getDirFromGeoLoc(GeographicalLocation gL) {
		if (gL.equals(loc1)) {
			return dirFrom1;
		} else if (gL.equals(loc2)) {
			return dirFrom2;
		}
		return CardinalDirection.ERR;
	}
	
	public CardinalDirection getDirToGeoLoc(GeographicalLocation gL) {
		if (gL.equals(loc1)) {
			return dirFrom1.opposite();
		} else if (gL.equals(loc2)) {
			return dirFrom2.opposite();
		}
		return CardinalDirection.ERR;
	}
	
	@Override
	public LocationLink removeLocationLink(int id) {
		if (!paths.containsKey(id)) {
			dirFromPath.remove(id);
			return paths.remove(id);
		}
		return null;
	}
	
	public boolean addLocationLink(LocationLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			addDirFromPath(l.getId(), CardinalDirection.ERR);
			return true;
		}
		return false;
	}
	
	public boolean addLocationLink(LocationLink l, CardinalDirection dir) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			addDirFromPath(l.getId(), dir);
			return true;
		}
		return false;
	}
	
	public boolean changeLocLinkDir(LocationLink l, CardinalDirection dirNew) {
		if (dirFromPath.containsKey(l.getId())) {
			dirFromPath.remove(l.getId());
			addDirFromPath(l.getId(), dirNew);
			return true;
		}
		return false;
	}
	
	private boolean addDirFromPath(int id, CardinalDirection fromId) {
		if (!paths.containsKey(id)) {
			dirFromPath.put(id, fromId);
			return true;
		}
		return false;
	}
	
	public int getLength() {
		return length;
	}
	
	public int setLength(int length) {
		int temp = this.length;
		this.length = length;
		return temp;
	}
}
