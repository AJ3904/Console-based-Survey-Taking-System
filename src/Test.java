import java.io.File;
import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import utils.SerializationHelper;
import utils.TimeHelper;

public class Test extends Questionnaire {
	@Serial
	private static final long serialVersionUID = 1L;
	private static final String basePath = "Test" + File.separator;

	public Test(Input input) {
		this.name = input.getNonEmptyResponse("Enter a name for the test: ", "Name") + " "
				+ TimeHelper.getUniqueTimeStamp();

		boolean returnToMain = false;
		while (!returnToMain) {
			showQuestionMenu();
			int choice = input.getIntInput("Enter your choice: ");
			switch (choice) {
			case 1:
				questions.add(new TrueOrFalse(input, true));
				break;
			case 2:
				questions.add(new MultipleChoice(input, true));
				break;
			case 3:
				questions.add(new ShortAnswer(input, true));
				break;
			case 4:
				questions.add(new Essay(input, true));
				break;
			case 5:
				questions.add(new ValidDate(input, true));
				break;
			case 6:
				questions.add(new Matching(input, true));
				break;
			case 7:
				returnToMain = true;
				break;
			default:
				System.out.println("Invalid choice. Try again.");
				break;
			}
		}
	}

	@Override
	public void save() {
		System.out.println("Test has been saved.");
		SerializationHelper.serialize(Test.class, this, basePath, name);
	}

	public void displayWithAnswers() {
		if (questions.isEmpty()) {
			System.out.println("There are no questions to display.");
		} else {
			int index = 1;
			for (Question question : questions) {
				System.out.print(index++ + ") ");
				question.displayQuestion();
				question.displayAnswer();
				System.out.println();
			}
		}
	}

	public void gradeTest() {
		File dir = new File(name + " Responses");
		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("The test doesn't have any responses to grade");
			return;
		}

		File[] files = dir.listFiles(File::isFile);
		System.out.println("Available responses:");
		for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
			System.out.printf("%d) %s%n", i + 1, files[i].getName());
		}

		Scanner scanner = new Scanner(System.in);
		int choice = -1;

		while (choice < 1 || choice > files.length) {
			System.out.print("Select a response to grade: ");
			String line = scanner.nextLine();
			try {
				choice = Integer.parseInt(line);
				if (choice < 1 || choice > files.length) {
					System.out.println("Please enter a valid choice.");
				}
			} catch (NumberFormatException ex) {
				System.out.println("Enter a valid number.");
			}
		}

		File selected = files[choice - 1];
		Response response = SerializationHelper.deserialize(Response.class, selected.getAbsolutePath());

		int score = 0;
		int needGrading = 0;
		List<List<String>> answers = response.getAnswers();
		for (int i = 0; i < answers.size(); i++) {
			if (questions.get(i) instanceof Essay) {
				needGrading++;
				continue;
			}
			List<String> answer = answers.get(i);
			List<String> correctAnswer = questions.get(i).getAnswers();
			boolean isCorrect = true;
			for (int j = 0; j < answer.size(); j++) {
				if (!answer.get(j).equalsIgnoreCase(correctAnswer.get(j))) {
					isCorrect = false;
					break;
				}
			}
			if (isCorrect) {
				score++;
			}
		}
		double grade = ((double) score / questions.size()) * 100;
		System.out.printf("This response received a %.2f%%" + " on the test\n", grade);
		if (needGrading > 0) {
			System.out.println(needGrading + " essay question(s) need to be graded manually.");
		}
	}
}
