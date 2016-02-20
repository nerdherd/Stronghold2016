package org.camsrobotics.frc2016.auto.modes;

import org.camsrobotics.frc2016.auto.AutoMode;
import org.camsrobotics.frc2016.auto.actions.*;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.controllers.DriveStraightController;

public class LowBarNoShootAuto extends AutoMode {

	@Override
	public void run() {
		// Pass the Low Bar
		drive.setController(new DriveStraightController(3, .5));
		runAction(new WaitForUltrasonicSeeAction(45, 15));
		runAction(new WaitForUltrasonicBlindAction(45, 15));
		
		// Stop
		drive.driveOpenLoop(DriveSignal.kStop);
	}

}
