import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoice extends Question implements Serializable {
	protected List<String> options = new ArrayList<String>();
	protected int noOfOptions = 0;
	private long serialVersionUID = 1L;

	MultipleChoice(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		for (int i = 0; i < options.size(); i++) {
			char label = (char) ('A' + i);
			System.out.print(label + ") " + options.get(i));
			if (i < options.size() - 1) {
				System.out.print(" "); // single space separator
			}
		}
		System.out.println();
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
}
