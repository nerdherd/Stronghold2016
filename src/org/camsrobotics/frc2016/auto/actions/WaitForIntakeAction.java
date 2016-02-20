package org.camsrobotics.frc2016.auto.actions;

/**
 * Delay until the Intake is on target
 * 
 * @author Wesley
 *
 */
public class WaitForIntakeAction extends TimeoutAction {
	private double m_tolerance;
	public WaitForIntakeAction(double timeout, double tolerance) {
		super(timeout);
		m_tolerance = tolerance;
	}

	@Override
	public boolean isFinished() {
		return super.isFinished() || intake.isOnTarget(m_tolerance);
	}
}
