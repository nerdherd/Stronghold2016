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
	
	private double[] m_defaultVal = new double[0];
	
	private int m_max;
	private int m_maxKey = 0;
	
	private double[] m_areas;
	private double[] m_widths;
	private double[] m_heights;
	private double[] m_centerxs;
	private double[] m_centerys;
	
	private String m_type;
	
	/**
	 * Constructs a network table given a key. (For instance 'GRIP/myContourReport')
	 * @param key
	 */
	public Vision(String key) {
		table = NetworkTable.getTable(key);
		if (key == "GRIP/myContourReport") {
			m_type = "contour";
		} else if (key == "GRIP/myLinesReport") {
			m_type = "line";
		} else {
			m_type = " ";
		}
	}
	
	/**
	 * Returns max value in a given array.
	 * @param array
	 */
	private int getMax(double[] array) {
		if (array != null && array != m_defaultVal) {
			m_max = 0;
			for (int i = 0; i < array.length; i++) {
				m_max = array[i] > m_max ? i : m_max;
			}
			return m_max;
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns width of largest contour. Only works with contour reports.
	 */
	public double getWidth() {
		if (m_type == "contour") {
			m_areas = table.getNumberArray("area", m_defaultVal);
			m_widths = table.getNumberArray("width", m_defaultVal);
			m_maxKey = getMax(m_areas);
			return m_widths[m_maxKey];
		} else {
			return 0;
		}
	}
	/**
	 * Returns height of largest contour, or the length of the longest line.
	 */
	public double getHeight() {
		if (m_type == "contour") {
			m_areas = table.getNumberArray("area", m_defaultVal);
			m_heights = table.getNumberArray("height", m_defaultVal);
			m_maxKey = getMax(m_areas);
			return m_heights[m_maxKey];
		} else if (m_type == "line") {
			m_heights = table.getNumberArray("length", m_defaultVal);
			m_maxKey = getMax(m_heights);
			return m_heights[m_maxKey];
		} else {
			return 0;
		}
	}
	/**
	 * Returns largest measured area. Only works for contour reports.
	 */
	public double getArea() {
		if (m_type == "contour") {
			m_areas = table.getNumberArray("area", m_defaultVal);
			m_maxKey = getMax(m_areas);
			return m_areas[m_maxKey];
		} else {
			return 0;
		}		
	}
	/**
	 * Returns centerX of the largest contour or longest line.
	 */
	public double getCenterX() {
		if (m_type == "contour") {
			m_areas = table.getNumberArray("area", m_defaultVal);
			m_centerxs = table.getNumberArray("centerX", m_defaultVal);
			m_maxKey = getMax(m_areas);
			return m_centerxs[m_maxKey];
		} else if (m_type == "line") {
			m_heights = table.getNumberArray("length", m_defaultVal);
			m_centerxs = table.getNumberArray("centerX", m_defaultVal);
			m_maxKey = getMax(m_heights);
			return m_centerxs[m_maxKey];
		} else {
			return 0;
		}
	}
	/**
	 * Returns centerY of the largest contour. 
	 */
	public double getCenterY() {
		if (m_type == "contour") {
			m_areas = table.getNumberArray("area", m_defaultVal);
			m_centerys = table.getNumberArray("centerY", m_defaultVal);
			m_maxKey = getMax(m_areas);
			return m_centerys[m_maxKey];
		} else if (m_type == "line") {
			m_heights = table.getNumberArray("length", m_defaultVal);
			m_centerys = table.getNumberArray("centerY", m_defaultVal);
			m_maxKey = getMax(m_heights);
			return m_centerys[m_maxKey];
		} else {
			return 0;
		}
	}
	//TODO Distance method, clean this code up
}
