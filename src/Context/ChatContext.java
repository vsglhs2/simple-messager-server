package Context;

import java.io.PrintWriter;
import java.util.ArrayList;

import Errors.*;
import Chat.Chats;
import Pack.Pack;

public class ChatContext extends CommandContext {
	public ChatContext(PrintWriter out) {
		super(out);
		type = Type.Chat;
	}
	
	public ChatContext(CommandContext context) {
		super(context);
		type = Type.Chat;
	}
	protected Pack[] listCommandExec(ArrayList<String> arguments) throws ExecException {
		String[] list;
		boolean isChatArg = arguments.size() == 1 && arguments.get(0).equals("chat");
		if (isChatArg) list = Chats.chatsList(); 
		else list = Chats.usersList(userKey);
		
		Pack[] packages;
		
		if (!isChatArg) packages = new Pack[] {
		new Pack(Pack.PackType.MMessage.Response.UserList, Pack.TargetType.Client, list)
		};
		else packages = new Pack[]{
		new Pack(Pack.PackType.MMessage.Response.ChatList, Pack.TargetType.Client, list)
		};
		return packages;
	}
	
	protected Pack[] historyCommandExec(ArrayList<String> arguments) throws ExecException {
		String[] historyList;
		
		if (arguments.size() == 1) {
			historyList = Chats.history(userKey, Integer.valueOf(arguments.get(0)));
		} else {
			historyList = Chats.history(userKey);
		}
		
		Pack[] packages = {
		new Pack(Pack.PackType.MMessage.Response.History, Pack.TargetType.Client, historyList)
		};
		return packages;
	}
	
	protected Pack[] messageCommandExec(ArrayList<String> arguments) throws ExecException {
		Chats.message(userKey, arguments.get(0));
		
		Pack[] packages = { new Pack(Pack.PackType.MMessage.Response.MessageSend, Pack.TargetType.Client, arguments.get(0)) };
		return packages;
	}
	
	protected Pack[] closeCommandExec(ArrayList<String> arguments) throws ExecException {
		Chats.disconnect(userKey);
		
		Pack[] packages = {
		new Pack(Pack.PackType.System.Context.User, Pack.TargetType.Client),
		new Pack(Pack.PackType.System.Context.User, Pack.TargetType.Server),
		new Pack(Pack.PackType.MMessage.Response.ChatClosed, Pack.TargetType.Server),
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
