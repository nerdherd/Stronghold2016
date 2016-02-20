package org.camsrobotics.frc2016.subsystems.controllers;


import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.subsystems.Drive.DriveController;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSensorSignal;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.lib.NerdyPID;

/**
 * Snaps to vision target 
 * 
 * @author Michael
 *
 */
public class VisionTargetingController implements DriveController {
	
	private double m_tolerance;
	private double m_pwr;
	private NerdyPID m_pid;
	
	
	private double kP = Constants.kDriveVisionP;
	private double kI = Constants.kDriveVisionI;
	private double kD = Constants.kDriveVisionD;
	
	public VisionTargetingController(double tolerance) {
		m_tolerance = tolerance;
		m_pid = new NerdyPID(kP,kI,kD);
		m_pid.setDesired(Constants.kCameraFrameWidth/2);
	}
	
	public DriveSignal get(DriveSensorSignal sig) {
		m_pwr = m_pid.calculate(sig.vision);
		return new DriveSignal(m_pwr, -m_pwr);
	}
	
	public boolean isOnTarget() {
		return m_pid.onTarget(m_tolerance);
	}
}
