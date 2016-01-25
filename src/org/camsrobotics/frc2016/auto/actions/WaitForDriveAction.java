package org.camsrobotics.frc2016.auto.actions;

/**
 * Delay until the Drive is on target
 * 
 * @author Wesley
 *
 */
public class WaitForDriveAction extends TimeoutAction {
	public WaitForDriveAction(double timeout) {
		super(timeout);
	}

	@Override
	public boolean isFinished() {
		return super.isFinished() || drive.isOnTarget();
	}
}
