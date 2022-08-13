/**
 * 
 */
package uk.ac.cf.milling.objects;

import java.util.Arrays;
import java.util.List;

import uk.ac.cf.milling.db.BilletDB;
import uk.ac.cf.milling.db.SettingsDB;

/**
 * This is the class to create the initial billet that will be machined in order to create the part
 * @author Theocharis Alexopoulos
 */
public class Billet {
	/** This is reserved for errors where shape is undefined	 */
	public final static int UNDEFINED = 0;
	/** A shape that is a combination of other shapes */
	public final static int COMPLEX = 1;
	/** Rectangular shape */
	public final static int RECTANGULAR = 2; 
	/** Cylindrical shape */
	public final static int CYLINDRICAL = 3;

	//params held in database
	int billetId;
	String billetName;
	int billetShape;
	int materialId;
	double xBilletMin;
	double xBilletMax;
	double yBilletMin;
	double yBilletMax;
	double zBilletMin;
	double zBilletMax;

	//other params
	boolean[][][] part;


	/**
	 * Billet constructor does not generate a mesh therefore part remains null.
	 * @param billetId - id and primary key for billet entry in the database
	 * @param billetName - name to show for this billet
	 * @param billetShape - shape of the billet (rectangle, cylinder, complex)
	 * @param materialId - id of the material that this billet is made of
	 * @param xBilletMin - min X coordinate of total billet volume when placed on table
	 * @param xBilletMax - max X coordinate of total billet volume when placed on table
	 * @param yBilletMin - min Y coordinate of total billet volume when placed on table
	 * @param yBilletMax - max Y coordinate of total billet volume when placed on table
	 * @param zBilletMin - min Z coordinate of total billet volume when placed on table
	 * @param zBilletMax - max Z coordinate of total billet volume when placed on table
	 */
	public Billet(int billetId, String billetName, int billetShape, int materialId,
			double xBilletMin, double xBilletMax, 
			double yBilletMin, double yBilletMax, 
			double zBilletMin, double zBilletMax) 
	{
		this.billetId = billetId;
		this.billetName = billetName;
		this.billetShape = billetShape;
		this.materialId = materialId; 
		this.xBilletMin = xBilletMin;
		this.xBilletMax = xBilletMax;
		this.yBilletMin = yBilletMin;
		this.yBilletMax = yBilletMax;
		this.zBilletMin = zBilletMin;
		this.zBilletMax = zBilletMax;

	}

	/**
	 * Constructor of a complex billet from primary billets with mesh
	 * @param billetName - The name of the generated complex billet, different from its elements
	 * @param primaryBillets - A List of billets to combine and create the complex
	 * @param materialId - The material of the billet independent of the materials of its elements
	 */
	public Billet(String billetName, List<Billet> primaryBillets, int materialId) {
		this.billetName = billetName;
		this.billetShape = COMPLEX;
		calculateComplexDimensions(primaryBillets);
		this.materialId = materialId;
	}


	/**
	 * Constructor for empty billet
	 */
	public Billet() {
		this.billetId = 0;
		this.billetName = "";
		this.billetShape = 0;
		this.materialId = 0;
		this.xBilletMin = 0;
		this.xBilletMax = 0;
		this.yBilletMin = 0;
		this.yBilletMax = 0;
		this.zBilletMin = 0;
		this.zBilletMax = 0;
	}

	/**
	 * 
	 */
	public final void generateMesh() {
		
		switch (this.billetShape) {
		
		case UNDEFINED :
			System.out.println("Billet shape has to be defined to generate a mesh");
			break;
		
		case COMPLEX :
			generateComplexMesh();
			break;
		
		case RECTANGULAR :
			generateRectangularMesh();
			break;
		
		case CYLINDRICAL :
			generateCylindricalMesh(); 
			break;
		
		default :
			System.out.println("Unknown shape of billet. (shape id: " + this.billetShape + ")");
		}
	}

	/**
	 * @param billets
	 */
	private void generateComplexMesh() {
		BilletDB bdb = new BilletDB();
		List<Billet> billets = bdb.getMeshBillets(this.billetId);
		//Check if there are primary meshes available to generate the billet from.
		if (billets.size() == 0) {
			System.out.println("A list of primary meshes is needed to generate a complex billet mesh.");
			return;
		}

		calculateComplexDimensions(billets);

		//Generate a fully machined mesh on which the primary billets will be added
		this.generateRectangularMesh();
		initialisePartToTrue();

		double elemSize = Double.parseDouble(new SettingsDB().getSetting("elementSize"));

		//Shape the mesh based on the provided billets.
		for (Billet billet : billets) {
			billet.generateMesh();
			boolean[][][] pPart = billet.getPart();

			//Get the coordinates/indexes to copy the shape into
			int firstIndexX = (int) ((billet.getXBilletMin() - this.xBilletMin) / elemSize);
			int firstIndexY = (int) ((billet.getYBilletMin() - this.yBilletMin) / elemSize);
			int firstIndexZ = (int) ((billet.getZBilletMin() - this.zBilletMin) / elemSize);

//			int lastIndexX = (int) ((billet.getXBilletMax() - this.xBilletMax) / elemSize);
//			int lastIndexY = (int) ((billet.getYBilletMax() - this.yBilletMax) / elemSize);
//			int lastIndexZ = (int) ((billet.getZBilletMax() - this.zBilletMax) / elemSize);
			int lastIndexX = firstIndexX + pPart.length - 1;
			int lastIndexY = firstIndexY + pPart[0].length - 1;
			int lastIndexZ = firstIndexZ + pPart[0][0].length - 1;

			//The complex mesh iterates from first to last element that the provided mesh is positioned. 
			//The provided mesh iterates from zero to end
			for (int xIndex = firstIndexX, pXIndex = 0; xIndex <= lastIndexX; xIndex++, pXIndex++) {
				for (int yIndex = firstIndexY, pYIndex = 0; yIndex <= lastIndexY; yIndex++, pYIndex++) {
					for (int zIndex = firstIndexZ, pZIndex = 0; zIndex <= lastIndexZ; zIndex++, pZIndex++) {
						this.part[xIndex][yIndex][zIndex] = pPart[pXIndex][pYIndex][pZIndex];
					}
				}
			}

		}

	}

	/**
	 * Turns a vergin part to a fully machines one (all elements true)
	 */
	private void initialisePartToTrue() {
		for (int i = 0; i < this.part.length; i++) {
			for (int j = 0; j < this.part[0].length; j++) {
				for (int k = 0; k < this.part[0][0].length; k++) {
					this.part[i][j][k] = true;
				}
			}
		}
		
	}

	/**
	 * @param billets 
	 */
	private void calculateComplexDimensions(List<Billet> billets) {
		//Calculate the dimensions of the complex billet
		double xMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;

		double yMin = Double.MAX_VALUE;
		double yMax = Double.MIN_VALUE;

		double zMin = Double.MAX_VALUE;
		double zMax = Double.MIN_VALUE;

		for (Billet billet : billets) {
			if (billet.getXBilletMin() < xMin) xMin = billet.getXBilletMin(); 
			if (billet.getYBilletMin() < yMin) yMin = billet.getYBilletMin(); 
			if (billet.getZBilletMin() < zMin) zMin = billet.getZBilletMin();
			if (billet.getXBilletMax() > xMax) xMax = billet.getXBilletMax();
			if (billet.getYBilletMax() > yMax) yMax = billet.getYBilletMax();
			if (billet.getZBilletMax() > zMax) zMax = billet.getZBilletMax();
		}

		this.xBilletMin = xMin;
		this.xBilletMax = xMax;
		this.yBilletMin = yMin;
		this.yBilletMax = yMax;
		this.zBilletMin = zMin;
		this.zBilletMax = zMax;

	}

	/**
	 * Generates the boolean[][][] part representing the mesh of rectangular cross section billet
	 */
	private void generateRectangularMesh() {
		double elemSize = Double.parseDouble(new SettingsDB().getSetting("elementSize"));

		// Number of elements in each dimension
		int xBilletElCount = (int) (1 + (xBilletMax - xBilletMin)/elemSize);
		int yBilletElCount = (int) (1 + (yBilletMax - yBilletMin)/elemSize);
		int zBilletElCount = (int) (1 + (zBilletMax - zBilletMin)/elemSize);

		// Initialise the billet mesh
		this.part = new boolean[xBilletElCount][yBilletElCount][zBilletElCount];
	}

	/**
	 * Generates the boolean[][][] part representing the mesh of a cylindrical cross section billet
	 */
	private void generateCylindricalMesh() {
		//Create the box containing the cylinder
		generateRectangularMesh();


		int xBilletElCount = part.length;
		int yBilletElCount = part[0].length;
		int zBilletElCount = part[0][0].length;

		//find the max diameter that could fit in this volume
		double radius = Math.min(xBilletElCount, yBilletElCount) / 2;

		//find the centre of circular cross section
		double centreX = xBilletElCount / 2;
		double centreY = yBilletElCount / 2;

		//Machine the elements that are out of the required cylinder
		for (int z = 0; z < zBilletElCount; z++) {
			for (int x = 0; x < xBilletElCount; x++) {
				for (int y = 0; y < yBilletElCount; y++) {
					if (Math.sqrt((x-centreX) * (x-centreX) + (y-centreY) * (y-centreY)) > radius){
						//Then remove this element because it is out of the cylinder
						this.part[x][y][z] = true;
					}
				}
			}
		}
	}


	/*
	 * Getters and Setters
	 */

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
	 * @return the billetShape
	 */
	public int getBilletShape() {
		return billetShape;
	}


	/**
	 * @param billetShape the billetShape to set
	 */
	public void setBilletShape(int billetShape) {
		this.billetShape = billetShape;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.billetName;
	}
}
