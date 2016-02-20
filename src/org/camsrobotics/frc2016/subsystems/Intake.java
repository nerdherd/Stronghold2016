	package org.camsrobotics.frc2016.subsystems;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.lib.StateHolder;
import org.camsrobotics.lib.Subsystem;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;


/**
 * Intake Interface
 * 
 * @author Jamari
 * @author Wesley
 *
 */
public class Intake extends Subsystem {
	private VictorSP m_intake;
	private CANTalon m_angleAdjust;
	
	private boolean m_manual = false;
	private double m_manualPow = 0;
	
	private double m_desiredAngle = 0;
	
	private IntakeStates m_rollerState;
	
    public Intake(String name, VictorSP rollers, CANTalon angleAdjust)	{
    	super(name);
    	m_intake = rollers;
    	m_angleAdjust = angleAdjust;
    	
    	m_angleAdjust.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);

		m_angleAdjust.changeControlMode(TalonControlMode.Position);

		m_angleAdjust.reverseSensor(false);
		m_angleAdjust.enableBrakeMode(true);
		
		m_angleAdjust.setF(Constants.kIntakeF);
		m_angleAdjust.setP(Constants.kIntakeP);
		m_angleAdjust.setI(Constants.kIntakeI);
		m_angleAdjust.setD(Constants.kIntakeD);
    }
    
    private enum IntakeStates	{
    	INTAKE, OUTTAKE, IDLE
    }
    
    public void intake()	{ // turn on intake rollers to intake
    	m_rollerState = IntakeStates.INTAKE;
    }
    
    public void outtake()	{ // turn on intake rollers to outake
    	m_rollerState = IntakeStates.OUTTAKE;
    }
    
    public void idle()	{ // cease all intake motors
    	m_rollerState = IntakeStates.IDLE;
    }
    
    public void setIntakeHeight(double angle)	{
    	m_manual = false;
    	m_desiredAngle = angle;
    }
    
    public void zero()	{
    	m_angleAdjust.setPosition(0);
    }
    
    public int getHeight()	{
    	return m_angleAdjust.getEncPosition();
    }
    
    public void manualDrive(double pow)	{
    	// deadband pow
    	if(Math.abs(pow) > 0.05)	{
    		m_manual = true;
    		m_manualPow = pow;
    	}	else	{
    		m_manual = false;
    		m_manualPow = 0;
    		m_desiredAngle = getHeight();
    	}
    }
    
    public boolean isOnTarget(double tolerance)	{
    	return Math.abs(m_desiredAngle - getHeight()) < tolerance;
    }

    public void stop()	{
    	idle();
    	setIntakeHeight(getHeight());
    }
    
	@Override
	public void update() {
		double intakePow;
		switch(m_rollerState)	{
		case INTAKE:
			intakePow = Constants.kIntakeSpeed;
			break;
		case OUTTAKE:
			intakePow = -Constants.kIntakeSpeed;
			break;
		case IDLE:
			intakePow = 0;
			break;
		default:
			intakePow = 0;
			break;
		}
		
		m_intake.set(intakePow);
		if(m_manual)	{
			m_angleAdjust.changeControlMode(TalonControlMode.PercentVbus);
			m_angleAdjust.set(m_manualPow);
		}	else	{
			m_angleAdjust.changeControlMode(TalonControlMode.Position);
			m_angleAdjust.set(m_desiredAngle);
		}
	}

	@Override
	public void getState(StateHolder states) {
		states.put("RollerPower", m_rollerState == IntakeStates.INTAKE ? Double.toString(Constants.kIntakeSpeed) : 
			m_rollerState == IntakeStates.OUTTAKE ? Double.toString(-Constants.kIntakeSpeed) : "0");
		states.put("AnglePower", m_manual ? Double.toString(m_manualPow) : Double.toString(m_desiredAngle));
	}
    
}