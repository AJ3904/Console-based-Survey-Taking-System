import java.io.File;
import java.util.Objects;
import java.util.Scanner;

import utils.SerializationHelper;

public class SurveyHandler {

	private final Input input;
	private Survey currentSurvey;

	SurveyHandler() {
		this.input = new Input();
	}

	public static void main(String[] args) {
		SurveyHandler handler = new SurveyHandler();
		handler.run();
	}

	private void run() {
		boolean running = true;
		while (running) {
			showMenu1();
			int choice = input.getIntInput("Enter your choice: ");
			switch (choice) {
			case 1:
				System.out.println();
				createSurvey();
				System.out.println();
				break;
			case 2:
				System.out.println();
				displaySurvey();
				break;
			case 3:
				System.out.println();
				loadSurvey();
				System.out.println();
				break;
			case 4:
				System.out.println();
				saveSurvey();
				System.out.println();
				break;
			case 5:
				System.out.println();
				takeSurvey();
				System.out.println();
				break;
			case 6:
				System.out.println();
				modifySurvey();
				System.out.println();
				break;
			case 7:
				running = false;
				System.out.println("Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				System.out.println();
				break;
			}
		}
	}

	private void takeSurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to take it");
		} else {
			currentSurvey.takeSurvey(input);
		}
	}

	private void modifySurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to modify it");
		} else {
			displaySurvey();
			int choice = input.getIntInput("Enter the number of the question you want to modify: ");
			while (choice <= 0 || choice > currentSurvey.getQuestions().size()) {
				choice = input.getIntInput("Enter a valid question number");
			}
			Question question = currentSurvey.getQuestions().get(choice - 1);

			String choice2 = input.getChoice("Do you wish to modify the prompt? [Y/N]", "Y", "N");
			if (choice2.equalsIgnoreCase("Y")) {
				String newPrompt = input.getPrompt("Enter new prompt:");
				question.setPrompt(newPrompt);
			}

			if (question instanceof MultipleChoice && !(question instanceof TrueOrFalse)) {
				modifyMultipleChoice((MultipleChoice) question);
			} else if (question instanceof ShortAnswer) {
				question.setPrompt(question.getPrompt() + " (In under " + ((ShortAnswer) question).getCharacterLimit()
						+ " characters)");
				modifyShortAnswer((ShortAnswer) question);
			} else if (question instanceof Matching) {
				modifyMatching((Matching) question);
			}
			System.out.println("Your survey has been modified.");
		}
	}

	private void modifyMatching(Matching question) {
		while (true) {
			String choice = input.getChoice("Do you wish to modify the matching pairs? [Y/N]", "Y", "N");

			if (choice.equalsIgnoreCase("Y")) {
				String leftItem;
				question.displayLeftItems();
				int lindex = input.getIntInput("Enter left-item you wish to modify: ");
				while (lindex <= 0 || lindex > question.getMatchCount()) {
					lindex = input.getIntInput("Please enter a valid number: ");
				}
				leftItem = input.getNonEmptyResponse("Enter new choice: ", "Choice");

				String rightItem;
				question.displayRightItems();
				int rindex = input.getIntInput("Enter right-item you wish to modify: ");
				while (rindex <= 0 || rindex > question.getMatchCount()) {
					rindex = input.getIntInput("Please enter a valid number: ");
				}
				rightItem = input.getNonEmptyResponse("Enter new choice: ", "Choice");
				question.modifyMatch(lindex - 1, leftItem, rindex - 1, rightItem);
			} else {
				break;
			}
		}
	}

	private void modifyShortAnswer(ShortAnswer question) {
		String choice = input.getChoice("Do you wish to modify the character limit? [Y/N]", "Y", "N");

		if (choice.equalsIgnoreCase("Y")) {
			int limit = input.getIntInput("Enter new character limit for responses: ");
			String prompt = question.getPrompt();

			prompt = prompt.replaceAll("\\s*\\(In under \\d+ characters\\)", "");

			prompt = prompt + " (In under " + limit + " characters)";
			question.setPrompt(prompt);
			question.setMaxCharacters(limit);
		}
	}

	private void modifyMultipleChoice(MultipleChoice question) {
		while (true) {
			String choice = input.getChoice("Do you wish to modify choices? [Y/N]", "Y", "N");

			if (choice.equalsIgnoreCase("Y")) {
				question.displayOptions(true);
				int index = input.getIntInput("Enter choice you wish to modify: ");
				while (index <= 0 || index > question.getOptions().size()) {
					index = input.getIntInput("Please enter a valid number: ");
				}
				String newChoice = input.getNonEmptyResponse("Enter new choice: ", "Choice");
				question.modifyOption(index - 1, newChoice);
			} else {
				break;
			}
		}

	}

	private void loadSurvey() {
		File dir = new File("Survey");
		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("You don't have any surveys saved");
			return;
		}

		File[] files = dir.listFiles(File::isFile);
		System.out.println("Available surveys:");
		for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
			System.out.printf("%d) %s%n", i + 1, files[i].getName());
		}

		Scanner scanner = new Scanner(System.in);
		int choice = -1;

		while (choice < 1 || choice > files.length) {
			System.out.print("Select a survey to load: ");
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
		currentSurvey = SerializationHelper.deserialize(Survey.class, selected.getAbsolutePath());
		System.out.println("Loaded survey: " + selected.getName());
	}

	private void saveSurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to save it.");
		} else {
			currentSurvey.saveSurvey();
		}
	}

	private void displaySurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to display it.\n");
		} else {
			currentSurvey.displaySurvey();
		}
	}

	private void createSurvey() {
		currentSurvey = new Survey();
		boolean returnToMain = false;
		currentSurvey.setName(input.getNonEmptyResponse("Enter a name for the survey: ", "Name"));

		while (!returnToMain) {
			showMenu2();
			int choice = input.getIntInput("Enter your choice: ");
			switch (choice) {
			case 1:
				addTrueFalseQuestion();
				break;
			case 2:
				addMultipleChoiceQuestion();
				break;
			case 3:
				addShortAnswerQuestion();
				break;
			case 4:
				addEssayQuestion();
				break;
			case 5:
				addDateQuestion();
				break;
			case 6:
				addMatchingQuestion();
				break;
			case 7:
				returnToMain = true;
				break;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		}
	}

	private void addMatchingQuestion() {
		String prompt = input.getPrompt("Enter the prompt for your matching question:");
		Matching question = new Matching(prompt);

		int numberOfMatches = input.getIntInput("Enter the number of matches: ");
		while (numberOfMatches <= 1) {
			System.out.println("A matching question must have more than one match. Please enter a valid number.");
			numberOfMatches = input.getIntInput("Enter the number of matches: ");
		}

		for (int i = 1; i <= numberOfMatches; i++) {
			String leftItem = input.getNonEmptyResponse("Enter item #" + i + " for the left-hand side: ", "Item");
			String rightItem = input.getNonEmptyResponse("Enter item #" + i + " for the right-hand side: ", "Item");
			question.setMatch(leftItem, rightItem);
		}

		currentSurvey.addQuestion(question);
		System.out.println("Your matching question has been added.\n");
	}

	private void addDateQuestion() {
		String prompt = input.getPrompt("Enter the prompt for your date question:");
		ValidDate question = new ValidDate(prompt);
		currentSurvey.addQuestion(question);
		System.out.println("Your date question has been added.\n");
	}

	private void addEssayQuestion() {
		String prompt = input.getPrompt("Enter the prompt for your essay question:");
		int maxOptions = input.getIntInput("Enter the number of responses: ");
		while (maxOptions < 1) {
			System.out.println("You must allow at least 1 response. Please try again.");
			maxOptions = input.getIntInput("Enter the number of responses: ");
		}
		prompt = prompt + " (Give atleast " + maxOptions + " points)";
		Essay question = new Essay(prompt);
		question.setNoOfAnswersAllowed(maxOptions);
		currentSurvey.addQuestion(question);
		System.out.println("Your essay question has been added.\n");
	}

	private void addShortAnswerQuestion() {
		String prompt = input.getPrompt("Enter the prompt for your short-answer question:");
		int maxChars = input.getIntInput("Enter the maximum response length (in characters): ");
		while (true) {
			if (0 > maxChars) {
				System.out.println("Maximum response length must be a positive integer.");
			} else {
				break;
			}
		}
		prompt = prompt + " (In under " + maxChars + " characters)";

		ShortAnswer question = new ShortAnswer(prompt, maxChars);
		currentSurvey.addQuestion(question);
		System.out.println("Your short-answer question has been added.\n");
	}

	private void addMultipleChoiceQuestion() {
		String prompt = input.getPrompt("Enter the prompt for your multiple-choice question:");

		int maxOptions = input.getIntInput("Enter the number of selectable options: ");
		while (maxOptions < 1) {
			System.out.println("You must allow at least 1 option. Please try again.");
			maxOptions = input.getIntInput("Enter the number of selectable options: ");
		}
		MultipleChoice question = new MultipleChoice(prompt);

		int noOfOptions = input.getIntInput("Enter the number of options: ");
		while (noOfOptions < maxOptions) {
			System.out.println(
					"Number of options must at least equal the maximum number of selectable options. Please try again.");
			noOfOptions = input.getIntInput("Enter the number of selectable options: ");
		}
		question.setNoOfAnswersAllowed(maxOptions);

		for (int i = 0; i < noOfOptions; i++) {
			String option = input.getNonEmptyResponse("Enter choice #" + (i + 1) + ":", "Choice");
			question.setOption(option);
		}

		currentSurvey.addQuestion(question);
		System.out.println("Your multiple-choice question has been added.\n");
	}

	private void addTrueFalseQuestion() {
		String prompt = input.getPrompt("Enter the prompt for your True/False question:");
		TrueOrFalse question = new TrueOrFalse(prompt);
		currentSurvey.addQuestion(question);
		System.out.println("Your True/False question has been added.\n");
	}

	private void showMenu1() {
		System.out.println("1) Create a new Survey");
		System.out.println("2) Display an existing Survey");
		System.out.println("3) Load an existing Survey");
		System.out.println("4) Save the current Survey");
		System.out.println("5) Take the current Survey");
		System.out.println("6) Modify the current Survey");
		System.out.println("7) Quit");
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
}
