package Context;

import java.util.ArrayList;
import Errors.ParseException;

public class Parser {
	public CommandContext.Command command;
	public ArrayList<String> arguments;
	private int minArgumentsSize = 0;
	private int maxArgumentsSize = 0;
	
	static private boolean checkQuotes(String string) {
		return (string.length() >= 2 && string.charAt(0) == '\"' && string.charAt(string.length() - 1) == '\"');
	}
	
	static public Parser parse(String message) throws ParseException {
		Parser parser = new Parser();
		
		String[] parts = message.split(" ");
		if (parts.length == 0) throw new ParseException(ParseException.Type.InvalidCommand);
		
		parser.arguments = new ArrayList<String>();
		
		switch (parts[0].toLowerCase()) {
			case "login": parser.command = CommandContext.Command.LOGIN; parser.minArgumentsSize = 2; parser.maxArgumentsSize = 2; break;
			case "reg": parser.command = CommandContext.Command.REG; parser.minArgumentsSize = 2; parser.maxArgumentsSize = 2; break;
			case "logout": parser.command = CommandContext.Command.LOGOUT; break;
			case "help": parser.command = CommandContext.Command.HELP; break;
			case "exit": parser.command = CommandContext.Command.EXIT; break;
			case "create": parser.command = CommandContext.Command.CREATE;  parser.minArgumentsSize = 1; parser.maxArgumentsSize = 1; break;
			case "delete": parser.command = CommandContext.Command.DELETE;  parser.minArgumentsSize = 1; parser.maxArgumentsSize = 1; break;
			case "open": parser.command = CommandContext.Command.OPEN;  parser.minArgumentsSize = 1; parser.maxArgumentsSize = 1; break;
			case "close": parser.command = CommandContext.Command.CLOSE; break;
			case "list": parser.command = CommandContext.Command.LIST;  parser.minArgumentsSize = 0; parser.maxArgumentsSize = 1; break;
			case "history": parser.command = CommandContext.Command.HISTORY;  parser.minArgumentsSize = 0; parser.maxArgumentsSize = 1; break;
			case "message": parser.command = CommandContext.Command.MESSAGE;  parser.minArgumentsSize = 1; parser.maxArgumentsSize = 1; break;
			default:  parser.command = CommandContext.Command.NONE; throw new ParseException(ParseException.Type.InvalidCommand);
		}	
		
		for (int i = 1; i < parts.length; i++) {
			if (checkQuotes(parts[i])) {
				parser.arguments.add(parts[i].substring(1, parts[i].length() - 1));		
				continue;
			} else if (!parts[i].equals(" ")) {
				String string = parts[i];
				boolean isAdded = false;
				for (int j = i + 1; j < parts.length; j++) {
					string += " " + parts[j];
					
					if (checkQuotes(string)) {
						parser.arguments.add(string.substring(1, string.length() - 1));
						i = j;
						isAdded = true;
						break;
					}				
				}	
				
				if (!isAdded) {
					throw new ParseException(ParseException.Type.InvalidArg, Integer.toString(parser.arguments.size() + 1));
				}
			}
		}
		
		if (parser.arguments.size() < parser.minArgumentsSize) throw new ParseException(ParseException.Type.InvalidMinSize, Integer.toString(parser.minArgumentsSize));
		else if (parser.arguments.size() > parser.maxArgumentsSize) throw new ParseException(ParseException.Type.InvalidMaxSize, Integer.toString(parser.maxArgumentsSize));
		
		return parser;
	}
}