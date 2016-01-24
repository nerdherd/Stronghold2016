package org.camsrobotics.lib;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Gearbox Interface
 * 
 * @author Wesley
 *
 */
public class Gearbox {
	private SpeedController m_motor1;
	private SpeedController m_motor2;
	private Encoder m_encoder;
	private DoubleSolenoid m_shifter;
	private boolean m_reversed;
	
	public Gearbox(SpeedController motor1, SpeedController motor2, Encoder encoder, DoubleSolenoid shifter)	{
		m_motor1 = motor1;
		m_motor2 = motor2;
		
		m_encoder = encoder;
		m_shifter = shifter;
	}
	
	public Gearbox(SpeedController motor1, SpeedController motor2, Encoder encoder)	{
		this(motor1, motor2, encoder, null);
	}
	
	public Gearbox(SpeedController motor1, SpeedController motor2, DoubleSolenoid shifter)	{
		this(motor1, motor2, null, shifter);
	}
	
	public Gearbox(SpeedController motor1, SpeedController motor2)	{
		this(motor1, motor2, null, null);
	}
	
	/**
	 * Sets whether to inverse the motor speed
	 * 
	 * @param reversed
	 */
	public void setReversed(boolean reversed)	{
		m_reversed = reversed;
	}
	
	/**
	 * Sets the reversed value to true
	 */
	public void setReversed()	{
		setReversed(true);
	}
	
	/**
	 * Shift to high gear
	 */
	public void shiftUp()	{
		if(m_shifter != null)	{
			m_shifter.set(DoubleSolenoid.Value.kForward);
		}
	}
	
	/**
	 * Shift to low gear
	 */
	public void shiftDown()	{
		if(m_shifter != null)	{
			m_shifter.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	/**
	 * Sets the Encoder distance per pulse
	 * 
	 * @param distancePerPulse
	 */
	public void setEncoderDistancePerPulse(double distancePerPulse)	{
		m_encoder.setDistancePerPulse(distancePerPulse);
	}
	
	/**
	 * Gets the encoder distance
	 * 
	 * @return
	 */
	public double getEncoderDistance()	{
		return m_encoder.getDistance();
	}
	
	/**
	 * Gets the raw value of the encoder
	 * 
	 * @return
	 */
	public int getEncoderRaw()	{
		return m_encoder.getRaw();
	}
	
	/**
	 * Sets the motor speed
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed)	{
		m_motor1.set(speed * (m_reversed ? -1 : 1));
		m_motor2.set(speed * (m_reversed ? -1 : 1));
	}
}
