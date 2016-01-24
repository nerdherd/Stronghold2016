package org.camsrobotics.frc2016.subsystems;

import org.camsrobotics.lib.Gearbox;
import org.camsrobotics.lib.Loopable;

/**
 * Drivebase Interface
 * 
 * @author Wesley
 *
 */
public class Drive implements Loopable {
	
	/**
	 * Send a DriveSignal to the drivebase to control it
	 * 
	 * @author Wesley
	 *
	 */
	public static class DriveSignal	{
		public double leftSpeed;
		public double rightSpeed;
		
		public DriveSignal(double leftSpeed, double rightSpeed)	{
			this.leftSpeed = leftSpeed;
			this.rightSpeed = rightSpeed;
		}
	}
	
	/**
	 * Abstract controller
	 * 
	 * @author Wesley
	 *
	 */
	public interface DriveController	{
		public DriveSignal get();
	}

	private Gearbox m_leftGearbox;
	private Gearbox m_rightGearbox;
	
	private DriveController m_controller = null;
	private DriveSignal m_signal = null;
	
	public Drive(Gearbox leftGearbox, Gearbox rightGearbox)	{
		m_leftGearbox = leftGearbox;
		m_rightGearbox = rightGearbox;
		m_rightGearbox.setReversed();
	}
	
	public void setController(DriveController controller)	{
		m_controller = controller;
	}
	
	public void setEncoderDistancePerPulse(double distancePerPulse)	{
		m_leftGearbox.setEncoderDistancePerPulse(distancePerPulse);
		m_rightGearbox.setEncoderDistancePerPulse(distancePerPulse);
	}
	
	public int getLeftEncoderRaw()	{
		return m_leftGearbox.getEncoderRaw();
	}
	
	public int getRightEncoderRaw()	{
		return m_rightGearbox.getEncoderRaw();
	}
	
	public double getLeftEncoderDistance()	{
		return m_leftGearbox.getEncoderDistance();
	}
	
	public double getRightEncoderDistance()	{
		return m_rightGearbox.getEncoderDistance();
	}
	
	public void shiftUp()	{
		m_leftGearbox.shiftUp();
		m_rightGearbox.shiftDown();
	}
	
	public void driveOpenLoop(DriveSignal signal)	{
		m_controller = null;
		m_signal = signal;
	}
	
	/**
	 * Let's drive the bot!
	 */
	@Override
	public void update() {
		if(m_controller != null)	{
			m_signal = m_controller.get();
		}
		
		if(m_signal != null)	{
			m_leftGearbox.setSpeed(m_signal.leftSpeed);
			m_rightGearbox.setSpeed(m_signal.rightSpeed);
		}	else	{
			m_leftGearbox.setSpeed(0);
			m_rightGearbox.setSpeed(0);
		}
	}
}
