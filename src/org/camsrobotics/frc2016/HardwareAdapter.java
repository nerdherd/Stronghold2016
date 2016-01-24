package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.lib.Gearbox;

import edu.wpi.first.wpilibj.VictorSP;

public class HardwareAdapter {
	// Motors
	public static final VictorSP kDriveFrontLeft	= new VictorSP(2);
	public static final VictorSP kDriveFrontRight	= new VictorSP(2);
	public static final VictorSP kDriveBackLeft		= new VictorSP(2);
	public static final VictorSP kDriveBackRight	= new VictorSP(2);
	
	// Gearboxes
	public static final Gearbox kDriveLeftGearbox	= new Gearbox(kDriveFrontLeft, kDriveBackLeft);
	public static final Gearbox kDriveRightGearbox	= new Gearbox(kDriveFrontRight, kDriveBackRight);
	
	// Subsystems
	public static final Drive drive = new Drive(kDriveLeftGearbox, kDriveRightGearbox);
	public static final Shooter shooter = new Shooter();
	public static final Intake intake = new Intake();
}
