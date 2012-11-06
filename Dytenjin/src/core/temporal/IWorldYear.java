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
package core.temporal;

import java.util.ArrayList;

import core.management.individual.AspectManager;

/**
 * @author SuperSimpleGuy
 */
public interface IWorldYear {

	/**
	 * Returns the unique value of the year, so there can't be
	 * two year 5134s.
	 * @return the unique value of this year object
	 */
	int getYearValue();
	
	/**
	 * Returns the number of months within this year
	 * @return the number of months within this year
	 */
	int getNumMonths();
	
	/**
	 * Returns the total number of days for this year
	 * @return the total number of days for this year
	 */
	int getTotalNumDays();
	
	/**
	 * Returns a name for the year (e.g. "Year of the Horse")
	 * @return a name for the year
	 */
	String getName();
	
	/**
	 * Returns special aspects related to a particular time of
	 * the month for this year
	 * @param month the month to determine special aspects of
	 * @return special aspects this year due to the month
	 */
	AspectManager getSpecialAspects(IWorldMonth month);
	
	/**
	 * Returns a list of all the months for this year
	 * @return a list of all the IWorldMonths for this year
	 */
	ArrayList<IWorldMonth> getMonths();
	
}
