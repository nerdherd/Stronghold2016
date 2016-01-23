package org.camsrobotics.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * Logger Class
 * 
 * @author Michael
 *
 */
public class Logger {
	private File m_csv;
	private FileWriter m_fw;
	private String m_filename;
	
	/**
	 * Constructs file logger.
	 * @param filename
	 */
	public Logger(String filename) {
		m_filename = filename;
	}
	
	/**
	 * Run to create file to write to.
	 */
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
	
	/**
	 * Pass a string to write a message to the file.
	 * @param message
	 */
	//TODO make it so you cant do this if init has not been run
	public void write(String message) {
		try {
			m_fw.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close the file.
	 */
	public void close() {
		try {
			m_fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
