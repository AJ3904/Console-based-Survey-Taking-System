import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrueOrFalse extends MultipleChoice implements Serializable {
	private static final long serialVersionUID = 1L;

	TrueOrFalse(String prompt) {
		super(prompt);
		options.add("True");
		options.add("False");
		this.noOfOptions = 2;
	}

	@Override
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		System.out.println("T/F");
	}

	@Override
	public List<String> getResponse(Input input) {
		List<String> answers = new ArrayList<>();
		String answer = input.getChoice("", "T", "F");
		answers.add(answer);
		return answers;
	}
}
