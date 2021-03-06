package coolawesomeme.basics_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.CommandErrorMessages;
import coolawesomeme.basics_plugin.TempBanList;

public class BanHammerCommand implements CommandExecutor{
	
	private Basics basics;
	public static Player[] banHammerVictims = Bukkit.getOnlinePlayers();
	
	public BanHammerCommand(Basics instance){
		basics = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || !(sender instanceof Player)){
			if(args.length > 0){
				return CommandErrorMessages.sendSyntaxError(sender);
			}else{
				Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
				if(onlinePlayers.length > 0){
					for(int i = 0; i < onlinePlayers.length; i++){
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + onlinePlayers[i].getName() + " THOU HATH BEEN BAN HAMMERED");
						TempBanList.addPlayer(onlinePlayers[i]);
					}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(basics, new Runnable() {
						@Override 
						public void run() {
							boolean flag = false;
							for(int i = 0; i < banHammerVictims.length; i++){
								if(banHammerVictims[i].isBanned()){
									Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + banHammerVictims[i].getName());
									Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + banHammerVictims[i].getName());
									flag = true;
								}
								TempBanList.removePlayer(banHammerVictims[i]);
							}
							if(flag)
								basics.getLogger().info("All ban hammer victims have been unbanned.");
						}
					}, 800L);
					return true;
				}else{
					basics.getLogger().info("There must be player(s) online to use this command!");
					return true;
				}
			}
		}else{
			return CommandErrorMessages.sendPermissionError(sender);
		}
	}

}
