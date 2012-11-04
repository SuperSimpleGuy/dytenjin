package core.parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import core.Constants;

public class ParsingMap {

	private HashMap<String, String> map;
	
	public ParsingMap() {
		map = new HashMap<String, String>();
		buildMap();
	}
	
	private void buildMap() {
		Scanner s = null;
		try {
			s = new Scanner(new File(Constants.MAP_PARSING_FILENAME));
		} catch (FileNotFoundException e) {
			System.out.println(Constants.MAP_PARSING_ERROR_STRING + e.getLocalizedMessage());
			System.exit(Constants.MAP_PARSING_ERROR_CODE);
		}
		while (s.hasNextLine()) {
			String line = s.nextLine();
			int pivot = line.indexOf(' ');
			if (pivot == -1) {
				System.out.println(Constants.MAP_PARSING_ERROR_INCORRECT_FORMAT);
				System.exit(Constants.MAP_PARSING_ERROR_CODE);
			}
			String fromClass = line.substring(0, pivot);
			String toParser = line.substring(pivot + 1);
			if (toParser.contains(" ")) {
				System.out.println(Constants.MAP_PARSING_ERROR_INCORRECT_FORMAT);
				System.exit(Constants.MAP_PARSING_ERROR_CODE);
			}
			try {
				Class.forName(fromClass);
				Class.forName(toParser);
			} catch (Exception e) {
				System.out.println(Constants.MAP_PARSING_ERROR_NO_CLASS + e.getLocalizedMessage());
				System.exit(Constants.MAP_PARSING_ERROR_CODE);
			}
			map.put(fromClass, toParser);
		}
	}
	
	public FileParser getParserFromClass(Class<?> classz) {
		FileParser p = null;
		try {
			p = (FileParser)Class.forName(map.get(classz.getName())).newInstance();
		} catch (Exception e) {
			System.out.println(Constants.MAP_PARSING_ERROR_NO_CLASS + e.getLocalizedMessage());
			System.exit(Constants.MAP_PARSING_ERROR_CODE);
		}
		return p;
	}
	
}
