import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrueOrFalse extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	TrueOrFalse(Input input) {
		this.prompt = input.getPrompt("Enter the prompt for your True/False question:");
	}

	@Override
	public void displayQuestion() {
		System.out.println(prompt);
		System.out.println("T/F");
	}

	@Override
	public List<String> getResponse(Input input) {
		List<String> answers = new ArrayList<>();
		String answer = input.getChoice("", "T", "F");
		answers.add(answer);
		return answers;
	}

	@Override
	public void tabulate(List<List<String>> allAnswers) {
		Map<String, Integer> counts = new LinkedHashMap<>();
		counts.put("True", 0);
		counts.put("False", 0);

		for (List<String> answer : allAnswers) {
			for (String response : answer) {
				if (response.equals("T")) {
					counts.put("True", counts.get("True") + 1);
				} else if (response.equals("F")) {
					counts.put("False", counts.get("False") + 1);
				}
			}
		}

		System.out.println("True: " + counts.get("True"));
		System.out.println("False: " + counts.get("False"));
	}
}
