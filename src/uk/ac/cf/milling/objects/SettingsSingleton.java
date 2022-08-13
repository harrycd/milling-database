/**
 * 
 */
package uk.ac.cf.milling.objects;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JProgressBar;

/**
 * Holds the application global settings and is also used to store objects available<br>
 * to multiple parallel threads.
 * @author Theocharis Alexopoulos
 *
 */
public class SettingsSingleton {
	private static SettingsSingleton instance = null;
	
	/**
	 * Exists to defeat instantiation
	 */
	private SettingsSingleton() {
		chartRegistry = new HashMap<String, Object>();
	}
	
	/**
	 * @return Returns an instance containing database settings or creates a new one the first time it is called.
	 */
	public static SettingsSingleton getInstance(){
		if (instance == null){
			instance = new SettingsSingleton();
		}
		return instance;
	}
	
	// Below the settings parameters
	
	/**
	 * The file path of SQLite database file
	 */
	public String dbFilePath;
	
	/**
	 * Holds the progress bar so multiple threads can update it
	 */
	public JProgressBar progressBar;
	
	/**
	 * Keep track of generated charts
	 */
	public Map<String, Object> chartRegistry;

}
