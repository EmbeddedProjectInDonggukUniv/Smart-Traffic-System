package daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DecisionTreeModel;
import dao.TrafficDataDAO;
import dto.TrafficDataDTO;

public class TrafficDataDAOImpl implements TrafficDataDAO {

	List<TrafficDataDTO> list;
	String jdbcUrl = "jdbc:mysql://localhost:3306/embedded";
	String dbId = "embedded";
	String dbPass = "!rlacl1234";

	private static final String SELECT_QUERY_STRING1 = "SELECT * FROM traffic_data_tb WHERE month = ? and yoil = ? and hour = ? and min = ?";
	private static final String INSERT_QUERY_STRING1 = "INSERT INTO traffic_data_tb (speed,year,month,yoil,hour,min) VALUES (?,?,?,?,?,?)";

	@Override
	public float calculateSpeed(List<TrafficDataDTO> traffics) {
		// TODO Auto-generated method stub
		float sum=0;
		
		for(int i=0;i<traffics.size();i++){
			sum+=traffics.get(i).getSpeed();
		}
		
		return sum/(float)traffics.size();
	}

	@Override
	public List<TrafficDataDTO> select(DecisionTreeModel dtm) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		list = new ArrayList<TrafficDataDTO>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			ps = conn.prepareStatement(SELECT_QUERY_STRING1);
			
			//SELECT * FROM traffic_data_tb WHERE yoil = ? and hour = ? and min = ?
			ps.setString(1, dtm.getMonth());
			ps.setString(2, dtm.getYoil());
			ps.setString(3, dtm.getHour());
			ps.setString(4, dtm.getMin());
			
			rs = ps.executeQuery();

			//boolean b = rs.next();

			while (rs.next()) {
				
				TrafficDataDTO trafficDataDTO = new TrafficDataDTO();
				trafficDataDTO.setId(rs.getInt("id"));			
				trafficDataDTO.setSpeed(rs.getFloat("speed"));
				trafficDataDTO.setYear(rs.getString("year"));
				trafficDataDTO.setMonth(rs.getString("month"));
				trafficDataDTO.setYoil(rs.getString("yoil"));
				trafficDataDTO.setHour(rs.getString("hour"));
				trafficDataDTO.setMin(rs.getString("min"));
					
				list.add(trafficDataDTO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException sqle) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}

		}
		return list;
	}

	@Override
	public void makeRDD(float speed, DecisionTreeModel dtm) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);

			ps = conn.prepareStatement(INSERT_QUERY_STRING1);

			ps.setFloat(1, speed);
			ps.setString(2, dtm.getYear());
			ps.setString(3, dtm.getMonth());
			ps.setString(4, dtm.getYoil());
			ps.setString(5, dtm.getHour());
			ps.setString(6, dtm.getMin());

			int n = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} // end try~catch

		finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
		}

	}

	@Override
	public DecisionTreeModel decisionTree(String year, String month,
			String day, String hour, String min) {
		// TODO Auto-generated method stub
		int totalOfDay = 0;

		int yearData = Integer.parseInt(year);
		int monthData = Integer.parseInt(month);
		int dayData = Integer.parseInt(day);

		/* 요일을 벡터화 시키는 부분 */
		totalOfDay += (yearData - 1900) * 365;
		totalOfDay += (yearData - 1900) / 4;

		if (((yearData % 4 == 0) && (yearData % 100 != 0))
				|| (yearData % 400 == 0)) {
			if ((monthData == 1) || (monthData == 2))
				totalOfDay += -1;
		}

		switch (monthData) {
		case 1:
			totalOfDay += dayData;
			break;
		case 2:
			totalOfDay += 31 + dayData;
			break;
		case 3:
			totalOfDay += 31 + 28 + dayData;
			break;
		case 4:
			totalOfDay += 31 + 28 + 31 + dayData;
			break;
		case 5:
			totalOfDay += 31 + 28 + 31 + 30 + dayData;
			break;
		case 6:
			totalOfDay += 31 + 28 + 31 + 30 + 31 + dayData;
			break;
		case 7:
			totalOfDay += 31 + 28 + 31 + 30 + 31 + 30 + dayData;
			break;
		case 8:
			totalOfDay += 31 + 28 + 31 + 30 + 31 + 30 + 31 + dayData;
			break;
		case 9:
			totalOfDay += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + dayData;
			break;
		case 10:
			totalOfDay += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + dayData;
			break;
		case 11:
			totalOfDay += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31
					+ dayData;
			break;
		case 12:
			totalOfDay += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30
					+ dayData;
			break;

		}

		int yoilVector = totalOfDay % 7;

		/* 분을 벡터화 시키는 부분 */

		int minData = Integer.parseInt(min);
		int minVector = 0;
		if (minData >= 0 && minData <= 4) {
			minVector = 0;
		} else if (minData >= 5 && minData <= 9) {
			minVector = 1;
		} else if (minData >= 10 && minData <= 14) {
			minVector = 2;
		} else if (minData >= 15 && minData <= 19) {
			minVector = 3;
		} else if (minData >= 20 && minData <= 24) {
			minVector = 4;
		} else if (minData >= 25 && minData <= 29) {
			minVector = 5;
		} else if (minData >= 30 && minData <= 34) {
			minVector = 6;
		} else if (minData >= 35 && minData <= 39) {
			minVector = 7;
		} else if (minData >= 40 && minData <= 44) {
			minVector = 8;
		} else if (minData >= 45 && minData <= 49) {
			minVector = 9;
		} else if (minData >= 50 && minData <= 54) {
			minVector = 10;
		} else if (minData >= 55 && minData <= 59) {
			minVector = 11;
		}
		
		int hourData= Integer.parseInt(hour);

		DecisionTreeModel dtm = new DecisionTreeModel(
				Integer.toString(yearData), Integer.toString(monthData),
				Integer.toString(yoilVector), Integer.toString(hourData), Integer.toString(minVector));

		return dtm;
	}

}
