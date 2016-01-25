package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Lifter;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.lib.Gearbox;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * 
 * 
 * @author Wesley
 *
 */
public class HardwareAdapter {
	// Motors
	public static final VictorSP kDriveFrontLeft	= new VictorSP(2);
	public static final VictorSP kDriveFrontRight	= new VictorSP(2);
	public static final VictorSP kDriveBackLeft		= new VictorSP(2);
	public static final VictorSP kDriveBackRight	= new VictorSP(2);
	
	// Solenoids
	public static final DoubleSolenoid kLeftShifter	= new DoubleSolenoid(1,2);
	public static final DoubleSolenoid kRightShifter= new DoubleSolenoid(3,4);
	
	// Sensors
	public static final AHRS kNavX					= new AHRS(new SerialPort(57600, SerialPort.Port.kMXP));
	public static final Encoder kDriveLeftEncoder	= new Encoder(0,1);
	public static final Encoder kDriveRightEncoder	= new Encoder(2,3);
	
	// Gearboxes
	public static final Gearbox kDriveLeftGearbox	= new Gearbox(kDriveFrontLeft, kDriveBackLeft, kDriveLeftEncoder, kLeftShifter);
	public static final Gearbox kDriveRightGearbox	= new Gearbox(kDriveFrontRight, kDriveBackRight, kDriveRightEncoder, kRightShifter);
	
	// Subsystems
	public static final Drive kDrive = new Drive(kDriveLeftGearbox, kDriveRightGearbox);
	public static final Shooter kShooter = new Shooter();
	public static final Intake kIntake = new Intake();
	public static final Lifter kLifter = new Lifter();
}
