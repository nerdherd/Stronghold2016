package org.camsrobotics.frc2016.auto.actions;

import org.camsrobotics.frc2016.HardwareAdapter;

import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Waits for the ultrasonic to be more less the specified range.
 * 
 * @author Wesley
 *
 */
public class WaitForUltrasonicSeeAction extends TimeoutAction {
	private double m_range;
	private Ultrasonic m_ultrasonic;

	/**
	 * Default constructor
	 * 
	 * @param range Max range before triggering, in inches
	 * @param timeout
	 */
	public WaitForUltrasonicSeeAction(double range, double timeout) {
		super(timeout);
		m_range = range;
		m_ultrasonic = HardwareAdapter.kUltrasonic;
	}

	@Override
	public boolean isFinished()	{
		return super.isFinished() || m_ultrasonic.getRangeInches() < m_range;
	}
}
