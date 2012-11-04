package core.entities.living;

import core.entities.Entity;
import core.geography.GeographicalLocation;
import core.management.individual.AspectManager;

public class LivingEntity extends Entity {
	
	protected GeographicalLocation currentLoc;
	
	public LivingEntity(String s, int id) {
		super(s, id);
		currentLoc = null;
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

	@Override
	public AspectManager getAspectMan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AspectManager setAspectMan(AspectManager aMan) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
