package Errors;

public class ValidatorException extends CommandException {
	public enum Type { InvalidArg, InvalidMinSize, InvalidMaxSize, WrongSymbol };
	private Type type;
	private static final long serialVersionUID = 1L;
	
	public ValidatorException(Type type, String message) {
		super(message);
		this.type = type;
	}
	
	public ValidatorException(Type type) {
		super(type.toString());
		this.type = type;
	}
	
	public String getType() {
		return type.toString();
	}
}
