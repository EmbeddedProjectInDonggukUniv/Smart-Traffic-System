package daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.AccidentDAO;
import dto.AccidentDTO;

public class AccidentDAOImpl implements AccidentDAO{
	
	List<AccidentDTO> list;
	String jdbcUrl = "jdbc:mysql://localhost:3306/embedded";
	String dbId="embedded";
	String dbPass="!rlacl1234";
	
	private static final String SELECT_QUERY_STRING1 ="SELECT * from accident_tb";
	private static final String INSERT_QUERY_STRING1 = "INSERT INTO accident_tb (accident) VALUES (?)";
	
	@Override
	public List<AccidentDTO> select() throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		list = new ArrayList<AccidentDTO>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl,dbId,dbPass);
			ps =conn.prepareStatement(SELECT_QUERY_STRING1);
			rs =ps.executeQuery();
			
			while(rs.next()){
				AccidentDTO accidentDTO = new AccidentDTO();
				accidentDTO.setId(rs.getInt("id"));
				accidentDTO.setAccident(rs.getString("accident"));
				
				list.add(accidentDTO);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
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
	public int countAccident(List<AccidentDTO> accidents) {
		// TODO Auto-generated method stub
		return accidents.size();
	}

	@Override
	public void insert(String accident) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);

			ps = conn.prepareStatement(INSERT_QUERY_STRING1);
			
			ps.setString(1,accident);
			
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
