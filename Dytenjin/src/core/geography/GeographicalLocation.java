package core.geography;

import java.util.HashMap;

import core.entities.nonliving.NonLivingEntityGroup;
import core.management.individual.AspectManager;
import core.temporal.TimeChanging;

public abstract class GeographicalLocation implements TimeChanging {

	private GeographicalRegion parent;
	private int id;
	private String name;
	private AspectManager aspMan;
	private int xCoord;
	private int yCoord;
	
	protected HashMap<Integer, LocationLink> paths;
	protected NonLivingEntityGroup owner;
	
	public GeographicalLocation(String name,
								int id,
								int xCoord,
								int yCoord,
								AspectManager aspMan,
								GeographicalRegion parent) {
		this.id = id;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.setName(name);
		this.aspMan = aspMan;
		this.setParent(parent);
		paths = new HashMap<Integer, LocationLink>();
		owner = null;
	}
	
	public CardinalDirection getDirToPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null) {
			return temp.getDirFromGeoLoc(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	public CardinalDirection getDirFromPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null) {
			return temp.getDirToGeoLoc(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	public LocationLink removeLocationLink(int id) {
		if (!paths.containsKey(id)) {
			return paths.remove(id);
		}
		return null;
	}
	
	public boolean addLocationLink(LocationLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			return true;
		}
		return false;
	}

	public GeographicalRegion getParent() {
		return parent;
	}

	public void setParent(GeographicalRegion parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AspectManager getAspect() {
		return aspMan;
	}

	public HashMap<Integer, LocationLink> getPaths() {
		return paths;
	}

	public NonLivingEntityGroup getOwner() {
		return owner;
	}

	public void setOwner(NonLivingEntityGroup owner) {
		this.owner = owner;
	}
	
	public int getId() {
		return id;
	}
	
	public int getxCoord() {
		return xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public boolean equals(Object other) {
		if (!(other instanceof GeographicalLocation)) {
			return false;
		}
		GeographicalLocation gL = (GeographicalLocation)other;
		return this.id == gL.getId();
	}
}
