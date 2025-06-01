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
				currentSurvey = new Survey(input);
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
				System.out.println();
				tabulateSurvey();
				System.out.println();
				break;
			case 8:
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
			currentSurvey.modifyQuestion(input);
			System.out.println("Your survey has been modified.");
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

	private void tabulateSurvey() {
		if (currentSurvey == null) {
			System.out.println("You must have a survey loaded in order to tabulate it.\n");
		} else {
			currentSurvey.tabulate();
		}
	}

	private void showMenu1() {
		System.out.println("1) Create a new Survey");
		System.out.println("2) Display an existing Survey");
		System.out.println("3) Load an existing Survey");
		System.out.println("4) Save the current Survey");
		System.out.println("5) Take the current Survey");
		System.out.println("6) Modify the current Survey");
		System.out.println("7) Tabulate a survey");
		System.out.println("8) Quit");
	}
}
