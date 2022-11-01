package Errors;

public abstract class CommandException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	protected String errorMessage;
	
	public CommandException(String message) {
		super(message);
	}
	
	public abstract String getType();
}
