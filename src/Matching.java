import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Matching extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private final List<String> leftItems = new ArrayList<>();
	private final List<String> rightItems = new ArrayList<>();
	private int maxKeyLength = 0;

	Matching(Input input, boolean flag) {
		this.prompt = input.getPrompt("Enter the prompt for your matching question:");

		int numberOfMatches = input.getIntInput("Enter the number of matches: ");
		while (numberOfMatches <= 1) {
			System.out.println("A matching question must have more than one match. Please enter a valid number.");
			numberOfMatches = input.getIntInput("Enter the number of matches: ");
		}

		for (int i = 1; i <= numberOfMatches; i++) {
			String leftItem = input.getNonEmptyResponse("Enter item #" + i + " for the left-hand side: ", "Item");
			String rightItem = input.getNonEmptyResponse("Enter item #" + i + " for the right-hand side: ", "Item");
			this.setMatch(leftItem, rightItem);
		}

		if (flag) {
			displayQuestion();
			System.out.println("Enter the correct answer:");
			answers = input.getMatchingResponse(numberOfMatches);
		}
	}

	private void setMatch(String question, String option) {
		leftItems.add(question);
		rightItems.add(option);
		this.maxKeyLength = Math.max(maxKeyLength, question.length());
	}

	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		int fieldWidth = maxKeyLength + 4;
		String format = "%c) %-" + fieldWidth + "s %d) %s%n";
		for (int i = 0; i < leftItems.size(); i++) {
			char letter = (char) ('A' + i);
			int number = i + 1;
			System.out.printf(format, letter, leftItems.get(i), number, rightItems.get(i));
		}
	}

	@Override
	public List<String> getResponse(Input input) {
		return input.getMatchingResponse(getMatchCount());
	}

	public void displayLeftItems() {
		for (int i = 0; i < leftItems.size(); i++) {
			System.out.println((i + 1) + ") " + leftItems.get(i));
		}
		System.out.println();
	}

	public void displayRightItems() {
		for (int i = 0; i < rightItems.size(); i++) {
			System.out.println((i + 1) + ") " + rightItems.get(i));
		}
		System.out.println();
	}

	private void modifyMatch(int leftIndex, String leftItem, int rightIndex, String rightItem) {
		leftItems.set(leftIndex, leftItem);
		rightItems.set(rightIndex, rightItem);
	}

	public int getMatchCount() {
		return leftItems.size();
	}

	@Override
	public void modifyQuestion(Input input, boolean flag) {
		super.modifyQuestion(input, flag);
		while (true) {
			String choice = input.getChoice("Do you wish to modify the matching pairs? [Y/N]", "Y", "N");

			if (choice.equalsIgnoreCase("Y")) {
				String leftItem;
				displayLeftItems();
				int lindex = input.getIntInput("Enter left-item you wish to modify: ");
				while (lindex <= 0 || lindex > getMatchCount()) {
					lindex = input.getIntInput("Please enter a valid number: ");
				}
				leftItem = input.getNonEmptyResponse("Enter new choice: ", "Choice");

				String rightItem;
				displayRightItems();
				int rindex = input.getIntInput("Enter right-item you wish to modify: ");
				while (rindex <= 0 || rindex > getMatchCount()) {
					rindex = input.getIntInput("Please enter a valid number: ");
				}
				rightItem = input.getNonEmptyResponse("Enter new choice: ", "Choice");
				modifyMatch(lindex - 1, leftItem, rindex - 1, rightItem);
			} else {
				break;
			}
		}

		if (flag) {
			displayQuestion();
			System.out.println("Enter the correct answer:");
			answers = input.getMatchingResponse(leftItems.size());
		}
	}

	@Override
	public void tabulate(List<List<String>> allAnswers) {
		Map<List<String>, Integer> permutationCounts = new LinkedHashMap<>();
		for (List<String> answer : allAnswers) {
			permutationCounts.put(answer, permutationCounts.getOrDefault(answer, 0) + 1);
		}

		int i = 0;
		int size = permutationCounts.size();
		for (Map.Entry<List<String>, Integer> entry : permutationCounts.entrySet()) {
			System.out.println(entry.getValue());
			for (String data : entry.getKey()) {
				System.out.println(data);
			}
			if (++i < size) {
				System.out.println();
			}
		}
	}
}
