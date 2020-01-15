/**
 * 
 */
package uk.ac.cf.milling.objects;

/**
 * @author Theocharis Alexopoulos
 * The surface profile of the tool. 
 * Indicates the distance of each surface element from the centre point at the nose of the tool
 * This reference point is chosen as it is normally the 
 */
public class CuttingToolProfile {
	int toolId = 0;
	double distanceFromNose = 0; 	//[mm] The axial distance from the tool's nose
	double distanceFromCentre = 0; 	//[mm] The radial distance from tools central axis (local radius of tool)
	int insertionsPerTooth = 0; 	//[  ] How many times the tooth has penetrated the material volume
	double materialRemoved = 0; 	//[mm] The length of material that has been machined by this tool profile
	boolean axialProfile = false;	//[  ] true if the profile can get axial usage. false if not
	boolean radialProfile = false;	//[  ] true if the profile can get radial usage. false if not
	
	/**
	 * @param materialRemoved - Length of machined material to append to this tool profile
	 */
	public void appendMaterialRemoved(double materialRemoved){
		this.materialRemoved += materialRemoved;
	}
	
	/**
	 * @param insertions - number of insertions of the tooth to the billet volume
	 */
	public void appendInsertionsPerTooth(int insertions){
		this.insertionsPerTooth += insertions;
	}
	
	/**
	 * @return the toolId
	 */
	public int getToolId() {
		return toolId;
	}
	/**
	 * @param toolId the toolId to set
	 */
	public void setToolId(int toolId) {
		this.toolId = toolId;
	}
	/**
	 * @return the distanceFromNose
	 */
	public double getDistanceFromNose() {
		return distanceFromNose;
	}
	/**
	 * @param distanceFromNose the distanceFromNose to set
	 */
	public void setDistanceFromNose(double distanceFromNose) {
		this.distanceFromNose = distanceFromNose;
	}
	/**
	 * @return the distanceFromCentre
	 */
	public double getDistanceFromCentre() {
		return distanceFromCentre;
	}
	/**
	 * @param distanceFromCentre the distanceFromCentre to set
	 */
	public void setDistanceFromCentre(double distanceFromCentre) {
		this.distanceFromCentre = distanceFromCentre;
	}
	/**
	 * @return the insertionsPerTooth
	 */
	public int getInsertionsPerTooth() {
		return insertionsPerTooth;
	}
	/**
	 * @param insertionsPerTooth the insertionsPerTooth to set
	 */
	public void setInsertionsPerTooth(int insertionsPerTooth) {
		this.insertionsPerTooth = insertionsPerTooth;
	}
	/**
	 * @return the materialRemoved
	 */
	public double getMaterialRemoved() {
		return materialRemoved;
	}
	/**
	 * @param materialRemoved the materialRemoved to set
	 */
	public void setMaterialRemoved(double materialRemoved) {
		this.materialRemoved = materialRemoved;
	}
	/**
	 * @return the axialProfile
	 */
	public boolean isAxialProfile() {
		return axialProfile;
	}
	/**
	 * @param axialProfile the axialProfile to set
	 */
	public void setAxialProfile(boolean axialProfile) {
		this.axialProfile = axialProfile;
	}
	/**
	 * @return the radialProfile
	 */
	public boolean isRadialProfile() {
		return radialProfile;
	}
	/**
	 * @param radialProfile the radialProfile to set
	 */
	public void setRadialProfile(boolean radialProfile) {
		this.radialProfile = radialProfile;
	}
	
}
