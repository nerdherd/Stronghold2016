package org.camsrobotics.frc2016.subsystems.controllers;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.subsystems.Drive.DriveController;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSensorSignal;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.lib.NerdyPID;

/**
 * PID Drive Straight Controller
 * 
 * @author Mike
 *
 */
public class DriveStraightController implements DriveController {
	private final double m_kP = Constants.kDriveStraightP;
	private final double m_kI = Constants.kDriveStraightI;
	private final double m_kD = Constants.kDriveStraightD;
	
	private double m_tolerance;
	private double m_pwr = 0;
	
	private NerdyPID m_pidController;
	
	public DriveStraightController(double tolerance)	{
		m_tolerance = tolerance;
		
		m_pidController = new NerdyPID(m_kP, m_kI, m_kD);
		m_pidController.setDesired(0);
	}
	
	@Override
	public DriveSignal get(DriveSensorSignal sig) {
		m_pwr = m_pidController.calculate((sig.yaw + 360) % 360);
		return new DriveSignal(m_pwr, -m_pwr);
	}

	@Override
	public boolean isOnTarget() {
		return m_pidController.onTarget(m_tolerance);
	}
}
