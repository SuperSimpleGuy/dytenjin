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

import core.management.game.UniqueId;
import core.temporal.ITimeChanging;
import core.temporal.WorldTimeDuration;

/**
 * @author SuperSimpleGuy
 */
public class StatCondition extends StatDouble implements ITimeChanging {

	private WorldTimeDuration durLeft;
	private StatManager parent;
	
	/**
	 * @param name
	 * @param value
	 */
	public StatCondition(String name,
			double value,
			UniqueId id,
			WorldTimeDuration initDuration,
			StatManager parent) {
		this(name, "", value, id, initDuration, parent);
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param value
	 */
	public StatCondition(String name,
			String description,
			double value,
			UniqueId id,
			WorldTimeDuration initDuration,
			StatManager parent) {
		super(name, description, value, id);
		this.durLeft = initDuration;
		this.parent = parent;
		if (!parent.hasStat(super.getUniqueId().getId())) {
			parent.addStat(this);
		}
	}
	
	public boolean isValid() {
		return durLeft.isNonNegative();
	}

	@Override
	public void updateOverPeriodOfTime(WorldTimeDuration d) {
		durLeft.decreaseDuration(d);
		if (!this.isValid()) {
			parent.removeStat(super.getUniqueId().getId());
		}
	}

}
