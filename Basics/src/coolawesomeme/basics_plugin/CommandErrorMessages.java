package coolawesomeme.basics_plugin;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandErrorMessages {

	public static boolean sendSyntaxError(CommandSender sender){
		sender.sendMessage("Invalid command syntax!");
		return false;
	}
	
	public static boolean sendSyntaxError(Player sender){
		sender.sendMessage("Invalid command syntax!");
		return false;
	}
	
	public static boolean sendConsoleError(CommandSender sender){
		sender.sendMessage("You must be a player to do that!");
		return true;
	}
	
	public static boolean sendConsoleError(ConsoleCommandSender sender){
		sender.sendMessage("You must be a player to do that!");
		return true;
	}
	
	public static boolean sendPermissionError(CommandSender sender){
		sender.sendMessage("You must be OP/ Admin to do that!");
		return true;
	}
	
	public static boolean sendPermissionError(Player sender){
		sender.sendMessage("You must be OP/ Admin to do that!");
		return true;
	}
}
