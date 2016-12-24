package dto;

public class TrafficDTO {
	private int id;
	private float speed;
	
	public TrafficDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TrafficDTO(int id, float speed) {
		super();
		this.id = id;
		this.speed = speed;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	
	
}
