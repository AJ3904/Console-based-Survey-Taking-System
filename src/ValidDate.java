import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidDate extends Question implements Serializable {
	private static final long serialVersionUID = 1L;

	public ValidDate(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		System.out.println("Date should be entered in the following format: YYYY-MM-DD");
	}

	@Override
	public List<String> getResponse(Input input) {
		List<String> answers = new ArrayList<>();
		String answer = input.getDateResponse();
		answers.add(answer);
		return answers;
	}
}
