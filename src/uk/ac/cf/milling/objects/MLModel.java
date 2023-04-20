/**
 * 
 */
package uk.ac.cf.milling.objects;

import java.util.Arrays;

/**
 * Holds the parameter values for the trained model.
 * @author Theocharis Alexopoulos
 *
 */
public class MLModel {
	private int mlModelId = 0;
	private int materialId = 0;
	private String toolSeries = "";
	private String[] inputNames = null;
	private String targetName = "";
	private String mlModelPath = "";
	private long sampleCounter = 0;
	
	
	/**
	 * @return the mlModelId
	 */
	public int getMLModelId() {
		return mlModelId;
	}
	
	/**
	 * @param mlModelId the mlModelId to set
	 */
	public void setMLModelId(int mlModelId) {
		this.mlModelId = mlModelId;
	}
	
	/**
	 * @return the materialId
	 */
	public int getMaterialId() {
		return materialId;
	}
	
	/**
	 * @param materialId the materialId to set
	 */
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}
	
	/**
	 * @return the toolSeries
	 */
	public String getToolSeries() {
		return toolSeries;
	}
	
	/**
	 * @param toolSeries the toolSeries to set
	 */
	public void setToolSeries(String toolSeries) {
		this.toolSeries = toolSeries;
	}
	
	/**
	 * @return the inputNames
	 */
	public String[] getInputNames() {
		return inputNames;
	}
	
	
	
	/**
	 * @param inputNames the inputNames to set
	 */
	public void setInputNames(String[] inputNames) {
		this.inputNames = inputNames;
	}

	/**
	 * @return the InputNames array stringified.
	 */
	public String getInputNamesStringified() {
		String stringified = Arrays.toString(inputNames);
		stringified = stringified.substring(1, stringified.length()-1).replaceAll(", ",",");
		
		return stringified;
	}
	
	public void setInputNamesStringified(String inputNamesStringified) {
		this.inputNames = inputNamesStringified.split(",");
	}
	
	/**
	 * @return the targetName
	 */
	public String getTargetName() {
		return targetName;
	}
	
	/**
	 * @param targetName the targetName to set
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	/**
	 * @return the sampleCounter
	 */
	public long getSampleCounter() {
		return sampleCounter;
	}
	
	/**
	 * @param sampleCounter the sampleCounter to set
	 */
	public void setSampleCounter(long sampleCounter) {
		this.sampleCounter = sampleCounter;
	}

	/**
	 * @return the mlModelPath
	 */
	public String getMLModelPath() {
		return mlModelPath;
	}

	/**
	 * @param mlModelPath the mlModelPath to set
	 */
	public void setMLModelPath(String mlModelPath) {
		this.mlModelPath = mlModelPath;
	}
}
