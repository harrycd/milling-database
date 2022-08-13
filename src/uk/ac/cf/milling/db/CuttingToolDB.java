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

import uk.ac.cf.milling.objects.CuttingTool;

/**
 * Contains CRUD methods to manage cutting tools in the database
 * @author Theocharis Alexopoulos
 *
 */
public class CuttingToolDB extends DB{
	
	/**
	 * @param toolName - description for the tool
	 * @param toolType - type of tool (endmill, ball nose etc.)
	 * @param toolSeries - the series of the tool as stated by the manufacturer.
	 * @param toolTeeth - number of teeth that the tool has
	 * @param toolLength - the total length of the tool
	 * @return the id of the newly created CuttingTool
	 */
	public int addCuttingTool(String toolName, String toolType, String toolSeries, int toolTeeth, double toolLength){
		int cuttingToolId = 0;
		Connection connection = getConnection();
		String query = "INSERT INTO cutting_tool(toolName, toolType, toolSeries, toolTeeth, toolLength) VALUES(?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, toolName);
			ps.setString(2, toolType);
			ps.setString(3, toolSeries);
			ps.setInt(4, toolTeeth);
			ps.setDouble(5, toolLength);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()){
				cuttingToolId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return cuttingToolId;
	}
	
	/**
	 * @param toolId - the id of the tool to retrieve
	 * @return the retrieved tool
	 */
	public CuttingTool getCuttingTool(int toolId){
		CuttingTool tool = new CuttingTool();
		Connection connection = getConnection();
		String query = "SELECT * from cutting_tool WHERE toolId=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			rs.next();
			tool.setToolId(toolId);
			tool.setToolName(rs.getString("toolName"));
			tool.setToolType(rs.getString("toolType"));
			tool.setToolSeries(rs.getString("toolSeries"));
			tool.setToolTeeth(rs.getInt("toolTeeth"));
			tool.setToolLength(rs.getDouble("toolLength"));
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			//Close the connection to the database regardless of errors
			closeConnection(connection);
		}
		return tool;
	}

	/**
	 * @return a List containing all tools in database
	 */
	public List<CuttingTool> getAllCuttingTools(){
		List<CuttingTool> tools = new ArrayList<CuttingTool>();
		Connection connection = getConnection();
		String query = "SELECT * from cutting_tool;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			while(rs.next()){
				CuttingTool tool = new CuttingTool();
				tool.setToolId(rs.getInt("toolId"));
				tool.setToolName(rs.getString("toolName"));
				tool.setToolType(rs.getString("toolType"));
				tool.setToolSeries(rs.getString("toolSeries"));
				tool.setToolTeeth(rs.getInt("toolTeeth"));
				tool.setToolLength(rs.getDouble("toolLength"));
				tools.add(tool);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			//Close the connection to the database regardless of errors
			closeConnection(connection);
		}
		return tools;
	}
	
	/**
	 * @param toolId - the id of the tool to update
	 * @param toolName - description for the tool
	 * @param toolType - type of tool (endmill, ball nose etc.)
	 * @param toolSeries - the series of the tool as stated by the manufacturer.
	 * @param toolTeeth - number of teeth that the tool has
	 * @param toolLength - the total length of the cutting tool
	 */
	public void updateCuttingTool(int toolId, String toolName, String toolType, String toolSeries, int toolTeeth, double toolLength ) {
		Connection connection = getConnection();
		String query = "UPDATE cutting_tool SET toolName=?, toolType=?, toolSeries=?, toolTeeth=?, toolLength=? WHERE toolId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, toolName);
			ps.setString(2, toolType);
			ps.setString(3, toolSeries);
			ps.setInt(4, toolTeeth);
			ps.setDouble(5, toolLength);
			ps.setInt(6, toolId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			//Close the connection to the database regardless of errors
			closeConnection(connection);
		}
	}
	
	/**
	 * @param toolId - the id of the tool to delete
	 */
	public void deleteCuttingTool(int toolId){
		Connection connection = getConnection();
		String query = "DELETE FROM cutting_tool WHERE toolId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			//Close the connection to the database regardless of errors
			closeConnection(connection);
		}
	}
}
