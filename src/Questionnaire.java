import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.SerializationHelper;
import utils.TimeHelper;

public abstract class Questionnaire implements Serializable {
	protected static final String RESPONSE_SUFFIX = " Responses";
	@Serial
	private static final long serialVersionUID = 1L;
	protected List<Question> questions = new ArrayList<>();
	protected List<Response> responses = new ArrayList<>();
	protected String name;

	public String getName() {
		return name;
	}

	protected void showQuestionMenu() {
		System.out.println("1) Add a new T/F question");
		System.out.println("2) Add a new multiple-choice question");
		System.out.println("3) Add a new short answer question");
		System.out.println("4) Add a new essay question");
		System.out.println("5) Add a new date question");
		System.out.println("6) Add a new matching question");
		System.out.println("7) Return to previous menu");
	}

	public void display() {
		if (questions.isEmpty()) {
			System.out.println("There are no questions to display.");
		} else {
			int index = 1;
			for (Question question : questions) {
				System.out.print(index++ + ") ");
				question.displayQuestion();
				System.out.println();
			}
		}
	}

	public void modifyQuestion(Input input, boolean flag) {
		display();
		int choice = input.getIntInput("Enter the number of the question you want to modify: ");
		while (choice <= 0 || choice > questions.size()) {
			choice = input.getIntInput("Enter a valid question number: ");
		}
		questions.get(choice - 1).modifyQuestion(input, flag);
	}

	public void tabulate() {
		responses.clear();
		String basePath = name + RESPONSE_SUFFIX;

		File responseDir = new File(basePath);
		if (!responseDir.exists() || !responseDir.isDirectory()) {
			System.out.println("No response directory found.");
			return;
		}

		File[] responseFiles = responseDir.listFiles();
		if (responseFiles == null || responseFiles.length == 0) {
			System.out.println("No responses found.");
			return;
		}

		for (File file : responseFiles) {
			Response response = SerializationHelper.deserialize(Response.class, file.getAbsolutePath());
			if (response != null) {
				responses.add(response);
			}
		}

		if (responses.isEmpty()) {
			System.out.println("No responses found for " + name);
			return;
		}

		System.out.println("Tabulating Responses for " + name);
		for (int i = 0; i < questions.size(); i++) {
			Question question = questions.get(i);
			question.displayQuestion();

			List<List<String>> allAnswers = new ArrayList<>();
			for (Response response : responses) {
				if (i < response.getAnswers().size()) {
					allAnswers.add(response.getAnswers().get(i));
				}
			}
			question.tabulate(allAnswers);
			if (i + 1 < questions.size()) {
				System.out.println();
			}
		}
	}

	public void take(Input input) {
		Response response = new Response();
		for (Question question : questions) {
			question.displayQuestion();
			List<String> answers = question.getResponse(input);
			response.addResponse(answers);
		}
		response.saveResponse(name, TimeHelper.getUniqueTimeStamp());
	}

	public abstract void save();
}
