package org.camsrobotics.frc2016.teleop;

/**
 * Teleoperated commands
 * 
 * @author Wesley
 *
 */
public class Commands {
	
	public static enum DriveCommands	{
		TANK_DRIVE, VISION
	}
	
	public static enum DriveShiftCommands	{
		DOWN, UP
	}
	
	public static enum ShooterCommands	{
		IDLE, MANUAL, LONG_RANGE, MEDIUM_RANGE, SHORT_RANGE
	}
	
	public static enum FlywheelCommands	{
		IDLE, LONG_RANGE, MEDIUM_RANGE, SHORT_RANGE, MANUAL
	}
	
	public static enum IntakeCommands	{
		IDLE, MANUAL, BALL_PICKUP, TUCKED_IN, GROUND, TUCKED
	}
	
	public static enum RollerCommands	{
		IDLE, INTAKE, OUTTAKE
	}
	
	public DriveCommands driveCommand = DriveCommands.TANK_DRIVE;
	public DriveShiftCommands shiftCommand = DriveShiftCommands.DOWN;
	public ShooterCommands shooterCommand = ShooterCommands.IDLE;
	public FlywheelCommands flywheelCommand = FlywheelCommands.IDLE;
	public IntakeCommands intakeCommand = IntakeCommands.IDLE;
	public RollerCommands rollerCommand = RollerCommands.IDLE;
	
	public boolean shooting = false;
	
	public boolean reset = false;
}
