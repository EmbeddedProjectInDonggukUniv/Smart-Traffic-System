package daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.FiremanDAO;
import dto.FiremanDTO;

public class FiremanDAOImpl implements FiremanDAO {

	String jdbcUrl = "jdbc:mysql://localhost:3306/embedded";
	String dbId = "embedded";
	String dbPass = "!rlacl1234";

	private static final String UPDATE_QUERY_STRING1 = "UPDATE fireman_tb SET accident_status = ? WHERE accident_status = ?";
	private static final String SELECT_QUERY_STRING1 = "SELECT * from fireman_tb";

	@Override
	public void update(String oldS, String newS) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);

			ps = conn.prepareStatement(UPDATE_QUERY_STRING1);
			ps.setString(1, newS);
			ps.setString(2, oldS);
			int n = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException se) {

				se.printStackTrace();

			}
		}

	}

	@Override
	public FiremanDTO select() {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			// 여기부터 !
			
			ps = conn.prepareStatement(SELECT_QUERY_STRING1);
			rs =ps.executeQuery();
			
			while(rs.next()){
				FiremanDTO firemanDTO = new FiremanDTO();
				firemanDTO.setAccidentState(rs.getString("accident_status"));
				
				return firemanDTO;
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
		return null;

	}

}
