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
	
	private NerdyButton m_shooterOuterWorks;
	private NerdyButton m_shooterBatter;
	private NerdyButton m_shooterLowGoal;
	private NerdyButton m_shooterVerticalAlign;
	private NerdyButton m_shootFire;
	private NerdyButton m_shootReady;

	private NerdyButton m_intake;
	private NerdyButton m_outtake;
	private NerdyButton m_ballPickup;
	private NerdyButton m_tuckedInAll;
	private NerdyButton m_groundIntake;
	private NerdyButton m_tuckedIntake;
	private NerdyButton m_stop;
	
	private NerdyButton m_compress;
	
	private NerdyButton m_reset;
	
	public DriverInput(NerdyJoystick leftStick, NerdyJoystick rightStick, NerdyJoystick buttonBox)	{
		m_commands = new Commands();
		
		m_driverLeftStick = leftStick;
		m_driverRightStick = rightStick;
		m_buttonBox = buttonBox;
		
		m_snapToVisionTarget	= m_driverLeftStick.getButton(1);
		m_shiftUp				= m_driverRightStick.getButton(4);
		m_shiftDown				= m_driverRightStick.getButton(3);
		
		
		m_shooterOuterWorks		= m_buttonBox.getButton(12);
		m_shooterBatter			= m_buttonBox.getButton(10);
		m_shooterLowGoal		= m_buttonBox.getButton(8);
		m_shooterVerticalAlign  = m_driverRightStick.getButton(7);
		m_shootFire					= m_buttonBox.getButton(1);
		m_shootReady				= m_buttonBox.getButton(2);
		
		m_intake				= m_buttonBox.getButton(3);
		m_outtake				= m_buttonBox.getButton(5);
		m_ballPickup			= m_buttonBox.getButton(9);
		m_tuckedInAll			= m_buttonBox.getButton(6);
		m_groundIntake			= m_buttonBox.getButton(7);
		m_tuckedIntake			= m_buttonBox.getButton(11);
		
		m_stop 					= m_buttonBox.getButton(4);
		
		m_compress				= m_driverLeftStick.getButton(9);
		
		m_reset					= m_buttonBox.getButton(8);
	}
	
	public Commands update()	{
		// Refresh buttons
//		m_snapToVisionTarget.update();
		m_shiftUp.update();
		m_shiftDown.update();
//		m_shooterOuterWorks.update();
//		m_shooterBatter.update();
		m_shooterLowGoal.update();
//		m_shooterVerticalAlign.update();
//		m_shoot.update();
		m_shootReady.update();
		
		m_intake.update();
		m_outtake.update();
		
		m_ballPickup.update();
		m_tuckedInAll.update();
		m_tuckedIntake.update();
//		m_groundIntake.update();
		m_stop.update();
		
		m_compress.update();
		
//		m_reset.update();
		
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
		}	else	{
			m_commands.shiftCommand = Commands.DriveShiftCommands.IDLE;
		}
		
		// Shooter
//		if(m_shooterOuterWorks.get())	{
//			m_commands.shooterCommand = Commands.ShooterCommands.OUTER_WORKS;
//		}	else if(m_shooterBatter.get())	{
//			m_commands.shooterCommand = Commands.ShooterCommands.BATTER;
//		}	else if(m_shooterVerticalAlign.get())	{
//			m_commands.shooterCommand = Commands.ShooterCommands.VERTICAL_ALIGN;
//		}
			
		if(m_shootReady.get())	{
			m_commands.flywheelCommand = Commands.FlywheelCommands.HIGH;
			m_commands.shooterCommand = Commands.ShooterCommands.BATTER;
			m_commands.intakeCommand = Commands.IntakeCommands.TUCKED;
		}	else if(m_shooterLowGoal.get())	{
			m_commands.flywheelCommand = Commands.FlywheelCommands.LOW;
			m_commands.shooterCommand = Commands.ShooterCommands.BATTER;
			m_commands.intakeCommand = Commands.IntakeCommands.TUCKED;
		}	else	{
			m_commands.flywheelCommand = Commands.FlywheelCommands.IDLE;
			m_commands.shooterCommand = Commands.ShooterCommands.IDLE;
		}
		
		m_commands.shooterFire = m_shootFire.get();
		
		// Intake
		if(m_intake.get())	{
			HardwareAdapter.kIntakeRollers.set(Constants.kIntakeSpeed);
			m_commands.shooterCommand = Commands.ShooterCommands.IDLE;
		}	else if(m_outtake.get())	{
			HardwareAdapter.kIntakeRollers.set(-Constants.kIntakeSpeed);
		}	else	{
			HardwareAdapter.kIntakeRollers.set(0);
		}
		
		if(m_ballPickup.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.BALL_PICKUP;
		}	else if(m_tuckedInAll.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.TUCKED_IN_ALL;
		}	else if(m_groundIntake.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.GROUND;
		}	else if(m_tuckedIntake.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.TUCKED;
		}	else if(m_stop.get())	{
			m_commands.intakeCommand = Commands.IntakeCommands.MANUAL;
		}
		
		m_commands.compress = m_compress.get();
		
		m_commands.reset = m_reset.get();
		SmartDashboard.putBoolean("reset", m_reset.get());
				
		return m_commands;
	}
	
}
