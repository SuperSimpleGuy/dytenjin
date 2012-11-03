package core.entities.living;

import java.util.HashMap;

import core.entities.Entity;

public abstract class LivingEntityGroup extends Entity {

	protected HashMap<Integer, LivingEntity> entities;
	
	public LivingEntityGroup(String s, int id) {
		super(s, id);
		this.entities = new HashMap<Integer, LivingEntity>();
	}
	
	public LivingEntityGroup(LivingEntity e, String s, int id) {
		this(s, id);
		this.entities.put(e.getId(), e);
	}
	
	public LivingEntityGroup(LivingEntity[] e, String s, int id) {
		this(s, id);
		for (LivingEntity ent : e) {
			this.entities.put(ent.getId(), ent);
		}
	}
	
	public String getName() {
		return name;
	}

	public String setName(String name) {
		String temp = this.name;
		this.name = name;
		return temp;
	}

	public HashMap<Integer, LivingEntity> getEntities() {
		return entities;
	}
	
	public boolean addEntity(LivingEntity e) {
		if (entities.containsKey(e.getId())) {
			return false;
		}
		entities.put(e.getId(), e);
		return true;
	}
	
	public LivingEntity removeEntity(int id) {
		if (!entities.containsKey(id)) {
			return null;
		}
		return entities.remove(id);
	}
	
}
