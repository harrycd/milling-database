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

import uk.ac.cf.milling.objects.Billet;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class BilletDB extends DB {
	/**
	 * @param billetName - The name of the billet to add
	 * @param materialId - Id of the material the billet is made of
	 * @param billetXMin - Min X coordinate of the billet when placed on table
	 * @param billetXMax - Max X coordinate of the billet when placed on table
	 * @param billetYMin - Min Y coordinate of the billet when placed on table
	 * @param billetYMax - Max Y coordinate of the billet when placed on table
	 * @param billetZMin - Min Z coordinate of the billet when placed on table
	 * @param billetZMax - Max Z coordinate of the billet when placed on table
	 * @return the newly assigned id for the billet
	 */
	public int addBillet(String billetName, int materialId, double billetXMin, double billetXMax, double billetYMin, double billetYMax, double billetZMin, double billetZMax){
		int billetId = 0;
		Connection connection = getConnection();
		String query = "INSERT INTO billet(billetName, materialId, billetXMin, billetXMax, billetYMin, billetYMax, billetZMin, billetZMax) VALUES(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, billetName);
			ps.setInt(2, materialId);
			ps.setDouble(3, billetXMin);
			ps.setDouble(4, billetXMax);
			ps.setDouble(5, billetYMin);
			ps.setDouble(6, billetYMax);
			ps.setDouble(7, billetZMin);
			ps.setDouble(8, billetZMax);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()){
				billetId = rs.getInt(1);
			}
			
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billetId;
	}
	
	/**
	 * @param billetId - The id of the billet to retrieve
	 * @return the retrieved billet
	 */
	public Billet getBillet(int billetId){
		Billet billet = new Billet();
		Connection connection = getConnection();
		String query = "SELECT * from billet WHERE billetId=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, billetId);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			rs.next();
			billet.setBilletId(billetId);
			billet.setBilletName(rs.getString("billetName"));
			billet.setXBilletMin(rs.getDouble("billetXMin"));
			billet.setXBilletMax(rs.getDouble("billetXMax"));
			billet.setYBilletMin(rs.getDouble("billetYMin"));
			billet.setYBilletMax(rs.getDouble("billetYMax"));
			billet.setZBilletMin(rs.getDouble("billetZMin"));
			billet.setZBilletMax(rs.getDouble("billetZMax"));
			
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billet;
	}

	/**
	 * @return a List of all billets stored in the database
	 */
	public List<Billet> getAllBillets(){
		List<Billet> billets = new ArrayList<Billet>();
		Connection connection = getConnection();
		String query = "SELECT * from billet;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			while(rs.next()){
				Billet billet = new Billet();
				billet.setBilletId(rs.getInt("billetId"));
				billet.setBilletName(rs.getString("billetName"));
				billet.setXBilletMin(rs.getDouble("billetXMin"));
				billet.setXBilletMax(rs.getDouble("billetXMax"));
				billet.setYBilletMin(rs.getDouble("billetYMin"));
				billet.setYBilletMax(rs.getDouble("billetYMax"));
				billet.setZBilletMin(rs.getDouble("billetZMin"));
				billet.setZBilletMax(rs.getDouble("billetZMax"));
				billets.add(billet);
			}
			
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billets;
	}
	
	/**
	 * @param billetId - the id of the billet to update
	 * @param billetName - The new name to update the billet with
	 * @param torqueFactor - The new factor used in the calculation of the torque while machining this billet
	 */
	/**
	 * @param billetId - Id of the billet to update
	 * @param billetName - name for the selected billet
	 * @param materialId - Id of the material the billet is made of
	 * @param billetXMin - Min X coordinate of the billet when placed on table
	 * @param billetXMax - Max X coordinate of the billet when placed on table
	 * @param billetYMin - Min Y coordinate of the billet when placed on table
	 * @param billetYMax - Max Y coordinate of the billet when placed on table
	 * @param billetZMin - Min Z coordinate of the billet when placed on table
	 * @param billetZMax - Max Z coordinate of the billet when placed on table
	 */
	public void updateBillet(int billetId, String billetName, int materialId, double billetXMin, double billetXMax, double billetYMin, double billetYMax, double billetZMin, double billetZMax){
		Connection connection = getConnection();
		String query = "UPDATE billet SET billetName=?, materialId=?, billetXMin=?, billetXMax=?, billetYMin=?, billetYMax=?, billetZMin=?, billetZMax=?, WHERE billetId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, billetName);
			ps.setInt(2, materialId);
			ps.setDouble(3, billetXMin);
			ps.setDouble(4, billetXMax);
			ps.setDouble(5, billetYMin);
			ps.setDouble(6, billetYMax);
			ps.setDouble(7, billetZMin);
			ps.setDouble(8, billetZMax);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param billetId - the id of the billet to delete
	 */
	public void deleteBillet(int billetId){
		Connection connection = getConnection();
		String query = "DELETE FROM billet WHERE billetId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, billetId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
