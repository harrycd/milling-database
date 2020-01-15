/**
 * 
 */
package uk.ac.cf.milling.objects;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JProgressBar;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class SettingsSingleton {
	private static SettingsSingleton instance = null;
	
	private SettingsSingleton() {
		// Exists to defeat instantiation
		chartRegistry = new HashMap<String, Object>();
	}
	
	public static SettingsSingleton getInstance(){
		if (instance == null){
			instance = new SettingsSingleton();
		}
		return instance;
	}
	
	// Below the settings parameters
	public String dbFilePath;
	public JProgressBar progressBar;
	public Map<String, Object> chartRegistry;

}
