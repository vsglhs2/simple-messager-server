package User;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;

import Chat.Chats;

public class Users {
	static private HashMap<Integer, User> users = new HashMap<>();
	static private HashMap<String, HashMap<String, Integer>> signUpList = new HashMap<>();
	static private HashSet<Integer> logInList = new HashSet<>();
	
	private static boolean isLoginExist(String login) {
		for (String current : signUpList.keySet()) {
			if (current.equals(login)) return true;
		}
		
		return false;
	}
	
	public static int logIn(String login, String password, PrintWriter out) {
		Integer key = null;
		
		if (!signUpList.containsKey(login) || !(signUpList.get(login).containsKey(password))) return -1;
		key = signUpList.get(login).get(password);
		if (key != null && logInList.contains(key)) return -2;
		
		users.get(key).setGetMessageStream(out);
		logInList.add(key);
		return key;
	}
	
	public static boolean logOut(int key) {
		if (!users.containsKey(key)) return false;
		
		Chats.disconnect(key);
		return logInList.remove(key);
	}
	
	public static boolean signUp(String login, String password) {
		if (isLoginExist(login)) return false;
		
		User newUser = new User(login);
		HashMap<String, Integer> map = new HashMap<>();
		map.put(password, newUser.hashCode());
		signUpList.put(login, map);
		users.put(newUser.hashCode(), newUser);
		
		return true;
	}
	
	public static boolean getMessage(int userKey, String message) {
		users.get(userKey).getMessage(message);
		
		return true;
	}
	
	public static boolean getContextMessage(int userKey) {
		users.get(userKey).getContextMessage();
		
		return true;
	}
	
	public static String getUserName(int userKey) {
		return users.get(userKey).getName();
	}
}