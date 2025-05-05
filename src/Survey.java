import java.util.ArrayList;
import java.util.List;

public class Survey {
	private static final long serialVersionUID = 1L;
	private List<Question> questions = new ArrayList<>();
	private List<Response> responses = new ArrayList<>();

	public void addQuestion(Question question) {
		questions.add(question);
	}

	public void displaySurvey() {
		if (questions.isEmpty()) {
			System.out.println("The survey has no questions.");
		} else {
			int index = 1;
			for (Question question : questions) {
				System.out.print(index + ") ");
				question.displayQuestion();
				System.out.println();
				index++;
			}
		}
	}
}
