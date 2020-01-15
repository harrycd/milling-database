/**
 * 
 */
package uk.ac.cf.milling.objects;

import java.util.List;


/**
 * @author Theocharis Alexopoulos
 *
 */
public class KPIs {
	
	private boolean[][][] part; // True are points of machined volume. False are points of the final part.

	private double distance = 0.0;
	private String distanceUnits = "";
	private double time = 0.0; // Time in seconds
	private String timeUnits = "";
	private List<CuttingTool> tools; // A list of the tools used during cutting with their properties and usage
	
	private String analysisData = ""; //The results of GCode simulation
	private long[] mrr; // Elements removed per time unit. Index 1 is the MRR for time = timestep
	private float[] timePoints; // The time points that the analysis file contains information for
	private double[] toolX; // The x coordinate of the tool at the corresponding timePoint (index matching) 
	private double[] toolY; // The y coordinate of the tool at the corresponding timePoint (index matching) 
	private double[] toolZ; // The z coordinate of the tool at the corresponding timePoint (index matching)
	private String[] carouselPocketId; // Id of the pocket in the carousel whose tool is being used at the corresponding timePoint (index matching)
	private double[] spindleSpeed; // The spindle speed of the tool at the corresponding timePoint (index matching) 
	
	private double[] xLoad;
	private double[] yLoad;
	private double[] zLoad;
	private double[] spindleLoad;
	
	
	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * @return the distanceUnits
	 */
	public String getDistanceUnits() {
		return distanceUnits;
	}
	/**
	 * @param distanceUnits the distanceUnits to set
	 */
	public void setDistanceUnits(String distanceUnits) {
		this.distanceUnits = distanceUnits;
	}
	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}
	/**
	 * @param time - the time to set in seconds
	 */
	public void setTime(double time) {
		this.time = time;
	}
	/**
	 * @return the timeUnits
	 */
	public String getTimeUnits() {
		return timeUnits;
	}
	/**
	 * @param timeUnits the timeUnits to set
	 */
	public void setTimeUnits(String timeUnits) {
		this.timeUnits = timeUnits;
	}
	/**
	 * @return the mrr
	 */
	public long[] getMrr() {
		return mrr;
	}
	/**
	 * @param mrr the mrr to set
	 */
	public void setMrr(long[] mrr) {
		this.mrr = mrr;
	}
	/**
	 * @return the part
	 */
	public boolean[][][] getPart() {
		return part;
	}
	/**
	 * @param part the part to set
	 */
	public void setPart(boolean[][][] part) {
		this.part = part;
	}
	/**
	 * @return the timePoints
	 */
	public float[] getTimePoints() {
		return timePoints;
	}
	/**
	 * @param timePoints the timePoints to set
	 */
	public void setTimePoints(float[] timePoints) {
		this.timePoints = timePoints;
	}

	/**
	 * @return the toolX
	 */
	public double[] getToolX() {
		return toolX;
	}

	/**
	 * @param toolX the toolX to set
	 */
	public void setToolX(double[] toolX) {
		this.toolX = toolX;
	}

	/**
	 * @return the toolY
	 */
	public double[] getToolY() {
		return toolY;
	}

	/**
	 * @param toolY the toolY to set
	 */
	public void setToolY(double[] toolY) {
		this.toolY = toolY;
	}

	/**
	 * @return the toolZ
	 */
	public double[] getToolZ() {
		return toolZ;
	}

	/**
	 * @param toolZ the toolZ to set
	 */
	public void setToolZ(double[] toolZ) {
		this.toolZ = toolZ;
	}

	/**
	 * @return the spindleSpeed
	 */
	public double[] getSpindleSpeed() {
		return spindleSpeed;
	}

	/**
	 * @param spindleSpeed the spindleSpeed to set
	 */
	public void setSpindleSpeed(double[] spindleSpeed) {
		this.spindleSpeed = spindleSpeed;
	}

	/**
	 * @return the carouselPocketId
	 */
	public String[] getCarouselPocketId() {
		return carouselPocketId;
	}

	/**
	 * @param carouselPocketId the carouselPocketId to set
	 */
	public void setCarouselPocketId(String[] carouselPocketId) {
		this.carouselPocketId = carouselPocketId;
	}

	/**
	 * @return the tools
	 */
	public List<CuttingTool> getTools() {
		return tools;
	}

	/**
	 * @param tools the tools to set
	 */
	public void setTools(List<CuttingTool> tools) {
		this.tools = tools;
	}
	/**
	 * @return the xLoad
	 */
	public double[] getxLoad() {
		return xLoad;
	}
	/**
	 * @param xLoad the xLoad to set
	 */
	public void setxLoad(double[] xLoad) {
		this.xLoad = xLoad;
	}
	/**
	 * @return the yLoad
	 */
	public double[] getyLoad() {
		return yLoad;
	}
	/**
	 * @param yLoad the yLoad to set
	 */
	public void setyLoad(double[] yLoad) {
		this.yLoad = yLoad;
	}
	/**
	 * @return the zLoad
	 */
	public double[] getzLoad() {
		return zLoad;
	}
	/**
	 * @param zLoad the zLoad to set
	 */
	public void setzLoad(double[] zLoad) {
		this.zLoad = zLoad;
	}
	/**
	 * @return the spindleLoad
	 */
	public double[] getSpindleLoad() {
		return spindleLoad;
	}
	/**
	 * @param spindleLoad the spindleLoad to set
	 */
	public void setSpindleLoad(double[] spindleLoad) {
		this.spindleLoad = spindleLoad;
	}
	/**
	 * @return the analysisData
	 */
	public String getAnalysisData() {
		 return analysisData;
	}
	/**
	 * @param analysisData the analysisData to set
	 */
	public void setAnalysisData(String analysisData) {
		this.analysisData = analysisData;
	}
	
	
	
	
}
