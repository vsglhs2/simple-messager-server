package Server;

import java.io.*;
import java.net.*;
import Context.*;
import Pack.Pack;

public class ClientHandler implements Runnable {
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;
	private CommandContext commandContext;
	
	private void processServerPackage(Pack pack) {
		if (pack.type == Pack.PackType.System.Connection.Disconnect) {
			Thread.currentThread().interrupt();
		} else if (pack.type == Pack.PackType.System.Context.Client) {
			commandContext = new ClientContext(commandContext);
		} else if (pack.type == Pack.PackType.System.Context.User) {
			commandContext = new UserContext(commandContext);
		} else if (pack.type == Pack.PackType.System.Context.Chat) {
			commandContext = new ChatContext(commandContext);
		}
	}
	
	private void processPackages(Pack[] packages) {
		for (Pack pack : packages) {
			if (pack.targetType == Pack.TargetType.Server) {
				processServerPackage(pack);
			} else {
				out.println(pack.buildMessage());
			}
		}
	}
	
	public void run() {
		try {
			clientStart();

			while (!Thread.currentThread().isInterrupted()) {		
				if (in.ready()) {
					String message = in.readLine();
					if (message != null) {
						synchronized (out) {
							Pack[] packages = commandContext.process(message);
									
							processPackages(packages);							
						}
					}
				}
				Thread.sleep(100);
			}
		} 
		catch (IOException | InterruptedException e) {} 
		
		clientStop();
	}
	
	private void clientStart() throws IOException {
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		commandContext = new ClientContext(out);
	}
	
	private void clientStop() {
		commandContext = new ClientContext(commandContext);
		Pack[] packages = commandContext.exec(CommandContext.Command.EXIT, null);
		processPackages(packages);
		
		try {
			in.close();
			out.close();
			clientSocket.close();			
		} catch (IOException e) {}
		
		System.out.println("Client " + Thread.currentThread().getName() + " was disconnected");
	}
	
	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
}