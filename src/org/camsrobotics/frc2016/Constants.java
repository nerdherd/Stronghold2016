package org.camsrobotics.frc2016;

/**
 * Assorted Constants
 * 
 * @author Wesley
 *
 */
public class Constants {
	
	/*
	 * Camera Constants
	 */
	public final static int kCameraFrameHeight	= 240;
	public final static int kCameraFrameWidth	= 320;
	public final static double kCameraFOVAngle	= 21.2505055;
	
	/*
	 * Drive Constants
	 */
	public final static double kDriveRotationP		= 0;
	public final static double kDriveRotationI		= 0;
	public final static double kDriveRotationD		= 0;
	public final static double kDriveVisionP		= 0.00245;
	public final static double kDriveVisionI		= 0;
	public final static double kDriveVisionD		= 0;
	public final static double kDriveTranslationP	= 0;
	public final static double kDriveTranslationI	= 0;
	public final static double kDriveTranslationD	= 0;
	
	/*
	 * Shooter Constants
	 */
	public final static double kFlywheelF		= 0;
	public final static double kFlywheelP		= 0.08204;
	public final static double kFlywheelI		= 0.0005;
	public final static double kFlywheelD		= 0.375;
	public final static double kLiftF			= 0;
	public final static double kLiftP			= 3;
	public final static double kLiftI			= 0.015625;
	public final static double kLiftD			= 0;
	public final static double kLiftAlpha		= 0.05;
	public final static double kShootTime		= 0.5;
	
	public final static int kManualRPM			= 3500;
	public final static int kShortRangeRPM		= 2000;
	public final static int kMediumRangeRPM		= 3000;
	public final static int kLongRangeRPM		= 4000;

	public final static int kShortRangeActivate	= 2700;
	public final static int kMediumRangeActivate= 4000;
	public final static int kLongRangeActivate	= 5400;
	
	public final static double kShortRangeAngle		= 0.555;
	public final static double kMediumRangeAngle	= 0.6;
	public final static double kLongRangeAngle		= 0.481;
	
	public final static double kMinHeight		= 0.431;
	public final static double kMaxHeight		= 0.561;
	
	/*
	 * Intake Constants
	 */
	public final static double kIntakeSpeed		= 1;
	public final static double kIntakeF			= 0;
	public final static double kIntakeP			= 5;
	public final static double kIntakeI			= 0;
	public final static double kIntakeD			= 16;
	public final static double kIntakeAlpha		= 0.05;
	
	public final static double kIntakeBallPickup	= -0.294;
	public final static double kIntakeTuckedIn		= 0.02;
	public final static double kIntakeTucked		= -0.02;
	public final static double kIntakeGround		= -0.343;
	
}
