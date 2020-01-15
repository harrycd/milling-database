/**
 * 
 */
package uk.ac.cf.milling.objects;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class Nc {
	int ncId = 0;
	String ncPath = "";
	String analysisPath = "";
	String monitoringPath = "";
	int billetId = 0;
	/**
	 * @return the ncId
	 */
	public int getNcId() {
		return ncId;
	}
	/**
	 * @param ncId the ncId to set
	 */
	public void setNcId(int ncId) {
		this.ncId = ncId;
	}
	/**
	 * @return the ncPath
	 */
	public String getNcPath() {
		return ncPath;
	}
	/**
	 * @param ncPath the ncPath to set
	 */
	public void setNcPath(String ncPath) {
		this.ncPath = ncPath;
	}
	/**
	 * @return the analysisPath
	 */
	public String getAnalysisPath() {
		return analysisPath;
	}
	/**
	 * @param analysisPath the analysisPath to set
	 */
	public void setAnalysisPath(String analysisPath) {
		this.analysisPath = analysisPath;
	}
	/**
	 * @return the monitoringPath
	 */
	public String getMonitoringPath() {
		return monitoringPath;
	}
	/**
	 * @param monitoringPath the monitoringPath to set
	 */
	public void setMonitoringPath(String monitoringPath) {
		this.monitoringPath = monitoringPath;
	}
	/**
	 * @return the billetId
	 */
	public int getBilletId() {
		return billetId;
	}
	/**
	 * @param billetId the billetId to set
	 */
	public void setBilletId(int billetId) {
		this.billetId = billetId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.ncPath.substring(ncPath.lastIndexOf("\\") + 1);
	}
	
}
