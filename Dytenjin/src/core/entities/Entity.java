package core.entities;

import core.HistoryManager;

public abstract class Entity {
	
	protected String name;
	private int id;
	protected HistoryManager hist;
	
	public Entity(int id) {
		this.id = id;
		this.name = "";
		this.hist = new HistoryManager();
	}
	
	public Entity(String s, int identificationNumber) {
		name = s;
		id = identificationNumber;
		this.hist = new HistoryManager();
	}
	
	public int getId() {
		return id;
	}
	
	public abstract String getName();
	public abstract String setName(String name);
	public abstract HistoryManager getHist();
	public abstract HistoryManager setHist(HistoryManager hist);
}
