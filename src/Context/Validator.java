package Context;

import java.util.ArrayList;

import Errors.ValidatorException;

public class Validator {
	public CommandContext.Command command;
	public ArrayList<String> arguments;
	
	static public Validator validate(CommandContext.Command command, ArrayList<String> arguments) throws ValidatorException {
		Validator validator = new Validator();
		validator.arguments = arguments;
		validator.command = command;

		switch (command) {
			case REG: 
				if (arguments.get(0).matches("[^a-zA-Z0-9-_]"))
					throw new ValidatorException(ValidatorException.Type.WrongSymbol, "1\n[a-z], [A-Z], [0-9], [_], [-]");
				if (arguments.get(1).matches("[^a-zA-Z0-9-_]"))
					throw new ValidatorException(ValidatorException.Type.WrongSymbol, "2\n[a-z], [A-Z], [0-9], [_], [-]");
				if (arguments.get(0).length() > 16)
					throw new ValidatorException(ValidatorException.Type.InvalidMaxSize, "1\n16");
				if (arguments.get(1).length() > 16)
					throw new ValidatorException(ValidatorException.Type.InvalidMaxSize, "2\n16");
				if (arguments.get(0).length() < 4)
					throw new ValidatorException(ValidatorException.Type.InvalidMinSize, "1\n4");
				if (arguments.get(1).length() < 4)
					throw new ValidatorException(ValidatorException.Type.InvalidMinSize, "2\n4");
			break;
			case CREATE: 
				if (arguments.get(0).matches("[^a-zA-X0-9-_]"))
					throw new ValidatorException(ValidatorException.Type.WrongSymbol, "[a-z], [A-Z], [0-9], [_], [-]");
				if (arguments.get(0).length() > 16)
					throw new ValidatorException(ValidatorException.Type.InvalidMaxSize, "1\n16");
				if (arguments.get(0).length() < 4)
					throw new ValidatorException(ValidatorException.Type.InvalidMinSize, "1\n4");
			break;
			case LIST: 
				if (arguments.size() != 0 && !arguments.get(0).equals("user") && !arguments.get(0).equals("chat"))
					throw new ValidatorException(ValidatorException.Type.InvalidArg, "user\nchat");
			break;
			case HISTORY:  
				if (arguments.size() != 0 && arguments.get(0).matches("[^a-zA-Z0-9-_]"))
					throw new ValidatorException(ValidatorException.Type.InvalidArg, "[0-9]");
			break;
			default: break;
		}	
		
		return validator;
	}
}