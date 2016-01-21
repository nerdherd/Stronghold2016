package org.camsrobotics.lib;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Button monitoring system
 * 
 * @author Wesley
 *
 */
public class NerdyButton {
	private int m_buttonPort;
	private boolean m_current = false;
	private boolean m_last = false;
	private Joystick m_joy;
	
	/**
	 * Default constructor
	 * 
	 * @param joy The joystick the button is located on
	 * @param buttonPort The port of the button
	 */
	public NerdyButton(Joystick joy, int buttonPort) {
		m_buttonPort = buttonPort;
		m_joy = joy;
	}
	
	/**
	 * Was the button just pressed?
	 * 
	 * @return True for pressed, false for not pressed
	 */
	public boolean wasPressed() {
		return m_current && !m_last;
	}
	
	/**
	 * Was the button just released?
	 * 
	 * @return True for released, false for not released
	 */
	public boolean wasReleased() {
		return !m_current && m_last;
	}
	
	/**
	 * Was the button held?
	 * 
	 * @return True for held, else false
	 */
	public boolean wasHeld()	{
		return m_current && m_last;
	}
	
	/**
	 * Is the button pressed?
	 * 
	 * @return True for pressed, false for not pressed
	 */
	public boolean get() {
		return m_current;
	}
	
	/**
	 * Updated the values. Run every time getting a value
	 */
	public void update() {
		m_last = m_current;
		m_current = m_joy.getRawButton(m_buttonPort);
	}
}
