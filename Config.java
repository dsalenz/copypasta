package de.copypasta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Config {

	private static Config _instance = null;

	public final String VERSION = "Copypasta ver.21.05.12";
	private String targetDir;
	private ArrayList<File> sourceFileList = new ArrayList<File>();
	private String propertiesDir;
	private long lastUpdate;

	public ArrayList<File> getSourceFileList() {
		return sourceFileList;
	}

	public void setSourceFileList(ArrayList<File> sourceDirList) {
		this.sourceFileList = sourceDirList;
	}

	public String getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public String getPropertiesDir() {
		return propertiesDir;
	}

	public void setPropertiesDir(String propertiesDir) {
		this.propertiesDir = propertiesDir;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getVERSION() {
		return VERSION;
	}

	private Config() {
		super();
		locateProgramDir();
	}

	public static Config instance() {
		if (_instance == null)
			_instance = new Config();
		return _instance;
	}

	public void locateProgramDir() {
		propertiesDir = (Copypasta.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		propertiesDir = propertiesDir.substring(0, propertiesDir.length() - 4);
		propertiesDir = propertiesDir + "Copypasta" + File.separator + "Config" + File.separator + "config.txt";
	}

	public void writeConfig() throws IOException {
		FileWriter writer = new FileWriter(propertiesDir);
		writer.write("Last Update:\n");
		writer.write(String.valueOf(lastUpdate) + "\n");
		writer.write("Target Dir:\n");
		writer.write(targetDir + "\n");
		writer.write("Source Dir(s):\n");
		for (File listEntry : sourceFileList) {
			writer.write(listEntry.getAbsolutePath() + "\n");
		}
		writer.close();
		System.out.println("Your Config has been updated!");
	}

	public void loadConfig() {
		FileReader freader;
		try {
			freader = new FileReader(propertiesDir);
			BufferedReader breader = new BufferedReader(freader);
			breader.readLine();
			lastUpdate = Long.valueOf(breader.readLine());
			breader.readLine();
			targetDir = breader.readLine();
			breader.readLine();
			String sourceDir = breader.readLine();
			while (sourceDir != null) {
				File sourceFile = new File(sourceDir);
				sourceFileList.add(sourceFile);
				sourceDir = breader.readLine();
			}
			breader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTimestamp() {
		Date d = new Date();
		lastUpdate = d.getTime();
	}

}
