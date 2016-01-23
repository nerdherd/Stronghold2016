package org.camsrobotics.frc2016.auto;

import org.camsrobotics.frc2016.auto.modes.*;

/**
 * Autonomous mode magic
 * 
 * @author Wesley
 *
 */
public class AutoExecutor {
	private AutoMode m_auto;
	private Thread m_thread = null;
	
	/**
	 * Autonomous Modes
	 * 
	 * @author Wesley
	 *
	 */
	public enum Mode	{
		DO_NOTHING
	}
	
	/**
	 * Default Constructor
	 * 
	 * @param mode
	 */
	public AutoExecutor(Mode mode)	{
		switch(mode)	{
		case DO_NOTHING:
			m_auto = new DoNothingAuto();
			break;
		default:
			m_auto = new DoNothingAuto();
			break;
		}
	}
	
	/**
	 * Let's go!
	 */
	public void start()	{
		if(m_thread == null)	{
			m_thread = new Thread(new Runnable() {
				@Override
				public void run() {
					if(m_auto != null)	{
						m_auto.run();
					}
				}
			});
			
			m_thread.start();
		}
	}
	
	/**
	 * Stops the Autonomous mode
	 */
	public void stop()	{
		if(m_thread != null)	{
			m_thread.stop();
		}
	}
}
