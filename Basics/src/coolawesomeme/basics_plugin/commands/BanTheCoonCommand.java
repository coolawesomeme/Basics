package coolawesomeme.basics_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.PlayerDataStorage;

public class BanTheCoonCommand implements CommandExecutor{
	
	private Basics basics;
	
	public BanTheCoonCommand(Basics instance){
		basics = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()){
			if(args.length > 0){
				sender.sendMessage("This command has no arguments!");
				return false;
			}else{
				Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
				if(onlinePlayers.length > 0){
					int a = onlinePlayers.length;
					int[] afactors = new int[a];
					for (int q = 0; q < onlinePlayers.length; q++) {
		        		afactors[q] = PlayerDataStorage.getPlayerAFactor(onlinePlayers[q]);
		    		}
					int highestNumber = afactors[0];
					Player highestPlayer = onlinePlayers[0];
					for(int i = 0; i < onlinePlayers.length; i++){
		    			if(afactors[i] > highestNumber){
		    				highestNumber = afactors[i];
		    				highestPlayer = onlinePlayers[i];
		    			}
		    		}        
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + highestPlayer.getName() + " You have been banned for five minutes, coon.");
					Bukkit.getServer().broadcastMessage("Player " + highestPlayer.getName() + " (The coon) has been banned.");
					final Player highestPlayer1 = highestPlayer;
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(basics, new Runnable() {
					@Override 
					public void run() {
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + highestPlayer1.getName());
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + highestPlayer1.getName());
							Bukkit.getServer().broadcastMessage("The coon (" + highestPlayer1.getName() + ") has been unbanned.");
						}
					}, 6000L);
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
