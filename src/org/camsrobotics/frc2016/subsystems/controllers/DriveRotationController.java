package org.camsrobotics.frc2016.subsystems.controllers;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.subsystems.Drive.DriveController;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSensorSignal;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.lib.NerdyPID;

/**
 * PID Drive Rotation Controller
 * 
 * @author Wesley
 *
 */
public class DriveRotationController implements DriveController {
	private final double m_kP = Constants.kDriveRotationP;
	private final double m_kI = Constants.kDriveRotationI;
	private final double m_kD = Constants.kDriveRotationD;
	
	private double m_tolerance;
	private double m_power = 0;
	
	private NerdyPID m_pidController;
	
	public DriveRotationController(double desired, double tolerance)	{
		m_tolerance = tolerance;
		
		m_pidController = new NerdyPID(m_kP, m_kI, m_kD);
		m_pidController.setDesired((desired + 360) %360);
	}
	
	@Override
	public DriveSignal get(DriveSensorSignal sig) {
		m_power = m_pidController.calculate((sig.yaw + 360) % 360);
		return new DriveSignal(m_power, -m_power);
	}

	@Override
	public boolean isOnTarget() {
		return m_pidController.onTarget(m_tolerance);
	}
	
}
