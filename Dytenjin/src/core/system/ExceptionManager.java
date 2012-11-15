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
package core.system;

import java.util.logging.Level;

/**
 * @author SuperSimpleGuy
 */
public class ExceptionManager {

	public static final ExceptionManager SYS_EXCEPTION_MANAGER = new ExceptionManager();
	
	private boolean severeQuit;
	private boolean warningQuit;
	
	private ExceptionManager() {
		this.severeQuit = true;
		this.warningQuit = false;
	}
	
	public void setSevereQuit(boolean severeQuit) {
		this.severeQuit = severeQuit;
	}
	
	public void setWarningQuit(boolean warningQuit) {
		this.warningQuit = warningQuit;
	}
	
	public boolean getSevereQuit() {
		return severeQuit;
	}
	
	public boolean getWarningQuit() {
		return warningQuit;
	}
	
	public void throwException(Exception e, Level level, String fileName) {
		if (!(level.equals(Level.SEVERE) || level.equals(Level.WARNING))) {
			return;
		}
		String method = "";
		if (e.getStackTrace().length > 0) {
			method = e.getStackTrace()[0].getMethodName();
		}
		CoreLogfileManager.ENGINE_LOGMNGR.logWithoutParams(this.getClass().toString(), fileName, level, this.getClass().toString(), method, e.getLocalizedMessage());
		if (severeQuit && level.equals(Level.SEVERE)) {
			e.printStackTrace();
			System.exit(1);
		} else if (warningQuit && level.equals(Level.WARNING)) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
