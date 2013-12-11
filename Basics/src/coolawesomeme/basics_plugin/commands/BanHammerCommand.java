package coolawesomeme.basics_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.TempBanList;

public class BanHammerCommand implements CommandExecutor{
	
	private Basics basics;
	public static Player[] banHammerVictims;
	
	public BanHammerCommand(Basics instance){
		basics = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()){
			if(args.length > 0){
				sender.sendMessage("Invalid command syntax!");
				return false;
			}else{
				Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
				if(onlinePlayers.length > 0){
					for(int i = 0; i < onlinePlayers.length; i++){
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + onlinePlayers[i].getName() + " THOU HATH BEEN BAN HAMMERED");
						banHammerVictims[i] = onlinePlayers[i];
						TempBanList.addPlayer(onlinePlayers[i]);
					}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(basics, new Runnable() {
						@Override 
						public void run() {
							for(int i = 0; i < banHammerVictims.length; i++){
								Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + banHammerVictims[i].getName());
								Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + banHammerVictims[i].getName());
								TempBanList.removePlayer(banHammerVictims[i]);
							}
							basics.getLogger().info("All ban hammer victims have been unbanned.");
						}
					}, 1200L);
					return true;
				}else{
					basics.getLogger().info("There must be player(s) online to use this command!");
					return true;
				}
			}
		}else{
			sender.sendMessage("You must be OP to use this command!");
			return true;
		}
	}

}
