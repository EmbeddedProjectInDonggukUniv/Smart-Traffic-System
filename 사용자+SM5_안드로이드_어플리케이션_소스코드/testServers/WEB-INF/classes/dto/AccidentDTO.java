package dto;

public class AccidentDTO {
	private int id;
	private String accident;
	
	public AccidentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AccidentDTO(int id, String accident) {
		super();
		this.id = id;
		this.accident = accident;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccident() {
		return accident;
	}

	public void setAccident(String accident) {
		this.accident = accident;
	}
	
}
