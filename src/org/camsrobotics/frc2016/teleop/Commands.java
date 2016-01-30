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
		MANUAL, LONG_RANGE, MEDIUM_RANGE, SHORT_RANGE, VISION
	}
	
	public static enum IntakeCommands	{
		MANUAL, FLOOR_LEVEL, BALL_PICKUP, TUCKED_IN
	}
	
	public static enum RollerCommands	{
		IDLE, INTAKE, OUTTAKE
	}
	
	public DriveCommands driveCommand = DriveCommands.TANK_DRIVE;
	public DriveShiftCommands shiftCommand = DriveShiftCommands.DOWN;
	public ShooterCommands shooterCommand = ShooterCommands.MANUAL;
	public IntakeCommands intakeCommand = IntakeCommands.MANUAL;
	public RollerCommands rollerCommand = RollerCommands.IDLE;
	
	public boolean shooting = false;
}
