package animalshelter.validator;

import animalshelter.validator.exception.BadKindValidationException;
import animalshelter.validator.exception.BadLongValidationException;
import animalshelter.validator.exception.BadDoubleValidationException;
import animalshelter.validator.exception.BadIntegerValidationException;
import animalshelter.validator.exception.BadNameValidationException;

public class Validator {

	public static Integer validateInteger(String numb) throws BadIntegerValidationException {
		try {
			return Integer.parseInt(numb);
		} catch (NumberFormatException e) {
			throw new BadIntegerValidationException();
		}
	}
	
	public static Long validateLong(String numb) throws BadLongValidationException {
		try {
			return Long.parseLong(numb);
		} catch (NumberFormatException e) {
			throw new BadLongValidationException();
		}
	}
	
	public static Double validateDouble(String numb) throws BadDoubleValidationException {
		try {
			return Double.parseDouble(numb);
		} catch (NumberFormatException e) {
			throw new BadDoubleValidationException();
		}
	}
	
	public static String validateKind(String kind) throws BadKindValidationException {
		kind = trimSpaceFromBeginAndEnd(kind);

		char[] chars = kind.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c) && c != ' ') {
				throw new BadKindValidationException();
			}
		}

		return kind;
	}

	public static String validateName(String name) throws BadNameValidationException {
		name = trimSpaceFromBeginAndEnd(name);

		char[] chars = name.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c)) {
				throw new BadNameValidationException();
			}
		}

		return name;
	}

	private static String trimSpaceFromBeginAndEnd(String str) {		
		return str.replaceAll("^\\s+|\\s+$", "");
	}

}
