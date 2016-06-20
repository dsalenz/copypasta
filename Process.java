package de.copypasta;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Process {

	private ArrayList<File> sourceFileList = Config.instance().getSourceFileList();

	// private static final Logger logger = LoggerFactory.getLogger(Process.class);

	public void greetUser() {
		System.out.println("Welcome to " + Config.instance().VERSION + "\n" + "Requirements: Java 1.7+ \n");
	}
	
	public void byeUser() {
		System.out.println("Thank you for using " + Config.instance().VERSION + "\n" + "requirements: Java 1.7+ \n");
	}

	public String createFileName(File f) {
		String filePath = f.getAbsolutePath();
		String fileName = f.getName();
		String newFileName = fileName + "_" + filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
		if (f.isFile()) {
			String dataType = fileName.substring(fileName.lastIndexOf("."));
			newFileName = newFileName.replace(File.separator, ":") + dataType;
			return newFileName;
		} else {
			newFileName = newFileName.replace(File.separator, ":");
			return newFileName;
		}
	}

	private void copyFile(File aDir, File bDir) {
		try {
			Files.copy(aDir.toPath(), bDir.toPath());
			System.out.println("copied: " + aDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO: die beiden if-Abfragen mit && verbinden
	private void copyDir(File sourceDir) {
		File[] sourceFiles = sourceDir.listFiles();
		for (int i = 0; i < sourceFiles.length; i++) {
			File file_i = sourceFiles[i];
			String fileName = createFileName(file_i);
			File tarFile = new File(Config.instance().getTargetDir() + fileName);
			if (sourceFiles[i].isFile()) {
				if (sourceFiles[i].lastModified() > Config.instance().getLastUpdate()) {
					copyFile(sourceFiles[i], tarFile);
				}
			} else {
				copyDir(sourceFiles[i]);
			}
			
		}
	}

//	Franks Version von "copyDir"
//	private void copyDir(File sourceDir) {
//		  for (File file : sourceDir.listFiles()) {
//		   if (file.isFile() && (file.lastModified() > Config.instance().getLastUpdate())) {
//		    copyFile(file, getTargetDir(file));
//		   } else {
//		    copyDir(file);
//		   }
//		  }
//		 }
	
	
	public void copy() {
		clearTargetDir();
		for (File listEntry : sourceFileList) {
			copyDir(listEntry);
		}
		Config.instance().setTimestamp();
		System.out.println("Your Source-Dir has been updated!");
	}

	private void clearTargetDir() {
		File targetDir = new File(Config.instance().getTargetDir());
		File[] oldFiles = targetDir.listFiles();
		for (File f : oldFiles) {
			f.delete();
		}
	}

}