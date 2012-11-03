package core.stats.geography;

import java.util.ArrayList;
import java.util.HashMap;

import core.stats.TimeDepStat;

public class GeographicalRegionStatsManager {

	private TimeDepStat<Integer> geoRegions;
	private TimeDepStat<Integer> regLinks;
	private int totalOrgs;
	
	public GeographicalRegionStatsManager() {
		this.geoRegions = new TimeDepStat<Integer>();
		this.regLinks = new TimeDepStat<Integer>();
		totalOrgs = 0;
	}
	
	public int getTotalOrgs() {
		return totalOrgs;
	}
	
	public boolean addStatBatch(HashMap<String, Integer> hmGeo, HashMap<String, Integer> hmReg) {
		totalOrgs += geoRegions.putNextTimeTick(hmGeo);
		regLinks.putNextTimeTick(hmReg);
		if (geoRegions.assertIntegrity() && regLinks.assertIntegrity()) {
			return false;
		}
		return true;
	}
	
	public HashMap<String, Integer> getTotalCurrStats() {
		HashMap<String, Integer> temp = new HashMap<String, Integer>();
		HashMap<String, Integer> geoTemp = geoRegions.getCurrentStats();
		HashMap<String, Integer> regTemp = regLinks.getCurrentStats();
		for (String s : geoTemp.keySet()) {
			temp.put(s, geoTemp.get(s));
		}
		for (String s : regTemp.keySet()) {
			if (temp.containsKey(s)) {
				int preExisting = temp.get(s) + regTemp.get(s);
				temp.put(s, preExisting);
			} else {
				temp.put(s, regTemp.get(s));
			}
		}
		return temp;
	}
	
	public HashMap<String, Integer> getTotalCurrRegs() {
		return geoRegions.getCurrentStats();
	}
	
	public HashMap<String, Integer> getTotalCurrLinks() {
		return regLinks.getCurrentStats();
	}
	
	public HashMap<String, Double> getAvgCurrStats() {
		HashMap<String, Double> temp = new HashMap<String, Double>();
		HashMap<String, Double> geoTemp = geoRegions.getCurrentAverages();
		HashMap<String, Double> regTemp = regLinks.getCurrentAverages();
		for (String s : geoTemp.keySet()) {
			temp.put(s, geoTemp.get(s));
		}
		for (String s : regTemp.keySet()) {
			if (temp.containsKey(s)) {
				double preExisting = temp.get(s) + regTemp.get(s);
				temp.put(s, preExisting);
			} else {
				temp.put(s, regTemp.get(s));
			}
		}
		return temp;
	}
	
	public HashMap<String, Double> getAvgCurrRegs() {
		return geoRegions.getCurrentAverages();
	}
	
	public HashMap<String, Double> getAvgCurrLinks() {
		return regLinks.getCurrentAverages();
	}
	
	public HashMap<String, ArrayList<Integer>> getTotalStats() {
		HashMap<String, ArrayList<Integer>> temp = new HashMap<String, ArrayList<Integer>>();
		HashMap<String, ArrayList<Integer>> geoTemp = geoRegions.getStats();
		HashMap<String, ArrayList<Integer>> regTemp = regLinks.getStats();
		for (String s : geoTemp.keySet()) {
			temp.put(s, geoTemp.get(s));
		}
		for (String s : regTemp.keySet()) {
			if (temp.containsKey(s)) {
				ArrayList<Integer> preExisting = mergeIntegerArrayLists(temp.get(s), regTemp.get(s));
				temp.put(s, preExisting);
			} else {
				temp.put(s, regTemp.get(s));
			}
		}
		return temp;
	}
	
	private ArrayList<Integer> mergeIntegerArrayLists(ArrayList<Integer> al1, ArrayList<Integer> al2) {
		int smallerSize = al1.size() > al2.size() ? al2.size() : al1.size();
		ArrayList<Integer> largerList = al1.size() > al2.size() ? al1 : al2;
		ArrayList<Integer> tempRet = new ArrayList<Integer>();
		for (int i = 0; i < smallerSize; i++) {
			tempRet.add(al1.get(i).intValue() + al2.get(2).intValue());
		}
		for (int i = smallerSize; i < largerList.size(); i++) {
			tempRet.add(largerList.get(i).intValue());
		}
		return tempRet;
	}
	
	private ArrayList<Double> mergeDoubleArrayLists(ArrayList<Double> al1, ArrayList<Double> al2) {
		int smallerSize = al1.size() > al2.size() ? al2.size() : al1.size();
		ArrayList<Double> largerList = al1.size() > al2.size() ? al1 : al2;
		ArrayList<Double> tempRet = new ArrayList<Double>();
		for (int i = 0; i < smallerSize; i++) {
			tempRet.add(al1.get(i).doubleValue() + al2.get(2).doubleValue());
		}
		for (int i = smallerSize; i < largerList.size(); i++) {
			tempRet.add(largerList.get(i).doubleValue());
		}
		return tempRet;
	}
	
	public HashMap<String, Integer> getTotalRegs() {
		return geoRegions.getCurrentStats();
	}
	
	public HashMap<String, Integer> getTotalLinks() {
		return regLinks.getCurrentStats();
	}
	
	public HashMap<String, ArrayList<Double>> getAvgStats() {
		HashMap<String, ArrayList<Double>> temp = new HashMap<String, ArrayList<Double>>();
		HashMap<String, ArrayList<Double>> geoTemp = geoRegions.getAverage();
		HashMap<String, ArrayList<Double>> regTemp = regLinks.getAverage();
		for (String s : geoTemp.keySet()) {
			temp.put(s, geoTemp.get(s));
		}
		for (String s : regTemp.keySet()) {
			if (temp.containsKey(s)) {
				ArrayList<Double> preExisting = mergeDoubleArrayLists(temp.get(s), regTemp.get(s));
				temp.put(s, preExisting);
			} else {
				temp.put(s, regTemp.get(s));
			}
		}
		return temp;
	}
	
	public HashMap<String, ArrayList<Integer>> getAvgRegs() {
		return geoRegions.getStats();
	}
	
	public HashMap<String, ArrayList<Double>> getAvgLinks() {
		return regLinks.getAverage();
	}
	
}
