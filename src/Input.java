import java.util.Scanner;

public class Input {
	private final Scanner scanner;

	public Input() {
		scanner = new Scanner(System.in);
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

	public String getYesOrNo(String prompt) {
		while (true) {
			System.out.println(prompt);
			String answer = scanner.nextLine().trim();
			if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N")) {
				return answer;
			} else {
				System.out.println("Enter a valid response (Y or N).");
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
}
