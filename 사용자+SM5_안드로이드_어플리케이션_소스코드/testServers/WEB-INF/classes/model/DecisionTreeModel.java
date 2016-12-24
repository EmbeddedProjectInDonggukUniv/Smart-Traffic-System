package model;

public class DecisionTreeModel {
	private String year;
	private String month;
	private String yoil;
	private String hour;
	private String min;
	public DecisionTreeModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DecisionTreeModel(String year, String month, String yoil,
			String hour, String min) {
		super();
		this.year = year;
		this.month = month;
		this.yoil = yoil;
		this.hour = hour;
		this.min = min;
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
