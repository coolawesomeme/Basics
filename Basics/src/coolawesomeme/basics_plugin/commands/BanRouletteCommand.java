package coolawesomeme.basics_plugin.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.CommandErrorMessages;
import coolawesomeme.basics_plugin.TempBanList;

public class BanRouletteCommand implements CommandExecutor{
	
	private Basics basics;
	public static Player banRouletteVictim;
	
	public BanRouletteCommand(Basics instance){
		basics = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()){
			if(args.length > 0){
				return CommandErrorMessages.sendSyntaxError(sender);
			}else if(banRouletteVictim == null || banRouletteVictim.equals(null)){
				Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
				if(onlinePlayers.length > 0){
					Random random = new Random();
					int r = random.nextInt(onlinePlayers.length);
					banRouletteVictim = onlinePlayers[r];
					TempBanList.addPlayer(banRouletteVictim);
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + banRouletteVictim.getName() + " You are the ban roulette victim! You have been banned for 30 minutes.");
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(basics, new Runnable() {
						@Override 
						public void run() {
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + banRouletteVictim.getName());
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + banRouletteVictim.getName());
							Bukkit.getServer().broadcastMessage("The ban roulette victim (" + banRouletteVictim.getName() + ") has been unbanned.");
							TempBanList.removePlayer(banRouletteVictim);
							banRouletteVictim = null;
						}
					}, 30000L);
					return true;
				}else{
    				basics.getLogger().info("There must be player(s) online to use this command!");
    				return true;
				}
			}else{
				sender.sendMessage("There is still a banned ban roulette victim!");
				sender.sendMessage("Please try again after the current victim is unbanned.");
				return true;
			}
		}else{
			return CommandErrorMessages.sendPermissionError(sender);
		}
	}

}
