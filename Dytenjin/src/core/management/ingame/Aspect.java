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

import core.management.game.IHasUniqueId;
import core.management.game.UniqueId;
import core.parsing.IIsParsable;
import core.parsing.ParserManager;

/**
 * @author SuperSimpleGuy
 */
public abstract class Aspect implements IHasUniqueId, IIsParsable {

	/* (non-Javadoc)
	 * @see core.management.game.IHasUniqueId#getUniqueId()
	 */
	@Override
	public UniqueId getUniqueId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see core.parsing.IIsParsable#setParserManager(core.parsing.ParserManager)
	 */
	@Override
	public void setParserManager(ParserManager<?> pM) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see core.parsing.IIsParsable#getParserManager()
	 */
	@Override
	public ParserManager<?> getParserManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
