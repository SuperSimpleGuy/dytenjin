/*
 *  Dytenjin is an engine for making dynamic text-based java games.
 *  Copyright (C) 2012 SuperSimpleGuy
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package core.entities.living;

import java.util.HashMap;

import core.entities.Entity;

/**
 * 
 * @author SuperSimpleGuy
 */
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
