package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Lifter;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.lib.Gearbox;
import org.camsrobotics.lib.NerdyJoystick;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * Hardware Components
 * 
 * @author Wesley
 *
 */
public class HardwareAdapter {
	// Driver Input
	public static final NerdyJoystick kDriveLeftStick	= new NerdyJoystick(0);
	public static final NerdyJoystick kDriveRightStick	= new NerdyJoystick(1);
	public static final NerdyJoystick kButtonBox		= new NerdyJoystick(2);
	public static final DriverInput kDriverInput		= new DriverInput(kDriveLeftStick, kDriveRightStick, kButtonBox);
	
	// Motors
	public static final VictorSP kDriveFrontLeft		= new VictorSP(2);
	public static final VictorSP kDriveFrontRight		= new VictorSP(3);
	public static final VictorSP kDriveBackLeft			= new VictorSP(4);
	public static final VictorSP kDriveBackRight		= new VictorSP(5);
	
	public static final CANTalon kShooterLeft			= new CANTalon(1);
	public static final CANTalon kShooterRight			= new CANTalon(2);
	public static final CANTalon kShooterLift			= new CANTalon(3);
	
	// Solenoids
	public static final DoubleSolenoid kLeftShifter		= new DoubleSolenoid(1,2);
	public static final DoubleSolenoid kRightShifter	= new DoubleSolenoid(3,4);
	public static final DoubleSolenoid kShooterPunch	= new DoubleSolenoid(5,6);
	
	// Sensors
	public static final AHRS kNavX						= new AHRS(new SerialPort(57600, SerialPort.Port.kMXP));
	public static final Encoder kDriveLeftEncoder		= new Encoder(0,1);
	public static final Encoder kDriveRightEncoder		= new Encoder(2,3);
	public static final Ultrasonic kUltrasonic			= new Ultrasonic(4,5);
	
	// Gearboxes
	public static final Gearbox kDriveLeftGearbox		= new Gearbox(kDriveFrontLeft, kDriveBackLeft, kDriveLeftEncoder, kLeftShifter);
	public static final Gearbox kDriveRightGearbox		= new Gearbox(kDriveFrontRight, kDriveBackRight, kDriveRightEncoder, kRightShifter);
	
	// Subsystems
	public static final Drive kDrive = new Drive(kDriveLeftGearbox, kDriveRightGearbox, kNavX);
	public static final Shooter kShooter = new Shooter(kShooterLeft, kShooterRight, kShooterPunch, kShooterLift);
	public static final Intake kIntake = new Intake(null, null, null);
	public static final Lifter kLifter = new Lifter();
}
