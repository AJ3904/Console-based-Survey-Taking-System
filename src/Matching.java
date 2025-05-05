import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Matching extends Question implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, String> match = new LinkedHashMap<>();
	private int maxKeyLength = 0;

	Matching(String prompt) {
		this.prompt = prompt;
	}

	public void setMatch(String question, String option) {
		match.put(question, option);
		this.maxKeyLength = Math.max(maxKeyLength, question.length());
	}

	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		int index = 0;
		int fieldWidth = maxKeyLength + 4;
		String format = "%c) %-" + fieldWidth + "s %d) %s%n";
		for (Map.Entry<String, String> entry : match.entrySet()) {
			char letter = (char) ('A' + index);
			int number = index + 1;
			System.out.printf(format, letter, entry.getKey(), number, entry.getValue());
			index++;
		}
	}
}
