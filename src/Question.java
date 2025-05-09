import java.io.Serializable;

public abstract class Question implements Serializable {
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
}
