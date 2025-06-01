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
	private final List<Question> questions = new ArrayList<>();
	private final List<Response> responses = new ArrayList<>();
	private final String name;

	public Survey(Input input) {
		boolean returnToMain = false;
		this.name = input.getNonEmptyResponse("Enter a name for the survey: ", "Name") + " "
				+ utils.TimeHelper.getUniqueTimeStamp();

		while (!returnToMain) {
			showMenu2();
			int choice = input.getIntInput("Enter your choice: ");
			switch (choice) {
			case 1:
				questions.add(new TrueOrFalse(input));
				break;
			case 2:
				questions.add(new MultipleChoice(input));
				break;
			case 3:
				questions.add(new ShortAnswer(input));
				break;
			case 4:
				questions.add(new Essay(input));
				break;
			case 5:
				questions.add(new ValidDate(input));
				break;
			case 6:
				questions.add(new Matching(input));
				break;
			case 7:
				returnToMain = true;
				break;
			default:
				System.out.println("Invalid choice. Try again.\n");
			}
		}
	}

	private void showMenu2() {
		System.out.println("1) Add a new T/F question");
		System.out.println("2) Add a new multiple-choice question");
		System.out.println("3) Add a new short answer question");
		System.out.println("4) Add a new essay question");
		System.out.println("5) Add a new date question");
		System.out.println("6) Add a new matching question");
		System.out.println("7) Return to previous menu");
	}

	public String getName() {
		return name;
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
		responses.add(response);
		response.saveResponse(name, TimeHelper.getUniqueTimeStamp());
	}

	public void modifyQuestion(Input input) {
		displaySurvey();
		int choice = input.getIntInput("Enter the number of the question you want to modify: ");
		while (choice <= 0 || choice > questions.size()) {
			choice = input.getIntInput("Enter a valid question number: ");
		}
		questions.get(choice - 1).modifyQuestion(input);
	}

	public void tabulate() {
		responses.clear();
		String basePath = name + " Responses";

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
			System.out.println("No responses found for the survey: " + name);
			return;
		}

		System.out.println("\nTabulating Responses for Survey: " + name);
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
			if (i < questions.size()) {
				System.out.println();
			}
		}
	}

}
