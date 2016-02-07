package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Lifter;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.lib.Gearbox;
import org.camsrobotics.lib.NerdyJoystick;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
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
	public static final VictorSP kDriveLeft1			= new VictorSP(0);
	public static final VictorSP kDriveLeft2			= new VictorSP(1);
	public static final VictorSP kDriveLeft3			= new VictorSP(2);
	public static final VictorSP kDriveRight1			= new VictorSP(3);
	public static final VictorSP kDriveRight2			= new VictorSP(4);
	public static final VictorSP kDriveRight3			= new VictorSP(5);
	
	public static final CANTalon kShooterLeft			= new CANTalon(1);
	public static final CANTalon kShooterRight			= new CANTalon(2);
	public static final CANTalon kShooterLift			= new CANTalon(3);
	
	public static final VictorSP kIntakeRollers			= new VictorSP(6);
	public static final CANTalon kIntakeArtic			= new CANTalon(4);
	
	// Pneumatics
	public static final Compressor kCompressor			= new Compressor();
	public static final DoubleSolenoid kLeftShifter		= new DoubleSolenoid(1,2);
	public static final DoubleSolenoid kRightShifter	= new DoubleSolenoid(3,4);
	public static final DoubleSolenoid kShooterPunch	= new DoubleSolenoid(5,6);
	
	// Sensors
	public static final AHRS kNavX						= new AHRS(SerialPort.Port.kMXP);
	public static final Encoder kDriveLeftEncoder		= new Encoder(0,1);
	public static final Encoder kDriveRightEncoder		= new Encoder(2,3);
	public static final Ultrasonic kUltrasonic			= new Ultrasonic(4,5);
	
	// Gearboxes
	public static final Gearbox kDriveLeftGearbox		= new Gearbox(kDriveLeft1, kDriveLeft2, kDriveRight3, kDriveLeftEncoder, kLeftShifter);
	public static final Gearbox kDriveRightGearbox		= new Gearbox(kDriveRight1, kDriveRight2, kDriveRight3, kDriveRightEncoder, kRightShifter);
	
	// Subsystems
	public static final Drive kDrive = new Drive("Drivebase", kDriveLeftGearbox, kDriveRightGearbox, kNavX);
	public static final Shooter kShooter = new Shooter("Shooter", kShooterLeft, kShooterRight, kShooterPunch, kShooterLift);
	public static final Intake kIntake = new Intake("Intakes", kIntakeRollers, kIntakeArtic);
	public static final Lifter kLifter = new Lifter();
}
