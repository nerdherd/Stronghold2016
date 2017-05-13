package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.subsystems.Shooter;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * NetworkTables Vision Adapter
 * 
 * @author Mike
 *
 */
public class Vision {
	private NetworkTable table;
	
	private double[] m_defaultVal = new double[0];
	
	private int m_max;
	private int m_maxKey = 0;
	
	private double m_apparentWidth  = 0;
	private double m_actualWidth    = Constants.kCameraActualWidth;
	private double m_apparentHeight = 0;
	private double m_actualHeightPX = 0;
	private double m_actualHeight	= Constants.kCameraActualHeight;
	private double m_viewAngle		= 0;
	private double m_theta			= 0;
	private double m_distanceToGoal = 0;
	private double m_distance		= 0;
	
	private CANTalon m_lifter = HardwareAdapter.kShooterLift;
	
//	private CANTalon m_lifter = HardwareAdapter.kShooterLift;
	
	private static Vision m_instance = null;
	
	/**
	 * Constructs a network table given a key. (For instance 'GRIP/myContourReport')
	 * @param key
	 */
	private Vision() {
		table = NetworkTable.getTable("GRIP/Courtyard");
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
	
	/** 
	 * After vertical alignment, gets shooter angle and uses it to calculate distance.
	 * @return Distance in inches
	 * @throws Exception
	 */
	public double getDistance() throws Exception	{
		if(Math.abs(getCenterY()-Constants.kCameraFrameHeight/2) < 3)	{
			m_theta = m_lifter.getPosition()/360+0.595; //Convert Encoder Reading to Angle
			m_distance = Math.tan(m_theta)/Constants.kGoalHeight;
		}	else	{
			throw new Exception("Target is not centered!");
		}
		return m_distance;
	}
}
