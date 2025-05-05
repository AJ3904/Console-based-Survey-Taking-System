import java.util.ArrayList;
import java.util.List;

public class Response {
	private static final long serialVersionUID = 1L;
	private List<String> response = new ArrayList<String>();

	public List<String> getResponse() {
		return response;
	}

	public void setResponse(List<String> response) {
		this.response = response;
	}

	public void addResponse(String response) {
		this.response.add(response);
	}

	public int getResponseSize(int index) {
		return response.get(index).length();
	}
}
