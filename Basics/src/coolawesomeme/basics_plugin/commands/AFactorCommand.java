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
		if(args.length > 0){
			if(args.length > 1){
				if(args[1].equalsIgnoreCase("set")){
					if(sender.isOp()){
						PlayerDataStorage.setPlayerAFactor(Bukkit.getPlayer(args[0]), Integer.parseInt(args[2]));
						sender.sendMessage("Annoying factor set!");
					}else{
						sender.sendMessage("You must be OP to do that!");	
					}
					return true;
				}
			}else{
				try{
					sender.sendMessage("Annoying Factor: " + PlayerDataStorage.getPlayerAFactor(Bukkit.getPlayer(args[0])));
				}catch(Exception e){
					sender.sendMessage("Annoying Factor: " + PlayerDataStorage.getPlayerAFactor(Bukkit.getOfflinePlayer(args[0])));
				}
				return true;
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
