package core.management.individual;

import java.util.ArrayList;

import core.parsing.ParsingMap;

public abstract class AspectStatDependent implements IAspect {
	
	protected ArrayList<String> statsDependent;
	
	public AspectStatDependent() {
		statsDependent = new ArrayList<String>();
	}
	
	public AspectStatDependent(String s) {
		this();
		statsDependent.add(s);
	}
	
	public AspectStatDependent(String[] sArr) {
		this();
		for (String s : sArr) {
			statsDependent.add(s);
		}
	}
	
	@Override
	public abstract String getDescription(ParsingMap p);

}
