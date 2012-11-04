package core.management.individual;

import java.util.Collection;
import java.util.HashMap;

import core.parsing.FileParser;

public class StatsManager implements IAspect {

	protected HashMap<String, Integer> stats;
	protected HashMap<String, StatsManager> subStats;
	
	public StatsManager() {
		subStats = new HashMap<String, StatsManager>();
		stats = new HashMap<String, Integer>();
	}
	
	public StatsManager(String[] stat, int baseNumber) {
		this();
		for (String s : stat) {
			stats.put(s, baseNumber);
		}
	}
	
	public boolean hasStatType(Class<?> c) {
		Collection<StatsManager> col = subStats.values();
		for (StatsManager s : col) {
			if (c.isAssignableFrom(s.getClass())) {
				return true;
			}
		}
		return false;
	}
	
	public StatsManager getSubStatGroup(Class<?> c) {
		Collection<StatsManager> col = subStats.values();
		for (StatsManager s : col) {
			if (c.isAssignableFrom(s.getClass())) {
				return s;
			}
		}
		for (StatsManager s: col) {
			StatsManager sG = s.getSubStatGroup(c);
			if (sG != null) {
				return sG;
			}
		}
		return null;
	}
	
	public int getStat(String s) {
		if (stats.containsKey(s)) {
			return stats.get(s);
		}
		Collection<StatsManager> col = subStats.values();
		for (StatsManager sT: col) {
			int get = sT.getStat(s);
			if (get != -1) {
				return get;
			}
		}
		return -1;
	}
	
	public boolean hasStat(String s) {
		if (stats.containsKey(s)) {
			return true;
		}
		Collection<StatsManager> col = subStats.values();
		for (StatsManager sT: col) {
			boolean get = sT.hasStat(s);
			if (get) {
				return true;
			}
		}
		return false;
	}
	
	public boolean decreaseStat(String s) {
		if (this.decreaseMyStat(s)) {
			return true;
		} else {
			Collection<StatsManager> col = subStats.values();
			for (StatsManager sT: col) {
				boolean get = sT.decreaseStat(s);
				if (get) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean increaseStat(String s) {
		if (this.increaseMyStat(s)) {
			return true;
		} else {
			Collection<StatsManager> col = subStats.values();
			for (StatsManager sT: col) {
				boolean get = sT.increaseStat(s);
				if (get) {
					return true;
				}
			}
			return false;
		}
	}
	
	public StatsManager removeSubStat(String str) {
		if (subStats.containsKey(str)) {
			return subStats.remove(str);
		}
		return null;
	}
	
	public boolean putSubStat(String str, String[] stat, int baseNumber) {
		StatsManager sG = new StatsManager(stat, baseNumber);
		return putSubStat(str, sG);
	}
	
	public boolean putSubStat(String str, StatsManager sM) {
		if (!subStats.containsKey(str)) {
			subStats.put(str, sM);
			return true;
		}
		return false;
	}
	
	public boolean decreaseMyStat(String s) {
		if (stats.containsKey(s)) {
			stats.put(s, stats.get(s) - 1);
			return true;
		}
		return false;
	}
	
	public boolean increaseMyStat(String s) {
		if (stats.containsKey(s)) {
			stats.put(s, stats.get(s) + 1);
			return true;
		}
		return false;
	}
	
	public int getMyStat(String s) {
		return stats.get(s);
	}
	
	public boolean hasMyStat(String s) {
		return stats.containsKey(s);
	}

	@Override
	public String getDescription(FileParser p) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
