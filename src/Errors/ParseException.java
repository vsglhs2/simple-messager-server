package Errors;

public class ParseException extends CommandException {
	public enum Type { InvalidCommand, InvalidMaxSize, InvalidMinSize, InvalidArg, InvalidSpace };
	private Type type;
	private static final long serialVersionUID = 1L;

	public ParseException(Type type, String message) {
		super(message);
		this.type = type;
	}
	
	public ParseException(Type type) {
		super(type.toString());
		this.type = type;
	}
	
	public String getType() {
		return type.toString();
	}
}
