/**
 * 
 */
package uk.ac.cf.milling.objects;

/**
 * @author Theocharis Alexopoulos
 * This is the class to create the initial billet that will be machined in order to create the part
 */
public class Billet {
	int billetId = 0;
	String billetName = "";
	double xBilletMin = 0;
	double xBilletMax = 0;
	double yBilletMin = 0;
	double yBilletMax = 0;
	double zBilletMin = 0;
	double zBilletMax = 0;
	Material material = null;
	
	/**
	 * This is a constructor of a billet. The billet is a cube whose edges are parallel to the axes
	 * @param xBilletMin
	 * @param xBilletMax
	 * @param yBilletMin
	 * @param yBilletMax
	 * @param zBilletMin
	 * @param zBilletMax
	 */
	public Billet(
			int billetId,
			String billetName,
			double xBilletMin, double xBilletMax, 
			double yBilletMin, double yBilletMax, 
			double zBilletMin, double zBilletMax,
			Material material) {
		this.billetId = billetId;
		this.billetName = billetName;
		this.xBilletMin = xBilletMin;
		this.xBilletMax = xBilletMax;
		this.yBilletMin = yBilletMin;
		this.yBilletMax = yBilletMax;
		this.zBilletMin = zBilletMin;
		this.zBilletMax = zBilletMax;
		this.material = material;
	}
	
	//Constructor for empty billet
	public Billet() {}
	
	/**
	 * @return the xBilletMin
	 */
	public double getXBilletMin() {
		return xBilletMin;
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

	/**
	 * @return the billetName
	 */
	public String getBilletName() {
		return billetName;
	}

	/**
	 * @param billetName the billetName to set
	 */
	public void setBilletName(String billetName) {
		this.billetName = billetName;
	}

	/**
	 * @param xBilletMin the xBilletMin to set
	 */
	public void setXBilletMin(double xBilletMin) {
		this.xBilletMin = xBilletMin;
	}
	/**
	 * @return the xBilletMax
	 */
	public double getXBilletMax() {
		return xBilletMax;
	}
	/**
	 * @param xBilletMax the xBilletMax to set
	 */
	public void setXBilletMax(double xBilletMax) {
		this.xBilletMax = xBilletMax;
	}
	/**
	 * @return the yBilletMin
	 */
	public double getYBilletMin() {
		return yBilletMin;
	}
	/**
	 * @param yBilletMin the yBilletMin to set
	 */
	public void setYBilletMin(double yBilletMin) {
		this.yBilletMin = yBilletMin;
	}
	/**
	 * @return the yBilletMax
	 */
	public double getYBilletMax() {
		return yBilletMax;
	}
	/**
	 * @param yBilletMax the yBilletMax to set
	 */
	public void setYBilletMax(double yBilletMax) {
		this.yBilletMax = yBilletMax;
	}
	/**
	 * @return the zBilletMin
	 */
	public double getZBilletMin() {
		return zBilletMin;
	}
	/**
	 * @param zBilletMin the zBilletMin to set
	 */
	public void setZBilletMin(double zBilletMin) {
		this.zBilletMin = zBilletMin;
	}
	/**
	 * @return the zBilletMax
	 */
	public double getZBilletMax() {
		return zBilletMax;
	}
	/**
	 * @param zBilletMax the zBilletMax to set
	 */
	public void setZBilletMax(double zBilletMax) {
		this.zBilletMax = zBilletMax;
	}
	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}
	/**
	 * @param material the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.billetName;
	}
}
