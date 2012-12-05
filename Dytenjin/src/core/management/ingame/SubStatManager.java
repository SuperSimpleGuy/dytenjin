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
	protected HashMap<String, Double> masterList;
	private ParserManager<?> pM;
	
	public SubStatManager(UniqueId id) {
		super(id);
		this.subStats = new HashMap<Integer, StatManager>();
	}
	
	public double getMasterStatRaw(String s) {
		double temp = masterList.get(s);
		return temp;
	}
	
	public int getMasterStatTrunc(String s) {
		double temp = masterList.get(s);
		return (int)Math.floor(temp);
	}
	
	public int getMasterStatRound(String s) {
		double temp = masterList.get(s);
		return (int)Math.round(temp);
	}
	
	@Override
	public double getStatRaw(int id) {
		StatDouble temp = findStat(id);
		return (temp != null ? temp.getValue() : -1);
	}
	
	@Override
	public int getStatTrunc(int id) {
		StatDouble temp = findStat(id);
		return (temp != null ? temp.getValueTrunc() : -1);
	}
	
	@Override
	public int getStatRound(int id) {
		StatDouble temp = findStat(id);
		return (temp != null ? temp.getValueRound() : -1);
	}
	
	@Override
	protected StatDouble findStat(int id) {
		if (stats.containsKey(id)) {
			return stats.get(id);
		}
		Collection<StatManager> col = subStats.values();
		for (StatManager sT: col) {
			StatDouble temp = sT.findStat(id);
			if (temp != null) {
				return temp;
			}
		}
		return null;
	}
	
	public boolean hasStat(int id) {
		return (findStat(id) != null);
	}
	
	@Override
	public boolean changeStat(int id, double amount) {
		StatDouble temp = findStat(id);
		if (temp == null) {
			return false;
		}
		temp.changeByValue(amount);
		return true;
	}
	
	public boolean addStat(StatDouble sD, UniqueId id) {
		StatManager temp = findSubStat(id);
		if (temp == null || findStat(sD.getUniqueId().getId()) != null) {
			return false;
		}
		if (this.masterList.containsKey(sD.getName())) {
			this.masterList.put(sD.getName(), this.masterList.get(sD.getName()) + sD.getValue());
		} else {
			this.masterList.put(sD.getName(), sD.getValue());
		}
		return temp.addStat(sD);
	}
	
	public StatDouble removeStat(int id, UniqueId idUnique) {
		StatManager tempSM = findSubStat(idUnique);
		if (tempSM == null || findStat(id) == null) {
			return null;
		}
		StatDouble tempSD = tempSM.removeStat(id);
		if (tempSD != null) {
			double temp = this.masterList.get(tempSD.getName()) - tempSD.getValue();
			if (temp == 0.0) {
				masterList.remove(tempSD.getName());
			}
		}
		return tempSD;
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
	
	protected Double findMasterStat(String s) {
		return masterList.get(s);
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
