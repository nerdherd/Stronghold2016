package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.subsystems.Drive;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Vision Class
 * 
 * @author Michael
 *
 */
public class Vision {
	private NetworkTable table;
	
	private double[] defaultVal = new double[0];
	
	private int max;
	private int maxKey = 0;
	
	private double[] areas;
	private double[] widths;
	private double[] heights;
	private double[] centerxs;
	private double[] centerys;
	
	private double conversionFactor;
	private double frameHeight = 240;
	private double frameWidth = 320;
	private double cameraAngle = 21.2505055;
	
	private double centerX;
	private double desired;
	private double error;
	/**
	 * Constructs a network table given a key. (For instance 'GRIP/myContourReport')
	 * @param key
	 */
	public Vision(String key) {
		table = NetworkTable.getTable(key);
	}
	
	/**
	 * Returns max value in a given array.
	 * @param array
	 */
	private int getMax(double[] array) {
		if (array != null && array != defaultVal) {
			max = 0;
			for (int i = 0; i < array.length; i++) {
				max = array[i] > max ? i : max;
			}
			return max;
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns width of largest contour.
	 */
	public double getWidth() {
		areas = table.getNumberArray("area", defaultVal);
		widths = table.getNumberArray("width", defaultVal);
		maxKey = getMax(areas);
		return widths[maxKey];
	}
	/**
	 * Returns height of largest contour.
	 */
	public double getHeight() {
		areas = table.getNumberArray("area", defaultVal);
		heights = table.getNumberArray("height", defaultVal);
		maxKey = getMax(areas);
		return heights[maxKey];
	}
	/**
	 * Returns largest measured area.
	 */
	public double getArea() {
		areas = table.getNumberArray("area", defaultVal);
		maxKey = getMax(areas);
		return areas[maxKey];
	}
	/**
	 * Returns centerX of the largest contour.
	 */
	public double getCenterX() {
		areas = table.getNumberArray("area", defaultVal);
		centerxs = table.getNumberArray("centerx", defaultVal);
		maxKey = getMax(areas);
		return centerxs[maxKey];
	}
	/**
	 * Returns centerY of the largest contour. 
	 */
	public double getCenterY() {
		areas = table.getNumberArray("area", defaultVal);
		centerys = table.getNumberArray("centery", defaultVal);
		maxKey = getMax(areas);
		return centerys[maxKey];
	}
	/**
	 * Distance calculated using the height of the contour.  
	 */
	public double getDistance(double pxHeight) {
		conversionFactor = 14/pxHeight;
		return (((frameHeight/2)*(conversionFactor))/Math.tan(cameraAngle*(Math.PI)/180));
	}
	
	public void snapToTarget() {
		centerX = getCenterX();
		desired = frameWidth/2;
		
		error = desired - centerX;
		//power = Math.abs(error) > 5 ? kP * error : 0;
		//TODO wait for drive controller
	}
}
