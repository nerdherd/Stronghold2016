package org.camsrobotics.frc2016.subsystems;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.Vision;
import org.camsrobotics.lib.Gearbox;
import org.camsrobotics.lib.Subsystem;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drivebase Interface
 * 
 * @author Wesley
 *
 */
public class Drive extends Subsystem {
	
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
		
		public static DriveSignal kStop = new DriveSignal(0, 0);
	}
	
	public static class DriveSensorSignal	{
		public double yaw;
		public double leftEncoderDistance;
		public double rightEncoderDistance;
		public double vision;
		
		public DriveSensorSignal(double yaw, double leftEncoderDistance, double rightEncoderDistance, double vision)	{
			this.yaw = yaw;
			this.leftEncoderDistance = leftEncoderDistance;
			this.rightEncoderDistance = rightEncoderDistance;
			this.vision = vision;
		}
	}
	
	/**
	 * Controller Interface
	 * 
	 * @author Wesley
	 *
	 */
	public interface DriveController	{
		public DriveSignal get(DriveSensorSignal sig);
		
		public boolean isOnTarget();
		
		public void setPID(double p, double i, double d);
	}

	private Gearbox m_leftGearbox;
	private Gearbox m_rightGearbox;
	
	private AHRS m_nav;
	
	private DriveController m_controller = null;
	private DriveSignal m_signal = null;
	
	private Vision m_table = Vision.getInstance();
	
	public Drive(String name, Gearbox leftGearbox, Gearbox rightGearbox, AHRS nav)	{
		super(name);
		m_leftGearbox = leftGearbox;
		m_rightGearbox = rightGearbox;
		m_rightGearbox.setReversed();
		m_nav = nav;
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
	
	public void shiftDown()	{
		m_leftGearbox.shiftUp();
		m_rightGearbox.shiftUp();
	}
	
	public void shiftUp()	{
		m_leftGearbox.shiftDown();
		m_rightGearbox.shiftDown();
	}
	
	public void driveOpenLoop(DriveSignal signal)	{
		m_controller = null;
		m_signal = signal;
	}
	
	public boolean isOnTarget()	{
		return m_controller != null && m_controller.isOnTarget();
	}
	
	public double getYaw()	{
		return m_nav.getYaw();
	}
	
	public double getVision() {
		try {
			return m_table.getCenterX();
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.kCameraDefault;
		}
	}
	
	public void setPID(double p, double i, double d)	{
		if(m_controller != null)	{
			m_controller.setPID(p, i, d);
		}
	}
	
	public void stop()	{
		m_leftGearbox.setSpeed(0);
		m_rightGearbox.setSpeed(0);
		m_signal = null;
	}
	
	public void zero()	{
		m_leftGearbox.resetEncoder();
		m_rightGearbox.resetEncoder();
	}
	
	/**
	 * Let's drive the bot!
	 */
	@Override
	public void update() {
		if(m_controller != null)	{
			m_signal = m_controller.get(new DriveSensorSignal(getYaw(), getLeftEncoderDistance(), getRightEncoderDistance(), getVision()));
		}
		
		if(m_signal != null)	{
			m_leftGearbox.setSpeed(m_signal.leftSpeed);
			m_rightGearbox.setSpeed(m_signal.rightSpeed);
		}	else	{
			m_leftGearbox.setSpeed(0);
			m_rightGearbox.setSpeed(0);
		}
	}

	@Override
	public void reportState() {
		SmartDashboard.putNumber("LeftSpeed", m_signal.leftSpeed);
		SmartDashboard.putNumber("RightSpeed", m_signal.rightSpeed);
		
		SmartDashboard.putNumber("Yaw", getYaw());
		SmartDashboard.putNumber("LeftEncoder", getLeftEncoderDistance());
		SmartDashboard.putNumber("RightEncoder", getRightEncoderDistance());
		SmartDashboard.putNumber("VisionCenterX", getVision());
		
		try {
			SmartDashboard.putNumber("Vision Distance", m_table.getDistance());
		} catch (Exception e) {
		}
	}
}
