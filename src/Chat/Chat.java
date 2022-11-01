package Chat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import User.Users;

public class Chat {
	private HashSet<Integer> userKeys;
	private ArrayList<String> messages;
	private String name;
	private Integer creatorKey;
	
	public Chat(String name, int userKey) {
		this.name = name;
		this.creatorKey = userKey;
		
		userKeys = new HashSet<Integer>();
		messages = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getUserNames() {	
		List<String> names = new ArrayList<>();
		for (int userKey : userKeys) {
			names.add(Users.getUserName(userKey));
		}
		
		return names;
	}
	
	public boolean isUserCreator(int userKey) {
		return creatorKey == userKey;
	}
	
	public void userConnect(int userKey) {
		userKeys.add(userKey);

		sendMessage(userKey, Users.getUserName(userKey) + " entered the chat " + name);
	}
		
	public void userDisconnect(int userKey) {	
		sendMessage(userKey, Users.getUserName(userKey) + " left from chat " + name);
		
		userKeys.remove(userKey);		
	}
	
	public void sendMessage(int userKey, String message) {
		String finalMessage = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString() + " " + message;
		
		userKeys.forEach( currentKey -> { if (currentKey != userKey) Users.getMessage(currentKey, finalMessage); } );
		
		messages.add(finalMessage);
	}
	
	public void close() {
		if (!userKeys.isEmpty()) {
			for (int userKey : userKeys) {
				sendMessage(userKey, Users.getUserName(userKey) + " left from chat " + name);
			}
			
			for (int userKey : userKeys) {
				Users.getContextMessage(userKey);
			}
		}
		
		sendMessage(creatorKey, Users.getUserName(creatorKey) + " was closed the chat " + name);		
		userKeys.clear();
		messages.clear();
	}
	
	public String[] history(int length) {
		int sub = messages.size() - length;
		if (length == -1) sub = 0;
		
		if (messages.size() == 0) return new String[] { "There are no history" };
		return messages.subList((sub > 0 ? sub : 0), messages.size()).toArray(new String[0]);
	}
	
	public String[] history() {
		if (messages.size() == 0) return new String[] { "There are no history" };
		return messages.subList(0, messages.size()).toArray(new String[0]);
	}
	
	public boolean isUserConnected(int userKey) {
		return userKeys.contains(userKey);
	}
}