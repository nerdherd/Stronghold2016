package org.camsrobotics.frc2016.auto;

import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.auto.actions.Action;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;

/**
 * Base class for Autonomous modes
 * 
 * @author Wesley
 *
 */
public abstract class AutoMode {
	protected Drive drive = HardwareAdapter.kDrive;
	protected Shooter shooter = HardwareAdapter.kShooter;
	protected Intake intake = HardwareAdapter.kIntake;
	
	private double m_period = 1/50.0;
	private boolean m_enabled = false;

	public abstract void run();
	
	public void stop()	{
		m_enabled = false;
	}
	
	public boolean isEnabled()	{
		return m_enabled;
	}
	
	public void runAction(Action a)	{
		if(m_enabled)	{	
			a.start();
			while(isEnabled() && !a.isFinished())	{
				a.update();
				try {
					Thread.sleep((long) (m_period * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
