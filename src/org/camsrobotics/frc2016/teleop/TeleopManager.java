package org.camsrobotics.frc2016.teleop;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.frc2016.subsystems.controllers.VisionTargetingController;
import org.camsrobotics.lib.NerdyJoystick;

/**
 * Executes or outsources the various teleoperated commands.
 * 
 * @author Wesley
 *
 */
public class TeleopManager {
	private Drive m_drive = HardwareAdapter.kDrive;
	private Shooter m_shooter = HardwareAdapter.kShooter;
	private Intake m_intake = HardwareAdapter.kIntake;
	
	private NerdyJoystick m_driverLeftStick = HardwareAdapter.kDriveLeftStick;
	private NerdyJoystick m_driverRightStick = HardwareAdapter.kDriveRightStick;
	private NerdyJoystick m_buttonBox = HardwareAdapter.kButtonBox;
	
	public void update(Commands c)	{
		// Drive
		switch(c.driveCommand)	{
		case TANK_DRIVE:
			m_drive.driveOpenLoop(new DriveSignal(m_driverLeftStick.getY(), m_driverRightStick.getY()));
			break;
		case VISION:
			m_drive.setController(new VisionTargetingController(3));
			break;
		}

		switch(c.shiftCommand)	{
		case UP:
			m_drive.shiftUp();
			break;
		case DOWN:
			m_drive.shiftDown();
			break;
		}
		
		// Shooter
		switch(c.shooterCommand)	{
		case IDLE:
			m_shooter.setManualShooterAngle(0);
		case MANUAL:
			m_shooter.setShooterAngle(m_buttonBox.getThrottle() * (Constants.kMaxHeight - Constants.kMinHeight) + Constants.kMinHeight);
			break;
		case LONG_RANGE:
			m_shooter.setShooterAngle(Constants.kLongRangeAngle);
			break;
		case MEDIUM_RANGE:
			m_shooter.setShooterAngle(Constants.kMediumRangeAngle);
			break;
		case SHORT_RANGE:
			m_shooter.setShooterAngle(Constants.kShortRangeAngle);
			break;
		}
		
		switch(c.flywheelCommand)	{
		case IDLE:
			m_shooter.setDesiredRPM(0);
			break;
		case LONG_RANGE:
			m_shooter.setDesiredRPM(Constants.kLongRangeRPM);
			if(m_shooter.getSpeed() > Constants.kLongRangeActivate)	{
				m_shooter.shoot();
			}
			break;
		case MEDIUM_RANGE:
			m_shooter.setDesiredRPM(Constants.kMediumRangeRPM);
			if(m_shooter.getSpeed() > Constants.kMediumRangeActivate)	{
				m_shooter.shoot();
			}
			break;
		case SHORT_RANGE:
			m_shooter.setDesiredRPM(Constants.kShortRangeRPM);
			if(m_shooter.getSpeed() > Constants.kShortRangeActivate)	{
				m_shooter.shoot();
			}
			break;
		case MANUAL:
			m_shooter.setDesiredRPM(Constants.kManualRPM);
			break;
		}
		
		if(c.shooting)	{
			m_shooter.shoot();
		}
		
		// Intake
		switch(c.intakeCommand)	{
		case IDLE:
			m_intake.manualDrive(0);
			break;
		case MANUAL:
			m_intake.manualDrive(m_buttonBox.getY());
			break;
		case BALL_PICKUP:
			m_intake.setIntakeHeight(Constants.kIntakeBallPickup);
			break;
		case TUCKED_IN:
			m_intake.setIntakeHeight(Constants.kIntakeTuckedIn);
			break;
		case TUCKED:
			m_intake.setIntakeHeight(Constants.kIntakeTucked);
			break;
		case GROUND:
			m_intake.setIntakeHeight(Constants.kIntakeGround);
			break;
		}
		
		switch(c.rollerCommand)	{
		case INTAKE:
			m_intake.intake();
			break;
		case OUTTAKE:
			m_intake.outtake();
			break;
		case IDLE:
			m_intake.idle();
			break;
		}
		
		if(c.reset)	{
			m_shooter.zero();
			m_intake.zero();
		}
	}
}
