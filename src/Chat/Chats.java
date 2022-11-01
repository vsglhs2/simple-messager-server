package Chat;

import java.util.HashMap;

import User.Users;

public class Chats {
	static private HashMap<String, Chat> list = new HashMap<>();
	
	private static Chat getUserChat(int userKey) {
		for (Chat chat : list.values()) {
			if (chat.isUserConnected(userKey)) {
				return chat;
			}
		}
		
		return null;
	}
	
	private static boolean isChatExist(String name) {
		return list.keySet().contains(name);
	}
	
	public static boolean disconnect(int userKey) {	
		Chat chat = getUserChat(userKey);
		if (chat != null) {
			chat.userDisconnect(userKey);			
		}
		
		return true;
		
	}
	
	public static boolean connect(int userKey, String name) {
		if (!isChatExist(name)) return false;
		
		list.get(name).userConnect(userKey);
		
		return true;
	}
	
	public static int delete(int userKey, String name) {
		if (!isChatExist(name)) return -1;
		
		if (!list.get(name).isUserCreator(userKey)) return -2;
		list.get(name).close();
		list.remove(name);
		
		return 1;
	}
	
	public static boolean create(int userKey, String name) {
		if (isChatExist(name)) return false;
		
		Chat newChat = new Chat(name, userKey);
		list.put(name, newChat);
		
		return true;
	}
	
	public static String[] chatsList() {	
		if (list.size() == 0) return new String[] { "There are no chats" };
		return list.keySet().toArray(new String[0]);
	}
	
	public static String[] usersList(int userKey) {
		if (getUserChat(userKey).getUserNames().size() == 0) return new String[] { "There are no users" };
		return getUserChat(userKey).getUserNames().toArray(new String[0]);
	}
	
	public static String[] history(int userKey) {
		return getUserChat(userKey).history(-1);
	}
	
	public static String[] history(int userKey, int length) {
		return getUserChat(userKey).history(length);
	}
	
	public static void message(int userKey, String message) {
		getUserChat(userKey).sendMessage(userKey, Users.getUserName(userKey) + ": " + message);
	}
}