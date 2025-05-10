import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoice extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	protected List<String> options = new ArrayList<>();
	protected int noOfOptions = 0;
	private String suffix;

	MultipleChoice(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public void setPrompt(String prompt) {
		this.prompt = prompt + suffix;
	}

	@Override
	public void setNoOfAnswersAllowed(int noOfAnswersAllowed) {
		this.noOfAnswersAllowed = noOfAnswersAllowed;
		suffix = " (Select up to " + noOfAnswersAllowed + " option" + ((noOfAnswersAllowed > 1) ? "s" : "") + ")";
		updatePrompt();
	}

	private void updatePrompt() {
		if (!prompt.contains("(Select up to ")) {
			prompt = prompt + suffix;
		}
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

	public void setOption(String option) {
		options.add(option);
		this.noOfOptions++;
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
}
