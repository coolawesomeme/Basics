package coolawesomeme.basics_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.PlayerDataStorage;

public class AFactorCommand implements CommandExecutor{
	
	public AFactorCommand(Basics instance){
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 3){
			sender.sendMessage("Invalid command syntax!");
			return false;
		}else if(args.length > 0){
			if(args.length == 3){
				if(args[1].equalsIgnoreCase("set")){
					if(sender.isOp() || sender.hasPermission("basics.afactor.set")){
						try{
							PlayerDataStorage.setPlayerAFactor(Bukkit.getPlayer(args[0]), Integer.parseInt(args[2]));
						}catch(Exception e){
							PlayerDataStorage.setPlayerAFactor(Bukkit.getOfflinePlayer(args[0]), Integer.parseInt(args[2]));
						}
						sender.sendMessage("Annoying factor set!");
					}else{
						sender.sendMessage("You must be OP/ Admin to do that!");	
					}
					return true;
				}
			}else if(args.length == 1){
				try{
					sender.sendMessage("Annoying Factor: " + PlayerDataStorage.getPlayerAFactor(Bukkit.getPlayer(args[0])));
				}catch(Exception e){
					sender.sendMessage("Annoying Factor: " + PlayerDataStorage.getPlayerAFactor(Bukkit.getOfflinePlayer(args[0])));
				}
				return true;
			}else{
				sender.sendMessage("Invalid command syntax!");
				return false;
			}
		}else{
			if(sender instanceof Player){
				sender.sendMessage("Annoying Factor: " + PlayerDataStorage.getPlayerAFactor((Player)sender));
			}else{
				sender.sendMessage("You must be a player to do that!");
			}
			return true;
		}
		return false;
	}

}
