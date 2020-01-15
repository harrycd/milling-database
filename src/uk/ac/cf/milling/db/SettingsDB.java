/**
 * 
 */
package uk.ac.cf.milling.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Theocharis Alexopoulos
 * Utility class to retrieve or update the settings of the application
 * Add or remove a setting is not allowed as everything is created during database creation
 */
public class SettingsDB extends DB{
	/**
	 * @param settingId - the setting to retrieve
	 * @return the value of the specified setting
	 */
	public String getSetting(String settingId){
		String value = "";
		Connection connection = getConnection();
		String query = "SELECT * from setting WHERE settingId=?;";
		try {

			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, settingId);

			ResultSet rs = ps.executeQuery();

			//Parse results
			if(rs.next()){
				value = rs.getString("value");
			}
			//Close the connection to the database
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * @param settingId - setting to update
	 * @param value - value of the specified setting
	 */
	public void updateSetting(String settingId, String value ) {
		Connection connection = getConnection();
		String query = "UPDATE setting SET value=? WHERE settingId=?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, value);
			ps.setString(2, settingId);
			ps.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
