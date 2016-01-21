package org.camsrobotics.frc2016;

import org.camsrobotics.lib.NerdyJoystick;

public class DriverInput {
	
	private static DriverInput m_instance;
	
	private NerdyJoystick m_driveLeftJoy;
	private NerdyJoystick m_driveRightJoy;
	private NerdyJoystick m_buttonBox;
	
	private DriverInput()	{
		m_driveLeftJoy = new NerdyJoystick(0);
		m_driveRightJoy = new NerdyJoystick(1);
		m_buttonBox = new NerdyJoystick(2);
	}
	
	public static DriverInput getInstance()	{
		if(m_instance == null)	{
			m_instance = new DriverInput();
		}
		
		return m_instance;
	}
	
}
