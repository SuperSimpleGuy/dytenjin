package core.parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import core.Constants;
import core.management.individual.IAspect;

public abstract class FileParser {

	protected Scanner inFile;
	
	public FileParser(String f) {
		try {
			inFile = new Scanner(new File(f));
		} catch (FileNotFoundException e) {
			System.out.println(Constants.PARSER_ERROR_STRING);
			System.exit(Constants.PARSER_ERROR_CODE);
		}
	}
	
	public abstract String getDescription(IAspect a);
	
}
