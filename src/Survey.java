import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.SerializationHelper;

public class Survey implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String basePath = "Survey" + File.separator;
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

	public void saveSurvey(String name) {
		System.out.println("Survey has been saved.");
		SerializationHelper.serialize(Survey.class, this, basePath, name);
	}

	public List<Question> getQuestions() {
		return questions;
	}
}
