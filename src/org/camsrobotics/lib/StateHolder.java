package org.camsrobotics.lib;

import java.util.HashMap;

/**
 * Should be passed into all subsystems to grab states
 * 
 * @author Wesley
 *
 */
public class StateHolder {
	private HashMap<String, String> m_states = new HashMap<String, String>();
	
	public void put(String key, String val)	{
		m_states.put(key,  val);
	}
	
	public String get(String key)	{
		return m_states.get(key);
	}
	
	public String[] getSet()	{
		return (String[]) m_states.keySet().toArray();
	}
}
