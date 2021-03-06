package org.camsrobotics.frc2016.auto.modes;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.auto.AutoMode;
import org.camsrobotics.frc2016.auto.actions.*;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.controllers.DriveRotationController;
import org.camsrobotics.frc2016.subsystems.controllers.DriveStraightController;

/**
 * Lowbar and shoot
 * 
 * @author Wesley
 *
 */
public class LowBarAuto extends AutoMode {

	@Override
	public void routine() {
		// Pass the Low Bar
		drive.setController(new DriveStraightController(3, .5));
		//runAction(new WaitForUltrasonicSeeAction(45, 15));
		//runAction(new WaitForUltrasonicBlindAction(45, 15));
		
		// Turn until vision sees
		drive.setController(new DriveRotationController(45, 0)); // Tolerance really does not matter
		runAction(new WaitForVisionTargetAction(15));
		
		// Stop
		drive.driveOpenLoop(DriveSignal.kStop);
		
		//Line up with target
		//runAction(new WaitForVisionAlignX(15));
		//runAction(new WaitForVisionAlignY());
		// Spin Wheels
		shooter.setDesiredRPM(Constants.kLongRangeRPM);
		runAction(new WaitForShooterRPMAction(15));
		
		// Shoot
		shooter.shoot();
		
	}
	
}
