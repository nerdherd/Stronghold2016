
package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.auto.AutoExecutor;
import org.camsrobotics.frc2016.auto.modes.*;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.frc2016.teleop.Commands;
import org.camsrobotics.frc2016.teleop.TeleopManager;
import org.camsrobotics.lib.MultiLooper;
import org.camsrobotics.lib.NerdyIterativeRobot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is where the magic happens!
 * 
 * @author Wesley
 * 
 */
public class Robot extends NerdyIterativeRobot {
	
	MultiLooper controllers = new MultiLooper("Controllers", 1/200.0);
	MultiLooper slowControllers = new MultiLooper("SlowControllers", 1/100.0);
	
	AutoExecutor auto = new AutoExecutor(new LowBarNoShootAuto());
	
	Compressor compressor = HardwareAdapter.kCompressor;
	Drive drive = HardwareAdapter.kDrive;
	Shooter shooter = HardwareAdapter.kShooter;
	Intake intake = HardwareAdapter.kIntake;
	
	DriverInput driverInput = HardwareAdapter.kDriverInput;
	TeleopManager teleop = new TeleopManager();
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
    public void robotInit() {
    	compressor.start();
    	
    	System.out.println("NerdyBot Mantis Initialization");
    	
    	controllers.addLoopable(shooter);
    	controllers.addLoopable(intake);
    	
    	slowControllers.addLoopable(drive);
    }
    
    public void autonomousInit() {
    	System.out.println("NerdyBot Mantis Autonomous Start");
    	
    	drive.zero();
    	
//    	auto.start();
    	
    	controllers.start();
    	slowControllers.start();
    }

    public void autonomousPeriodic() {
    	
    }

    public void teleopInit()	{
    	System.out.println("NerdyBot Mantis Teleoperated Start");
    	
    	drive.zero();
    	
    	controllers.start();
    	slowControllers.start();
    }
    
    public void teleopPeriodic() {
    	Commands c = driverInput.update();
        teleop.update(c);
    }
    
    public void disabledInit()	{
    	System.out.println("NerdyBot Mantis Disabled...enable me!");
    	
    	auto.stop();
    	
    	controllers.stop();
    	slowControllers.stop();
    	
    	drive.stop();
    	
    	shooter.stop();
    	
    	intake.stop();
    }
    
    public void disabledPeriodic()	{
    	
    }
    
    public void allPeriodic()	{
    	
    }
    
    public void testPeriodic() {
    	SmartDashboard.putData("PDP", pdp);
    	
    	// Drive Data
    	SmartDashboard.putData("Drive Left 1", HardwareAdapter.kDriveLeft1);
    	SmartDashboard.putData("Drive Left 2", HardwareAdapter.kDriveLeft2);
    	SmartDashboard.putData("Drive Left 3", HardwareAdapter.kDriveLeft3);
    	SmartDashboard.putData("Drive Right 1", HardwareAdapter.kDriveRight1);
    	SmartDashboard.putData("Drive Right 2", HardwareAdapter.kDriveRight2);
    	SmartDashboard.putData("Drive Right 3", HardwareAdapter.kDriveRight3);

    	SmartDashboard.putData("Drive Left Encoder", HardwareAdapter.kDriveLeftEncoder);
    	SmartDashboard.putData("Drive Right Encoder", HardwareAdapter.kDriveRightEncoder);
    	
    	// Shooter Data
    	SmartDashboard.putData("Shooter Lift", HardwareAdapter.kShooterLift);
    	SmartDashboard.putData("Shooter Left", HardwareAdapter.kShooterLeft);
    	SmartDashboard.putData("Shooter Right", HardwareAdapter.kShooterRight);
    	
    	SmartDashboard.putNumber("Shooter Lift Position", HardwareAdapter.kShooterLift.getPosition());
    	SmartDashboard.putNumber("Shooter Left RPM", HardwareAdapter.kShooterLeft.getSpeed());
    	SmartDashboard.putNumber("Shooter Right RPM", HardwareAdapter.kShooterRight.getSpeed());
    	
    	// Intake Data
    	SmartDashboard.putData("Intake Artic", HardwareAdapter.kIntakeArtic);
    	SmartDashboard.putData("Intake Rollers", HardwareAdapter.kIntakeRollers);
    	
    	SmartDashboard.putNumber("Intake Position", HardwareAdapter.kIntakeArtic.getPosition());
    	SmartDashboard.putNumber("Intake RPM", HardwareAdapter.kIntakeRollers.getSpeed());
    }
    
}
