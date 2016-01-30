package org.camsrobotics.frc2016.subsystems;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.lib.Loopable;
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
public class Intake implements Loopable {
	private VictorSP m_intake;
	private CANTalon m_angleAdjust;
	
	private double m_desiredAngle = 0;
	
	private IntakeStates m_rollerState;
	
    public Intake(VictorSP rollers, CANTalon angleAdjust)	{
    	m_intake = rollers;
    	m_angleAdjust = angleAdjust;
    	
    	m_angleAdjust.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	m_angleAdjust.configEncoderCodesPerRev(Constants.kIntakeTicksPerRev);
		m_angleAdjust.changeControlMode(TalonControlMode.Position);
		
		m_angleAdjust.enableBrakeMode(true);
		
		m_angleAdjust.setF(Constants.kIntakeP);
		m_angleAdjust.setP(Constants.kIntakeI);
		m_angleAdjust.setI(Constants.kIntakeI);
		m_angleAdjust.setD(Constants.kIntakeD);
    }
    
    private enum IntakeStates	{
    	INTAKE, OUTTAKE, IDLE
    }
    
    public void intake(){ // turn on intake rollers to intake
    	m_rollerState = IntakeStates.INTAKE;
    }
    
    public void outtake(){ // turn on intake rollers to outake
    	m_rollerState = IntakeStates.OUTTAKE;
    }
    
    public void idle(){ // cease all intake motors
    	m_rollerState = IntakeStates.IDLE;
    }
    
    public void setIntakeHeight(double angle)	{
    	m_desiredAngle = angle;
    }
    
    public int getHeight()	{
    	return m_angleAdjust.getEncPosition();
    }
    
    public boolean isOnTarget(double tolerance)	{
    	return Math.abs(m_desiredAngle - getHeight()) < tolerance;
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
		m_angleAdjust.set(m_desiredAngle);
	}
    
}
