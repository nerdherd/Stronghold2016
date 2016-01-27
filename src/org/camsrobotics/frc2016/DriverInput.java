package org.camsrobotics.frc2016;

import org.camsrobotics.lib.NerdyButton;
import org.camsrobotics.lib.NerdyJoystick;

/**
 * Driver Interface
 * 
 * @author Wesley
 *
 */
public class DriverInput {
	
	// Driver Functions
	public static final NerdyJoystick kDriverLeftStick	= new NerdyJoystick(0);
	public static final NerdyJoystick kDriverRightStick	= new NerdyJoystick(1);
	
	public static final NerdyButton kSnapToVisionTarget	= kDriverRightStick.getButton(99);
	
	// Operator Functions
	public static final NerdyJoystick kButtonBox		= new NerdyJoystick(2);
	
	public static final NerdyButton kShooterShortRange	= kButtonBox.getButton(99);
	public static final NerdyButton kShooterMediumRange	= kButtonBox.getButton(99);
	public static final NerdyButton kShooterLongRange	= kButtonBox.getButton(99);
	public static final NerdyButton kShooterVision		= kButtonBox.getButton(99);
	public static final NerdyButton kShoot				= kButtonBox.getButton(99);
	
}
