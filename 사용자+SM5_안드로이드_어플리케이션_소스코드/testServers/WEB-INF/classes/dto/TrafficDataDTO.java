package dto;

public class TrafficDataDTO {
	private int id;
	private float speed;
	private String year;
	private String month;
	private String yoil;
	private String hour;
	private String min;
	public TrafficDataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TrafficDataDTO(int id, float speed, String year, String month,
			String yoil, String hour, String min) {
		super();
		this.id = id;
		this.speed = speed;
		this.year = year;
		this.month = month;
		this.yoil = yoil;
		this.hour = hour;
		this.min = min;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYoil() {
		return yoil;
	}
	public void setYoil(String yoil) {
		this.yoil = yoil;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}
	
	
}
