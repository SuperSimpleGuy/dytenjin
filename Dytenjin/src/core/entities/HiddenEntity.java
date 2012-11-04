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

package core.entities;

public abstract class HiddenEntity<T extends Entity> extends Entity {

	protected T hiddenEntity;
	
	public HiddenEntity(int id) {
		super(id);
	}
	
	public HiddenEntity(String s, int id) {
		super(s, id);
	}
	
	public boolean isHidingEntity() {
		return hiddenEntity != null;
	}
	
	public boolean hideEntity(T entity) {
		if (hiddenEntity != null) {
			return false;
		}
		hiddenEntity = entity;
		return true;
	}
	
	public T unhideEntity() {
		T temp = hiddenEntity;
		hiddenEntity = null;
		return temp;
	}
	
	public T getHiddenEntity() {
		return hiddenEntity;
	}

}
