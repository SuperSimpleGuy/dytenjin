package core.management.game;

import java.util.HashMap;

public class IdentityManager {

	private HashMap<String, Integer> identities;
	
	public IdentityManager() {
		identities = new HashMap<String, Integer>();
	}
	
	public IdentityManager(String[] keys) {
		this();
		for (String s : keys) {
			this.addNewIdTracking(s);
		}
	}
	
	public boolean isIdFree(String key, int id) {
		return identities.get(key) <= id;
	}
	
	public boolean addNewIdTracking(String key) {
		if (identities.containsKey(key)) {
			return false;
		}
		identities.put(key, 0);
		return true;
	}
	
	public int getNextFreeId(String key) {
		if (!identities.containsKey(key)) {
			return -1;
		}
		int temp = identities.get(key);
		identities.put(key, temp + 1);
		return temp;
	}
	
}
