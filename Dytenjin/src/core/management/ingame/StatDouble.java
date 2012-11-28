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

/**
 * @author SuperSimpleGuy
 */
public class StatDouble {

	private String name;
	private String description;
	private double value;
	
	public StatDouble(String name, double value) {
		this.name = name;
		this.value = value;
		this.description = "";
	}
	
	public StatDouble(String name, String description, double value) {
		this.name = name;
		this.value = value;
		this.description = description;
	}
	
	public void changeByValue(double amount) {
		this.value += amount;
	}

	/**
	 * Returns the name 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the description 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the value 
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns the value rounded to an integer
	 * @return the value rounded to an integer
	 */
	public int getValueRound() {
		return (int) Math.round(value);
	}
	
	/**
	 * Returns the value truncated to an integer
	 * @return the value truncated to an integer
	 */
	public int getValueTrunc() {
		return (int) Math.floor(value);
	}
}
