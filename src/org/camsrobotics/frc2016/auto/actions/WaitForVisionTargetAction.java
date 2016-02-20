package org.camsrobotics.frc2016.auto.actions;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Waits for the vision target
 * 
 * @author Michael
 *
 */
public class WaitForVisionTargetAction extends TimeoutAction {
	private NetworkTable m_table;
	private double m_contour;
	
	public WaitForVisionTargetAction(double timeout) {
		super(timeout);
		m_table = NetworkTable.getTable("GRIP/myContourReport");
		m_contour = m_table.getNumber("centerx",0);
	}
	
	@Override
	public boolean isFinished()	{
		return super.isFinished() || m_contour != 0;
	}
}