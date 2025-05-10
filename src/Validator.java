public class Validator {

	public boolean isValidMultipleChoiceOption(String response, int noOfChoices) {
		if (response == null || response.length() != 1) {
			return false;
		}
		char c = Character.toUpperCase(response.charAt(0));
		char maxOption = (char) ('A' + noOfChoices - 1);

		return c >= 'A' && c <= maxOption;
	}

	public boolean isValidShortAnswer(String response, int characterLimit) {
		if (response.isEmpty() || response.length() > characterLimit) {
			return false;
		}

		return !response.matches("[^A-Za-z0-9]+") && !response.matches("[0-9]+");
	}

	public boolean isValidDate(String date) {
		String regex = "^\\d{4}-\\d{2}-\\d{2}$";
		if (!date.matches(regex)) {
			System.out.println("Date must be in YYYY-MM-DD format.");
			return false;
		}

		String[] parts = date.split("-");
		int year;
		int month;
		int day;

		try {
			year = Integer.parseInt(parts[0]);
			month = Integer.parseInt(parts[1]);
			day = Integer.parseInt(parts[2]);
		} catch (NumberFormatException e) {
			System.out.println("Year, month, and day must be valid numbers.");
			return false;
		}

		if (month < 1 || month > 12) {
			System.out.println("Month must be between 1 and 12.");
			return false;
		}

		int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if (isLeapYear(year)) {
			daysInMonth[1] = 29;
		}

		int maxDay = daysInMonth[month - 1];
		if (day < 1 || day > maxDay) {
			System.out.println("Day must be between 1 and " + maxDay + " for month " + month + ".");
			return false;
		}

		return true;
	}

	private boolean isLeapYear(int year) {
		if (year % 4 != 0) {
			return false;
		} else if (year % 100 != 0) {
			return true;
		} else {
			return year % 400 == 0;
		}
	}

	public boolean isValidMatchingPair(String response, int matchCount) {
		if (response == null || response.trim().isEmpty()) {
			return false;
		}

		String[] parts = response.split("\\s+");

		if (parts.length != 2) {
			return false;
		}

		String left = parts[0];
		if (!isValidMultipleChoiceOption(left, matchCount)) {
			return false;
		}

		int right;
		try {
			right = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			return false;
		}

		return right >= 1 && right <= matchCount;
	}
}
