package de.copypasta;

import java.io.IOException;
import java.util.Scanner;

public class Copypasta {

	public static void main(String[] args) throws IOException {

		Config.instance().locateProgramDir();
		Config.instance().loadConfig();
		
		Process process = new Process();
		process.greetUser();
		
		System.out.println("Search for Updates? y/n");
		Scanner scn = new Scanner(System.in);
		String input = scn.nextLine();
		scn.close();
		
		if (input.equalsIgnoreCase("y")) {
			History.instance().loadHistory();
			process.copy();			
			Config.instance().writeConfig();
			History.instance().writeHistory();
		}
		
		else {
			process.byeUser();
		}

	}

}
