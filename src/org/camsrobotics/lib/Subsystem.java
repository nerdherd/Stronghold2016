package org.camsrobotics.lib;

public abstract class Subsystem implements Loopable {
	private final String m_name;
	
	public Subsystem(String name)	{
		m_name = name;
	}
	
	public String getName()	{
		return m_name;
	}
	
	public abstract void getState(StateHolder states);
}
