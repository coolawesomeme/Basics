package coolawesomeme.basics_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.CommandErrorMessages;

public class BrbCommand implements CommandExecutor{
	
	private String owner;
	public static boolean isOwnerBRBing = false;
	
	public BrbCommand(Basics instance){
		owner = Basics.owner;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return brbCommand(sender, cmd, label, args);
		} else {
			boolean flag = false;
			if(sender.getName().equalsIgnoreCase(owner)){
				flag = true;
			}
			if(flag){
				return brbCommand(sender, cmd, label, args);
			}else{
				sender.sendMessage("You must be the owner to use this command!");
				return true;
			}
		}
	}
	
	private boolean brbCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0){
			if(isOwnerBRBing){
				isOwnerBRBing = false;
				Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Basics] The server owner is no longer away!");
			}else{
				isOwnerBRBing = true;
				Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Basics] The server owner is away!");
			}
			return true;
		}else if(args.length > 1){
			return CommandErrorMessages.sendSyntaxError(sender);
		}else if(args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")){
			boolean oldBRB = isOwnerBRBing;
			isOwnerBRBing = Boolean.parseBoolean(args[0]);
			if(oldBRB != isOwnerBRBing)
				Bukkit.broadcastMessage(isOwnerBRBing ? (ChatColor.LIGHT_PURPLE + "[Basics] The server owner is away!") : (ChatColor.LIGHT_PURPLE + "[Basics] The server ownder is no longer away!"));
			return true;
		}else{
			return CommandErrorMessages.sendSyntaxError(sender);
		}
	}

}
