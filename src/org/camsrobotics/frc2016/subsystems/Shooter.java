package org.camsrobotics.frc2016.subsystems;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.lib.Loopable;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Shooter Interface
 * 
 * @author Wesley
 *
 */
public class Shooter implements Loopable {
	
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
	
	private double m_shootTime = Constants.kShootTime;
	
	private int m_desiredRPM = 0;
	private double m_desiredAngle = 0.0;
	private double m_offsetAngle = 0.0;
	
	private boolean m_shooting = false;
	private Timer m_shootTimer;
	
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
		
		m_shooterLeft.reverseSensor(false);
		m_shooterRight.reverseOutput(true);
		
		m_shootTimer = new Timer();
		
		m_lifter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		m_lifter.changeControlMode(TalonControlMode.Position);
		
		m_lifter.enableBrakeMode(true);
		
		m_lifter.setF(m_lifterF);
		m_lifter.setP(m_lifterP);
		m_lifter.setI(m_lifterI);
		m_lifter.setD(m_lifterD);
	}

	public void setDesiredRPM(int rpm)	{
		m_desiredRPM = rpm;
	}
	
	public boolean flywheelOnTarget(double threshold)	{
		return threshold > Math.abs(m_shooterLeft.getEncVelocity() - m_desiredRPM) &&
				threshold > Math.abs(m_shooterRight.getEncVelocity() - m_desiredRPM);
	}
	
	public void setShooterAngle(double angle)	{
		m_desiredAngle = angle + m_offsetAngle;
	}
	
	public void zeroShooterAngle()	{
		m_offsetAngle = getShooterAngle();
	}
	
	public double getShooterAngle()	{
		return m_lifter.getEncPosition() - m_offsetAngle;
	}
	
	public boolean liftOnTarget(double threshold)	{
		return threshold > Math.abs(m_lifter.getEncPosition() - m_desiredAngle);
	}
	
	public void shoot()	{
		if(!m_shooting)	{
			m_shooting = true;
			m_shootTimer.reset();
			m_shootTimer.start();
		}
	}
	
	public void stop()	{
		m_shooting = false;
		m_shooterLeft.set(0);
		m_shooterRight.set(0);
		setShooterAngle(getShooterAngle());
	}
	
	@Override
	public void update() {
		m_shooterLeft.set(m_desiredRPM);
		m_shooterRight.set(m_desiredRPM);
		
		m_lifter.set(m_desiredAngle);
		
		if(m_shooting)	{
			if(m_shootTimer.get() < m_shootTime)	{
				if(m_shooterPunch.get() != DoubleSolenoid.Value.kForward)
					m_shooterPunch.set(DoubleSolenoid.Value.kForward);
			}	else	{
				m_shooterPunch.set(DoubleSolenoid.Value.kReverse);
				m_shootTimer.stop();
				m_shooting = false;
			}
		}	else	{
			if(m_shooterPunch.get() != DoubleSolenoid.Value.kReverse)
				m_shooterPunch.set(DoubleSolenoid.Value.kReverse);
		}
	}
}
