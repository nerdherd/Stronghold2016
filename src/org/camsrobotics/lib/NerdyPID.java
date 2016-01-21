package org.camsrobotics.lib;

/**
 * PID Looper
 * 
 * @author Wesley
 *
 */
public class NerdyPID {
	// Constants
	private double m_kP;
	private double m_kI;
	private double m_kD;
	
	private double m_minimumOutput = -1;
	private double m_maximumOutput = 1;
	
	private boolean m_continuous = false; //Do the endpoints wrap around? Ex: Gyro
	
	private double m_lastError = 0;
	private double m_totalError = 0;
	private double m_desired = 0;
	
	
	
	public NerdyPID()	{
		
	}
}
