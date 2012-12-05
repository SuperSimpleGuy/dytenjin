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

	protected HashMap<Integer, StatDouble> stats;
	private UniqueId id;
	
	public StatManager(UniqueId id) {
		this.id = id;
		this.stats = new HashMap<Integer, StatDouble>();
	}
	
	public double getStatRaw(int id) {
		StatDouble temp = stats.get(id);
		return (temp != null ? temp.getValue() : -1);
	}
	
	public int getStatTrunc(int id) {
		StatDouble temp = stats.get(id);
		return (temp != null ? temp.getValueTrunc() : -1);
	}
	
	public int getStatRound(int id) {
		StatDouble temp = stats.get(id);
		return (temp != null ? temp.getValueRound() : -1);
	}
	
	public boolean changeStat(int id, double amount) {
		StatDouble temp = stats.get(id);
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
		this.stats.put(sD.getUniqueId().getId(), sD);
		return true;
	}
	
	public StatDouble removeStat(int id) {
		return stats.remove(id);
	}
	
	public String getDescription(int id) {
		StatDouble temp = stats.get(id);
		if (temp == null) {
			return null;
		} else {
			return temp.getDescription();
		}
	}
	
	public void setDescription(int id, String desc) {
		StatDouble temp = stats.get(id);
		if (temp != null) {
			stats.get(id).setDescription(desc);
		}
	}
	
	protected StatDouble findStat(int id) {
		return stats.get(id);
	}
	
	public boolean hasStat(int id) {
		return stats.get(id) != null;
	}

	@Override
	public UniqueId getUniqueId() {
		return id;
	}
	
}
