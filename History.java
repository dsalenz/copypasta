package de.copypasta;

import java.io.FileReader;

public class History {
	
	private static History _instance = null;
	
	private String historyDir;
	
	public static History instance() {
		if (_instance == null)
			_instance = new History();
		return _instance;
	}
	
	
	
	
	// Programm-Dir abfangen um die history Dir zu lokalisieren!
	
	
	
	
	
		
	
	public void writeHistory() {

	}

	public void loadHistory() {
		FileReader hreader;

	}



}
