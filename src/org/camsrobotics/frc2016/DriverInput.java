package org.camsrobotics.frc2016;

import org.camsrobotics.lib.NerdyJoystick;

/**
 * Driver Station Interface
 * 
 * @author Wesley
 *
 */
public class DriverInput {
	
	public static NerdyJoystick m_driveLeftJoy = new NerdyJoystick(0);
	public static NerdyJoystick m_driveRightJoy = new NerdyJoystick(1);
	public static NerdyJoystick m_buttonBox = new NerdyJoystick(2);
	
	// Prevents initialization
	private DriverInput()	{}
	
}
