package Server;

import java.util.ArrayList;

class ClientThreads {
	private static ArrayList<Thread> threads = new ArrayList<>();
	private static int threadCount = 1;
	
	public static void add(Thread clientThread) {
		threads.add(clientThread);
	}
	
	public static boolean interrupt(String threadName) {
		for (int i = 0; i < threads.size(); i++) {
			if (threads.get(i).getName().equals(threadName) && threads.get(i).isAlive()) {
				threads.get(i).interrupt();
				return true;
			}
		}
		
		return false;
	}
	
	public static void interruptAll() {
		for (int i = 0; i < threads.size(); i++) {
			if (threads.get(i).isAlive()) threads.get(i).interrupt();
		}
		threads.clear();
	}
	
	public static String nextName() {
		return Integer.toString(threadCount++);
	}
}