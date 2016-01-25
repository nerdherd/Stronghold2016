package org.camsrobotics.frc2016.auto;

import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.auto.actions.Action;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Lifter;
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
	protected Lifter lifter = HardwareAdapter.kLifter;
	
	private double m_rate = 1/50;
	private boolean m_enabled = false;

	public abstract void run();
	
	public void stop()	{
		m_enabled = false;
	}
	
	public boolean isEnabled()	{
		return m_enabled;
	}
	
	public void runAction(Action a) throws Exception	{
		if(!m_enabled)	{
			throw new Exception("The Auto Mode has ended!");
		}
		
		a.start();
		while(isEnabled() && !a.isFinished())	{
			a.update();
			Thread.sleep((long) m_rate * 1000);
		}
	}
}
