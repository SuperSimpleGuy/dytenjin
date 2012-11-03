package core.entities.living;

import core.HistoryManager;
import core.geography.GeographicalLocation;
import core.possessions.PossessionsManager;
import core.stats.StatsManager;

public abstract class Player extends LivingEntity {
	
	public Player(String s, int id, StatsManager st, HistoryManager h, PossessionsManager e, GeographicalLocation l) {
		super(s, id);
		super.setCurrentLoc(l);
		super.setEquip(e);
		super.setHist(h);
		super.setStats(st);
	}
	
}
