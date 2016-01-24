package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.lib.Gearbox;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class HardwareAdapter {
	// Motors
	public static final VictorSP kDriveFrontLeft	= new VictorSP(2);
	public static final VictorSP kDriveFrontRight	= new VictorSP(2);
	public static final VictorSP kDriveBackLeft		= new VictorSP(2);
	public static final VictorSP kDriveBackRight	= new VictorSP(2);
	
	// Solenoids
	public static final DoubleSolenoid kLeftShifter	= new DoubleSolenoid(1,2);
	public static final DoubleSolenoid kRightShifter= new DoubleSolenoid(3,4);
	
	// Gearboxes
	public static final Gearbox kDriveLeftGearbox	= new Gearbox(kDriveFrontLeft, kDriveBackLeft, kLeftShifter);
	public static final Gearbox kDriveRightGearbox	= new Gearbox(kDriveFrontRight, kDriveBackRight, kRightShifter);
	
	// Subsystems
	public static final Drive kDrive = new Drive(kDriveLeftGearbox, kDriveRightGearbox);
	public static final Shooter kShooter = new Shooter();
	public static final Intake kIntake = new Intake();
}
