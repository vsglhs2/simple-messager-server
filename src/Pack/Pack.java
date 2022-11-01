package Pack;

import java.util.ArrayList;

public class Pack {
	public interface PackType {
		public interface System extends PackType {
			public enum Connection implements System {
				Disconnect
			}
			public enum Sending implements System {
				Resend
			} 
			public enum Context implements System {
				Client, User, Chat, Additional, None
			}
		}
		public interface Error extends PackType {
			public enum Context implements Error { 
				Client, User, Chat, Additional, None
			}
			public enum Execute implements Error { 
				WrongLoginOrPassword, LoginAlreadExist, UserNotCreator, 
				UserAlreadyLoggedIn, ChatExist, ChatNotExist 
			}
			public enum Parse implements Error { 
				InvalidCommand, InvalidMinSize, InvalidMaxSize, InvalidArg, InvalidSpace
			}
			public enum Validation implements Error { 
				InvalidArg, InvalidMinSize, InvalidMaxSize, WrongSymbol
			}
		}
		
		public interface MMessage extends PackType {
			public enum Response implements MMessage { 
				ClientExited, UserSignedUp, UserLoggedIn, 
				UserLoggedOut, ChatCreated, ChatDeleted, 
				ChatOpened, ChatClosed, ChatList, UserList, History, MessageSend, MessageGet, Help
			}
			public enum Request implements MMessage { 
				Command, Prompt
			}
		}
	}
	
	public enum TargetType { Client, Server };
	
	public PackType type;
	public String[] args = null;
	public TargetType targetType;
	
	public Pack(PackType type, TargetType targetType) {
		this.type = type;
		this.targetType = targetType;
	}
	
	public Pack(PackType type, TargetType targetType, String ...args) {
		this.type = type;
		this.args = args;
		this.targetType = targetType;
	}
	
	public String buildMessage() {
		String headerString = type.getClass().getDeclaringClass().getSimpleName();
		String subHeaderString = type.getClass().getSimpleName();
		String headerTypeString = type.toString();	
		
		String message = headerString + "\n" + subHeaderString + "\n" + headerTypeString;
		if (args != null) {
			ArrayList<String> newArgs = new ArrayList<>();
			
			for (String arg : args) {
				if (arg.contains("\n")) {
					String[] subArgs = arg.split("\n");
					
					for (String subArg : subArgs) newArgs.add(subArg);
				} else {
					newArgs.add(arg);
				}
			}
			
			message += "\n" + newArgs.size();
			for (String arg : newArgs) message += "\n" + arg;	

		} else {
			message += "\n0";
		}
		
		return message;
	}
}

