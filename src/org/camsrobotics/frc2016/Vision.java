package org.camsrobotics.frc2016;

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
	
	private double[] areas;
	private double[] widths;
	private double[] heights;
	
	private double largestWidth;
	private double largestHeight;
	private double largestArea;
	
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
	private double getMax(double[] array) {
		if (array != null && array != defaultVal) {
			max = 0;
			for (int i = 0; i < array.length; i++) {
				max = array[i] > max ? i : max;
			}
			return array[max];
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns largest measured width.
	 */
	public double getWidth() {
		widths = table.getNumberArray("width", defaultVal);
		largestWidth = getMax(widths);
		return largestWidth;
	}
	/**
	 * Returns largest measured height.
	 */
	public double getHeight() {
		heights = table.getNumberArray("height", defaultVal);
		largestHeight = getMax(heights);
		return largestHeight;
	}
	/**
	 * Returns largest measured area.
	 */
	public double getArea() {
		areas = table.getNumberArray("area", defaultVal);
		largestArea = getMax(areas);
		return largestArea;
	}
}
