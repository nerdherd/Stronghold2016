package org.camsrobotics.frc2016.auto.modes;

import org.camsrobotics.frc2016.auto.AutoMode;
import org.camsrobotics.frc2016.auto.actions.*;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;

public class RockWallAuto extends AutoMode {

	@Override
	public void run() {
		// Pass the Low Bar
		drive.driveOpenLoop(new DriveSignal(1,1));
		runAction(new TimeoutAction(5));
		
		// Stop
		drive.driveOpenLoop(DriveSignal.kStop);
	}

}
