package dao;

import java.util.List;

import model.DecisionTreeModel;
import dto.TrafficDataDTO;

public interface TrafficDataDAO {
	public List<TrafficDataDTO> select(DecisionTreeModel dtm) throws Exception;
	public float calculateSpeed(List<TrafficDataDTO> traffics);
	public void makeRDD(float speed,DecisionTreeModel dtm);
	public DecisionTreeModel decisionTree(String year, String month, String day,String hour, String min);

}
