package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.teleop.Commands;
import org.camsrobotics.lib.NerdyButton;
import org.camsrobotics.lib.NerdyJoystick;

/**
 * Driver Interface
 * 
 * @author Wesley
 *
 */
public class DriverInput {
	
	private Commands m_commands;
	
	private NerdyJoystick m_driverLeftStick;
	private NerdyJoystick m_driverRightStick;
	private NerdyJoystick m_buttonBox;
	
	private NerdyButton m_snapToVisionTarget;
	
	private NerdyButton m_shooterShortRange;
	private NerdyButton m_shooterMediumRange;
	private NerdyButton m_shooterLongRange;
	private NerdyButton m_shooterVision;
	private NerdyButton m_shoot;
	
	public DriverInput(NerdyJoystick leftStick, NerdyJoystick rightStick, NerdyJoystick buttonBox)	{
		m_commands = new Commands();
		
		m_driverLeftStick = leftStick;
		m_driverRightStick = rightStick;
		m_buttonBox = buttonBox;
		
		m_snapToVisionTarget	= m_driverLeftStick.getButton(99);
		m_shooterShortRange		= m_buttonBox.getButton(99);
		m_shooterMediumRange	= m_buttonBox.getButton(99);
		m_shooterLongRange		= m_buttonBox.getButton(99);
		m_shooterVision			= m_buttonBox.getButton(99);
		m_shoot					= m_buttonBox.getButton(99);
	}
	
	public Commands update()	{
		
		if(m_snapToVisionTarget.get())	{
			m_commands.driveCommand = Commands.DriveCommands.VISION;
		}	else	{
			m_commands.driveCommand = Commands.DriveCommands.TANK_DRIVE;
		}
		
		if(m_shooterShortRange.get())	{
			
		}
		
		return m_commands;
	}
}
