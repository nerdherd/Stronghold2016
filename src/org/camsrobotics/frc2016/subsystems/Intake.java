package org.camsrobotics.frc2016.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Intake{
                             //need to add lift and put down intake variables
	private Joystick m_joy;
	private VictorSP m_intake;
	private CANTalon m_angleAdjust;
	private boolean m_forward = false;
	private boolean m_reverse = false;
	private double m_kP = 0;
	private double m_P;
	private double m_anglePwr;
	private double m_intakePwr;
	private double m_error;
	private double m_desired;
	private double m_actual;

    public Intake(Joystick joy, VictorSP rollers, CANTalon angleAdjust){
    	m_joy = joy;
    	m_intake = rollers;
    	m_angleAdjust = angleAdjust;
    }
    
    public void intake(){ // turn on intake rollers to intake
    	m_forward = true;
    	m_reverse = false;
    }
    
    public void outtake(){ // turn on intake rollers to outake
    	m_forward = false;
    	m_reverse = true;
    }
    
    public void allStop(){ // cease all intake motors
    	m_forward = false;
    	m_reverse = false;
    }
    
    
    
    public void run(){
    	
    	m_angleAdjust.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
    	m_angleAdjust.reverseOutput(false);
    	m_actual = m_angleAdjust.getEncPosition();
    	
    	if(m_joy.getRawButton(1)){
    		m_desired = 39; //random value until we figure out the correct one
    	}else if(m_joy.getRawButton(2)){
    		m_desired = 52; //random value until we figure out the correct one
    	}
    	
    	m_error = m_actual - m_desired; // P loop maths
    	m_P = m_kP * m_error;
    	m_anglePwr = m_P;
    	
    	if(m_forward == true && m_reverse == false){  // intake roller logic
    		m_intakePwr = 1.0;
    	}else if(m_forward == false && m_reverse == true){
    		m_intakePwr = -1.0;
    	}else if(m_forward == false && m_reverse == false){
    		m_intakePwr = 0;
    	}
    	
    	m_intake.set(m_intakePwr);  // set power for both intake rollers and intake height
    	m_angleAdjust.set(m_anglePwr);
    }
    
}
