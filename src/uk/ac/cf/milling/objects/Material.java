/**
 * 
 */
package uk.ac.cf.milling.objects;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class Material {
	int materialId = 0;
	String materialName = "";
	double torqueFactor = 0;
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
	 * @return the materialName
	 */
	public String getMaterialName() {
		return materialName;
	}
	/**
	 * @param materialName the materialName to set
	 */
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	/**
	 * @return the torqueFactor
	 */
	public double getTorqueFactor() {
		return torqueFactor;
	}
	/**
	 * @param torqueFactor the torqueFactor to set
	 */
	public void setTorqueFactor(double torqueFactor) {
		this.torqueFactor = torqueFactor;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.materialName;
	}
	
}
