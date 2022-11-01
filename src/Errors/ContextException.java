package Errors;

public class ContextException extends CommandException {
	public enum Type { Client, User, Chat, None };
	private Type type;
	
	private static final long serialVersionUID = 1L;
	
	public ContextException(Type type, String message) {
		super(message);
		this.type = type;
	}
	
	public ContextException(Type type) {
		super(type.toString());
		this.type = type;
	}
	
	public String getType() {
		return type.toString();
	}
}
