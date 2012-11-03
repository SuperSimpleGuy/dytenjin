package core.stats.survival;

import core.possessions.items.Item;

public interface Hunger {

	int getHungerLevel();
	int tickHunger();
	boolean canEat(Item i);
	
}
