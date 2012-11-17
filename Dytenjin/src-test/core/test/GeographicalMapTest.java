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
package core.test;

import org.junit.Before;
import org.junit.Test;

import core.geography.GeographicalLocation;
import core.geography.GeographicalMap;
import core.geography.GeographicalRegion;
import core.geography.LocationLink;
import core.geography.RegionLink;
import core.management.individual.AspectManager;

/**
 * @author SuperSimpleGuy
 */
public class GeographicalMapTest {

	private GeographicalMap mapTest;
	private GeographicalRegion gR;
	private GeographicalRegion gR2;
	private RegionLink rL;
	private RegionLink rL2;
	private GeographicalLocation gL;
	private GeographicalLocation gL2;
	private GeographicalLocation gL3;
	private LocationLink lL;
	private LocationLink lL2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mapTest = new GeographicalMap("My Test Map" , 0);
		gR = new TestGeographicalRegion("test0", 0, 0, 0);
		gR2 = new TestGeographicalRegion("test1", 1, 1, 1);
		rL = new TestRegionLink("testLink0", 2, 1, 0);
		rL2 = new TestRegionLink("testLink1", 3, -1, 0);
		gL = new TestGeographicalLocation("gL", 0, 1, 1);
		gL2 = new TestGeographicalLocation("gL2", 1, 1, -1);
		gL3 = new TestGeographicalLocation("gL3", 2, -1, 1);
		lL = new TestLocationLink("LL", 3, -2, -3);
		lL2 = new TestLocationLink("LL", 4, 2, 3);
	}

	@Test
	public void testSettingUp() {
		assert(mapTest.getGeoRegions().size() == 0 && mapTest.getRegLinks().size() == 0);
	}
	
	@Test
	public void testRegisterGeoRegion() {
		mapTest.registerGeoRegion(gR);
		assert(mapTest.getGeoRegions().get(0).getName().equals("test0"));
	}
	
	@Test
	public void testUnregisterGeoRegion() {
		mapTest.registerGeoRegion(gR);
		mapTest.unregisterGeoRegion(gR.getId());
		assert(mapTest.getGeoRegions().size() == 0);
	}
	
	@Test
	public void testPutRegLinkBetween() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.putRegLinkBetween(rL, gR, gR2);
		assert(rL.getLoc1().equals(gR) && rL.getLoc2().equals(gR2) && gR.getPathById(rL.getId()).getId() == 2 && gR2.getPathById(rL.getId()).getId() == 2);
	}
	
	@Test
	public void testRemoveRegLink() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.putRegLinkBetween(rL, gR, gR2);
		mapTest.removeRegionLink(rL.getId());
		assert(rL.getLoc1() == null && rL.getLoc2() == null && gR.getPathById(rL.getId()) == null && gR2.getPathById(rL.getId()) == null);
	}
	
	@Test
	public void testUnregisterGeoRegionLinked() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.putRegLinkBetween(rL, gR, gR2);
		mapTest.unregisterGeoRegion(gR.getId());
		assert(mapTest.getGeoRegions().size() == 1);
	}
	
	@Test
	public void testRegisterRegLinkWrong() {
		boolean test = mapTest.registerGeoRegion(rL);
		assert(!test);
	}
	
	@Test
	public void testUnRegisterRegLinkWrong() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.putRegLinkBetween(rL, gR, gR2);
		GeographicalRegion test = mapTest.unregisterGeoRegion(rL);
		assert(test == null);
	}
	
	@Test
	public void testLinkARegLinkAndGeoReg() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.putRegLinkBetween(rL, gR, gR2);
		mapTest.putRegLinkBetween(rL2, rL, gR2);
		assert(rL2.getLoc1().getName().equals("testLink0") && rL2.getLoc2().getName().equals("test1") && rL.getPathById(rL.getId()).getName().equals("testLink1") && gR2.getPathById(rL2.getId()).getName().equals("testLink1"));
	}
	
	@Test
	public void testRemoveRegLinkThatIsLinked() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.putRegLinkBetween(rL, gR, gR2);
		mapTest.putRegLinkBetween(rL2, rL, gR2);
		mapTest.removeRegionLink(rL);
		assert(rL.getLoc1() == null && rL.getLoc2() == null && gR.getPathById(rL.getId()) == null && gR2.getPathById(rL.getId()) == null &&
				rL2.getLoc1() == null && rL2.getLoc2() == null && gR.getPathById(rL2.getId()) == null && gR2.getPathById(rL2.getId()) == null);
	}
	
	@Test
	public void testAddChildLoc() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoLocation(gL, gR);
		assert(gR.getChildLocById(gL.getId()).getName().equals("gR") && gL.getParent() != null && gL.getParent().getName().equals("test0"));
	}
	
	@Test
	public void testRemoveChildLoc() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.unregisterGeoLocation(gR, gL);
		assert(gR.getChildLocById(gL.getId()) == null && gL.getParent() == null);
	}
	
	@Test
	public void testRemoveChildLocWithSearch() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.unregisterGeoLocation(gL);
		assert(gR.getChildLocById(gL.getId()) == null && gL.getParent() == null);
	}
	
	@Test
	public void testRemoveWrongChildLoc() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoLocation(gL, gR);
		GeographicalLocation result = mapTest.unregisterGeoLocation(gR2, gL);
		assert(result == null && gR.getChildLocById(gL.getId()).getName().equals("gL") && gL.getParent().getName().equals("test0"));
	}
	
	@Test
	public void testRemoveNonexistantChildLocWithSearch() {
		assert(mapTest.unregisterGeoLocation(gL) == null);
	}
	
	@Test
	public void testPutLocationLink() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR);
		mapTest.putLocationLink(lL, gR, gL, gR, gL2);
		assert(lL.getParent().getId() == gR.getId() && lL.getLoc1().getId() == gL.getId() && lL.getLoc2().getId() == gL2.getId() && gL.getPathById(lL.getId()) != null && gL2.getPathById(lL.getId()) != null);
	}
	
	@Test
	public void testPutLocationLinkWithSearch() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR);
		mapTest.putLocationLink(lL, gL, gL2);
		assert(lL.getParent().getId() == gR.getId() && lL.getLoc1().getId() == gL.getId() && lL.getLoc2().getId() == gL2.getId() && gL.getPathById(lL.getId()) != null && gL2.getPathById(lL.getId()) != null);
	}
	
	@Test
	public void testRemoveLocationLink() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR);
		mapTest.putLocationLink(lL, gR, gL, gR, gL2);
		mapTest.removeLocationLink(lL, gR);
		assert(lL.getParent() == null && lL.getLoc1() == null && lL.getLoc2() == null && gL.getPathById(lL.getId()) == null && gL2.getPathById(lL.getId()) == null);
	}
	
	@Test
	public void testRemoveLocationLinkWithSearch() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR);
		mapTest.putLocationLink(lL, gL, gL2);
		mapTest.removeLocationLink(lL);
		assert(lL.getParent() == null && lL.getLoc1() == null && lL.getLoc2() == null && gL.getPathById(lL.getId()) == null && gL2.getPathById(lL.getId()) == null);
	}
	
	@Test
	public void testRemoveWrongChildLocLink() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR);
		mapTest.putLocationLink(lL, gL, gL2);
		mapTest.removeLocationLink(lL2);
		assert(lL.getParent().getId() == gR.getId() && lL.getLoc1().getId() == gL.getId() && lL.getLoc2().getId() == gL2.getId() && gL.getPathById(lL.getId()) != null && gL2.getPathById(lL.getId()) != null &&
				lL2.getParent() == null && lL2.getLoc1() == null && lL2.getLoc2() == null && gL.getPathById(lL2.getId()) == null && gL2.getPathById(lL2.getId()) == null);
	}
	
	@Test
	public void testRemoveNonexistantChildLocLinkWithSearch() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR);
		assert(mapTest.removeLocationLink(lL) == null);
	}
	
	@Test
	public void testPutLocationLinkIncorrectly() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR);
		boolean result = mapTest.putLocationLink(lL, gR, gL, gR2, gL2);
		assert(!result && lL.getParent() == null && lL.getLoc1() == null && lL.getLoc2() == null && gL.getPathById(lL.getId()) == null && gL2.getPathById(lL.getId()) == null);
	}
	
	@Test
	public void testPutLocationLinkDiffRegions() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR2);
		mapTest.putLocationLink(lL, gR, gL, gR2, gL2);
		assert(lL.getParent().getId() == gR.getId() && lL.getLoc1().getId() == gL.getId() && lL.getLoc2().getId() == gL2.getId() && gL.getPathById(lL.getId()) != null && gL2.getPathById(lL.getId()) != null);
	}
	
	@Test
	public void testPutLocationLinkWithSearchDiffRegions() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR2);
		mapTest.putLocationLink(lL, gL, gL2);
		assert(lL.getParent().getId() == gR.getId() && lL.getLoc1().getId() == gL.getId() && lL.getLoc2().getId() == gL2.getId() && gL.getPathById(lL.getId()) != null && gL2.getPathById(lL.getId()) != null);
	}
	
	@Test
	public void testRemoveRegionWithLinkedChildren() {
		mapTest.registerGeoRegion(gR);
		mapTest.registerGeoRegion(gR2);
		mapTest.putRegLinkBetween(rL, gR, gR2);
		mapTest.registerGeoLocation(gL, gR);
		mapTest.registerGeoLocation(gL2, gR);
		mapTest.registerGeoLocation(gL3, rL);
		mapTest.putLocationLink(lL, gL, gL2);
		mapTest.putLocationLink(lL2, gL2, gL3);
		mapTest.unregisterGeoRegion(gR.getId());
		assert(mapTest.getGeoRegions().size() == 1 && gR.getChildLocById(gL.getId()) != null &&  gR.getChildLocById(gL2.getId()) != null && gR.getChildLocById(lL.getId()) != null && gR.getChildLocById(lL2.getId()) != null && rL.getChildLocById(gL3.getId()) != null &&
				rL.getLoc1() == null && rL.getLoc2() == null && gR.getPathById(rL.getId()) == null && gR2.getPathById(rL.getId()) == null &&
				lL2.getParent() == null && lL2.getLoc1() == null && lL2.getLoc2() == null && gL2.getPathById(lL2.getId()) == null && gL3.getPathById(lL2.getId()) == null);
	}
	
	
	private class TestGeographicalRegion extends GeographicalRegion {
		public TestGeographicalRegion(String name, int id, int xCoord, int yCoord) {
			super(name, id, xCoord, yCoord, new AspectManager());
		}
	}
	
	private class TestRegionLink extends RegionLink {
		public TestRegionLink(String name, int id, int xCoord, int yCoord) {
			super(name, id, xCoord, yCoord, new AspectManager());
		}
	}
	
	private class TestGeographicalLocation extends GeographicalLocation {
		public TestGeographicalLocation(String name, int id, int xCoord, int yCoord) {
			super(name, id, xCoord, yCoord, new AspectManager(), null);
		}
	}
	
	private class TestLocationLink extends LocationLink {
		public TestLocationLink(String name, int id, int xCoord, int yCoord) {
			super (name, id, xCoord, yCoord, new AspectManager(), null);
		}
	}

}
