
package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.auto.AutoExecutor;
import org.camsrobotics.frc2016.auto.modes.*;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Shooter;
import org.camsrobotics.frc2016.teleop.Commands;
import org.camsrobotics.frc2016.teleop.TeleopManager;
import org.camsrobotics.lib.MultiLooper;
import org.camsrobotics.lib.NerdyIterativeRobot;
import org.camsrobotics.lib.NerdyMath;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
	
	AutoExecutor auto = new AutoExecutor(new RockWallAuto());
	
	Compressor compressor = HardwareAdapter.kCompressor;
	Drive drive = HardwareAdapter.kDrive;
	Shooter shooter = HardwareAdapter.kShooter;
	Intake intake = HardwareAdapter.kIntake;
	
	DriverInput driverInput = HardwareAdapter.kDriverInput;
	TeleopManager teleop = new TeleopManager();
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	SendableChooser testCommands;
	
	enum TEST_COMMANDS	{
		DISABLED, DRIVE_LEFT, DRIVE_RIGHT, SHOOTER_RPM
	}
	
	enum AUTO_MODES	{
		LOW_BAR, CAT_D, RAMPARTS
	}
	
	AUTO_MODES autoMode = AUTO_MODES.RAMPARTS;
    double driveP = 0.044444;
    double driveI = 0;
    
    public void robotInit() {
    	compressor.start();
    	
    	System.out.println("NerdyBot Mantis Initialization");
    	
    	controllers.addLoopable(shooter);
    	controllers.addLoopable(intake);
    	
    	slowControllers.addLoopable(drive);
    	
    	testCommands = new SendableChooser();
    	testCommands.addDefault("Disabled", TEST_COMMANDS.DISABLED);
    	testCommands.addObject("Drive Left", TEST_COMMANDS.DRIVE_LEFT);
    	testCommands.addObject("Drive Right", TEST_COMMANDS.DRIVE_RIGHT);
    	testCommands.addObject("Shooter RPM", TEST_COMMANDS.SHOOTER_RPM);
    	
    	SmartDashboard.putNumber("OuterWorks", Constants.kOuterWorksAngle);
    	SmartDashboard.putNumber("OffBatter", Constants.kOffBatterAngle);
    	SmartDashboard.putNumber("Batter", Constants.kBatterAngle);

    	SmartDashboard.putNumber("Vision P", Constants.kDriveVisionP);
    	SmartDashboard.putNumber("Vision I", Constants.kDriveVisionI);
    	SmartDashboard.putNumber("Vision D", Constants.kDriveVisionD);
    	
    	SmartDashboard.putNumber("Camera Aim", Constants.kCameraAim);

    	SmartDashboard.putNumber("driveP", driveP);
    	SmartDashboard.putNumber("driveI", driveI);

    	SmartDashboard.putNumber("Default Vision", Constants.kCameraDefault);
    	
    	SmartDashboard.putNumber("Time", 3);
    	SmartDashboard.putNumber("Time2", 10);
    	
    	SmartDashboard.putNumber("Forward Priority", 1);
    	
		SmartDashboard.putNumber("Ramparts Straight Power", 1);
		
		SmartDashboard.putNumber("Camera Lift P", Constants.kCameraLiftP);
		SmartDashboard.putNumber("Camera Lift D", Constants.kCameraLiftD);
		SmartDashboard.putNumber("Camera Lift Alpha", Constants.kCameraLiftAlpha);
		
		SmartDashboard.putNumber("ShooterPositionVision", 120);
		
		SmartDashboard.putNumber("Intakes Tucked", Constants.kIntakeTucked);
		SmartDashboard.putNumber("Intakes Ground", Constants.kIntakeGround);
		SmartDashboard.putNumber("Intakes Resting", Constants.kIntakeResting);
		
		SmartDashboard.putNumber("Distortion", Constants.kCameraDistortion);
		
		SmartDashboard.putNumber("High Goal RPM", Constants.kHighGoalRPM);
		SmartDashboard.putNumber("Low Goal RPM", Constants.kLowGoalRPM);
		
		SmartDashboard.putNumber("Auto Mode", 0); // Use this to select auto mode
		
    }
    
    Timer autoTimer = new Timer();
    double lastTime;
    AHRS nav = HardwareAdapter.kNavX;
    
    public void autonomousInit() {
    	System.out.println("NerdyBot Mantis Autonomous Start");
    	integration = 0;
    	drive.zero();
    	drive.shiftDown();
    	
//    	auto.start();
    	
    	nav.zeroYaw();

    	autoTimer.start();

    	controllers.start();
    	slowControllers.start();
    	
    	lastTime = Timer.getFPGATimestamp();
    	
    	drive.shiftUp();
    	
    	double aMode = SmartDashboard.getNumber("Auto Mode");
    	if(aMode == 0)	{
    		autoMode = AUTO_MODES.RAMPARTS;
    	}	else if(aMode == 1)	{
    		autoMode = AUTO_MODES.CAT_D;
    	}	else if(aMode == 2)	{
    		autoMode = AUTO_MODES.LOW_BAR;
    	}
    	
    }
   
    double integration = 0;
    double lastError = 0;
    
    double m_oscilateCount = 0;
    
    int state = 0;
    
    public void autonomousPeriodic() {
    	Constants.kCameraDefault = (int) SmartDashboard.getNumber("Default Vision");
    	driveP = SmartDashboard.getNumber("driveP");
    	driveI = SmartDashboard.getNumber("driveI");
    	
    	SmartDashboard.putNumber("Yaw", nav.getYaw());
    	
    	if(autoMode == AUTO_MODES.LOW_BAR)	{
    		intake.manualDrive(-0.25);
    		if(autoTimer.get() < 4)	{
	        	drive.driveOpenLoop(new DriveSignal(-1,-1));
	    	}	else	{
	    		drive.driveOpenLoop(DriveSignal.kStop);
	    	}
    	}	else if(autoMode == AUTO_MODES.CAT_D)	{
    		if(autoTimer.get() < 4)	{
	        	drive.driveOpenLoop(new DriveSignal(-1,-1));
	    	}	else	{
	    		drive.driveOpenLoop(DriveSignal.kStop);
	    	}
    	}	else if(autoMode == AUTO_MODES.RAMPARTS)	{
    		if(autoTimer.get() < 5)	{
//	    			intake.setIntakeHeight (Constants.kIntakeBallPickup);
    			double straightPower = SmartDashboard.getNumber("Ramparts Straight Power");
    			double time = Timer.getFPGATimestamp();
    			double error = nav.getYaw();
    			double p = error * driveP;
    			integration += (error + lastError) * (time - lastTime)/2;
    			lastTime = time;
    			lastError = error;
    			double i = integration * driveI;
    			SmartDashboard.putNumber("P", p);
    			SmartDashboard.putNumber("I",i);
    			double pow = p + i;
    			SmartDashboard.putNumber("Pow", pow);
    			double leftPow  =  pow - straightPower;
    			double rightPow = -pow - straightPower;
    			SmartDashboard.putNumber("Left Power", leftPow);
    			SmartDashboard.putNumber("Right Power", rightPow);
    			SmartDashboard.putNumber("Yaw", error);
    			
    			double[] unnormalized = {leftPow, rightPow};
    		 	double[] normalized = NerdyMath.normalize(unnormalized, true);
    			
    			double leftNormalized = normalized[0];
    			double rightNormalized = normalized[1];
    			SmartDashboard.putNumber("Left Normalized", leftNormalized);
    			SmartDashboard.putNumber("Right Normalized", rightNormalized);
    			
    			drive.driveOpenLoop(new DriveSignal(leftNormalized, rightNormalized));
    		}	else	{
    			drive.driveOpenLoop(DriveSignal.kStop);
    		}
//    		}	else if(state == 1)	{
//    			shooter.setShooterAngle(Constants.kOffBatterAngle);
//    			if(autoTimer.get() < SmartDashboard.getNumber("Time2"))	{
//        			drive.setController(new VisionTargetingController(3));
//    			}	else	{
//    				drive.driveOpenLoop(DriveSignal.kStop);
//    				state++;
//    			}
//    		}	else if(state == 2)	{
//    			shooter.setDesiredRPM(Constants.kManualRPM);
//    			
//    			if(Math.abs(shooter.getSpeed() - Constants.kManualRPM) < 5)	{
//    				m_oscilateCount++;
//    				
//    				if(m_oscilateCount == 5)	{
//    					shooter.shoot();
//    				}
//    			}
//    		}
    	}
    	
    }

    public void teleopInit()	{
    	System.out.println("NerdyBot Mantis Teleoperated Start");
    	
    	drive.zero();
    	
    	controllers.start();
    	slowControllers.start();
    }
    
    public void teleopPeriodic() {
    	Constants.kCameraAim = (int) SmartDashboard.getNumber("Camera Aim");
    	
    	Commands c = driverInput.update();
        teleop.update(c);
        
    	SmartDashboard.putData("PDP", pdp);
        
        drive.reportState();
        shooter.reportState();
        intake.reportState();
        
        Constants.kBatterAngle = SmartDashboard.getNumber("Batter");
        Constants.kOffBatterAngle = SmartDashboard.getNumber("OffBatter");
        Constants.kOuterWorksAngle = SmartDashboard.getNumber("OuterWorks");
        
        Constants.kCameraLiftP = SmartDashboard.getNumber("Camera Lift P");
        Constants.kCameraLiftD = SmartDashboard.getNumber("Camera Lift D");
        Constants.kCameraLiftAlpha = SmartDashboard.getNumber("Camera Lift Alpha");
        Constants.kCameraDistortion = SmartDashboard.getNumber("Distortion");
        
        Constants.kHighGoalRPM = (int) SmartDashboard.getNumber("High Goal RPM");
        Constants.kLowGoalRPM = (int) SmartDashboard.getNumber("Low Goal RPM");
     
        
        drive.setPID(SmartDashboard.getNumber("Vision P", Constants.kDriveVisionP), 
        		SmartDashboard.getNumber("Vision I", Constants.kDriveVisionI), 
        		SmartDashboard.getNumber("Vision D", Constants.kDriveVisionD));
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
    	SmartDashboard.putNumber("PDP 0", pdp.getCurrent(0));
    	SmartDashboard.putNumber("PDP 1", pdp.getCurrent(1));
    	SmartDashboard.putNumber("PDP 2", pdp.getCurrent(2));
    	SmartDashboard.putNumber("PDP 3", pdp.getCurrent(3));
    	SmartDashboard.putNumber("PDP 4", pdp.getCurrent(4));
    	SmartDashboard.putNumber("PDP 5", pdp.getCurrent(5));
    	SmartDashboard.putNumber("PDP 6", pdp.getCurrent(6));
    	SmartDashboard.putNumber("PDP 7", pdp.getCurrent(7));
    	SmartDashboard.putNumber("PDP 8", pdp.getCurrent(8));
    	SmartDashboard.putNumber("PDP 9", pdp.getCurrent(9));
    	SmartDashboard.putNumber("PDP 10", pdp.getCurrent(10));
    	SmartDashboard.putNumber("PDP 11", pdp.getCurrent(11));
    	SmartDashboard.putNumber("PDP 12", pdp.getCurrent(12));
    	SmartDashboard.putNumber("PDP 13", pdp.getCurrent(13));
    	SmartDashboard.putNumber("PDP 14", pdp.getCurrent(14));
    	SmartDashboard.putNumber("PDP 15", pdp.getCurrent(15));

    }
    
    public void testPeriodic() {
    	LiveWindow.setEnabled(false);
    	
//    	// Get the Command
//    	SmartDashboard.putData("Test Mode Commands", testCommands);
//    	if(testCommands.getSelected() == TEST_COMMANDS.DRIVE_LEFT)	{
//    		HardwareAdapter.kDriveLeft1.set(1);
//    		HardwareAdapter.kDriveLeft2.set(1);
//    		HardwareAdapter.kDriveLeft3.set(1);
//
//    		HardwareAdapter.kDriveRight1.set(0);
//    		HardwareAdapter.kDriveRight2.set(0);
//    		HardwareAdapter.kDriveRight3.set(0); 		
//    	}	else if(testCommands.getSelected() == TEST_COMMANDS.DRIVE_RIGHT)	{
//    		HardwareAdapter.kDriveLeft1.set(0);
//    		HardwareAdapter.kDriveLeft2.set(0);
//    		HardwareAdapter.kDriveLeft3.set(0);
//
//    		HardwareAdapter.kDriveRight1.set(1);
//    		HardwareAdapter.kDriveRight2.set(1);
//    		HardwareAdapter.kDriveRight3.set(1); 		
//    	}	else	{
//    		HardwareAdapter.kDriveLeft1.set(0);
//    		HardwareAdapter.kDriveLeft2.set(0);
//    		HardwareAdapter.kDriveLeft3.set(0);
//
//    		HardwareAdapter.kDriveRight1.set(0);
//    		HardwareAdapter.kDriveRight2.set(0);
//    		HardwareAdapter.kDriveRight3.set(0); 		
//    	}
    	
    	// PDP
    	SmartDashboard.putData("PDP", pdp);
    	
    	// Drive Data
//    	SmartDashboard.putData("Drive Left 1", HardwareAdapter.kDriveLeft1);
//    	SmartDashboard.putData("Drive Left 2", HardwareAdapter.kDriveLeft2);
//    	SmartDashboard.putData("Drive Left 3", HardwareAdapter.kDriveLeft3);
//    	SmartDashboard.putData("Drive Right 1", HardwareAdapter.kDriveRight1);
//    	SmartDashboard.putData("Drive Right 2", HardwareAdapter.kDriveRight2);
//    	SmartDashboard.putData("Drive Right 3", HardwareAdapter.kDriveRight3);

    	SmartDashboard.putData("Drive Left Encoder", HardwareAdapter.kDriveLeftEncoder);
    	SmartDashboard.putData("Drive Right Encoder", HardwareAdapter.kDriveRightEncoder);
    	
    	// Shooter Data
//    	SmartDashboard.putData("Shooter Lift", HardwareAdapter.kShooterLift);
//    	SmartDashboard.putData("Shooter Left", HardwareAdapter.kShooterLeft);
//    	SmartDashboard.putData("Shooter Right", HardwareAdapter.kShooterRight);
    	
    	SmartDashboard.putNumber("Shooter Lift Position", HardwareAdapter.kShooterLift.getPosition());
    	SmartDashboard.putNumber("Shooter Left RPM", HardwareAdapter.kShooterLeft.getSpeed());
    	SmartDashboard.putNumber("Shooter Right RPM", HardwareAdapter.kShooterRight.getSpeed());
    	
    	// Intake Data
//    	SmartDashboard.putData("Intake Artic", HardwareAdapter.kIntakeArtic);
//    	SmartDashboard.putData("Intake Rollers", HardwareAdapter.kIntakeRollers);
    	
    	SmartDashboard.putNumber("Intake Position", HardwareAdapter.kIntakeArtic.getPosition());
    	SmartDashboard.putNumber("Intake RPM", HardwareAdapter.kIntakeRollers.getSpeed());
    	
//    	shooter.setOuterWorksAngle(SmartDashboard.getNumber("OuterWorks"));
//    	shooter.setOffBatterAngle(SmartDashboard.getNumber("OffBatter"));
//    	shooter.setBatterAngle(SmartDashboard.getNumber("Batter"));
    	
    	Commands c = driverInput.update();
        teleop.update(c);
    }
    
}
