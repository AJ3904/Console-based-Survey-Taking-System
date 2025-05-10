import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Matching extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private List<String> leftItems = new ArrayList<>();
	private List<String> rightItems = new ArrayList<>();
	private int maxKeyLength = 0;

	Matching(String prompt) {
		this.prompt = prompt;
	}

	public void setMatch(String question, String option) {
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
	public void modifyQuestion(Input input) {
		super.modifyQuestion(input);
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
	}
}
