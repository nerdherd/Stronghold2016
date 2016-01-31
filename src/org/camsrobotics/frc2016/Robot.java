
package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.auto.AutoExecutor;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.teleop.TeleopManager;
import org.camsrobotics.lib.MultiLooper;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * This is where the magic happens!
 * 
 * @author Wesley
 * 
 */
public class Robot extends IterativeRobot {
	
	MultiLooper controllers = new MultiLooper("Controllers", 1/200.0);
	MultiLooper slowControllers = new MultiLooper("SlowControllers", 1/100.0);
	
	AutoExecutor auto = new AutoExecutor(AutoExecutor.Mode.LOW_BAR);
	
	Drive drive = HardwareAdapter.kDrive;
	Shooter shooter = HardwareAdapter.kShooter;
	Intake intake = HardwareAdapter.kIntake;
	
	DriverInput driverInput = HardwareAdapter.kDriverInput;
	TeleopManager teleop = new TeleopManager();
	
    public void robotInit() {
    	System.out.println("NerdyBot Apotheosis Initialization");
    	
    	controllers.addLoopable(shooter);
    	controllers.addLoopable(intake);
    	
    	slowControllers.addLoopable(drive);
    }
    
    public void autonomousInit() {
    	System.out.println("NerdyBot Apotheosis Autonomous Start");
    	
    	drive.resetEncoders();
    	
    	auto.start();
    	
    	controllers.start();
    	slowControllers.start();
    }

    public void autonomousPeriodic() {
    	
    }

    public void teleopInit()	{
    	System.out.println("NerdyBot Apotheosis Teleoperated Start");
    	
    	drive.resetEncoders();
    	
    	controllers.start();
    	slowControllers.start();
    }
    
    public void teleopPeriodic() {
        teleop.update(driverInput.update());
    }
    
    public void disabledInit()	{
    	System.out.println("NerdyBot Apotheosis Disabled...enable me!");
    	
    	auto.stop();
    	
    	controllers.stop();
    	slowControllers.stop();
    	
    	drive.driveOpenLoop(DriveSignal.kStop);
    	drive.stop();
    	
    	shooter.setDesiredRPM(0);
    	shooter.stop();
    }
    
    public void disabledPeriodic()	{
    	
    }
    
    public void allPeriodic()	{
    	// TODO: add the logging
    }
    
}
