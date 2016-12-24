package dao;

import dto.FiremanDTO;

public interface FiremanDAO {
	public void update(String oldS,String newS);
	public FiremanDTO select();
	
}
