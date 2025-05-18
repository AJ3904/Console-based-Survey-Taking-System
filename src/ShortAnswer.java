import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShortAnswer extends Essay implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private int maxCharacters;

	ShortAnswer(String prompt, int maxCharacters) {
		super(prompt);
		this.maxCharacters = maxCharacters;
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
	public void modifyQuestion(Input input) {
		super.modifyQuestion(input);
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
	}
}
