package uk.ac.cf.milling.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import uk.ac.cf.milling.objects.LearningSet;

/**
 * Contains CRUD methods to manage machine learning models in the database
 * @author Theocharis Alexopoulos
 * 
 */
public class LearningSetDB extends DB {

	/**
	 * @param learningSet - Add the created LearningSet into the database
	 * @return Returns the id of the newly created LearningSet
	 */
	public int addLearningSet(LearningSet learningSet) {

		int learningSetId = 0;
		Connection connection = getConnection();
		String query = "INSERT INTO learning_set(materialId, toolSeries, targetName, sampleCounter) VALUES(?,?,?,?)";
		try {
			//Add to learning_set first to get an id
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, learningSet.getMaterialId());
			ps.setString(2, learningSet.getToolSeries());
			ps.setString(3, learningSet.getTargetName());
			ps.setLong(4, learningSet.getSampleCounter());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()){
				learningSetId = rs.getInt(1);
			}

			// Then add to learning_inputs
			for (Map.Entry<String, Double> input : learningSet.getInputs().entrySet()) {
				query = "INSERT INTO learning_input(learningSetId, inputName, inputValue) VALUES(?,?,?)";
				ps = connection.prepareStatement(query);
				ps.setInt(1, learningSetId);
				ps.setString(2, input.getKey());
				ps.setDouble(3, input.getValue());
				ps.executeUpdate();
			}

			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return learningSetId;
	}

	/**
	 * @param learningSetId - The id of the learning set to retrieve
	 * @return The retieved learning set
	 */
	public LearningSet getLearningSet(int learningSetId) {
		LearningSet learningSet = new LearningSet();
		Connection connection = getConnection();
		String query = "SELECT * FROM learning_set "
				+ "INNER JOIN learning_input "
				+ "ON learning_set.learningSetId = learning_input.learningSetId "
				+ "WHERE learning_set.learningSetId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, learningSetId);
			ResultSet rs = ps.executeQuery();

			//parse learning_set table contents (appear repeated in results)
			boolean repeatedDataNotParsed = true;

			//parse learning_input table contents
			Map<String, Double> inputs = new HashMap<String, Double>();
			while (rs.next()) { 
				
				if (repeatedDataNotParsed) { 
					learningSet.setLearningSetId(learningSetId);
					learningSet.setMaterialId(rs.getInt("materialId"));
					learningSet.setToolSeries(rs.getString("toolSeries"));
					learningSet.setTargetName(rs.getString("targetName"));
					learningSet.setSampleCounter(rs.getLong("sampleCounter"));
					repeatedDataNotParsed = false;
				}

				inputs.put(rs.getString("inputName"), rs.getDouble("inputValue"));
			}
			learningSet.setInputs(inputs);

			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return learningSet;

	}
	
	/**
	 * @param materialId - Material Id of the LearningSet to retrieve
	 * @param toolSeries - Series of the tool related to the LearningSet to retrieve
	 * @param targetName - Target parameter of the LearningSet to retrieve
	 * @return Returns the retrieved LearningSet
	 */
	public LearningSet getLearningSet(int materialId, String toolSeries, String targetName) {
		LearningSet learningSet = new LearningSet();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM learning_set WHERE materialId=? AND toolSeries=? AND targetName=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, materialId);
			ps.setString(2, toolSeries);
			ps.setString(3, targetName);
			ResultSet rs = ps.executeQuery();

			//parse learning_set table contents (appear repeated in results)
			if (rs.next()) { 
				int learningSetId = rs.getInt("learningSetId");
				learningSet = getLearningSet(learningSetId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return learningSet;

	}

	/**
	 * Uses the id of the provided LearningSet to find the previous version of LearningSet in the database and update it.
	 * @param learningSet - The version of the LearningSet to update the database with. 
	 */
	public void updateLearningSet(LearningSet learningSet) {
		Connection connection = getConnection();
		try {
			connection.setAutoCommit(false);

			//Update learning_set
			String query = "UPDATE learning_set SET materialId=?, toolSeries=?, targetName=?, sampleCounter=? WHERE learningSetId=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, learningSet.getMaterialId());
			ps.setString(2, learningSet.getToolSeries());
			ps.setString(3, learningSet.getTargetName());
			ps.setLong(4, learningSet.getSampleCounter());
			ps.setInt(5, learningSet.getLearningSetId());
			ps.executeUpdate();

			// Delete previous learning inputs
			query = "DELETE FROM learning_input WHERE learningSetId=?;";
			ps = connection.prepareStatement(query);
			ps.setInt(1, learningSet.getLearningSetId());
			ps.executeUpdate();

			for (Map.Entry<String, Double> input : learningSet.getInputs().entrySet()) {
				//
				query = "INSERT INTO learning_input(learningSetId, inputName, inputValue) VALUES(?,?,?);";
				ps = connection.prepareStatement(query);
				ps.setInt(1, learningSet.getLearningSetId());
				ps.setString(2, input.getKey());
				ps.setDouble(3, input.getValue());
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			try {connection.rollback();} catch (SQLException e1) {e1.printStackTrace();}
			e.printStackTrace();
		} finally {
			try {connection.commit();} catch (SQLException e1) {e1.printStackTrace();}
			closeConnection(connection);
		}
	}

	/**
	 * @param learningSetId - The ID of the LearningSet to delete
	 */
	public void deleteLearningSet(int learningSetId) {
		Connection connection = getConnection();
		try {
			connection.setAutoCommit(false);
			
			//Delete learning_inputs first
			String query = "DELETE FROM learning_input WHERE learningSetId=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, learningSetId);
			ps.executeUpdate();

			//Delete learning_set
			query = "DELETE FROM learning_set WHERE learningSetId=?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, learningSetId);
			ps.executeUpdate();

		} catch (SQLException e) {
			try {connection.rollback();} catch (SQLException e1) {e1.printStackTrace();}
			e.printStackTrace();
		} finally {
			try {connection.commit();} catch (SQLException e1) {e1.printStackTrace();}
			closeConnection(connection);
		}
	}

}
