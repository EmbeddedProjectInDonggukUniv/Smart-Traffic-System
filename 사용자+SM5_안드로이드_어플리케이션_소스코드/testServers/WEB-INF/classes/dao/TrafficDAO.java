package dao;

import java.util.List;

import dto.TrafficDTO;

public interface TrafficDAO {
	public List<TrafficDTO> select() throws Exception;
	public float calculateSpeed(List<TrafficDTO> traffics);
	public void insert(float speed);
}
