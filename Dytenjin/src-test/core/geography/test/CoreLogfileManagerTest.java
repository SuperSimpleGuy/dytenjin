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
package core.geography.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Constants;
import core.system.CoreLogfileManager;

/**
 * @author SuperSimpleGuy
 */
public class CoreLogfileManagerTest {

	private CoreLogfileManager testingManager = CoreLogfileManager.ENGINE_LOGMNGR;

	private static final String testLoggerName = "logger.test.one";
	private static final String testFileName = "logs/test.log";
	private Level originalLevel;
	
	@BeforeClass
	public static void setupTests() {
		File f = new File(testFileName);
		if (f.exists()) {
			f.delete();
		}
		f = new File(Constants.SYS_LOG_FILE);
		if (f.exists()) {
			f.delete();
		}
		Logger.getLogger(testFileName).setLevel(Level.INFO);
	}
	
	@Before
	public void setup() {
		originalLevel = Logger.getLogger(testFileName).getLevel();
	}
	
	@After
	public void cleanup() {
		Logger.getLogger(testFileName).setLevel(originalLevel);
		testingManager.removeLogLevelForFile(testFileName);
	}
	
	@Test
	public void testWithoutParams() {
		boolean b = testingManager.logWithoutParams(testLoggerName, Constants.SYS_LOG_FILE, Level.WARNING, this.getClass().toString(), "testWithoutParams", "Success!");
		assert(b == true);
	}
	
	@Test
	public void testWithParams() {
		boolean b = testingManager.logWithParams(testLoggerName, Constants.SYS_LOG_FILE, Level.WARNING, this.getClass().toString(), "testWithParams", "Success!", new String[] {"test", "this", "array"});
		assert(b == true);
	}
	
	@Test
	public void testSetLogLevelForFile() {
		testingManager.setLogLevelForFile(testFileName, Level.FINEST);
		assert(Logger.getLogger(testFileName).getLevel().equals(Level.FINEST));
	}
	
	@Test
	public void testFinestLogLevelDefault() {
		boolean b = testingManager.logWithoutParams(testLoggerName, testFileName, Level.FINEST, this.getClass().toString(), "testFinestLogLevelDefault", "Failure!");
		assert(b == true);
	}
	
	@Test
	public void testFinestLogLevelNonDefault() {
		testingManager.setLogLevelForFile(testFileName, Level.FINEST);
		boolean b = testingManager.logWithoutParams(testLoggerName, testFileName, Level.FINEST, this.getClass().toString(), "testFinestLogLevelNonDefault", "Success!");
		assert(b == true);
	}
	
	@Test
	public void testFinestLogLevelDefaultEmptyParams() {
		boolean b = testingManager.logWithParams(testLoggerName, testFileName, Level.FINEST, this.getClass().toString(), "testFinestLogLevelDefaultEmptyParams", "Failure!", new Object[] {});
		assert(b == true);
	}
	
	@Test
	public void testFinestLogLevelNonDefaultEmptyParams() {
		testingManager.setLogLevelForFile(testFileName, Level.FINEST);
		boolean b = testingManager.logWithParams(testLoggerName, testFileName, Level.FINEST, this.getClass().toString(), "testFinestLogLevelNonDefaultEmptyParams", "Success!", new Object[] {});
		assert(b == true);
	}
	
	@Test
	public void testFinestLogLevelDefaultParams() {
		boolean b = testingManager.logWithParams(testLoggerName, testFileName, Level.FINEST, this.getClass().toString(), "testFinestLogLevelDefaultParams", "Failure!", new Object[] {"test", "one"});
		assert(b == true);
	}
	
	@Test
	public void testFinestLogLevelNonDefaultParams() {
		testingManager.setLogLevelForFile(testFileName, Level.FINEST);
		boolean b = testingManager.logWithParams(testLoggerName, testFileName, Level.FINEST, this.getClass().toString(), "testFinestLogLevelNonDefaultParams", "Success!", new Object[] {"two", "test"});
		assert(b == true);
	}
	
	@Test
	public void testInfoLogLevelDefaultParams() {
		boolean b = testingManager.logWithParams(testLoggerName, testFileName, Level.INFO, this.getClass().toString(), "testInfoLogLevelDefaultParams", "Success!", new Object[] {"test", "one"});
		assert(b == true);
	}
	
	@Test
	public void testInfoLogLevelNonDefaultParams() {
		testingManager.setLogLevelForFile(testFileName, Level.SEVERE);
		boolean b = testingManager.logWithParams(testLoggerName, testFileName, Level.INFO, this.getClass().toString(), "testInfoLogLevelNonDefaultParams", "Failure!", new Object[] {"two", "test"});
		assert(b == true);
	}
	
	@Test
	public void testInfoLogLevelDefaultLongParams() {
		boolean b = testingManager.logWithParams(testLoggerName, testFileName, Level.INFO, this.getClass().toString(), "testInfoLogLevelNonDefaultParams", "Success!", new Object[] {"really.long.one", "really.long.two", "really.long.three", "very.very.very.very.long.four", "long.five", "6", "7", "8", "9", "10", "11"});
		assert(b == true);
	}
	
	@Test
	public void testEnteringWithParams() {
		testingManager.setLogLevelForFile(testFileName, Level.FINER);
		boolean b = testingManager.enteringWithParams(testLoggerName, testFileName, "testEnteringWithParams", "Enter Success!", new Object[] {"param0", "param1", "param2"});
		assert(b == true);
	}
	
	@Test
	public void testEnteringWithoutParams() {
		testingManager.setLogLevelForFile(testFileName, Level.FINER);
		boolean b = testingManager.enteringWithoutParams(testLoggerName, testFileName, "testEnteringWithoutParams", "Enter Success!");
		assert(b == true);
	}
	
	@Test
	public void testExitingWithResult() {
		testingManager.setLogLevelForFile(testFileName, Level.FINER);
		boolean b = testingManager.exitingWithResult(testLoggerName, testFileName, "testExitingWithResult", "Exit Success!", "result0");
		assert(b == true);
	}
	
	@Test
	public void testExitingWithoutResult() {
		testingManager.setLogLevelForFile(testFileName, Level.FINER);
		boolean b = testingManager.exitingWithoutResult(testLoggerName, testFileName, "testExitingWithoutResult", "Exit Success!");
		assert(b == true);
	}
}
