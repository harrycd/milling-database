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

import uk.ac.cf.milling.objects.CuttingToolProfile;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class CuttingToolProfileDB extends DB{
	/**
	 */
	/**
	 * @param toolId - the tool that the profile refers to
	 * @param distanceFromNose - [mm] distance from the nose of the tool
	 * @param distanceFromCentre - [mm] distance from the axial centre of the tool
	 * @param insertionsPerTool - number of times that each tooth penetrated the material
	 * @param materialRemoved - [mm] length of travel in the material
	 * @param axialProfile - true if the profile can get axial usage. false if not
	 * @param radialProfile - true if the profile can get radial usage. false if not
	 */
	public void addCuttingToolProfile(int toolId, double distanceFromNose, double distanceFromCentre, int insertionsPerTooth, double materialRemoved, boolean axialProfile, boolean radialProfile){
		Connection connection = getConnection();
		String query = "INSERT INTO cutting_tool_profile(toolId, distanceFromNose, distanceFromCentre, insertionsPerTooth, materialRemoved, axialProfile, radialProfile) VALUES(?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			ps.setDouble(2, distanceFromNose);
			ps.setDouble(3, distanceFromCentre);
			ps.setInt(4, insertionsPerTooth);
			ps.setDouble(5, materialRemoved);
			ps.setBoolean(6, axialProfile);
			ps.setBoolean(7, radialProfile);
			
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param profiles - cutting tool profiles to add as batch
	 */
	public void addCuttingToolProfiles(List<CuttingToolProfile> profiles){
		Connection connection = getConnection();
		String query = "INSERT INTO cutting_tool_profile(toolId, distanceFromNose, distanceFromCentre, insertionsPerTooth, materialRemoved, axialProfile, radialProfile) VALUES(?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			for (CuttingToolProfile profile:profiles){
				ps.setInt(1, profile.getToolId());
				ps.setDouble(2, profile.getDistanceFromNose());
				ps.setDouble(3, profile.getDistanceFromCentre());
				ps.setInt(4, profile.getInsertionsPerTooth());
				ps.setDouble(5, profile.getMaterialRemoved());
				ps.setBoolean(6, profile.isAxialProfile());
				ps.setBoolean(7, profile.isRadialProfile());
				ps.executeUpdate();
			}
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param toolId - the tool that the profile refers to
	 * @param distanceFromNose - distance from the nose of the tool
	 * @param distanceFromCentre - distance from the central axis of the tool
	 * @return the profile at the specified position
	 */
	public CuttingToolProfile getCuttingToolProfile(int toolId, double distanceFromNose, double distanceFromCentre){
		CuttingToolProfile profile = new CuttingToolProfile();
		Connection connection = getConnection();
		String query = "SELECT * from cutting_tool_profile WHERE toolId=? AND distanceFromNose=? AND distanceFromCentre=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			ps.setDouble(2, distanceFromNose);
			ps.setDouble(3, distanceFromCentre);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			rs.next();
			profile.setToolId(toolId);
			profile.setDistanceFromNose(distanceFromNose);
			profile.setDistanceFromCentre(distanceFromCentre);
			profile.setInsertionsPerTooth(rs.getInt("insertionsPerTooth"));
			profile.setMaterialRemoved(rs.getDouble("materialRemoved"));
			profile.setAxialProfile(rs.getBoolean("axialProfile"));
			profile.setRadialProfile(rs.getBoolean("radialProfile"));
			
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profile;
	}
	
	/**
	 * @param toolId - tool to return the profiles for
	 * @param axialProfile - true for axial profile, false for radial
	 * @param axialProfile - true if the profile can get axial usage. false if not
	 * @param radialProfile - true if the profile can get radial usage. false if not
	 */
	//TODO sort based on radial or axial profile
	public List<CuttingToolProfile> getCuttingToolProfiles(int toolId, boolean axialProfile, boolean radialProfile){
		List<CuttingToolProfile> profiles = new ArrayList<CuttingToolProfile>();
		Connection connection = getConnection();
		
		String query = "SELECT * FROM cutting_tool_profile WHERE toolId=?";
		if (axialProfile) query += " AND axialProfile=1";
		if (radialProfile) query += " AND radialProfile=1";
		else query += " AND axialProfile=0 AND radialProfile=0"; //There should be no such profiles in the db
		query += ";";
		
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			while(rs.next()){
				CuttingToolProfile profile = new CuttingToolProfile();
				profile.setToolId(toolId);
				profile.setDistanceFromNose(rs.getDouble("distanceFromNose"));
				profile.setDistanceFromCentre(rs.getDouble("distanceFromCentre"));
				profile.setInsertionsPerTooth(rs.getInt("insertionsPerTooth"));
				profile.setMaterialRemoved(rs.getDouble("materialRemoved"));
				profile.setAxialProfile(rs.getBoolean("axialProfile"));
				profile.setRadialProfile(rs.getBoolean("radialProfile"));
				profiles.add(profile);
			}
			
			//Close the connection to the database
			closeConnection(connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return profiles;
	}
	
	/**
	 * @param toolId - tool to return the profiles for
	 * @return - all cutting tool profiles for the specified tool
	 */
	public List<CuttingToolProfile> getCuttingToolProfiles(int toolId){
		List<CuttingToolProfile> profiles = new ArrayList<CuttingToolProfile>();
		Connection connection = getConnection();
		String query = "SELECT * FROM cutting_tool_profile WHERE toolId=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			while(rs.next()){
				CuttingToolProfile profile = new CuttingToolProfile();
				profile.setToolId(toolId);
				profile.setDistanceFromNose(rs.getDouble("distanceFromNose"));
				profile.setDistanceFromCentre(rs.getDouble("distanceFromCentre"));
				profile.setInsertionsPerTooth(rs.getInt("insertionsPerTooth"));
				profile.setMaterialRemoved(rs.getDouble("materialRemoved"));
				profile.setAxialProfile(rs.getBoolean("axialProfile"));
				profile.setRadialProfile(rs.getBoolean("radialProfile"));
				profiles.add(profile);
			}
			
			//Close the connection to the database
			closeConnection(connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return profiles;
	}
	
	/**
	 * @param toolId - the tool that the profile refers to
	 * @param distanceFromNose - distance from the nose of the cutting tool
	 * @param distanceFromCentre - local radius of the tool
	 * @param insertionsPerTool - number of times that each tooth penetrated the material
	 * @param materialRemoved - length of travel in the material
	 */
	public void updateCuttingToolProfile(int toolId, double distanceFromNose, double distanceFromCentre, int insertionsPerTooth, double materialRemoved){
		Connection connection = getConnection();
		String query = "UPDATE cutting_tool_profile SET insertionsPerTooth=?, materialRemoved=? WHERE toolId=?, distanceFromNose=?, distanceFromCentre=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, insertionsPerTooth);
			ps.setDouble(2, materialRemoved);
			ps.setInt(3, toolId);
			ps.setDouble(4, distanceFromNose);
			ps.setDouble(5, distanceFromCentre);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param profiles - profiles to update as batch
	 */
	public void updateCuttingToolProfiles(List<CuttingToolProfile> profiles){
		Connection connection = getConnection();
		String query = "UPDATE cutting_tool_profile SET insertionsPerTooth=?, materialRemoved=? WHERE toolId=? AND distanceFromNose=? AND distanceFromCentre=?;";
		try {
			for (CuttingToolProfile profile:profiles){
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, profile.getInsertionsPerTooth());
				ps.setDouble(2, profile.getMaterialRemoved());
				ps.setInt(3, profile.getToolId());
				ps.setDouble(4, profile.getDistanceFromNose());
				ps.setDouble(5, profile.getDistanceFromCentre());
				ps.executeUpdate();
			}
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param toolId - the tool that the profile refers to
	 * @param distanceFromNose - distance from the nose of the cutting tool
	 * @param distanceFromCentre - local radius / distance from the centre of the tool
	 */
	public void deleteCuttingToolProfile(int toolId, double distanceFromNose, double distanceFromCentre){
		Connection connection = getConnection();
		String query = "DELETE FROM cutting_tool_profile WHERE toolId=? AND distanceFromNose=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			ps.setDouble(2, distanceFromNose);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param toolId - the tool to remove related profiles
	 */
	public void deleteCuttingToolProfiles(int toolId){
		Connection connection = getConnection();
		String query = "DELETE FROM cutting_tool_profile WHERE toolId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
