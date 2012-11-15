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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.system.ExceptionManager;

/**
 * @author SuperSimpleGuy
 */
public class ExceptionManagerTest {

	private static final String testFileName = "logs/error.log";
	
	@BeforeClass
	public static void setupTests() {
		File f = new File(testFileName);
		if (f.exists()) {
			f.delete();
		}
		Logger.getLogger(testFileName).setLevel(Level.INFO);
	}
	
	@Before
	public void setup() {
		ExceptionManager.SYS_EXCEPTION_MANAGER.setSevereQuit(true);
		ExceptionManager.SYS_EXCEPTION_MANAGER.setWarningQuit(false);
	}
	
	@Test
	public void testLowLevel() {
		ExceptionManager.SYS_EXCEPTION_MANAGER.throwException(new Exception("Failure!"), Level.INFO, testFileName);
	}
	
	@Test
	public void testSetSevereQuit() {
		ExceptionManager.SYS_EXCEPTION_MANAGER.setSevereQuit(false);
		assert(!ExceptionManager.SYS_EXCEPTION_MANAGER.getSevereQuit());
	}
	
	@Test
	public void testSetWarningQuit() {
		ExceptionManager.SYS_EXCEPTION_MANAGER.setWarningQuit(true);
		assert(ExceptionManager.SYS_EXCEPTION_MANAGER.getWarningQuit());
	}
	
	@Test
	public void testNoQuitWarning() {
		ExceptionManager.SYS_EXCEPTION_MANAGER.setWarningQuit(false);
		ExceptionManager.SYS_EXCEPTION_MANAGER.throwException(new Exception("Success!"), Level.WARNING, testFileName);
	}
	
	@Test
	public void testNoQuitSevere() {
		ExceptionManager.SYS_EXCEPTION_MANAGER.setSevereQuit(false);
		ExceptionManager.SYS_EXCEPTION_MANAGER.throwException(new Exception("Success!"), Level.SEVERE, testFileName);
	}

}
