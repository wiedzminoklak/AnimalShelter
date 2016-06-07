package validator;

import animalshelter.dao.exception.BadIdValidtionException;
import animalshelter.dao.exception.BadKindValidationException;
import animalshelter.dao.exception.BadNameValidationException;

public class Validator {

	public static Long validateId(String id) throws BadIdValidtionException {
		try {
			return Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new BadIdValidtionException();
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
