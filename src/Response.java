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
	private List<List<String>> response = new ArrayList<>();

	public void addResponse(List<String> response) {
		this.response.add(response);
	}

	public void displayResponse() {
		System.out.println("\nResponse:");
		for (int i = 0; i < response.size(); i++) {
			System.out.print((i + 1) + ") ");
			for (int j = 0; j < response.get(i).size(); j++) {
				System.out.println(response.get(i).get(j));
			}
			if (i != response.size() - 1) {
				System.out.println();
			}
		}
	}

	public void saveResponse(String name, String fileName) {
		System.out.println("Response has been saved.");
		SerializationHelper.serialize(Response.class, this, name + basePath, fileName);
	}
}
