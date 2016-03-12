package org.camsrobotics.frc2016.subsystems.controllers;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.subsystems.Drive.DriveController;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSensorSignal;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.lib.NerdyMath;
import org.camsrobotics.lib.NerdyPID;

/**
 * PID Drive Straight Controller
 * 
 * @author Mike
 *
 */
public class DriveStraightController implements DriveController {
	private final double m_kP = Constants.kDriveRotationP;
	private final double m_kI = Constants.kDriveRotationI;
	private final double m_kD = Constants.kDriveRotationD;
	
	private double m_tolerance;
	private double m_rotPower = 0;
	private double m_straitPower = 0;
	
	private NerdyPID m_pidController;
	
	public DriveStraightController(double tolerance, double power)	{
		m_tolerance = tolerance;
		
		m_straitPower = power;
		
		m_pidController = new NerdyPID(m_kP, m_kI, m_kD);
		m_pidController.setDesired(0);
	}
	
	@Override
	public DriveSignal get(DriveSensorSignal sig) {
		m_rotPower = m_pidController.calculate((sig.yaw + 360) % 360);
		double[] pow = {m_rotPower + m_straitPower, -m_rotPower + m_straitPower};
		pow = NerdyMath.normalize(pow, false);
		return new DriveSignal(pow[0], pow[1]);
	}

	@Override
	public boolean isOnTarget() {
		return m_pidController.onTarget(m_tolerance);
	}

	@Override
	public void setPID(double p, double i, double d) {
		// TODO Auto-generated method stub
		
	}
}
