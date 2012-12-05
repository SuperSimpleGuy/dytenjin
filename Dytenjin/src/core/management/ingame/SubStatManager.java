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

import core.management.game.UniqueId;
import core.parsing.IIsParsable;
import core.parsing.ParserManager;

/**
 * @author SuperSimpleGuy
 */
public class SubStatManager extends StatManager implements IIsParsable {

	private HashMap<Integer, StatManager> subStats;
	private ParserManager<?> pM;
	
	public SubStatManager(UniqueId id) {
		super(id);
		this.subStats = new HashMap<Integer, StatManager>();
	}
	
	public SubStatManager(UniqueId id, String[] stat, double baseNumber) {
		super(id, stat, baseNumber);
		this.subStats = new HashMap<Integer, StatManager>();
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
	
	protected StatDouble findStat(String s) {
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
	
	public boolean addStat(StatDouble sD, UniqueId id) {
		StatManager temp = findSubStat(id);
		if (temp == null || findStat(sD.getName()) != null) {
			return false;
		}
		return temp.addStat(sD);
	}
	
	public StatDouble removeStat(String s, UniqueId id) {
		StatManager temp = findSubStat(id);
		if (temp == null || findStat(s) == null) {
			return null;
		}
		return temp.removeStat(s);
	}
	
	private StatManager findSubStat(StatManager s) {
		return subStats.get(s.getUniqueId().getId());
	}
	
	private StatManager findSubStat(UniqueId id) {
		return subStats.get(id.getId());
	}
	
	public boolean addSubStat(StatManager sM) {
		if (!sM.getUniqueId().hasSameType(super.getUniqueId()) || findSubStat(sM) != null) {
			return false;
		}
		subStats.put(sM.getUniqueId().getId(), sM);
		return true;
	}
	
	public StatManager removeSubStat(UniqueId id) {
		return subStats.remove(id.getId());
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
