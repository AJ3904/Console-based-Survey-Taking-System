import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.SerializationHelper;
import utils.TimeHelper;

public class Survey implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private static final String basePath = "Survey" + File.separator;
	private List<Question> questions = new ArrayList<>();
	private List<Response> responses = new ArrayList<>();
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name + " " + TimeHelper.getUniqueTimeStamp();
	}

	public void addQuestion(Question question) {
		questions.add(question);
	}

	public void displaySurvey() {
		if (questions.isEmpty()) {
			System.out.println("The survey has no questions.\n");
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

	public void saveSurvey() {
		System.out.println("Survey has been saved.");
		SerializationHelper.serialize(Survey.class, this, basePath, name);
	}

	public void takeSurvey(Input input) {
		Response response = new Response();
		for (Question question : questions) {
			question.displayQuestion();
			List<String> answers = question.getResponse(input);
			response.addResponse(answers);
		}
		addResponse(response);
		response.saveResponse(name, TimeHelper.getUniqueTimeStamp());
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void addResponse(Response response) {
		responses.add(response);
	}
}
