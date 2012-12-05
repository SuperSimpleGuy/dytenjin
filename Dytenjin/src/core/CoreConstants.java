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

package core;

/**
 * This class manages the constants used in the framework
 * @author SuperSimpleGuy
 */
public class CoreConstants {
	
	public static final String ERR_WORLDCAL_YEARS = "Cannot have gap between consecutive years.";
	
	
	public static final boolean LOG_APPEND = true;
	public static final int MAX_LOG_BYTES = 2097152;
	public static final int MAX_LOG_FILES = 1;
	public static final String SYS_LOGGER_NAME = "DYTENJIN_MASTER";
	public static final String SYS_LOG_FILE = "logs/sys.log";
	public static final String SYS_ERR_FILE = "logs/err.log";
	public static final String SYS_FINER_FILE = "logs/finer.log";
	
	public static final String ID_VERTEX = "VTX";
	public static final String ID_EDGE = "EDG";
	public static final String ID_CAL = "CAL";
	public static final String ID_ENTITY = "ENT";
	public static final String ID_RLINK = "RLK";
	
	public static final String MAP_PARSING_FILENAME = "PARSE_MAP.txt";
	public static final String MAP_PARSING_ERROR_STRING = "";
	public static final String MAP_PARSING_ERROR_INCORRECT_FORMAT = "";
	public static final String MAP_PARSING_ERROR_NO_CLASS = "";
	public static final int MAP_PARSING_ERROR_CODE = 1;
	
	public static final String PARSER_ERROR_STRING = "";
	public static final int PARSER_ERROR_CODE = 2;
	
	//TODO Remove/refactor all the crap below this line
	
	public static final String ID_REG = "REG";
	public static final String ID_MAP = "MAP";
	public static final String ID_LOC = "LOC";
	public static final String[] ID_TYPES = {ID_REG,
											 ID_MAP,
											 ID_LOC};
	//public static IdentityManager ID_MANAGER = new IdentityManager(ID_TYPES);
	
	public static final int BASE_CORE_STAT = 10;
	
	public static final String INTELLECT = "Intellect";
	public static final String ACUITY = "Acuity";
	public static final String STRENGTH = "Strength";
	public static final String ENDURANCE = "Endurance";
	public static final String SPEED = "Speed";
	public static final String AGILITY = "Agility";
	public static final String INFLUENCE = "Influence";
	public static final String CHARISMA = "Charisma";
	public static final String[] CORE_STATS = {INTELLECT,
												ACUITY,
												ENDURANCE,
												STRENGTH,
												SPEED,
												AGILITY,
												INFLUENCE,
												CHARISMA};
	public static final String INTELLECT_SHORT = "Int";
	public static final String ACUITY_SHORT = "Acu";
	public static final String STRENGTH_SHORT = "Str";
	public static final String ENDURANCE_SHORT = "End";
	public static final String SPEED_SHORT = "Spd";
	public static final String AGILITY_SHORT = "Agl";
	public static final String INFLUENCE_SHORT = "Inf";
	public static final String CHARISMA_SHORT = "Cha";
	public static final String[] CORE_STATS_SHORT = {INTELLECT_SHORT,
												ACUITY_SHORT,
												ENDURANCE_SHORT,
												STRENGTH_SHORT,
												SPEED_SHORT,
												AGILITY_SHORT,
												INFLUENCE_SHORT,
												CHARISMA_SHORT};
	
}
