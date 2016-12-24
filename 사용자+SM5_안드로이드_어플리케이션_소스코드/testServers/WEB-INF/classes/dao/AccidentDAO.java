package dao;

import java.util.List;

import dto.AccidentDTO;

public interface AccidentDAO {
	public List<AccidentDTO> select() throws Exception;
	public int countAccident(List<AccidentDTO> accidents);
	public void insert(String accident);
}
