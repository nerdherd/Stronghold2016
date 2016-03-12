package org.camsrobotics.frc2016.teleop;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.frc2016.subsystems.controllers.VisionTargetingController;
import org.camsrobotics.lib.NerdyJoystick;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	private int m_oscilateCount = 0;
	
	boolean rolling = false;
	
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
		case MANUAL:
			m_shooter.setManualShooterAngle(0);
			SmartDashboard.putNumber("ShooterAngleSet", 0);
			break;
		case OFF_BATTER:
			m_shooter.setShooterAngle(Constants.kOffBatterAngle);
			SmartDashboard.putNumber("ShooterAngleSet", Constants.kOffBatterAngle);
			break;
		case BATTER:
			m_shooter.setShooterAngle(Constants.kBatterAngle);
			SmartDashboard.putNumber("ShooterAngleSet", Constants.kBatterAngle);
			break;
		case OUTER_WORKS:
			m_shooter.setShooterAngle(Constants.kOuterWorksAngle);
			SmartDashboard.putNumber("ShooterAngleSet", Constants.kOuterWorksAngle);
			break;
		case RESTING:
			m_shooter.setShooterAngle(Constants.kMinHeight);
			SmartDashboard.putNumber("ShooterAngleSet", Constants.kMinHeight);
			break;
		}
		
		switch(c.flywheelCommand)	{
		case IDLE:
			m_shooter.setDesiredRPM(0);
			m_oscilateCount = 0;
			break;
		case ON:
			m_shooter.setDesiredRPM(Constants.kManualRPM);
			
			if(Math.abs(m_shooter.getSpeed() - Constants.kManualRPM) < 5)	{
				m_oscilateCount++;
				
				if(m_oscilateCount == 5)	{
					m_shooter.shoot();
				}
			}
			break;
		}
		
		if(c.shooting)	{
			m_shooter.shoot();
		}
		
		// Intake
		switch(c.intakeCommand)	{
		case IDLE:
			m_intake.manualDrive(0);
			rolling = false;
			break;
		case MANUAL:
			m_intake.manualDrive(m_buttonBox.getY());
			rolling = false;
			break;
		case BALL_PICKUP:
			m_intake.setIntakeHeight(Constants.kIntakeBallPickup);
			rolling = false;
			break;
		case TUCKED_IN:
			m_intake.manualDrive(0.25);
			rolling = false;
			break;
		case TUCKED:
			m_intake.setIntakeHeight(Constants.kIntakeTucked);
			rolling = false;
			break;
		case GROUND:
			m_intake.manualDrive(-0.25);
			HardwareAdapter.kIntakeRollers.set(0.25);
			rolling = true;
			break;
		}
		
		if(!rolling)	{
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
		}
		
		if(c.reset)	{
			m_shooter.zero();
			m_intake.zero();
		}
	}
}
