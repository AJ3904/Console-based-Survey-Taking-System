import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.SerializationHelper;

public class Response implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private static final String basePath = " Responses" + File.separator;
	private final List<List<String>> response = new ArrayList<>();

	public void addResponse(List<String> response) {
		this.response.add(response);
	}

	public List<List<String>> getAnswers() {
		return response;
	}

	public void saveResponse(String name, String fileName) {
		System.out.println("Response has been saved.");
		SerializationHelper.serialize(Response.class, this, name + basePath, fileName);
	}
}
