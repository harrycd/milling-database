package uk.ac.cf.milling.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.cf.milling.objects.MLModel;

/**
 * Contains CRUD methods to manage machine learning models in the database
 * @author Theocharis Alexopoulos
 * 
 */
public class MLModelDB extends DB {

	/**
	 * @param mlModel - Add the created MLModel into the database
	 * @return Returns the id of the newly created MLModel
	 */
	public int addMLModel(MLModel mlModel) {

		int mlModelId = 0;
		Connection connection = getConnection();
		String query = "INSERT INTO ml_model(materialId, toolSeries, inputNames, targetName, mlModelPath, sampleCounter) VALUES(?,?,?,?,?,?)";
		try {

			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, mlModel.getMaterialId());
			ps.setString(2, mlModel.getToolSeries());
			ps.setString(3, mlModel.getInputNamesStringified());
			ps.setString(4, mlModel.getTargetName());
			ps.setString(5, mlModel.getMLModelPath());
			ps.setLong(6, mlModel.getSampleCounter());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()){
				mlModelId = rs.getInt(1);
			}

			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mlModelId;
	}

	/**
	 * @param mlModelId - The id of the ML model to retrieve
	 * @return The retrieved MLModel
	 */
	public MLModel getMLModel(int mlModelId) {
		MLModel mlModel = new MLModel();
		Connection connection = getConnection();
		String query = "SELECT * FROM ml_model WHERE mlModelId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, mlModelId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				mlModel = getMLModel(rs); 
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return mlModel;

	}
	
	/**
	 * @param materialId - Material Id of the MLModel to retrieve
	 * @param toolSeries - Series of the tool related to the MLModel to retrieve
	 * @param targetName - Target parameter of the MLModel to retrieve
	 * @return Returns the retrieved MLModel
	 */
	public MLModel getMLModel(int materialId, String toolSeries, String targetName) {
		MLModel mlModel = new MLModel();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM ml_model WHERE materialId=? AND toolSeries=? AND targetName=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, materialId);
			ps.setString(2, toolSeries);
			ps.setString(3, targetName);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) { 
				mlModel.setMLModelId(rs.getInt("mlModelId"));
				mlModel.setMaterialId(rs.getInt("materialId"));
				mlModel.setToolSeries(rs.getString("toolSeries"));
				mlModel.setInputNamesStringified(rs.getString("inputNames"));
				mlModel.setTargetName(rs.getString("targetName"));
				mlModel.setMLModelPath(rs.getString("mlModelPath"));
				mlModel.setSampleCounter(rs.getLong("sampleCounter"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return mlModel;

	}
	
	/**
	 * Retrieves from the database MLModels that related to the specified material and toolSeries
	 * @param materialId - the materialId of the MLModel to retrieve
	 * @param toolSeries - the toolSeries of the MLModel to retrieve
	 * @return a List<MLModel> containing the MLModels with the specified materialId and toolSeries
	 */
	public List<MLModel> getMLModels(int materialId, String toolSeries) {
		List<MLModel> mlModels = new ArrayList<MLModel>();
		Connection connection = getConnection();
		String query = "SELECT * FROM ml_model WHERE materialId=? AND toolSeries=?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, materialId);
			ps.setString(2, toolSeries);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				mlModels.add(getMLModel(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		} 
		
		return mlModels;
	}
	
	public void updateMLModel(
			int mlModelId, 
			int materialId, 
			String toolSeries, 
			String inputNames,
			String targetName, 
			String mlModelPath, 
			long sampleCounter) {
		
		Connection connection = getConnection();
		String query = "UPDATE ml_model "
				+ "SET materialId=?, toolSeries=?, inputNames=?, targetName=?, mlModelPath=?, sampleCounter=? "
				+ "WHERE mlModelId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, materialId);
			ps.setString(2, toolSeries);
			ps.setString(3, inputNames);
			ps.setString(4, targetName);
			ps.setString(5, mlModelPath);
			ps.setLong(6, sampleCounter);
			ps.setInt(7, mlModelId);
			ps.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
	}



	/**
	 * @param mlModelId - The ID of the MLModel to delete
	 */
	public void deleteMLModel(int mlModelId) {
		Connection connection = getConnection();
		try {
			
			String query = "DELETE FROM ml_model WHERE mlModelId=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, mlModelId);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * @param rs - ResultSet containing MLModel data
	 * @return a new MLModel loaded with the provided ResultSet data
	 * @throws SQLException 
	 */
	private MLModel getMLModel(ResultSet rs) throws SQLException {
		MLModel mlModel = new MLModel();
		
		mlModel.setMLModelId(rs.getInt("mlModelId"));
		mlModel.setMaterialId(rs.getInt("materialId"));
		mlModel.setToolSeries(rs.getString("toolSeries"));
		mlModel.setInputNamesStringified(rs.getString("inputNames"));
		mlModel.setTargetName(rs.getString("targetName"));
		mlModel.setMLModelPath(rs.getString("mlModelPath"));
		mlModel.setSampleCounter(rs.getLong("sampleCounter"));
		
		return mlModel;
	}
}
