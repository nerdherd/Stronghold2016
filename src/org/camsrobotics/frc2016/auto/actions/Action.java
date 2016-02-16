package org.camsrobotics.frc2016.auto.actions;

import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;

/**
 * It does a thing
 * 
 * @author Wesley
 *
 */
public abstract class Action {
	protected Drive drive = HardwareAdapter.kDrive;
	protected Shooter shooter = HardwareAdapter.kShooter;
	protected Intake intake = HardwareAdapter.kIntake;
	
	public abstract boolean isFinished();
	
	public abstract void update();
	
	public abstract void disable();
	
	public abstract void start();
}
