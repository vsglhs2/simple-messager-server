package Context;

import java.io.PrintWriter;
import java.util.ArrayList;

import User.Users;
import Pack.Pack;
import Errors.ExecException;

public class ClientContext extends CommandContext {
	public ClientContext(PrintWriter out) {
		super(out);
		type = Type.Client;
	}
	
	public ClientContext(CommandContext context) {
		super(context);
		type = Type.Client;
	}
	protected Pack[] regCommandExec(ArrayList<String> arguments) throws ExecException {
		if (!Users.signUp(arguments.get(0), arguments.get(1))) throw new ExecException(ExecException.Type.LoginAlreadExist, arguments.get(0));
		
		Pack[] packages = {
		new Pack(Pack.PackType.MMessage.Response.UserSignedUp, Pack.TargetType.Client, arguments.get(0))
		};
		return packages;
	}
	protected Pack[] loginCommandExec(ArrayList<String> arguments) throws ExecException {
		int result = Users.logIn(arguments.get(0), arguments.get(1), out);
		
		if (result == -1) throw new ExecException(ExecException.Type.WrongLoginOrPassword);
		if (result == -2) throw new ExecException(ExecException.Type.UserAlreadyLoggedIn, arguments.get(0));
		userKey = result;
	
		Pack[] packages = {
		new Pack(Pack.PackType.System.Context.User, Pack.TargetType.Client),
		new Pack(Pack.PackType.System.Context.User, Pack.TargetType.Server),
		new Pack(Pack.PackType.MMessage.Response.UserLoggedIn, Pack.TargetType.Client, arguments.get(0))
		};
		return packages;
	}
	
	protected Pack[] exitCommandExec(ArrayList<String> arguments) throws ExecException {
		Users.logOut(userKey);
		
		Pack[] packages = {
		new Pack(Pack.PackType.System.Connection.Disconnect, Pack.TargetType.Client),
		new Pack(Pack.PackType.System.Connection.Disconnect, Pack.TargetType.Server)
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
