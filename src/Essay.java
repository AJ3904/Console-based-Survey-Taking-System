import java.io.Serializable;

public class Essay extends Question implements Serializable {
	private static final long serialVersionUID = 1L;

	Essay(String prompt) {
		this.prompt = prompt;
	}
}
