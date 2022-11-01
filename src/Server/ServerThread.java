package Server;

import java.io.IOException;
import java.net.ServerSocket;

class ServerThread extends Thread {
	private ServerSocket serverSocket;
	
	public ServerThread(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void run() {
		while (true) {
			try {
				ClientHandler clientHandler = new ClientHandler(serverSocket.accept());	
				Thread clientThread = new Thread(clientHandler, ClientThreads.nextName());
				clientThread.start();
				ClientThreads.add(clientThread);
				System.out.println("Client " + clientThread.getName() + " was connected");		
			}
			catch (IOException e) {
				break;
			}
		}
		
		System.out.println("Server thread closed (2/2)");
	}
}