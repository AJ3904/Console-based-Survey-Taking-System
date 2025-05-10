import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShortAnswer extends Essay implements Serializable {
	private static final long serialVersionUID = 1L;
	private int maxCharacters;

	ShortAnswer(String prompt, int maxCharacters) {
		super(prompt);
		this.maxCharacters = maxCharacters;
	}

	public void setMaxCharacters(int maxCharacters) {
		this.maxCharacters = maxCharacters;
	}

	public int getCharacterLimit() {
		return maxCharacters;
	}

	@Override
	public List<String> getResponse(Input input) {
		List<String> answers = new ArrayList<>();
		String answer = input.getShortAnswerResponse(maxCharacters);
		answers.add(answer);
		return answers;
	}
}
