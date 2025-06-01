import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ShortAnswer extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private int maxCharacters;

	ShortAnswer(Input input, boolean flag) {
		this.prompt = input.getPrompt("Enter the prompt for your short-answer question:");
		int maxChars = input.getIntInput("Enter the maximum response length (in characters): ");
		while (true) {
			if (0 > maxChars) {
				System.out.println("Maximum response length must be a positive integer.");
			} else {
				break;
			}
		}
		prompt = prompt + " (In under " + maxChars + " characters)";
		this.maxCharacters = maxChars;

		if (flag) {
			System.out.println("Enter the correct answer:");
			answers = Collections.singletonList(input.getShortAnswerResponse(maxCharacters));
		}
	}

	public void setMaxCharacters(int maxCharacters) {
		this.maxCharacters = maxCharacters;
	}

	@Override
	public List<String> getResponse(Input input) {
		List<String> answers = new ArrayList<>();
		String answer = input.getShortAnswerResponse(maxCharacters);
		answers.add(answer);
		return answers;
	}

	@Override
	public void modifyQuestion(Input input, boolean flag) {
		super.modifyQuestion(input, flag);
		setPrompt(prompt + " (In under " + maxCharacters + " characters)");
		String choice = input.getChoice("Do you wish to modify the character limit? [Y/N]", "Y", "N");

		if (choice.equalsIgnoreCase("Y")) {
			int limit = input.getIntInput("Enter new character limit for responses: ");
			String prompt = getPrompt();

			prompt = prompt.replaceAll("\\s*\\(In under \\d+ characters\\)", "");

			prompt = prompt + " (In under " + limit + " characters)";
			setPrompt(prompt);
			setMaxCharacters(limit);
		}

		if (flag) {
			System.out.println("Enter the correct answer:");
			answers = Collections.singletonList(input.getShortAnswerResponse(maxCharacters));
		}
	}

	@Override
	public void tabulate(List<List<String>> allAnswers) {
		Map<String, Integer> counts = new LinkedHashMap<>();
		for (List<String> answer : allAnswers) {
			for (String data : answer) {
				counts.put(data, counts.getOrDefault(data, 0) + 1);
			}
		}

		for (Map.Entry<String, Integer> entry : counts.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
}
