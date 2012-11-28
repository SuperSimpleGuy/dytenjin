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
package core.parsing;

/**
 * @author SuperSimpleGuy
 */
public class MultiGenericsWrapperMessage {

	private Object[] data;
	private Class<?>[] typing;
	private boolean locked;
	
	public MultiGenericsWrapperMessage(Class<?> cl) {
		data = new Object[1];
		typing = new Class<?>[1];
		typing[0] = cl;
	}
	
	public MultiGenericsWrapperMessage(Class<?>[] cl) {
		data = new Object[cl.length];
		typing = new Class<?>[cl.length];
		for (int i = 0; i < cl.length; i++) {
			typing[i] = cl[i];
		}
	}
	
	private boolean tryTyping(Object o, Class<?> cl) {
		try {
			cl.cast(o); //Trigger exception, return false if so
			return true;
		} catch (ClassCastException e) {
			//TODO: Logging
			return false;
		}
	}
	
	public boolean setData(Object[] o) {
		if (locked) {
			return false;
		}
		if (o.length != typing.length) {
			return false;
		}
		for (int i = 0; i < o.length; i++) {
			if (!tryTyping(o[i], typing[i])) {
				return false;
			}
		}
		for(int i = 0; i < o.length; i++) {
			data[i] = o[i];
		}
		locked = true;
		return true;
	}
	
	public boolean doTypesMatch(Class<?>[] cl) {
		if (cl.length != typing.length) {
			return false;
		}
		for (int i = 0; i < cl.length; i++) {
			if (!cl[i].equals(typing[i])) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	public int length() {
		return data.length;
	}
	
	public Object get(int index) {
		return data[index];
	}
	
	public Class<?> getType(int index) {
		return typing[index];
	}
}
