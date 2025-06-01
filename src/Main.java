import java.io.File;
import java.util.Objects;
import java.util.Scanner;

import utils.SerializationHelper;

public class Main {

	private static final String SURVEY_PATHNAME = "Survey";
	private static final String TEST_PATHNAME = "Test";
	private final Input input;
	private Questionnaire current;

	Main() {
		this.input = new Input();
	}

	public static void main(String[] args) {
		Main handler = new Main();
		handler.run();
	}

	private void run() {
		boolean running = true;
		while (running) {
			showMainMenu();
			int choice = input.getIntInput("Enter your choice: ");
			switch (choice) {
			case 1:
				System.out.println();
				runSurveyMenu();
				System.out.println();
				break;
			case 2:
				System.out.println();
				runTestMenu();
				System.out.println();
				break;
			case 3:
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

	private void runSurveyMenu() {
		boolean running = true;
		while (running) {
			showSurveyMenu();
			int choice = input.getIntInput("Enter your choice: ");
			switch (choice) {
			case 1:
				System.out.println();
				current = new Survey(input);
				System.out.println();
				break;
			case 2:
				System.out.println();
				display("survey");
				break;
			case 3:
				System.out.println();
				load(SURVEY_PATHNAME);
				System.out.println();
				break;
			case 4:
				System.out.println();
				save("survey");
				System.out.println();
				break;
			case 5:
				System.out.println();
				take("survey");
				System.out.println();
				break;
			case 6:
				System.out.println();
				modify("survey");
				System.out.println();
				break;
			case 7:
				System.out.println();
				tabulate("survey");
				System.out.println();
				break;
			case 8:
				running = false;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				System.out.println();
				break;
			}
		}
	}

	private void runTestMenu() {
		boolean running = true;
		while (running) {
			showTestMenu();
			int choice = input.getIntInput("Enter your choice: ");
			switch (choice) {
			case 1:
				System.out.println();
				current = new Test(input);
				System.out.println();
				break;
			case 2:
				System.out.println();
				display("test");
				break;
			case 3:
				System.out.println();
				displayTest();
				System.out.println();
				break;
			case 4:
				System.out.println();
				load(TEST_PATHNAME);
				System.out.println();
				break;
			case 5:
				System.out.println();
				save("test");
				System.out.println();
				break;
			case 6:
				System.out.println();
				take("test");
				System.out.println();
				break;
			case 7:
				System.out.println();
				modify("test");
				System.out.println();
				break;
			case 8:
				System.out.println();
				tabulate("test");
				System.out.println();
				break;
			case 9:
				System.out.println();
				gradeTest();
				System.out.println();
				break;
			case 10:
				running = false;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				System.out.println();
				break;
			}
		}
	}

	private void take(String type) {
		if (current == null) {
			System.out.println("You must have a " + type + " loaded in order to take it");
		} else {
			current.take(input);
		}
	}

	private void modify(String type) {
		if (current == null) {
			System.out.println("You must have a " + type + " loaded in order to modify it");
		} else {
			current.modifyQuestion(input, type.equalsIgnoreCase("test"));
			System.out.println("Your " + type + " has been modified.");
		}
	}

	private void load(String pathName) {
		String type = pathName.toLowerCase();
		File dir = new File(pathName);
		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("You don't have any " + type + "s saved");
			return;
		}

		File[] files = dir.listFiles(File::isFile);
		System.out.println("Available " + type + "s:");
		for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
			System.out.printf("%d) %s%n", i + 1, files[i].getName());
		}

		Scanner scanner = new Scanner(System.in);
		int choice = -1;

		while (choice < 1 || choice > files.length) {
			System.out.print("Select a " + type + " to load: ");
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
		if (type.equals("test")) {
			current = SerializationHelper.deserialize(Test.class, selected.getAbsolutePath());
		} else {
			current = SerializationHelper.deserialize(Survey.class, selected.getAbsolutePath());
		}
		System.out.println("Loaded " + type + ": " + selected.getName());
	}

	private void save(String type) {
		if (current == null) {
			System.out.println("You must have a " + type + " loaded in order to save it.");
		} else {
			current.save();
		}
	}

	private void display(String type) {
		if (current == null) {
			System.out.println("You must have a " + type + " loaded in order to display it.");
		} else {
			current.display();
		}
	}

	private void tabulate(String type) {
		if (current == null) {
			System.out.println("You must have a " + type + " loaded in order to tabulate it.");
		} else {
			current.tabulate();
		}
	}

	private void gradeTest() {
		if (current == null) {
			load(TEST_PATHNAME);
		}
		Test test = (Test) current;
		test.gradeTest();
	}

	private void displayTest() {
		if (current == null) {
			System.out.println("You must have a test loaded in order to display it.");
		} else {
			Test test = (Test) current;
			test.displayWithAnswers();
		}
	}

	private void showSurveyMenu() {
		System.out.println("1) Create a new Survey");
		System.out.println("2) Display an existing Survey");
		System.out.println("3) Load an existing Survey");
		System.out.println("4) Save the current Survey");
		System.out.println("5) Take the current Survey");
		System.out.println("6) Modify the current Survey");
		System.out.println("7) Tabulate a survey");
		System.out.println("8) Quit");
	}

	private void showTestMenu() {
		System.out.println("1) Create a new Test");
		System.out.println("2) Display an existing Test without correct answers");
		System.out.println("3) Display an existing Test with correct answers");
		System.out.println("4) Load an existing Test");
		System.out.println("5) Save the current Test");
		System.out.println("6) Take the current Test");
		System.out.println("7) Modify the current Test");
		System.out.println("8) Tabulate a Test");
		System.out.println("9) Grade a Test");
		System.out.println("10) Return to the previous menu");
	}

	private void showMainMenu() {
		System.out.println("1) Survey");
		System.out.println("2) Test");
		System.out.println("3) Exit");
	}
}