package org.camsrobotics.frc2016.subsystems;

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
	public class DriveSignal	{
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

	@Override
	public void update() {
		//TODO Add the motor controllers...wait for the gearbox class
	}
}
