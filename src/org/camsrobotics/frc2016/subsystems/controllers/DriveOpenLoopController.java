package org.camsrobotics.frc2016.subsystems.controllers;

import org.camsrobotics.frc2016.subsystems.Drive.DriveController;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;

/**
 * Drives the robot in open loop mode
 * 
 * @author Wesley
 *
 */
public class DriveOpenLoopController implements DriveController {
	private DriveSignal m_signal;
	
	public DriveOpenLoopController()	{
		m_signal = new DriveSignal(0,0);
	}
	
	public void set(double leftSpeed, double rightSpeed)	{
		m_signal.leftSpeed = leftSpeed;
		m_signal.rightSpeed = rightSpeed;
	}
	
	public void setLeft(double leftSpeed)	{
		m_signal.leftSpeed = leftSpeed;
	}
	
	public void setRight(double rightSpeed)	{
		m_signal.rightSpeed = rightSpeed;
	}
	
	@Override
	public DriveSignal get() {
		return m_signal;
	}
	
}
