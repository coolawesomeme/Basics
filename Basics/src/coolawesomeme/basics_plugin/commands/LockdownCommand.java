package coolawesomeme.basics_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.CommandErrorMessages;
import coolawesomeme.basics_plugin.ThreatLevel;

public class LockdownCommand implements CommandExecutor{
	
	public LockdownCommand(Basics instance){
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || !(sender instanceof Player) || sender.hasPermission("basics.lockdown")){
			if(args.length == 0){
				if(Basics.getServerThreatLevel() != ThreatLevel.SEVERE){
					Basics.setServerThreatLevel(ThreatLevel.SEVERE);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + ThreatLevel.SEVERE.formattedName());
					kickConnectedPlayers();
				}else{
					Basics.setServerThreatLevel(ThreatLevel.NULL);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + ThreatLevel.NULL.formattedName());
				}
				return true;
			}else if(args.length == 1){
				if(args[0].equalsIgnoreCase(ThreatLevel.NULL.toString()) || args[0].equalsIgnoreCase("green")){
					Basics.setServerThreatLevel(ThreatLevel.NULL);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + ThreatLevel.NULL.formattedName());
				}else if(args[0].equalsIgnoreCase(ThreatLevel.MILD.toString()) || args[0].equalsIgnoreCase("yellow")){
					Basics.setServerThreatLevel(ThreatLevel.MILD);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + ThreatLevel.MILD.formattedName());
				}else if(args[0].equalsIgnoreCase(ThreatLevel.SEVERE.toString()) || args[0].equalsIgnoreCase("red")){
					Basics.setServerThreatLevel(ThreatLevel.SEVERE);
					Bukkit.getServer().broadcastMessage("[Basics] Threat Level: " + ThreatLevel.SEVERE.formattedName());
					kickConnectedPlayers();
				}else{
					if(args[0].equalsIgnoreCase("status")){
						sender.sendMessage("Current Server Threat Level: " + Basics.getServerThreatLevel().formattedName());
						return true;
					}
					return CommandErrorMessages.sendSyntaxError(sender);
				}
				return true;
			}else{
				return CommandErrorMessages.sendSyntaxError(sender);
			}
		}else{
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("status")){
					sender.sendMessage("Current Server Threat Level: " + Basics.getServerThreatLevel().formattedName());
					return true;
				}
			}else if(args.length == 0){
				return CommandErrorMessages.sendPermissionError(sender);
			}else{
				return CommandErrorMessages.sendSyntaxError(sender);
			}
			return false;
		}
	}

	private void kickConnectedPlayers() {
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();
		for(int i = 0; i < onlinePlayers.length; i++){
			if(!onlinePlayers[i].isOp()){
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kick " + onlinePlayers[i].getName() + " Server is in lockdown mode!");
			}
		}
	}
}