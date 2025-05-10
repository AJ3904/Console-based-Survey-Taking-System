import java.io.Serializable;
import java.util.List;

public class Essay extends Question implements Serializable {
	private static final long serialVersionUID = 1L;

	Essay(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public List<String> getResponse(Input input) {
		return input.getEssayResponse(noOfAnswersAllowed);
	}
}
