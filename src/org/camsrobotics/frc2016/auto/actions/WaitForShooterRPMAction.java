package org.camsrobotics.frc2016.auto.actions;

import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.subsystems.Shooter;

/**
 * Wait for the shooter to get up to the right RPM
 * 
 * @author Wesley
 *
 */
public class WaitForShooterRPMAction extends TimeoutAction {
	private Shooter m_shooter;
	
	public WaitForShooterRPMAction(double timeout) {
		super(timeout);
		
		m_shooter = HardwareAdapter.kShooter;
	}
	
	@Override
	public boolean isFinished()	{
		return m_shooter.flywheelOnTarget(25);
	}

}
