package Errors;

public class ExecException extends CommandException {
	public enum Type { 
		WrongLoginOrPassword, LoginAlreadExist, UserNotCreator, 
		UserAlreadyLoggedIn, ChatExist, ChatNotExist 
	};
	private Type type;
	
	private static final long serialVersionUID = 1L;
	
	public ExecException(Type type, String message) {
		super(message);
		this.type = type;
	}
	
	public ExecException(Type type) {
		super(type.toString());
		this.type = type;
	}
	
	public String getType() {
		return type.toString();
	}
}
