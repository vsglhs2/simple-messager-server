package Context;
import java.io.PrintWriter;
import java.util.ArrayList;
import Errors.*;
import Pack.Pack;


public abstract class CommandContext {
	public enum Command { REG, LOGIN, LOGOUT, HELP, EXIT, CREATE, DELETE, OPEN, CLOSE, LIST, HISTORY, MESSAGE, NONE };
	protected enum Type { Client, User, Chat };
	protected Type type;
	protected int userKey = -1;
	protected PrintWriter out = null;
	protected CommandContext previousContext = null;
	
	public CommandContext(PrintWriter out) {
		this.out = out;
	}
	
	public CommandContext(CommandContext context) {
		userKey = context.userKey;
		out = context.out;
		previousContext = context;
	}
	
	protected Pack[] regCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.Client);
	}
	
	protected Pack[] loginCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.Client);
	}
	
	protected  Pack[] logoutCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.User);
	}
	
	protected Pack[] helpCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.None);
	}
	
	protected Pack[] exitCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.Client);
	}
	
	protected Pack[] createCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.User);
	}
	
	protected Pack[] deleteCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.User);
	}
	
	protected Pack[] openCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.User);
	}
	
	protected Pack[] closeCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.None);
	}
	
	protected Pack[] listCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.None);
	}
	
	protected Pack[] historyCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.Chat);
	}
	
	protected Pack[] messageCommandExec(ArrayList<String> arguments) throws ContextException {
		throw new ContextException(ContextException.Type.Chat);
	}
	
	public Pack[] process(String message) {
		Pack[] response = new Pack[0];
		try {
			Parser parser = Parser.parse(message);
			Validator validator =  Validator.validate(parser.command, parser.arguments);
			response = exec(validator.command, validator.arguments);
		}
		catch(CommandException error) {
			if (error.getMessage().equals(error.getType())) {
				if (error.getClass().getSimpleName().equals(ContextException.class.getSimpleName())) 
					response = new Pack[] { 
						new Pack(
							Pack.PackType.Error.Context.valueOf(error.getType()), 
							Pack.TargetType.Client
						) 
					};
					else if (error.getClass().getSimpleName().equals(ExecException.class.getSimpleName())) 
					response = new Pack[] { 
						new Pack(
							Pack.PackType.Error.Execute.valueOf(error.getType()), 
							Pack.TargetType.Client
						) 
					};
					else if (error.getClass().getSimpleName().equals(ParseException.class.getSimpleName())) 
					response = new Pack[] { 
						new Pack(
							Pack.PackType.Error.Parse.valueOf(error.getType()), 
							Pack.TargetType.Client
						) 
					};
					else if (error.getClass().getSimpleName().equals(ValidatorException.class.getSimpleName())) 
					response = new Pack[] { 
						new Pack(
							Pack.PackType.Error.Validation.valueOf(error.getType()), 
							Pack.TargetType.Client
						) 
					};
			} else {
				if (error.getClass().getSimpleName().equals(ContextException.class.getSimpleName())) 
					response = new Pack[] { 
						new Pack(
							Pack.PackType.Error.Context.valueOf(error.getType()), 
							Pack.TargetType.Client,
							error.getMessage()
						) 
					};
				else if (error.getClass().getSimpleName().equals(ExecException.class.getSimpleName())) 
					response = new Pack[] { 
						new Pack(
							Pack.PackType.Error.Execute.valueOf(error.getType()), 
							Pack.TargetType.Client,
							error.getMessage()
						) 
					};
				else if (error.getClass().getSimpleName().equals(ParseException.class.getSimpleName())) 
					response = new Pack[] { 
						new Pack(
							Pack.PackType.Error.Parse.valueOf(error.getType()), 
							Pack.TargetType.Client,
							error.getMessage()
						) 
					};
				else if (error.getClass().getSimpleName().equals(ValidatorException.class.getSimpleName())) 
					response = new Pack[] { 
						new Pack(
							Pack.PackType.Error.Validation.valueOf(error.getType()), 
							Pack.TargetType.Client,
							error.getMessage()
						) 
					};
			}
			

		}
		
		return response;
	}
	
	public Pack[] exec(Command command, ArrayList<String> arguments) throws ExecException, ContextException {	
		Pack[] response = new Pack[0];
		
		switch (command) {
			case REG: response = regCommandExec(arguments); break;
			case LOGIN: response = loginCommandExec(arguments); break;
			case LOGOUT: response = logoutCommandExec(arguments); break;
			case HELP: response = helpCommandExec(arguments); break;
			case EXIT: response = exitCommandExec(arguments); break;
			case CREATE: response = createCommandExec(arguments); break;
			case DELETE: response = deleteCommandExec(arguments); break;
			case OPEN: response = openCommandExec(arguments); break;
			case CLOSE: response = closeCommandExec(arguments); break;
			case LIST: response = listCommandExec(arguments); break;
			case HISTORY: response = historyCommandExec(arguments); break;
			case MESSAGE: response = messageCommandExec(arguments); break;
			case NONE: break;
		}		
		
		return response;
	}
}