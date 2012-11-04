package core.entities.living;

import core.geography.GeographicalLocation;
import core.management.individual.AspectManager;

public abstract class Player extends LivingEntity {
	
	public Player(String s, int id, AspectManager aMan, GeographicalLocation l) {
		super(s, id);
		super.setCurrentLoc(l);
		super.setAspectMan(aMan);
	}
	
}
