package Server;

import java.net.*;
import java.io.*;

public class Server {
	private static ServerSocket serverSocket;
	
	private static void serverStart(int port) {
		System.out.println("Server started on " + Integer.toString(port) + " port");
		
		Thread serverThread = new ServerThread(serverSocket);
		serverThread.start();
		
		Thread terminalThread = new TerminalThread();
		terminalThread.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    public void run()
		    {
		    	ClientThreads.interruptAll();
		    	
		    	if (terminalThread.isAlive()) {
		    		terminalThread.interrupt();
		    	}

				if (serverThread.isAlive()) {
					serverThread.interrupt();	
				}
				
				try {
					serverStop();
				} catch (IOException e) {}
		    }
		});
	}
	
	public static void serverStop() throws IOException {
		serverSocket.close();
	}
	
	public static void main(String[] args) {
		int port = 775;
		if (args.length == 1) {
			port = Integer.valueOf(args[0]);
		}
		
		try {
			serverSocket = new ServerSocket(port);
		} 
		catch (IOException e) {
			System.out.println("Server closed");
			System.exit(0);
		}
		
		serverStart(port);
	}
}
