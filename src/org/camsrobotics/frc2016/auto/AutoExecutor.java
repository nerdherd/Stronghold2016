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
	 * Constructs the AutoExecutor with the given mode
	 * 
	 * @param mode The mode to be executed
	 */
	public AutoExecutor(AutoMode mode)	{
		m_auto = mode;
	}
	
	/**
	 * Let's go!
	 */
	public void start()	{
		if(m_thread == null)	{
			m_thread = new Thread(new Runnable() {
				@Override
				public void run() {
					m_auto.run();
				}
			});
			
			m_thread.start();
		}
	}
	
	/**
	 * Stops the Autonomous mode
	 */
	public void stop()	{
		if(m_auto != null)	{
			m_auto.stop();
		}
		m_thread = null;
	}
}
