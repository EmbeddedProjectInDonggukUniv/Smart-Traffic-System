package daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.TrafficDAO;
import dto.TrafficDTO;

public class TrafficDAOImpl implements TrafficDAO {
	
	List<TrafficDTO> list;
	String jdbcUrl = "jdbc:mysql://localhost:3306/embedded";
	String dbId="embedded";
	String dbPass="!rlacl1234";
	
	private static final String SELECT_QUERY_STRING1 ="SELECT * from traffic_tb";
	private static final String INSERT_QUERY_STRING1 = "INSERT INTO traffic_tb (speed) VALUES (?)";
	
	@Override
	public List<TrafficDTO> select() throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		list = new ArrayList<TrafficDTO>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			ps = conn.prepareStatement(SELECT_QUERY_STRING1);
			rs = ps.executeQuery();

			//boolean b = rs.next();

			while (rs.next()) {
				
				TrafficDTO trafficDTO = new TrafficDTO();
				trafficDTO.setId(rs.getInt("id"));			
				trafficDTO.setSpeed(rs.getFloat("speed"));
				
				list.add(trafficDTO);
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
	public float calculateSpeed(List<TrafficDTO> traffics){
		
		float sum=0;
		
		for(int i=0;i<traffics.size();i++){
			sum+=traffics.get(i).getSpeed();
		}
		
		return sum/(float)traffics.size();
	}

	@Override
	public void insert(float speed) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);

			ps = conn.prepareStatement(INSERT_QUERY_STRING1);
			
			ps.setFloat(1, speed);
			
			int n = ps.executeUpdate();
			
		}catch (Exception e) {
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
	
	
	
}
