package Server;

import java.io.IOException;
import java.util.Scanner;

class TerminalThread extends Thread {
	private Scanner reader;
	
	public TerminalThread() {
		reader = new Scanner(System.in);
	}
	
	public void run() {
		String response = "";
		while (true) {
			if (reader.hasNext()) {
				response = reader.nextLine();
				
				if (response.equals("Terminate")) {
					ClientThreads.interruptAll();
					try {
						Server.serverStop();
					} catch (IOException e) {}
					
					break;
				} else if (response.equals("Client")) {
					response = reader.nextLine();
					boolean result = ClientThreads.interrupt(response);
					
					if (!result) {
						System.out.println("There are no thread with such name");
					}
				} else {
					System.out.println("Wrong input");
				}
			}
		}
		
		reader.close();		
		System.out.println("Terminal thread closed (1/2)");
	}
}
	