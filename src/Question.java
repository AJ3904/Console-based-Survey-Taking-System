import java.io.Serializable;

public abstract class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String prompt;
	protected int noOfAnswersAllowed;
	protected Response response;

	public void displayQuestion() {
		System.out.println(prompt);
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public void setNoOfAnswersAllowed(int noOfAnswersAllowed) {
		this.noOfAnswersAllowed = noOfAnswersAllowed;
	}
}
