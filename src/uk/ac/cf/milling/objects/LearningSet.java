/**
 * 
 */
package uk.ac.cf.milling.objects;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds the parameter values for the trained model.
 * @author Theocharis Alexopoulos
 *
 */
public class LearningSet {
	private int learningSetId = 0;
	private int materialId = 0;
	private String toolSeries = "";
	private String targetName = "";
	private long sampleCounter = 0;
	private Map<String, Double> inputs = new HashMap<String, Double>();
	/**
	 * @return the learningSetId
	 */
	public int getLearningSetId() {
		return learningSetId;
	}
	/**
	 * @param learningSetId the learningSetId to set
	 */
	public void setLearningSetId(int learningSetId) {
		this.learningSetId = learningSetId;
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
	 * @return the inputs
	 */
	public Map<String, Double> getInputs() {
		return inputs;
	}
	/**
	 * @param inputs the inputs to set
	 */
	public void setInputs(Map<String, Double> inputs) {
		this.inputs = inputs;
	}
	
}
