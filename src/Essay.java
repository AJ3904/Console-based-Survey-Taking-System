import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Essay extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	Essay(Input input) {
		String prompt = input.getPrompt("Enter the prompt for your essay question:");
		int maxOptions = input.getIntInput("Enter the number of responses: ");
		while (maxOptions < 1) {
			System.out.println("You must allow at least 1 response. Please try again.");
			maxOptions = input.getIntInput("Enter the number of responses: ");
		}
		prompt = prompt + " (Give atleast " + maxOptions + " points)";
		this.noOfAnswersAllowed = maxOptions;
		this.prompt = prompt;
	}

	@Override
	public List<String> getResponse(Input input) {
		return input.getEssayResponse(noOfAnswersAllowed);
	}

	@Override
	public void tabulate(List<List<String>> allAnswers) {
		for (int i = 0; i < allAnswers.size(); i++) {
			List<String> answer = allAnswers.get(i);
			for (String data : answer) {
				System.out.println(data);
			}
			if (i < allAnswers.size() - 1) {
				System.out.println();
			}
		}
	}
}
