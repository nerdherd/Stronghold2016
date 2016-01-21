package org.camsrobotics.lib;

import edu.wpi.first.wpilibj.util.BoundaryException;

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
	
	private double m_error = 0;
	private double m_lastError = 0;
	private double m_totalError = 0;
	private double m_desired = 0;
	private double m_result = 0;
	private double m_lastInput = Double.NaN;
	
	/**
	 * Default Constructor. Need to call the setPID method after this.
	 * Default PID values are all 0.
	 */
	public NerdyPID()	{
		setPID(0, 0, 0);
	}
	
	/**
	 * Constructs a PID controller with the set PID Values
	 * 
	 * @param p
	 * @param i
	 * @param d
	 */
	public NerdyPID(double p, double i, double d)	{
		setPID(p, i, d);
	}
	
	/**
	 * The magical calculation
	 * 
	 * @param input The sensor input
	 * @return The magical output
	 */
	public double calculate(double input)	{
		m_lastInput = input;
		m_lastError = m_error;
		m_error = m_desired - input;
		m_totalError += m_error;
		
		m_result = NerdyMath.threshold((m_kP * m_error) + (m_kI * m_totalError) - (m_kD * (m_error - m_lastError)), m_minimumOutput, m_maximumOutput);
		
		return m_result;
	}
	
	/**
	 * Sets the PID Values
	 * 
	 * @param p
	 * @param i
	 * @param d
	 */
	public void setPID(double p, double i, double d)	{
		m_kP = p;
		m_kI = i;
		m_kD = d;
	}
	
	/**
	 * Sets the target value
	 * 
	 * @param desired
	 */
	public void setDesired(double desired)	{
		m_desired = desired;
	}
	
	public void setOutputRange(double minimum, double maximum)	{
		if(minimum > maximum)	{
			throw new BoundaryException("Lower bound is greater than upper bound");
		}
	}
	
	/**
	 * Gets the output of the controller
	 * 
	 * @return The last calculated output.
	 */
	public double get()	{
		return m_result;
	}
	
	/**
     * Return true if the error is within the tolerance
     *
     * @return true if the error is less than the tolerance
     */
    public boolean onTarget(double tolerance) {
        return m_lastInput != Double.NaN && Math.abs(m_lastInput - m_desired) < tolerance;
    }
}
