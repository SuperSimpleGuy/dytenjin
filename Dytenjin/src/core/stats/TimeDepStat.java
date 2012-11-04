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

package core.stats;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeDepStat<T extends Number> {

	private HashMap<String, ArrayList<T>> stat;
	private int numTimeTicks;
	private HashMap<String, ArrayList<Double>> average;
	
	public TimeDepStat() {
		this.stat = new HashMap<String, ArrayList<T>>();
		this.average = new HashMap<String, ArrayList<Double>>();
		this.numTimeTicks = 0;
	}
	
	public boolean assertIntegrity() {
		if(average.keySet().size() == stat.keySet().size()) {
			for (String s : average.keySet()) {
				if (!stat.containsKey(s)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public HashMap<String, T> getCurrentStats() {
		HashMap<String, T> temp = new HashMap<String, T>();
		for (String s : stat.keySet()) {
			ArrayList<T> tempAL = stat.get(s);
			T payload = tempAL.get(tempAL.size() - 1);
			temp.put(s, payload);
		}
		return temp;
	}
	
	public HashMap<String, Double> getCurrentAverages() {
		HashMap<String, Double> temp = new HashMap<String, Double>();
		for (String s : average.keySet()) {
			ArrayList<Double> tempAL = average.get(s);
			Double payload = tempAL.get(tempAL.size() - 1);
			temp.put(s, payload);
		}
		return temp;
	}
	
	public HashMap<String, ArrayList<T>> getStats() {
		HashMap<String, ArrayList<T>> temporary = new HashMap<String, ArrayList<T>>();
		for(String s : stat.keySet()) {
			temporary.put(s, this.getStatsForKey(s));
		}
		return temporary;
	}
	
	public HashMap<String, ArrayList<Double>> getAverage() {
		HashMap<String, ArrayList<Double>> temporary = new HashMap<String, ArrayList<Double>>();
		for(String s : stat.keySet()) {
			temporary.put(s, this.getAverageForKey(s));
		}
		return temporary;
	}
	
	public ArrayList<Double> getAverageForKey(String key) {
		if (!average.containsKey(key)) {
			return null;
		}
		ArrayList<Double> toCopy = average.get(key);
		ArrayList<Double> temp = new ArrayList<Double>(numTimeTicks);
		for (int i = toCopy.size() - 1; i >= 0; i--) {
			temp.add(numTimeTicks - toCopy.size() + i, toCopy.get(i));
		}
		for (int j = numTimeTicks - toCopy.size() - 1; j >= 0; j--) {
			temp.add(j, 0.0);
		}
		return temp;
	}
	
	public ArrayList<T> getStatsForKey(String key) {
		if (!stat.containsKey(key)) {
			return null;
		}
		ArrayList<T> toCopy = stat.get(key);
		ArrayList<T> temp = new ArrayList<T>(numTimeTicks);
		for (int i = toCopy.size() - 1; i >= 0; i--) {
			temp.add(numTimeTicks - toCopy.size() + i, toCopy.get(i));
		}
		for (int j = numTimeTicks - toCopy.size() - 1; j >= 0; j--) {
			temp.add(j, null);
		}
		return temp;
	}
	
	public int putNextTimeTick(HashMap<String, T> hashM) {
		int numNew = 0;
		for (String s : hashM.keySet()) {
			T tempPayload = hashM.get(s);
			if (!this.putNextStat(tempPayload, s)) {
				numNew++;
			}
			putNextAverage(tempPayload, s);
		}
		this.numTimeTicks++;
		return numNew;
	}
	
	private boolean putNextStat(T payload, String key) {
		if (stat.containsKey(key)) {
			stat.get(key).add(payload);
			return true;
		} else {
			ArrayList<T> temp = new ArrayList<T>();
			temp.add(payload);
			stat.put(key, temp);
			return false;
		}
	}
	
	private boolean putNextAverage(T payload, String key) {
		if (average.containsKey(key)) {
			ArrayList<Double> temp = average.get(key);
			double prevAvg = temp.get(temp.size() - 1) * (temp.size()/(double)(temp.size()+1));
			average.get(key).add(payload.doubleValue() * (1.0/(temp.size()+1)) + prevAvg);
			return true;
		} else {
			ArrayList<Double> temp = new ArrayList<Double>();
			temp.add(payload.doubleValue());
			average.put(key, temp);
			return false;
		}
	}
}
