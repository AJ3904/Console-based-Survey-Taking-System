import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.SerializationHelper;

public class Response implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private List<List<String>> response = new ArrayList<>();

	public void addResponse(List<String> response) {
		this.response.add(response);
	}

	public void saveResponse(String name, int size) {
		System.out.println("Survey has been saved.");
		SerializationHelper.serialize(Response.class, this, name + " Responses", Integer.toString(size));
	}
}
