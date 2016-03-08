package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.teleop.Commands;
import org.camsrobotics.lib.NerdyButton;
import org.camsrobotics.lib.NerdyJoystick;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private NerdyButton m_shiftUp;
	private NerdyButton m_shiftDown;
	
	private NerdyButton m_shooterShortRange;
	private NerdyButton m_shooterMediumRange;
	private NerdyButton m_shooterLongRange;
	private NerdyButton m_shooterManual;
	private NerdyButton m_shoot;

	private NerdyButton m_intake;
	private NerdyButton m_outtake;
	private NerdyButton m_ballPickup;
	private NerdyButton m_tuckedIn;
	private NerdyButton m_groundIntake;
	private NerdyButton m_tuckedIntake;
	private NerdyButton m_stop;
	
	private NerdyButton m_reset;
	
	public DriverInput(NerdyJoystick leftStick, NerdyJoystick rightStick, NerdyJoystick buttonBox)	{
		m_commands = new Commands();
		
		m_driverLeftStick = leftStick;
		m_driverRightStick = rightStick;
		m_buttonBox = buttonBox;
		
		m_snapToVisionTarget	= m_driverLeftStick.getButton(1);
		m_shiftUp				= m_driverRightStick.getButton(4);
		m_shiftDown				= m_driverRightStick.getButton(3);
		
		m_shooterShortRange		= m_buttonBox.getButton(11);
		m_shooterMediumRange	= m_buttonBox.getButton(9);
		m_shooterLongRange		= m_buttonBox.getButton(9);
		m_shooterManual			= m_buttonBox.getButton(2);
		m_shoot					= m_buttonBox.getButton(1);
		
		m_intake				= m_buttonBox.getButton(12);
		m_outtake				= m_buttonBox.getButton(10);
		m_ballPickup			= m_buttonBox.getButton(3);
		m_tuckedIn				= m_buttonBox.getButton(5);
		m_groundIntake			= m_buttonBox.getButton(6);
		m_tuckedIntake			= m_buttonBox.getButton(7);
		m_stop 					= m_buttonBox.getButton(4);
		
		m_reset					= m_buttonBox.getButton(8);
	}
	
	public Commands update()	{
		// Refresh buttons
		m_snapToVisionTarget.update();
		m_shiftUp.update();
		m_shiftDown.update();
		m_shooterShortRange.update();
		m_shooterMediumRange.update();
		m_shooterLongRange.update();
		m_shooterManual.update();
		m_shoot.update();
		
		m_intake.update();
		m_outtake.update();
		
		m_ballPickup.update();
		m_tuckedIn.update();
		m_groundIntake.update();
		m_stop.update();
		
		m_reset.update();
		
		/* Do the magic */
		
		// Drive
		if(m_snapToVisionTarget.get())	{
			m_commands.driveCommand = Commands.DriveCommands.VISION;
		}	else	{
			m_commands.driveCommand = Commands.DriveCommands.TANK_DRIVE;
		}
		
		if(m_shiftUp.wasPressed())	{
			m_commands.shiftCommand = Commands.DriveShiftCommands.UP;
		}	else if(m_shiftDown.wasPressed())	{
			m_commands.shiftCommand = Commands.DriveShiftCommands.DOWN;
		}
		
		// Shooter
		if(m_shooterShortRange.get())	{
			m_commands.flywheelCommand = Commands.FlywheelCommands.SHORT_RANGE;
		}	else if(m_shooterMediumRange.get())	{
			m_commands.flywheelCommand = Commands.FlywheelCommands.MEDIUM_RANGE;
		}	else if(m_shooterLongRange.get())	{
			m_commands.flywheelCommand = Commands.FlywheelCommands.LONG_RANGE;
		}	else if(m_shooterManual.get())	{
			m_commands.flywheelCommand = Commands.FlywheelCommands.MANUAL_SPIN;
		}	else	{
			m_commands.flywheelCommand = Commands.FlywheelCommands.MANUAL;
		}
		
		if(m_shoot.wasPressed())	{
			m_commands.shooting = true;
		}	else	{
			m_commands.shooting = false;
		}
		
		// Intake
		if(m_intake.get())	{
			m_commands.rollerCommand = Commands.RollerCommands.INTAKE;
		}	else if(m_outtake.get())	{
			m_commands.rollerCommand = Commands.RollerCommands.OUTTAKE;
		}	else	{
			m_commands.rollerCommand = Commands.RollerCommands.IDLE;
		}
		
		if(m_ballPickup.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.BALL_PICKUP;
		}	else if(m_tuckedIn.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.TUCKED_IN;
		}	else if(m_groundIntake.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.GROUND;
		}	else if(m_tuckedIntake.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.TUCKED;
		}	else if(m_stop.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.MANUAL;
		}
		
		m_commands.reset = m_reset.get();
		SmartDashboard.putBoolean("reset", m_reset.get());
				
		return m_commands;
	}
	
}
