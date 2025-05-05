import java.io.Serializable;

public class ShortAnswer extends Essay implements Serializable {
	private static final long serialVersionUID = 1L;
	private int maxCharacters;

	ShortAnswer(String prompt, int maxCharacters) {
		super(prompt);
		this.maxCharacters = maxCharacters;
	}

	private boolean isUnderCharLimit(int index) {
		return response.getResponseSize(index) <= maxCharacters;
	}
}
