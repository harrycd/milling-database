/**
 * 
 */
package uk.ac.cf.milling.objects;

import uk.ac.cf.milling.objects.Billet;

/**
 * Contains simulator configuration parameters and corresponding getter<br>
 * and setter methods.
 * @author Theocharis Alexopoulos
 *
 */
public final class SimulatorConfig {
	
	private String inputFilePath = ""; 	// The input file to use
	private String inputFileType = ""; 	// The type of input file (starting point of simulation)
	private Billet billet = null;		// Billet to machine based on input file instructions
	private double maxFeedRate = 1000; 	// The maximum feed rate used for G0
	private boolean show3dPart = true; 	// Show a 3d scatter of the machined part
	private boolean show2dGraphs = true; // Show the MRR and toolwear graphs
	/**
	 * @return the inputFilePath
	 */
	public String getInputFilePath() {
		return inputFilePath;
	}
	/**
	 * @param inputFilePath the inputFilePath to set
	 */
	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	/**
	 * @return the inputFileType
	 */
	public String getInputFileType() {
		return inputFileType;
	}
	/**
	 * @param inputFileType the inputFileType to set
	 */
	public void setInputFileType(String inputFileType) {
		this.inputFileType = inputFileType;
	}
	/**
	 * @return the billet
	 */
	public Billet getBillet() {
		return billet;
	}
	/**
	 * @param billet the billet to set
	 */
	public void setBillet(Billet billet) {
		this.billet = billet;
	}
	/**
	 * @return the maxFeedRate
	 */
	public double getMaxFeedRate() {
		return maxFeedRate;
	}
	/**
	 * @param maxFeedRate the maxFeedRate to set
	 */
	public void setMaxFeedRate(double maxFeedRate) {
		this.maxFeedRate = maxFeedRate;
	}
	/**
	 * @return the show3dPart
	 */
	public boolean isShow3dPart() {
		return show3dPart;
	}
	/**
	 * @param show3dPart the show3dPart to set
	 */
	public void setShow3dPart(boolean show3dPart) {
		this.show3dPart = show3dPart;
	}
	/**
	 * @return the show2dGraphs
	 */
	public boolean isShow2dGraphs() {
		return show2dGraphs;
	}
	/**
	 * @param show2dGraphs the show2dGraphs to set
	 */
	public void setShow2dGraphs(boolean show2dGraphs) {
		this.show2dGraphs = show2dGraphs;
	}

	
}
