package org.camsrobotics.frc2016.teleop;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.frc2016.subsystems.controllers.VisionTargetingController;
import org.camsrobotics.lib.NerdyJoystick;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
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
	
	private DoubleSolenoid m_compress = HardwareAdapter.kCompress;
	private Solenoid m_photonCannon = HardwareAdapter.kPhotonCannon;
	
	private BuiltInAccelerometer m_accelerometer = HardwareAdapter.kAccelerometer;
	
	private int m_oscilateCount = 0;
	private int m_compressCount = 0;
	
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
		case IDLE:
			if(Math.abs(m_accelerometer.getY()) > Constants.kDriveMaxAccel)	{
				m_drive.shiftUp();
			}
			break;
		}
		
		// Shooter
		if(c.intakeCommand != Commands.IntakeCommands.TUCKED_IN_ALL)	{
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
			case VERTICAL_ALIGN:
				m_shooter.verticalAlign();
				break;
			case RESTING:
				m_shooter.setShooterAngle(Constants.kMinHeight);
				SmartDashboard.putNumber("ShooterAngleSet", Constants.kMinHeight);
				break;
			}
		}	else	{
			m_shooter.setManualShooterAngle(0);
		}
		
		switch(c.flywheelCommand)	{
		case IDLE:
			m_shooter.setDesiredRPM(0);
			m_oscilateCount = 0;
			m_compressCount = 0;
			break;
		case HIGH:
			m_shooter.setDesiredRPM(Constants.kHighGoalRPM);
			m_shooter.compress(true);
			m_photonCannon.set(true);
			
			if(Math.abs(m_shooter.getSpeed() - Constants.kHighGoalRPM) < 5)	{
				m_oscilateCount++;
				
				if(m_oscilateCount > 5 && c.shooterFire)	{
					m_shooter.compress(false);
					m_compressCount++;
					
					if(m_compressCount > 2)	{
						m_shooter.shoot();
						m_photonCannon.set(false);
					}
				}
			}
			break;
		case LOW:
			m_shooter.setDesiredRPM(Constants.kLowGoalRPM);
			m_shooter.compress(true);
			m_photonCannon.set(true);
			
			if(Math.abs(m_shooter.getSpeed() - Constants.kLowGoalRPM) < 5)	{
				m_oscilateCount++;
				
				if(m_oscilateCount > 5 && c.shooterFire)	{
					m_shooter.compress(false);
					m_compressCount++;
					
					if(m_compressCount > 2)	{
						m_shooter.shoot();
						m_photonCannon.set(false);
					}
				}
			}
			break;
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
		case TUCKED_IN_ALL:
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
		
	}
}
