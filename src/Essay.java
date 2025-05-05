import java.io.Serializable;

public class Essay extends Question implements Serializable {
	private long serialVersionUID = 1L;

	Essay(String prompt) {
		this.prompt = prompt;
	}
}
