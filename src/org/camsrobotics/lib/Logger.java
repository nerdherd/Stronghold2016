package org.camsrobotics.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Logger {
	private File m_csv;
	private FileWriter m_fw;
	private String m_filename;

	public Logger(String filename) {
		m_filename = filename;
	}
	
	public void init() {
		m_csv = new File(m_filename);
		try {
			m_csv.createNewFile();
			m_fw = new FileWriter(m_csv);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void write(String message) {
		try {
			m_fw.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			m_fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
