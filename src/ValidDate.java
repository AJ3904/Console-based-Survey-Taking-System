import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ValidDate extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	public ValidDate(Input input, boolean flag) {
		this.prompt = input.getPrompt("Enter the prompt for your date question:");

		if (flag) {
			System.out.println("Enter the correct answer (in YYYY-MM-DD):");
			answers = Collections.singletonList(input.getDateResponse());
		}
	}

	@Override
	public void modifyQuestion(Input input, boolean flag) {
		super.modifyQuestion(input, flag);
		if (flag) {
			System.out.println("Enter the correct answer (in YYYY-MM-DD):");
			answers = Collections.singletonList(input.getDateResponse());
		}
	}

	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		System.out.println("Date should be entered in the following format: YYYY-MM-DD");
	}

	@Override
	public List<String> getResponse(Input input) {
		List<String> answers = new ArrayList<>();
		String answer = input.getDateResponse();
		answers.add(answer);
		return answers;
	}

	@Override
	public void tabulate(List<List<String>> allAnswers) {
		Map<List<String>, Integer> counts = new LinkedHashMap<>();
		for (List<String> answer : allAnswers) {
			counts.put(answer, counts.getOrDefault(answer, 0) + 1);
		}

		int i = 0;
		int size = counts.size();
		for (Map.Entry<List<String>, Integer> entry : counts.entrySet()) {
			for (String date : entry.getKey()) {
				System.out.println(date);
			}
			System.out.println(entry.getValue());
			if (++i < size) {
				System.out.println();
			}
		}
	}
}
