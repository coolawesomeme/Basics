package coolawesomeme.basics_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.MinecraftColors;
import coolawesomeme.basics_plugin.ThreatLevel;

public class LockdownCommand implements CommandExecutor{
	
	public LockdownCommand(Basics instance){
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || !(sender instanceof Player)){
			if(args.length == 0){
				if(Basics.getServerThreatLevel() != ThreatLevel.SEVERE){
					Basics.setServerThreatLevel(ThreatLevel.SEVERE);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + MinecraftColors.red + "Code Red/ Severe");
					kickConnectedPlayers();
				}else{
					Basics.setServerThreatLevel(ThreatLevel.NULL);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + MinecraftColors.green + "Code Green/ Null");
				}
				return true;
			}else if(args.length == 1){
				if(args[0].equalsIgnoreCase("null") || args[0].equalsIgnoreCase("green")){
					Basics.setServerThreatLevel(ThreatLevel.NULL);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + MinecraftColors.green + "Code Green/ Null");
				}else if(args[0].equalsIgnoreCase("mild") || args[0].equalsIgnoreCase("orange")){
					Basics.setServerThreatLevel(ThreatLevel.MILD);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + MinecraftColors.gold + "Code Orange/ Mild");
				}else{
					Basics.setServerThreatLevel(ThreatLevel.SEVERE);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + MinecraftColors.red + "Code Red/ Severe");
					kickConnectedPlayers();
				}
				return true;
			}else{
				sender.sendMessage("Invalid command syntax!");
				return false;
			}
		}else{
			sender.sendMessage("You must be OP to use that command!");
			return true;
		}
	}

	private void kickConnectedPlayers() {
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();
		for(int i = 0; i < onlinePlayers.length; i++){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kick " + onlinePlayers[i].getName() + " Server is in lockdown mode!");
		}
	}
}