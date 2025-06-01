import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MultipleChoice extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private final String suffix;
	protected List<String> options = new ArrayList<>();
	protected int noOfOptions;

	MultipleChoice(Input input) {
		this.prompt = input.getPrompt("Enter the prompt for your multiple-choice question:");

		int maxOptions = input.getIntInput("Enter the number of selectable options: ");
		while (maxOptions < 1) {
			System.out.println("You must allow at least 1 option. Please try again.");
			maxOptions = input.getIntInput("Enter the number of selectable options: ");
		}
		this.noOfAnswersAllowed = maxOptions;
		suffix = " (Select up to " + maxOptions + " options)";
		this.prompt = this.prompt + suffix;

		int noOfOptions = input.getIntInput("Enter the number of options: ");
		while (noOfOptions < maxOptions) {
			System.out.println(
					"Number of options must at least equal the maximum number of selectable options. Please try again.");
			noOfOptions = input.getIntInput("Enter the number of selectable options: ");
		}
		this.noOfOptions = noOfOptions;

		for (int i = 0; i < noOfOptions; i++) {
			String option = input.getNonEmptyResponse("Enter choice #" + (i + 1) + ": ", "Choice");
			options.add(option);
		}
	}

	@Override
	public void setPrompt(String prompt) {
		this.prompt = prompt + suffix;
	}

	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		displayOptions(false);
		System.out.println();
	}

	public void displayOptions(boolean numbered) {
		if (numbered) {
			for (int i = 0; i < options.size(); i++) {
				System.out.print(i + 1 + ") " + options.get(i));
				if (i < options.size() - 1) {
					System.out.print("\t");
				}
			}
			System.out.println();
		} else {
			for (int i = 0; i < options.size(); i++) {
				char label = (char) ('A' + i);
				System.out.print(label + ") " + options.get(i));
				if (i < options.size() - 1) {
					System.out.print("\t");
				}
			}
		}
	}

	public List<String> getOptions() {
		return options;
	}

	public void modifyOption(int index, String option) {
		options.set(index, option);
	}

	@Override
	public List<String> getResponse(Input input) {
		return input.getMultipleChoiceResponse(noOfAnswersAllowed, noOfOptions);
	}

	@Override
	public void modifyQuestion(Input input) {
		super.modifyQuestion(input);
		while (true) {
			String choice = input.getChoice("Do you wish to modify choices? [Y/N]", "Y", "N");

			if (choice.equalsIgnoreCase("Y")) {
				displayOptions(true);
				int index = input.getIntInput("Enter choice you wish to modify: ");
				while (index <= 0 || index > getOptions().size()) {
					index = input.getIntInput("Please enter a valid option: ");
				}
				String newChoice = input.getNonEmptyResponse("Enter new choice: ", "Choice");
				modifyOption(index - 1, newChoice);
			} else {
				break;
			}
		}
	}

	@Override
	public void tabulate(List<List<String>> allAnswers) {
		Map<String, Integer> counts = new TreeMap<>();

		for (List<String> answer : allAnswers) {
			for (String data : answer) {
				counts.put(data, counts.getOrDefault(data, 0) + 1);
			}
		}

		for (char option = 'A'; option <= 'Z'; option++) {
			String key = String.valueOf(option);
			if (counts.containsKey(key)) {
				System.out.println(key + ": " + counts.get(key));
			}
		}
	}
}
