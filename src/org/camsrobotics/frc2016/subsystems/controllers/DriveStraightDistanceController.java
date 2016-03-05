package org.camsrobotics.frc2016.subsystems.controllers;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.subsystems.Drive.DriveController;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSensorSignal;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.lib.NerdyMath;
import org.camsrobotics.lib.NerdyPID;

/**
 * PID Drive Straight to distance Controller
 * 
 * @author Wesley
 *
 */
public class DriveStraightDistanceController implements DriveController {
	private final double m_kRotP = Constants.kDriveRotationP;
	private final double m_kRotI = Constants.kDriveRotationI;
	private final double m_kRotD = Constants.kDriveRotationD;
	
	private final double m_kDistP = Constants.kDriveTranslationP;
	private final double m_kDistI = Constants.kDriveTranslationI;
	private final double m_kDistD = Constants.kDriveTranslationD;
	
	private double m_tolerance;
	private double m_rotPower = 0;
	private double m_straightPower = 0;
	
	private NerdyPID m_pidRotController;
	private NerdyPID m_pidDistController;
	
	public DriveStraightDistanceController(double tolerance, double distance)	{
		m_tolerance = tolerance;
		
		m_pidDistController = new NerdyPID(m_kDistP, m_kDistI, m_kDistD);
		m_pidDistController.setDesired(distance);
		
		m_pidRotController = new NerdyPID(m_kRotP, m_kRotI, m_kRotD);
		m_pidRotController.setDesired(0);
	}
	
	@Override
	public DriveSignal get(DriveSensorSignal sig) {
		m_rotPower = m_pidRotController.calculate((sig.yaw + 360) % 360);
		m_straightPower = m_pidDistController.calculate((sig.leftEncoderDistance + sig.rightEncoderDistance)/2);
		double[] pow = {m_rotPower + m_straightPower, -m_rotPower + m_straightPower};
		pow = NerdyMath.normalize(pow, false);
		return new DriveSignal(pow[0], pow[1]);
	}

	@Override
	public boolean isOnTarget() {
		return m_pidDistController.onTarget(m_tolerance) && m_pidRotController.onTarget(m_tolerance);
	}
}
