import java.io.File;
import java.io.Serial;

import utils.SerializationHelper;
import utils.TimeHelper;

public class Survey extends Questionnaire {
	@Serial
	private static final long serialVersionUID = 1L;
	private static final String basePath = "Survey" + File.separator;

	public Survey(Input input) {
		this.name = input.getNonEmptyResponse("Enter a name for the survey: ", "Name") + " "
				+ TimeHelper.getUniqueTimeStamp();

		boolean returnToMain = false;
		while (!returnToMain) {
			showQuestionMenu();
			int choice = input.getIntInput("Enter your choice: ");
			switch (choice) {
			case 1:
				questions.add(new TrueOrFalse(input, false));
				break;
			case 2:
				questions.add(new MultipleChoice(input, false));
				break;
			case 3:
				questions.add(new ShortAnswer(input, false));
				break;
			case 4:
				questions.add(new Essay(input, false));
				break;
			case 5:
				questions.add(new ValidDate(input, false));
				break;
			case 6:
				questions.add(new Matching(input, false));
				break;
			case 7:
				returnToMain = true;
				break;
			default:
				System.out.println("Invalid choice. Try again.");
				break;
			}
		}
	}

	@Override
	public void save() {
		System.out.println("Survey has been saved.");
		SerializationHelper.serialize(Survey.class, this, basePath, name);
	}
}