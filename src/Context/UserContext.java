package Context;

import java.io.PrintWriter;
import java.util.ArrayList;
import Errors.ExecException;
import Chat.Chats;
import User.Users;
import Pack.Pack;

public class UserContext extends CommandContext {
	public UserContext(PrintWriter out) {
		super(out);
		type = Type.User;
	}
	
	public UserContext(CommandContext context) {
		super(context);
		type = Type.User;
	}
	
	protected Pack[] logoutCommandExec(ArrayList<String> arguments) throws ExecException {
		Users.logOut(userKey);
		userKey = -1;
		
		Pack[] packages = {
		new Pack(Pack.PackType.System.Context.Client, Pack.TargetType.Client),
		new Pack(Pack.PackType.System.Context.Client, Pack.TargetType.Server),
		new Pack(Pack.PackType.MMessage.Response.UserLoggedOut, Pack.TargetType.Client)
		};
		return packages;
	}
	
	protected Pack[] createCommandExec(ArrayList<String> arguments) throws ExecException {
		if (!Chats.create(userKey, arguments.get(0))) throw new ExecException(ExecException.Type.ChatExist, arguments.get(0));
		
		Pack[] packages = {
		new Pack(Pack.PackType.MMessage.Response.ChatCreated, Pack.TargetType.Client, arguments.get(0))
		};
		return packages;
	}
	
	protected Pack[] deleteCommandExec(ArrayList<String> arguments) throws ExecException {
		int result = Chats.delete(userKey, arguments.get(0));
		
		if (result == -1) throw new ExecException(ExecException.Type.ChatNotExist, arguments.get(0));
		if (result == -2) throw new ExecException(ExecException.Type.UserNotCreator, arguments.get(0));
		
		Pack[] packages = {
		new Pack(Pack.PackType.MMessage.Response.ChatDeleted, Pack.TargetType.Client)
		};
		return packages;
	}
	
	protected Pack[] openCommandExec(ArrayList<String> arguments) throws ExecException {
		if (!Chats.connect(userKey, arguments.get(0))) throw new ExecException(ExecException.Type.ChatNotExist, arguments.get(0));
		
		Pack[] packages = {
		new Pack(Pack.PackType.System.Context.Chat, Pack.TargetType.Client),
		new Pack(Pack.PackType.System.Context.Chat, Pack.TargetType.Server),
		new Pack(Pack.PackType.MMessage.Response.ChatOpened, Pack.TargetType.Client, arguments.get(0))
		};
		return packages;
	}

	
	protected Pack[] listCommandExec(ArrayList<String> arguments) throws ExecException {
		String[] chatsList = Chats.chatsList();
		
		Pack[] packages = {
		new Pack(Pack.PackType.MMessage.Response.ChatList, Pack.TargetType.Client, chatsList)
		};
		
		return packages;
	}
	
	protected Pack[] helpCommandExec(ArrayList<String> arguments) throws ExecException {	
		Pack[] packages = {
		new Pack(Pack.PackType.MMessage.Response.Help, Pack.TargetType.Client, "There are not help yet")
		};
		return packages;
	}
}
