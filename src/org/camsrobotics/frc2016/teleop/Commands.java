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
		DOWN, UP, IDLE
	}
	
	public static enum ShooterCommands	{
		IDLE, MANUAL, OFF_BATTER, BATTER, OUTER_WORKS, RESTING, VERTICAL_ALIGN
	}
	
	public static enum FlywheelCommands	{
		IDLE, HIGH, LOW
	}
	
	public static enum IntakeCommands	{
		IDLE, MANUAL, BALL_PICKUP, TUCKED_IN_ALL, GROUND, TUCKED
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
	
	public boolean compress = false;
	public boolean shooterFire = false;
	
	public boolean reset = false;
}
