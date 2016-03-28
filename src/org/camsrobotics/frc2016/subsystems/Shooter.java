package org.camsrobotics.frc2016.subsystems;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.Vision;
import org.camsrobotics.lib.NerdyPID;

import org.camsrobotics.lib.Subsystem;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shooter Interface
 * 
 * @author Wesley
 *
 */
public class Shooter extends Subsystem {
	
	private CANTalon m_shooterLeft;
	private CANTalon m_shooterRight;
	private DoubleSolenoid m_shooterPunch;
	
	private CANTalon m_lifter;
	
	AnalogInput m_leftCompress = HardwareAdapter.kCompressLeft;
	AnalogInput m_rightCompress = HardwareAdapter.kCompressRight;
	
	private double m_flywheelF = Constants.kFlywheelF;
	private double m_flywheelP = Constants.kFlywheelP;
	private double m_flywheelI = Constants.kFlywheelI;
	private double m_flywheelD = Constants.kFlywheelD;
	
	private double m_lifterF = Constants.kLiftF;
	private double m_lifterP = Constants.kLiftP;
	private double m_lifterI = Constants.kLiftI;
	private double m_lifterD = Constants.kLiftD;
	private double m_lifterAlpha = Constants.kLiftAlpha;
	
	private double m_shootTime = Constants.kShootTime;
	
	private int m_desiredRPM = 0;
	
	private double m_desiredAngle = 0.0;
	private double m_actualAngle = Constants.kMinHeight;
	
	private NerdyPID m_visionPID = new NerdyPID();
	private Vision m_table = Vision.getInstance();
	private double m_currentCenterY = 0;
	private double m_desiredTargetPos = 0;
	
	private boolean m_shooting = false;
	private boolean m_setLift = false;
	private boolean m_cameraLift = false;
	private boolean m_manualLift = true;
	private Timer m_shootTimer;
	
	public Shooter(String name, CANTalon shooterLeft, CANTalon shooterRight, DoubleSolenoid shooterPunch, CANTalon lifter)	{
		super(name);
		
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
		
		m_shooterLeft.reverseSensor(true);
		m_shooterLeft.reverseOutput(true);
		m_shooterRight.reverseSensor(false);
		m_shooterRight.reverseOutput(false);

		m_shootTimer = new Timer();
		
		m_lifter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		m_lifter.changeControlMode(TalonControlMode.Position);
		
		m_lifter.reverseSensor(false);
		m_lifter.reverseOutput(false);
		
		m_lifter.setF(m_lifterF);
		m_lifter.setP(m_lifterP);
		m_lifter.setI(m_lifterI);
		m_lifter.setD(m_lifterD);
		
		//TODO tune
		m_visionPID.setPID(0, 0, 0);
	}
	
	public void setDesiredRPM(int rpm)	{
		m_desiredRPM = rpm;
	}
	
	public boolean flywheelOnTarget(double threshold)	{
		return threshold > Math.abs(m_shooterLeft.getSpeed() - m_desiredRPM) &&
				threshold > Math.abs(m_shooterRight.getSpeed() - m_desiredRPM);
	}
	
	public void setShooterAngle(double angle)	{
		m_manualLift = false;
		m_cameraLift = false;
		m_setLift = true;
		m_desiredAngle = angle;
	}
	
	public void setShooterCamera(double targetPos)	{
		m_manualLift = false;
		m_cameraLift = true;
		m_setLift = false;
		m_desiredTargetPos = targetPos;
		m_visionPID.setDesired(m_desiredTargetPos);
	}
	
	public void setManualShooterAngle(double pow)	{
		m_manualLift = true;
		m_cameraLift = false;
		m_setLift = false;
		m_desiredAngle = pow;
	}
	
	public double getShooterAngle()	{
		return m_lifter.getPosition();
	}
	
	public double getSpeed()	{
		return m_shooterLeft.getSpeed();
	}
	
	
	public boolean liftOnTarget(double threshold)	{
		return threshold > Math.abs(m_lifter.getPosition() - m_desiredAngle);
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
		setManualShooterAngle(0);
		setDesiredRPM(0);
	}
	
	@Override
	public void update() {
		if(m_desiredRPM != 0)	{
			m_shooterLeft.changeControlMode(TalonControlMode.Speed);
			m_shooterRight.changeControlMode(TalonControlMode.Speed);
			
			m_shooterLeft.set(m_desiredRPM);
			m_shooterRight.set(m_desiredRPM);	
		}	else	{
			m_shooterLeft.changeControlMode(TalonControlMode.PercentVbus);
			m_shooterRight.changeControlMode(TalonControlMode.PercentVbus);
			
			m_shooterLeft.set(m_desiredRPM);
			m_shooterRight.set(m_desiredRPM);
		}
		
		if(m_manualLift)	{
			m_lifter.changeControlMode(TalonControlMode.PercentVbus);
			m_actualAngle = Constants.kMinHeight;
			m_lifter.set(m_desiredAngle);
		}	else if (m_setLift)	{
			m_lifter.changeControlMode(TalonControlMode.Position);
			m_actualAngle = m_actualAngle*(1-m_lifterAlpha) + m_desiredAngle*m_lifterAlpha; 
			m_lifter.set(m_actualAngle);
		}	else if (m_cameraLift) {
			m_lifter.changeControlMode(TalonControlMode.Position);
			try {
				m_currentCenterY = m_table.getCenterY();
			} catch (Exception e) {
				e.printStackTrace();
			}
			m_currentCenterY = m_currentCenterY*(1-m_lifterAlpha)+m_desiredTargetPos*m_lifterAlpha;
			m_lifter.set(m_currentCenterY);
			
		}
		
		if(m_shooting)	{
			if(m_shootTimer.get() < m_shootTime)	{
				if(m_shooterPunch.get() != DoubleSolenoid.Value.kReverse)
					m_shooterPunch.set(DoubleSolenoid.Value.kReverse);
			}	else	{
				m_shooterPunch.set(DoubleSolenoid.Value.kForward);
				m_shootTimer.stop();
				m_shooting = false;
			}
		}	else	{
			if(m_shooterPunch.get() != DoubleSolenoid.Value.kForward)
				m_shooterPunch.set(DoubleSolenoid.Value.kForward);
		}
		
	}

	@Override
	public void reportState() {		
		SmartDashboard.putNumber("Left Compress", m_leftCompress.getVoltage());
		SmartDashboard.putNumber("Right Compress", m_rightCompress.getVoltage());
		
		SmartDashboard.putNumber("Desired Angle", m_actualAngle);
		SmartDashboard.putNumber("Shooter Position", m_lifter.getPosition());
		SmartDashboard.putNumber("Shooter Left RPM", m_shooterLeft.getSpeed());
		SmartDashboard.putNumber("Shooter Right RPM", m_shooterRight.getSpeed());
	}
}
