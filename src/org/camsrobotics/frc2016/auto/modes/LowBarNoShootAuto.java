package org.camsrobotics.frc2016.auto.modes;

import org.camsrobotics.frc2016.Constants;
import org.camsrobotics.frc2016.auto.AutoMode;
import org.camsrobotics.frc2016.auto.actions.*;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.controllers.DriveStraightController;

public class LowBarNoShootAuto extends AutoMode {

	@Override
	public void run() {
		// Lower Intake
		intake.setIntakeHeight(Constants.kIntakeBallPickup);
		runAction(new WaitForIntakeAction(15, .025));
		
		// Pass the Low Bar
		drive.setController(new DriveStraightController(3, .25));
		runAction(new TimeoutAction(5));
		
		// Stop
		drive.driveOpenLoop(DriveSignal.kStop);
	}

}
