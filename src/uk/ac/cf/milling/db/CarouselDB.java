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
 * Contains CRUD methods to manage carousel data in the database
 * @author Theocharis Alexopoulos
 *
 */
public class CarouselDB extends DB {
	/**
	 * @param position - the pocket number of the specified tool
	 * @param toolId - the tool to load on that position
	 */
	public void addCarouselPocket(int position, int toolId){
		Connection connection = getConnection();
		String query = "INSERT INTO carousel(position, toolId) VALUES(?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, position);
			ps.setInt(2, toolId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * @param pocketNumber - the pocket number of the carousel
	 * @return the id of the tool loaded in the specified pocket
	 */
	public int getLoadedCuttingToolId(int pocketNumber){
		int toolId = 0;
		Connection connection = getConnection();
		String query = "SELECT * from carousel WHERE position=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, pocketNumber);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			if (rs.next())
				toolId = rs.getInt("toolId");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//Close the connection to the database
			closeConnection(connection);
		}
		return toolId;
	}

	/**
	 * @param pocketNumber - the pocket number of the carousel
	 * @return the cutting tool loaded in the specified pocket
	 */
	public CuttingTool getLoadedCuttingTool(int pocketNumber){
		CuttingTool tool = new CuttingTool();
		Connection connection = getConnection();
		String query = "SELECT * from carousel INNER JOIN cutting_tool ON carousel.toolId = cutting_tool.toolId WHERE position=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, pocketNumber);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			if (rs.next()) {
				tool.setToolId(rs.getInt("toolId"));
				tool.setToolName(rs.getString("toolName"));
				tool.setToolType(rs.getString("toolType"));
				tool.setToolSeries(rs.getString("toolSeries"));
				tool.setToolTeeth(rs.getInt("toolTeeth"));
				tool.setToolLength(rs.getDouble("toolLength"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeConnection(connection);
		}
		return tool;
	}

	/**
	 * @param toolId - the id of the tool to search for
	 * @return the pocket number of the carousel that the tool was found (0 if not found)
	 */
	public int getCarouselPocketPosition(int toolId){
		int position = 0;
		Connection connection = getConnection();
		String query = "SELECT * from carousel WHERE toolId=?;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			if(rs.next())
				position = rs.getInt("position");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeConnection(connection);
		}
		return position;
	}

	/**
	 * @return a List of Integer[] containing all pockets (with or without tools).
	 * Each array has the following elements
	 * pocket[0] : The position in the pocket
	 * pocket[1] : The toolId of the tool in the pocket (it is 0 when no tool present)
	 */
	public List<Integer[]> getAllCarouselPockets(){
		//TODO this doesn't work!!.
		List<Integer[]> pockets = new ArrayList<Integer[]>();
		Connection connection = getConnection();
		String query = "SELECT * from carousel ORDER BY position ASC;";
		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			//Parse results
			while(rs.next()){
				Integer[] pocket = new Integer[2];
				pocket[0] = rs.getInt("position");
				pocket[1] = rs.getInt("toolId");
				pockets.add(pocket);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeConnection(connection);
		}
		return pockets;
	}
	
	/**
	 * @return the list of toolIds for the corresponding pocket position
	 */
	public List<CuttingTool> getLoadedTools(){
		List<CuttingTool> tools = new ArrayList<CuttingTool>();
		Connection connection = getConnection();
		String query = "SELECT * FROM cutting_tool INNER JOIN carousel ON carousel.toolId = cutting_tool.toolId;";
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
				tools.add(tool);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeConnection(connection);
		}
		
		return tools;
		
	}
	
	/**
	 * @param position - the pocket number of the specified tool
	 * @param toolId - the tool to load on that position
	 */
	public void updateCarouselPocket(int position, int toolId){
		Connection connection = getConnection();
		String query = "UPDATE carousel SET toolId=? WHERE position=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, toolId);
			ps.setInt(2, position);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * @param position - the pocket of the carousel to remove
	 */
	public void deleteCarouselPocket(int position){
		Connection connection = getConnection();
		String query = "DELETE FROM carousel WHERE position=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, position);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			closeConnection(connection);
		}
	}
}
