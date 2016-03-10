
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
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
	
	final int DRIVE_TUNE = 0;
	
	int mode = DRIVE_TUNE;

    public void robotInit() {
    	compressor.start();
    	
    	System.out.println("NerdyBot Mantis Initialization");
    	
    	controllers.addLoopable(shooter);
    	controllers.addLoopable(intake);
    	
    	slowControllers.addLoopable(drive);
    	
    	SmartDashboard.putNumber("RPM", 3000);
    	SmartDashboard.putNumber("Angle", 0);
    	if (DRIVE_TUNE == mode) {
    		
    		SmartDashboard.putNumber("Drive Right Encoder Position", HardwareAdapter.kDriveRightEncoder.get());
    		SmartDashboard.putNumber("Drive Left Encoder Position",HardwareAdapter.kDriveLeftEncoder.get());
    		
    		SmartDashboard.putNumber("Drive Right Encoder Speed", (HardwareAdapter.kDriveRightEncoder.getRate()));
    		SmartDashboard.putNumber("Drive Left Encoder Speed", (HardwareAdapter.kDriveLeftEncoder.getRate()));
    		
    		SmartDashboard.putNumber("Channel0", pdp.getCurrent(0));
        	SmartDashboard.putNumber("Channel1", pdp.getCurrent(1));
        	SmartDashboard.putNumber("Channel2", pdp.getCurrent(2));
        	SmartDashboard.putNumber("Channel13", pdp.getCurrent(13));
        	SmartDashboard.putNumber("Channel14", pdp.getCurrent(14));
        	SmartDashboard.putNumber("Channel15", pdp.getCurrent(15));
    		
    		LiveWindow.addActuator("Intake", "Intake Artic", HardwareAdapter.kIntakeArtic);
    		LiveWindow.addActuator("Intake", "Intake Rollers", HardwareAdapter.kIntakeRollers);
    		
    	}
    	LiveWindow.setEnabled(true);
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
        SmartDashboard.putNumber("Current Channel 0", pdp.getCurrent(0));
        SmartDashboard.putNumber("Current Channel 1", pdp.getCurrent(1));
        SmartDashboard.putNumber("Current Channel 2", pdp.getCurrent(2));
        SmartDashboard.putNumber("Current Channel 13", pdp.getCurrent(13));
        SmartDashboard.putNumber("Current Channel 14", pdp.getCurrent(14));
        SmartDashboard.putNumber("Current Channel 15", pdp.getCurrent(15));
        SmartDashboard.putNumber("Current Channel 10", pdp.getCurrent(10));
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
    	// TODO: add the logging
    }
    
    public void testPeriodic() {
    	System.out.println("Intake Pos:    " + HardwareAdapter.kIntakeArtic.getPosition());
    	System.out.println("Intake Roller: " + HardwareAdapter.kIntakeRollers.getSpeed());
    	
    }
    
}
