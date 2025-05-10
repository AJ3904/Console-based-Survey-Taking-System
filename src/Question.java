import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public abstract class Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	protected String prompt;
	protected int noOfAnswersAllowed;

	public void displayQuestion() {
		System.out.println(prompt);
	}

	public void setNoOfAnswersAllowed(int noOfAnswersAllowed) {
		this.noOfAnswersAllowed = noOfAnswersAllowed;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public void modifyQuestion(Input input) {
		String choice2 = input.getChoice("Do you wish to modify the prompt? [Y/N]", "Y", "N");
		if (choice2.equalsIgnoreCase("Y")) {
			String newPrompt = input.getPrompt("Enter new prompt:");
			setPrompt(newPrompt);
		}
	}

	public abstract List<String> getResponse(Input input);
}
