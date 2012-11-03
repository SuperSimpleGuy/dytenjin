package core.geography;

import java.util.HashMap;

import core.HistoryManager;
import core.entities.nonliving.NonLivingEntityGroup;
import core.geography.environment.EnvironmentManager;
import core.geography.environment.Terrain;
import core.temporal.TimeChanging;

public abstract class GeographicalLocation implements TimeChanging {

	private GeographicalRegion parent;
	private int id;
	private String name;
	private HistoryManager history;
	private Terrain terrain;
	private EnvironmentManager environment;
	
	protected HashMap<Integer, LocationLink> paths;
	protected NonLivingEntityGroup owner;
	
	public GeographicalLocation(String name,
								int id,
								HistoryManager history,
								Terrain terrain,
								EnvironmentManager env,
								GeographicalRegion parent) {
		this.id = id;
		this.setName(name);
		this.setHistory(history);
		this.setTerrain(terrain);
		this.setEnvironment(env);
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

	public HistoryManager getHistory() {
		return history;
	}

	public void setHistory(HistoryManager history) {
		this.history = history;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	public EnvironmentManager getEnvironment() {
		return environment;
	}

	public void setEnvironment(EnvironmentManager environment) {
		this.environment = environment;
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
	
	public boolean equals(Object other) {
		if (!(other instanceof GeographicalLocation)) {
			return false;
		}
		GeographicalLocation gL = (GeographicalLocation)other;
		return this.id == gL.getId();
	}
}
