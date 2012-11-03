package core.stats.survival;

import core.possessions.items.Item;

public interface Thirst {

	int getThirstLevel();
	int tickThirst();
	boolean canDrink(Item i);
	
}
