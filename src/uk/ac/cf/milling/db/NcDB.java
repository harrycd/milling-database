/**
 * 
 */
package uk.ac.cf.milling.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.cf.milling.objects.Nc;

/**
 * Contains CRUD methods to manage NC files in the database
 * @author Theocharis Alexopoulos
 *
 */
public class NcDB extends DB{
	/**
	 * @param ncPath - file path of the numerical control file
	 * @param analysisPath - file path of the simulator analysis file
	 * @param monitoringPath - file path of the file containing machine monitoring data
	 * @param billetId - Id of the billet used to produce analysis/monitoring file
	 * @return the id of the numerical control file
	 */
	public int addNcFile(String ncPath, String analysisPath, String monitoringPath, int billetId){
		int ncId = 0;
		Connection connection = getConnection();
		String query = "INSERT INTO nc(ncPath, analysisPath, monitoringPath, billetId) VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, ncPath);
			ps.setString(2, analysisPath);
			ps.setString(3, monitoringPath);
			ps.setInt(4, billetId);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()){
				ncId = rs.getInt(1);
			}
			
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ncId;
	}
	
	/**
	 * @param ncId - id of numerical control file
	 * @return the Nc object specified by the id
	 */
	public Nc getNc(int ncId){
		Nc nc = new Nc();
		Connection connection = getConnection();
		String query = "SELECT * from nc WHERE ncId=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, ncId);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			rs.next();
			nc.setNcId(rs.getInt("ncId"));
			nc.setNcPath(rs.getString("ncPath"));
			nc.setAnalysisPath(rs.getString("analysisPath"));
			nc.setMonitoringPath(rs.getString("monitoringPath"));
			nc.setBilletId(rs.getInt("billetId"));
			
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nc;
	}

	/**
	 * @param ncPath - file path of numerical control file
	 * @return the Nc object specified by the id
	 */
	public Nc getNc(String ncPath){
		Nc nc = new Nc();
		Connection connection = getConnection();
		String query = "SELECT * from nc WHERE ncPath=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, ncPath);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			if (rs.next()){
				nc.setNcId(rs.getInt("ncId"));
				nc.setNcPath(rs.getString("ncPath"));
				nc.setAnalysisPath(rs.getString("analysisPath"));
				nc.setMonitoringPath(rs.getString("monitoringPath"));
				nc.setBilletId(rs.getInt("billetId"));
			}
			
			
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nc;
	}
	
	/**
	 * @return a list of all NC programs in the database
	 */
	public List<Nc> getNcs(){
		List<Nc> ncs = new ArrayList<Nc>();
		Connection connection = getConnection();
		String query = "SELECT * from nc;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			while(rs.next()){
				Nc nc = new Nc();
				nc.setNcId(rs.getInt("ncId"));
				nc.setNcPath(rs.getString("ncPath"));
				nc.setAnalysisPath(rs.getString("analysisPath"));
				nc.setMonitoringPath(rs.getString("monitoringPath"));
				nc.setBilletId(rs.getInt("billetId"));
				ncs.add(nc);
			}
			
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ncs;
	}
	
	/**
	 * @param ncId - id of numerical control file
	 * @param ncPath - file path of the numerical control file
	 * @param analysisPath - file path of the simulator analysis file
	 * @param monitoringPath - file path of the file containing machine monitoring data
	 * @param billetId - Id of the billet used to produce analysis/monitoring file
	 */
	public void updateNc(int ncId, String ncPath, String analysisPath, String monitoringPath, int billetId){
		Connection connection = getConnection();
		String query = "UPDATE nc SET ncPath=?, analysisPath=?, monitoringPath=?, billetId=? WHERE ncId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, ncPath);
			ps.setString(2, analysisPath);
			ps.setString(3, monitoringPath);
			ps.setInt(4, billetId);
			ps.setInt(5, ncId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param ncId - id of numerical control file
	 * @param ncPath - file path of the numerical control file
	 * @param billetId - Id of the billet used to produce analysis/monitoring file
	 */
	public void updateNcPath(int ncId, String ncPath, int billetId){
		Connection connection = getConnection();
		String query = "UPDATE nc SET ncPath=?, billetId=? WHERE ncId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, ncPath);
			ps.setInt(2, billetId);
			ps.setInt(3, ncId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param ncId - id of numerical control file
	 * @param analysisPath - file path of the simulator analysis file
	 * @param billetId - Id of the billet used to produce analysis/monitoring file
	 */
	public void updateNcAnalysis(int ncId, String analysisPath, int billetId){
		Connection connection = getConnection();
		String query = "UPDATE nc SET analysisPath=?, billetId=? WHERE ncId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, analysisPath);
			ps.setInt(2, billetId);
			ps.setInt(3, ncId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param ncId - id of numerical control file
	 * @param monitoringPath - file path of the file containing machine monitoring data
	 */
	public void updateNcMonitoring(int ncId, String monitoringPath){
		Connection connection = getConnection();
		String query = "UPDATE nc SET monitoringPath=? WHERE ncId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, monitoringPath);
			ps.setInt(2, ncId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param ncId - id of nc to delete
	 */
	public void deleteNc(int ncId){
		Connection connection = getConnection();
		String query = "DELETE FROM nc WHERE ncId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, ncId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
