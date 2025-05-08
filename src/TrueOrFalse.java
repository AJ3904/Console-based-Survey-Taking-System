import java.io.Serializable;

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
}
