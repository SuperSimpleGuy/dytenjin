package core.entities.living;

import core.HistoryManager;
import core.entities.Entity;
import core.geography.GeographicalLocation;
import core.possessions.PossessionsManager;
import core.stats.StatsManager;

public class LivingEntity extends Entity {
	
	protected StatsManager stats;
	protected PossessionsManager equip;
	protected GeographicalLocation currentLoc;
	
	public LivingEntity(String s, int id) {
		super(s, id);
		stats = new StatsManager();
		equip = new PossessionsManager();
		currentLoc = null;
	}

	public StatsManager getStats() {
		return stats;
	}

	public StatsManager setStats(StatsManager stats) {
		StatsManager s = this.stats;
		this.stats = stats;
		return s;
	}

	public HistoryManager getHist() {
		return hist;
	}

	public HistoryManager setHist(HistoryManager hist) {
		HistoryManager h = this.hist;
		this.hist = hist;
		return h;
	}

	public PossessionsManager getEquip() {
		return equip;
	}

	public PossessionsManager setEquip(PossessionsManager equip) {
		PossessionsManager p = this.equip;
		this.equip = equip;
		return p;
	}

	public GeographicalLocation getCurrentLoc() {
		return currentLoc;
	}

	public GeographicalLocation setCurrentLoc(GeographicalLocation currentLoc) {
		GeographicalLocation l = this.currentLoc;
		this.currentLoc = currentLoc;
		return l;
	}
	
	public String getName() {
		return name;
	}

	public String setName(String name) {
		String s = this.name;
		this.name = name;
		return s;
	}
	
}
