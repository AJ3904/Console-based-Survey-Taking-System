import java.io.Serializable;

public abstract class Question implements Serializable {
	protected String prompt;
	protected int noOfAnswersAllowed;
	protected Response response;
	private long serialVersionUID = 1L;

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
}
