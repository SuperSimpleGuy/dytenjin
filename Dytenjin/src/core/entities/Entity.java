package core.entities;

import core.management.individual.AspectManager;

public abstract class Entity {
	
	protected String name;
	private int id;
	protected AspectManager aMan;
	
	public Entity(int id) {
		this.id = id;
		this.name = "";
		this.aMan = new AspectManager();
	}
	
	public Entity(String s, int identificationNumber) {
		name = s;
		id = identificationNumber;
		this.aMan = new AspectManager();
	}
	
	public int getId() {
		return id;
	}
	
	public abstract String getName();
	public abstract String setName(String name);
	public abstract AspectManager getAspectMan();
	public abstract AspectManager setAspectMan(AspectManager aMan);
}
