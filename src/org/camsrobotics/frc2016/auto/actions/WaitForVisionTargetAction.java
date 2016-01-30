package org.camsrobotics.frc2016.auto.actions;

import org.camsrobotics.frc2016.Vision;

/**
 * Waits for the vision target to 
 * 
 * @author Michael
 *
 */
public class WaitForVisionTargetAction extends TimeoutAction {
	private Vision m_table = new Vision();
	
	public WaitForVisionTargetAction(double timeout) {
		super(timeout);
	
	}
	
	@Override
	public boolean isFinished()	{
		return super.isFinished() || m_table != null;
	}
}
