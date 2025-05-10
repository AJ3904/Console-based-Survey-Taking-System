import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Input {
	private final Scanner scanner;
	private final Validator validator;

	public Input() {
		scanner = new Scanner(System.in);
		validator = new Validator();
	}

	public int getIntInput(String prompt) {
		System.out.print(prompt);
		while (!scanner.hasNextInt()) {
			System.out.print("Please enter a valid number: ");
			scanner.next();
		}
		int value = scanner.nextInt();
		scanner.nextLine();
		return value;
	}

	public String getPrompt(String question) {
		String prompt;
		while (true) {
			System.out.println(question);
			prompt = scanner.nextLine().trim();
			if (prompt.isEmpty()) {
				System.out.println("Question cannot be empty.");
			} else if (prompt.matches("[0-9]+")) {
				System.out.println("Question cannot contain only numbers.");
			} else if (prompt.matches("[-+*/%=]")) {
				System.out.println("Question cannot contain '-', '+', '*', '/', '%', or '=' characters.");
			} else {
				return prompt;
			}
		}
	}

	public String getChoice(String prompt, String option1, String option2) {
		while (true) {
			if (!prompt.isEmpty()) {
				System.out.println(prompt);
			}
			String answer = scanner.nextLine().trim();
			if (answer.equalsIgnoreCase(option1) || answer.equalsIgnoreCase(option2)) {
				return answer;
			} else {
				System.out.println("Enter a valid response (" + option1 + " or " + option2 + ").");
			}
		}
	}

	public String getNonEmptyResponse(String prompt, String keyword) {
		while (true) {
			System.out.print(prompt);
			String response = scanner.nextLine().trim();
			if (response.isEmpty()) {
				System.out.println(keyword + " cannot be empty.");
			} else {
				return response;
			}
		}
	}

	public List<String> getMultipleChoiceResponse(int noOfAnswersAllowed, int noOfChoices) {
		List<String> responses = new ArrayList<>();
		for (int i = 0; i < noOfAnswersAllowed; i++) {
			String response = scanner.nextLine().trim();
			if (validator.isValidMultipleChoiceOption(response, noOfChoices)) {
				responses.add(response);
			} else {
				i--;
				System.out.println(response + " is not a valid choice.");
			}
		}
		return responses;
	}

	public String getShortAnswerResponse(int characterLimit) {
		while (true) {
			String response = scanner.nextLine().trim();
			if (validator.isValidShortAnswer(response, characterLimit)) {
				return response;
			} else {
				System.out.println("Your response is invalid.");
				System.out.println("Current response length: " + response.length());
			}
		}
	}

	public List<String> getEssayResponse(int noOfAnswersAllowed) {
		List<String> responses = new ArrayList<>();
		for (int i = 0; i < noOfAnswersAllowed; i++) {
			String response = scanner.nextLine().trim();
			if (response.isEmpty()) {
				System.out.println("Your response cannot be empty.");
				i--;
			} else {
				responses.add(response);
			}
		}
		return responses;
	}

	public String getDateResponse() {
		while (true) {
			String response = scanner.nextLine().trim();
			if (validator.isValidDate(response)) {
				return response;
			}
		}
	}

	public List<String> getMatchingResponse(int matchCount) {
		List<String> responses = new ArrayList<>();
		System.out.println("Enter left option with the matching right option separated by a space");
		for (int i = 0; i < matchCount; i++) {
			String response = scanner.nextLine().trim();
			if (validator.isValidMatchingPair(response, matchCount)) {
				responses.add(response);
			} else {
				i--;
				System.out.println("Your response is invalid.");
			}
		}
		return responses;
	}
}
