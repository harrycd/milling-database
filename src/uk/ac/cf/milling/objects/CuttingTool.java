/**
 * 
 */
package uk.ac.cf.milling.objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Holds all information related to a cutting tool and contains methods related<br>
 * to cutting tool management and manipulation
 * @author Theocharis Alexopoulos
 *
 */
public class CuttingTool {
	
	int toolId = 0;
	String toolName = "";
	String toolType = "";
	String toolSeries = "";
	int toolTeeth = 0;
	double toolLength = 0; //The total length of the tool (affecting distance from nose to spindle)
	List<CuttingToolProfile> cuttingToolProfiles = new ArrayList<CuttingToolProfile>();
	
	/**	Simplified end mill tool represented by a cylinder */
	public static String END_MILL = "End Mill";
	
	/** Simplified shape of ball nose tool represented by a cylinder ending to a part of a sphere. */
	public static String BALL_NOSE_MILL = "Ball Nose Mill";
	
	/** Simplified version of a slot mill represented by a cylinder at the height of the slot cutter. */
	public static String SLOT_MILL = "Slot Mill";
	
	
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
	 * @return the toolName
	 */
	public String getToolName() {
		return toolName;
	}


	/**
	 * @param toolName the toolName to set
	 */
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}


	/**
	 * @return the toolType
	 */
	public String getToolType() {
		return toolType;
	}


	/**
	 * @param toolType the toolType to set
	 */
	public void setToolType(String toolType) {
		this.toolType = toolType;
	}


	/**
	 * @return the toolTeeth
	 */
	public int getToolTeeth() {
		return toolTeeth;
	}


	/**
	 * @param toolTeeth the toolTeeth to set
	 */
	public void setToolTeeth(int toolTeeth) {
		this.toolTeeth = toolTeeth;
	}
	
	/**
	 * @return the toolLength
	 */
	public double getToolLength() {
		return toolLength;
	}
	
	/**
	 * @param toolLength the toolLength to set
	 */
	public void setToolLength(double toolLength) {
		this.toolLength = toolLength;
	}
	
	/**
	 * @return the cuttingToolProfile
	 */
	public List<CuttingToolProfile> getProfiles() {
		return cuttingToolProfiles;
	}

	/**
	 * @param profiles - the cuttingToolProfile to set
	 */
	public void setProfiles(List<CuttingToolProfile> profiles) {
		this.cuttingToolProfiles = profiles;
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
	 * @return the list of axial profiles sorted ascending based on distance from cutting tool nose
	 */
	public List<CuttingToolProfile> getAxialProfile(){
		List<CuttingToolProfile> axialProfiles = this.cuttingToolProfiles.stream().filter(o -> o.isAxialProfile()).collect(Collectors.toList()); 
		axialProfiles.sort(Comparator.comparingDouble(CuttingToolProfile::getDistanceFromNose));
		return axialProfiles;
	}

	/**
	 * @return the list of radial profiles sorted ascending based on distance from cutting tool axial centre
	 */
	public List<CuttingToolProfile> getRadialProfile(){
		List<CuttingToolProfile> radialProfiles = this.cuttingToolProfiles.stream().filter(o -> o.isRadialProfile()).collect(Collectors.toList());
		radialProfiles.sort(Comparator.comparingDouble(CuttingToolProfile::getDistanceFromCentre));
		return radialProfiles;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\ttoolName : " + toolName);
		sb.append("\ttoolType : " + toolType);
		sb.append("\ttoolSeries : " + toolSeries);
		sb.append("\ttoolTeeth : " + toolTeeth);
		sb.append("\ttoolLength : " + toolLength);
		return sb.toString();
	}
}