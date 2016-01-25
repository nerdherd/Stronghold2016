package org.camsrobotics.frc2016.auto.actions;

import edu.wpi.first.wpilibj.Timer;

/**
 * Delays for a certain amount of time
 * 
 * @author Wesley
 *
 */
public class TimeoutAction extends Action {
	protected double m_timeout;
	protected double m_startTime;
	
	public TimeoutAction(double timeout)	{
		m_timeout = timeout;
	}
	
	@Override
	public boolean isFinished() {
		return m_timeout < Timer.getFPGATimestamp() - m_startTime;
	}

	@Override
	public void update() {
		// Nothing. Just wait
	}

	@Override
	public void start() {
		m_startTime = Timer.getFPGATimestamp();
	}

	@Override
	public void disable() {
		
	}

}
