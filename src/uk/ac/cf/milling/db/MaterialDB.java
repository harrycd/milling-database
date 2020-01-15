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

import uk.ac.cf.milling.objects.Material;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class MaterialDB extends DB {
	/**
	 * @param materialName - The name of the material to add
	 * @param torqueFactor - A factor used in the calculation of the torque while machining this material
	 * @return the newly assigned id for the material
	 */
	public int addMaterial(String materialName, double torqueFactor){
		int materialId = 0;
		Connection connection = getConnection();
		String query = "INSERT INTO material(materialName, torqueFactor) VALUES(?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, materialName);
			ps.setDouble(2, torqueFactor);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()){
				materialId = rs.getInt(1);
			}
			
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materialId;
	}
	
	/**
	 * @param materialId - The id of the material to retrieve
	 * @return
	 */
	public Material getMaterial(int materialId){
		Material material = new Material();
		Connection connection = getConnection();
		String query = "SELECT * from material WHERE materialId=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, materialId);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			rs.next();
			material.setMaterialId(materialId);
			material.setTorqueFactor(rs.getDouble("torqueFactor"));
			
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return material;
	}

	/**
	 * @return a List of all materials stored in the database
	 */
	public List<Material> getAllMaterials(){
		List<Material> materials = new ArrayList<Material>();
		Connection connection = getConnection();
		String query = "SELECT * from material;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			while(rs.next()){
				Material material = new Material();
				material.setMaterialId(rs.getInt("materialId"));
				material.setMaterialName(rs.getString("materialName"));
				material.setTorqueFactor(rs.getDouble("torqueFactor"));
				materials.add(material);
			}
			
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materials;
	}
	
	/**
	 * @param materialId - the id of the material to update
	 * @param materialName - The new name to update the material with
	 * @param torqueFactor - The new factor used in the calculation of the torque while machining this material
	 */
	public void updateMaterial(int materialId, String materialName, double torqueFactor){
		Connection connection = getConnection();
		String query = "UPDATE material SET materialName=?, torqueFactor=?, WHERE materialId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, materialName);
			ps.setDouble(2, torqueFactor);
			ps.setInt(3, materialId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param materialId - the id of the material to delete
	 */
	public void deleteMaterial(int materialId){
		Connection connection = getConnection();
		String query = "DELETE FROM material WHERE materialId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, materialId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
