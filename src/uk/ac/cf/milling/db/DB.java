/**
 * 
 */
package uk.ac.cf.milling.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import uk.ac.cf.milling.objects.SettingsSingleton;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class DB {
	/**
	 * Open a connection to virtual machine database
	 * @return the connection
	 */
	public Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			String dbFilePath = SettingsSingleton.getInstance().dbFilePath;
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbFilePath);
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}

//		System.out.println("Connection opened for database: " + GUIBuilder.database);
		return connection;
	}

	/**
	 * @param connection - the connection to close
	 */
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("Connection closed");
	}

	/**
	 * Creates an empty db
	 */
	public void initialiseDB(String dbFilePath){
		//Store the database path at the settings
		SettingsSingleton.getInstance().dbFilePath = dbFilePath;
		System.out.println("Initialising: " + dbFilePath);
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			// Create the tables if the database is new
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS billet ("
					+ "billetId		INTEGER, "
					+ "billetName 	TEXT, "
					+ "materialId	INTEGER, "
					+ "billetXMin	REAL, "
					+ "billetXMax	REAL, "
					+ "billetYMin	REAL, "
					+ "billetYMax	REAL, "
					+ "billetZMin	REAL, "
					+ "billetZMax	REAL, "
					+ "PRIMARY KEY(billetId) )");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS cutting_tool ("
					+ "toolId 		INTEGER, "
					+ "toolName 	TEXT, "
					+ "toolType 	TEXT, "
					+ "toolTeeth 	INTEGER, "
					+ "toolLength 	REAL, "
					+ "PRIMARY KEY(toolId) )");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS cutting_tool_profile ("
					+ "toolId 				INTEGER 	NOT NULL, "
					+ "distanceFromNose 	REAL 		NOT NULL, "
					+ "distanceFromCentre 	REAL 		NOT NULL, "
					+ "insertionsPerTooth 	INTEGER 	DEFAULT 0, "
					+ "materialRemoved 		REAL 		DEFAULT 0, "
					+ "axialProfile 		INTEGER, "
					+ "radialProfile 		INTEGER, "
					+ "PRIMARY KEY(toolId, distanceFromNose, distanceFromCentre), "
					+ "FOREIGN KEY(toolId) REFERENCES cutting_tool(toolId)) "
					+ "WITHOUT ROWID");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS carousel ("
					+ "position 	INTEGER, "
					+ "toolId 		INTEGER DEFAULT 0, "
					+ "PRIMARY KEY(position)) "
					+ "WITHOUT ROWID");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS setting ("
					+ "settingId 	STRING, "
					+ "value		STRING, "
					+ "PRIMARY KEY(settingId))");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS material ("
					+ "materialId 	INTEGER, "
					+ "materialName	STRING, "
					+ "torqueFactor	REAL	DEFAULT 1, "
					+ "PRIMARY KEY(materialId))");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS nc ("
					+ "ncId 		INTEGER, "
					+ "ncPath		STRING UNIQUE, "
					+ "analysisPath	STRING, "
					+ "monitoringPath	STRING, "
					+ "billetId	INTEGER, "
					+ "PRIMARY KEY(ncId))");
			
			// Enter required settings for the app to run
			statement.executeUpdate("INSERT INTO setting(settingId, value) VALUES('elementSize','1'),('timeStep','1')");
			
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Initialisation finished");
	}

}
