package org.camsrobotics.frc2016.auto.modes;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.auto.AutoMode;
import org.camsrobotics.frc2016.auto.actions.WaitForShooterRPMAction;
import org.camsrobotics.frc2016.auto.actions.WaitForUltrasonicAction;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.controllers.DriveRotationController;
import org.camsrobotics.frc2016.subsystems.controllers.DriveStraightController;

public class LowBarAuto extends AutoMode {

	@Override
	public void run() {
		// Pass the Low Bar
		drive.setController(new DriveStraightController(3, .5));
		runAction(new WaitForUltrasonicAction(5, 15));
		
		// Turn until vision sees
		drive.setController(new DriveRotationController(45, 0)); // Tolerance really does not matter
		// insert wait for vision target action
		
		// Stop
		drive.driveOpenLoop(DriveSignal.kStop);
		
		// Spin Wheels
		shooter.setDesiredRPM(Constants.kLongRangeRPM);
		runAction(new WaitForShooterRPMAction(15));
		
		// Shoot
		shooter.shoot();
		
	}
	
}
