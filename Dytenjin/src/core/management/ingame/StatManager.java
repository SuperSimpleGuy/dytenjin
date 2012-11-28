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

import java.util.Collection;
import java.util.HashMap;

import core.management.game.IHasUniqueId;
import core.management.game.UniqueId;
import core.parsing.IIsParsable;
import core.parsing.ParserManager;

/**
 * 
 * @author SuperSimpleGuy
 */
public class StatManager implements IHasUniqueId, IIsParsable {

	private HashMap<String, StatDouble> stats;
	private HashMap<Integer, StatManager> subStats;
	private UniqueId id;
	private ParserManager<?> pM;
	
	public StatManager(UniqueId id) {
		this.id = id;
		this.subStats = new HashMap<Integer, StatManager>();
		this.stats = new HashMap<String, StatDouble>();
	}
	
	public StatManager(UniqueId id, String[] stat, double baseNumber) {
		this(id);
		for (String s : stat) {
			stats.put(s, new StatDouble(s, 0));
		}
	}
	
	public double getStatRaw(String s) {
		StatDouble temp = findStat(s);
		return (temp != null ? temp.getValue() : -1);
	}
	
	public int getStatTrunc(String s) {
		StatDouble temp = findStat(s);
		return (temp != null ? temp.getValueTrunc() : -1);
	}
	
	public int getStatRound(String s) {
		StatDouble temp = findStat(s);
		return (temp != null ? temp.getValueRound() : -1);
	}
	
	private StatDouble findStat(String s) {
		if (stats.containsKey(s)) {
			return stats.get(s);
		}
		Collection<StatManager> col = subStats.values();
		for (StatManager sT: col) {
			StatDouble temp = sT.findStat(s);
			if (temp != null) {
				return temp;
			}
		}
		return null;
	}
	
	private StatManager findSubStat(StatManager s) {
		if (subStats.containsKey(s.getUniqueId().getId())) {
			return subStats.get(s.getUniqueId().getId());
		}
		Collection<StatManager> col = subStats.values();
		for (StatManager sT: col) {
			StatManager temp = sT.findSubStat(s);
			if (temp != null) {
				return temp;
			}
		}
		return null;
	}
	
	private StatManager findSubStat(UniqueId id) {
		if (subStats.containsKey(id.getId())) {
			return subStats.get(id.getId());
		}
		Collection<StatManager> col = subStats.values();
		for (StatManager sT: col) {
			StatManager temp = sT.findSubStat(id);
			if (temp != null) {
				return temp;
			}
		}
		return null;
	}
	
	public boolean hasStat(String s) {
		return (findStat(s) != null);
	}
	
	public boolean changeStat(String s, double amount) {
		StatDouble temp = findStat(s);
		if (temp == null) {
			return false;
		}
		temp.changeByValue(amount);
		return true;
	}
	
	public boolean addSubStat(StatManager sM) {
		if (!sM.getUniqueId().hasSameType(id) || findSubStat(sM) != null) {
			return false;
		}
		subStats.put(sM.getUniqueId().getId(), sM);
		return true;
	}
	
	public StatManager removeSubStat(UniqueId id) {
		if (subStats.containsKey(id.getId())) {
			return subStats.remove(id.getId());
		}
		Collection<StatManager> col = subStats.values();
		for (StatManager sT: col) {
			StatManager temp = sT.removeSubStat(id);
			if (temp != null) {
				return temp;
			}
		}
		return null;
	}
	
	public boolean addStat(StatDouble sD, UniqueId id) {
		StatManager temp = findSubStat(id);
		if (temp == null || findStat(sD.getName()) != null) {
			return false;
		}
		return temp.addStat(sD);
	}
	
	private boolean addStat(StatDouble sD) {
		this.stats.put(sD.getName(), sD);
		return true;
	}
	
	private StatDouble removeStat(String s) {
		return stats.remove(s);
	}
	
	public StatDouble removeStat(String s, UniqueId id) {
		StatManager temp = findSubStat(id);
		if (temp == null || findStat(s) == null) {
			return null;
		}
		return temp.removeStat(s);
	}
	
	public String getDescription(String s) {
		return findStat(s).getDescription();
	}
	
	public void setDescription(String s, String desc) {
		findStat(s).setDescription(desc);
	}

	@Override
	public UniqueId getUniqueId() {
		return id;
	}

	@Override
	public void setParserManager(ParserManager<?> pM) {
		this.pM = pM;
	}

	@Override
	public ParserManager<?> getParserManager() {
		return pM;
	}
	
}
