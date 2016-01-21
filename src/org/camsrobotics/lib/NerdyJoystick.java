package org.camsrobotics.lib;

import edu.wpi.first.wpilibj.Joystick;

/**
 * An improved Joystick class
 * 
 * @author Wesley
 *
 */
public class NerdyJoystick extends Joystick{
	
	/**
	 * Default constructor
	 * 
	 * @param port The port on driverstation
	 */
	public NerdyJoystick(int port) {
		super(port);
	}
	
	/**
	 * Gets the real Y position (up is positive)
	 * 
	 * @return +1 for all the way up, -1 for all the way down
	 */
	public double getTrueY()	{
		return -getY();
	}
	
	/**
	 * Gets the angle of the joystick in radians
	 * @return 0 is forward, pi/2 is right, pi is backwards, 3pi/2 is left
	 */
	public double getAngleRad()	{
		return (Math.atan2(getTrueY(), getX()) + 3*Math.PI/2) % (2*Math.PI);
	}
	
	/**
	 * Gets the angle of the joystick in degrees
	 * @return 0 is forward, 90 is right, 180 is backwards, 270 is left
	 */
	public double getAngleDeg()	{
		return NerdyMath.radsToDeg(getAngleRad());
	}
	
	/**
	 * Gets a Button class with a specified port
	 * 
	 * @param port	The number that corresponds to the joystick button number
	 * @return		The instance of this button.
	 */
	public NerdyButton getButton(int port)	{
		return new NerdyButton(this, port);
	}
}
