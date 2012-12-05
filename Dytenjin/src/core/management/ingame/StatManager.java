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

package core.management.ingame;

import java.util.HashMap;

import core.management.game.IHasUniqueId;
import core.management.game.UniqueId;

/**
 * 
 * @author SuperSimpleGuy
 */
public class StatManager implements IHasUniqueId {

	protected HashMap<String, StatDouble> stats;
	private UniqueId id;
	
	public StatManager(UniqueId id) {
		this.id = id;
		this.stats = new HashMap<String, StatDouble>();
	}
	
	public StatManager(UniqueId id, String[] stat, double baseNumber) {
		this(id);
		for (String s : stat) {
			stats.put(s, new StatDouble(s, 0));
		}
	}
	
	public double getStatRaw(String s) {
		StatDouble temp = stats.get(s);
		return (temp != null ? temp.getValue() : -1);
	}
	
	public int getStatTrunc(String s) {
		StatDouble temp = stats.get(s);
		return (temp != null ? temp.getValueTrunc() : -1);
	}
	
	public int getStatRound(String s) {
		StatDouble temp = stats.get(s);
		return (temp != null ? temp.getValueRound() : -1);
	}
	
	public boolean hasStat(String s) {
		return (stats.get(s) != null);
	}
	
	public boolean changeStat(String s, double amount) {
		StatDouble temp = stats.get(s);
		if (temp == null) {
			return false;
		}
		temp.changeByValue(amount);
		return true;
	}
	
	public boolean addStat(StatDouble sD) {
		if (stats.containsKey(sD.getName())) {
			return false;
		}
		this.stats.put(sD.getName(), sD);
		return true;
	}
	
	public StatDouble removeStat(String s) {
		return stats.remove(s);
	}
	
	public String getDescription(String s) {
		StatDouble temp = stats.get(s);
		if (temp == null) {
			return null;
		} else {
			return temp.getDescription();
		}
	}
	
	public void setDescription(String s, String desc) {
		StatDouble temp = stats.get(s);
		if (temp != null) {
			stats.get(s).setDescription(desc);
		}
	}
	
	protected StatDouble findStat(String s) {
		return stats.get(s);
	}

	@Override
	public UniqueId getUniqueId() {
		return id;
	}
	
}
