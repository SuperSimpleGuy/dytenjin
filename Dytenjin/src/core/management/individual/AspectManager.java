package core.management.individual;

import java.util.ArrayList;

public class AspectManager {

	private ArrayList<IAspect> aspects;
	
	public AspectManager() {
		aspects = new ArrayList<IAspect>();
	}
	
	public AspectManager(IAspect a) {
		this();
		aspects.add(a);
	}
	
	public AspectManager(IAspect[] a) {
		this();
		for (IAspect aspect : a) {
			aspects.add(aspect);
		}
	}
	
}
