package org.camsrobotics.frc2016.subsystems;

import org.camsrobotics.frc2016.Constants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Shooter Interface
 * 
 * @author Wesley
 *
 */
public class Shooter {
	
	private CANTalon m_shooterLeft;
	private CANTalon m_shooterRight;
	private DoubleSolenoid m_shooterPunch;
	
	private CANTalon m_lifter;
	
	private double m_flywheelF = Constants.kFlywheelF;
	private double m_flywheelP = Constants.kFlywheelP;
	private double m_flywheelI = Constants.kFlywheelI;
	private double m_flywheelD = Constants.kFlywheelD;
	
	private double m_lifterF = Constants.kLiftF;
	private double m_lifterP = Constants.kLiftP;
	private double m_lifterI = Constants.kLiftI;
	private double m_lifterD = Constants.kLiftD;
	
	public Shooter(CANTalon shooterLeft, CANTalon shooterRight, DoubleSolenoid shooterPunch, CANTalon lifter)	{
		m_shooterLeft = shooterLeft;
		m_shooterRight = shooterRight;
		m_shooterPunch = shooterPunch;
		
		m_lifter = lifter;
		
		m_shooterLeft.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		m_shooterRight.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		m_shooterLeft.changeControlMode(TalonControlMode.Speed);
		m_shooterRight.changeControlMode(TalonControlMode.Speed);
		
		m_shooterLeft.enableBrakeMode(false);
		m_shooterRight.enableBrakeMode(false);
		
		m_shooterLeft.setF(m_flywheelF);
		m_shooterLeft.setP(m_flywheelP);
		m_shooterLeft.setI(m_flywheelI);
		m_shooterLeft.setD(m_flywheelD);
		
		m_shooterRight.setF(m_flywheelF);
		m_shooterRight.setP(m_flywheelP);
		m_shooterRight.setI(m_flywheelI);
		m_shooterRight.setD(m_flywheelD);
		
		m_lifter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		m_lifter.changeControlMode(TalonControlMode.Position);
		
		m_lifter.enableBrakeMode(true);
		
		m_lifter.setF(m_lifterF);
		m_lifter.setP(m_lifterP);
		m_lifter.setI(m_lifterI);
		m_lifter.setD(m_lifterD);
	}
}
