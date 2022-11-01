package User;

import java.io.PrintWriter;

import Pack.Pack;

public class User {
	private String name;
	private PrintWriter out;
	
	public User(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public synchronized void getContextMessage() {
		out.println((new Pack(Pack.PackType.System.Context.User, Pack.TargetType.Client)).buildMessage());
	}

	public synchronized void getMessage(String message) {
		out.println((new Pack(Pack.PackType.MMessage.Response.MessageGet, Pack.TargetType.Client, message)).buildMessage());
	}
	
	public void setGetMessageStream(PrintWriter out) {
		this.out = out;
	}
}