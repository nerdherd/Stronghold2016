package org.camsrobotics.frc2016;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * NetworkTables Vision Adapter
 * 
 * @author Michael
 *
 */
public class Vision {
	private NetworkTable table;
	
	private double[] m_defaultVal = new double[0];
	
	private int m_max;
	private int m_maxKey = 0;
	
	private static Vision m_instance = null;
	
	/**
	 * Constructs a network table given a key. (For instance 'GRIP/myContourReport')
	 * @param key
	 */
	private Vision() {
		table = NetworkTable.getTable("GRIP/myContourReport");
	}
	 /**
	  * Get one instance of vision object. (Since we will never need more than one.)
	  * @Vision Instance
	  */
	public static Vision getInstance() {
		if (m_instance == null) {
			m_instance = new Vision();
		}
		return m_instance;
	}
	/**
	 * Returns max value in a given array.
	 * @param array
	 * @throws Exception 
	 */
	private int getMax(double[] array) throws Exception {
		if (array != m_defaultVal) {
			m_max = 0;
			for (int i = 0; i < array.length; i++) {
				m_max = array[i] > m_max ? i : m_max;
			}
			return m_max;
		} else {
			throw new Exception("There is nothing in the array.");
		}
	}
	
	/**
	 * Returns width of largest contour. Only works with contour reports.
	 * @throws Exception 
	 */
	public double getWidth() throws Exception {
		double[] m_areas = table.getNumberArray("area", m_defaultVal);
		double[] m_widths = table.getNumberArray("width", m_defaultVal);
		m_maxKey = getMax(m_areas);
		return m_widths[m_maxKey];
	}
	/**
	 * Returns height of largest contour, or the length of the longest line.
	 * @throws Exception 
	 */
	public double getHeight() throws Exception {
		double[] m_areas = table.getNumberArray("area", m_defaultVal);
		double[] m_heights = table.getNumberArray("height", m_defaultVal);
		m_maxKey = getMax(m_areas);
		return m_heights[m_maxKey];
	}
	/**
	 * Returns largest measured area. Only works for contour reports.
	 * @throws Exception 
	 */
	public double getArea() throws Exception {
		double[] m_areas = table.getNumberArray("area", m_defaultVal);
		m_maxKey = getMax(m_areas);
		return m_areas[m_maxKey];	
	}
	/**
	 * Returns centerX of the largest contour or longest line.
	 * @throws Exception 
	 */
	public double getCenterX() throws Exception {
		double[] m_areas = table.getNumberArray("area", m_defaultVal);
		double[] m_centerxs = table.getNumberArray("centerX", m_defaultVal);
		m_maxKey = getMax(m_areas);
		return m_centerxs[m_maxKey];
	}
	/**
	 * Returns centerY of the largest contour. 
	 * @throws Exception 
	 */
	public double getCenterY() throws Exception {
		double[] m_areas = table.getNumberArray("area", m_defaultVal);
		double[] m_centerys = table.getNumberArray("centerY", m_defaultVal);
		m_maxKey = getMax(m_areas);
		return m_centerys[m_maxKey];
	}
	//TODO Distance method, clean this code up
}
