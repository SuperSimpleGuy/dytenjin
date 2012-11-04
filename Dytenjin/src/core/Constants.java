package core;

import core.management.game.IdentityManager;

public class Constants {
	
	public static final String ID_REG = "REG";
	public static final String ID_MAP = "MAP";
	public static final String ID_LOC = "LOC";
	public static final String[] ID_TYPES = {ID_REG,
											 ID_MAP,
											 ID_LOC};
	public static IdentityManager ID_MANAGER = new IdentityManager(ID_TYPES);
	
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
