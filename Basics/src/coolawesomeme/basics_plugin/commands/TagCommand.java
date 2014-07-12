package coolawesomeme.basics_plugin.commands;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.CommandErrorMessages;

public class TagCommand implements CommandExecutor{
	
	private static Player originalTagger;
	private static Player currentTagger;
	private static List<String> nonTaggedPlayers = new LinkedList<String>();
	public static boolean isTagOn = false;
	private static int id = -1;
	private Basics basics;
	private int tagMinutes = Basics.tagMinutes;
	
	public TagCommand(Basics instance){
		basics = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 1){
			return CommandErrorMessages.sendSyntaxError(sender);
		}else if(args.length == 1){
			if(args[0].equals("end")){
				if(sender.isOp() || sender.hasPermission("basics.tag.end")){
					if(isTagOn){
						Bukkit.getServer().broadcastMessage("The game has ended!");
						if(nonTaggedPlayers.size() > 1){
							Bukkit.getServer().broadcastMessage("The winners are:");
							Bukkit.getServer().broadcastMessage(nonTaggedPlayers.toString().replace("[", "").replace("]", ""));
						}else{
							Bukkit.getServer().broadcastMessage("The winner is:");
							Bukkit.getServer().broadcastMessage(nonTaggedPlayers.toString().replace("[", "").replace("]", "").replace(", ", ""));
						}
						isTagOn = false;
					}else{
						sender.sendMessage("No current game of tag!");
					}
				}
				return true;
			}
			return false;
		}else{
			Player[] onlinePlayers = Bukkit.getOnlinePlayers();
			if(onlinePlayers.length > 1){
				if(!isTagOn){
					Random random = new Random();
					int playerIndex = random.nextInt(onlinePlayers.length);
					originalTagger = onlinePlayers[playerIndex];
					nonTaggedPlayers = getOnlinePlayers();
					nonTaggedPlayers.remove(originalTagger.getName());
					currentTagger = originalTagger;
					isTagOn = true;
					Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.ITALIC + "A game of tag has started! You have " + tagMinutes + " minutes!");
					Bukkit.getServer().broadcastMessage(ChatColor.RED + originalTagger.getName() + " is the tagger!");
					originalTagger.sendMessage(ChatColor.LIGHT_PURPLE + "Right click a player to tag them!");
					id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(basics, new Runnable() {
						@Override 
						public void run() {
							if(isTagOn){
								Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "The game has ended!");
								if(nonTaggedPlayers.size() > 1){
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "The winners are:");
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + nonTaggedPlayers.toString().replace("[", "").replace("]", ""));
								}else if(nonTaggedPlayers.size() == 1){
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "The winner is: " + nonTaggedPlayers.toString().replace("[", "").replace("]", "").replace(", ", ""));
								}else{
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + originalTagger.getName() + " has won!");
								}
								isTagOn = false;
							}
						}
					}, (long)(tagMinutes*20*60));
				}else{
					sender.sendMessage(ChatColor.RED + "There is already a game of tag in progress! Please finish that game first.");
				}
			}else{
				sender.sendMessage(ChatColor.RED + "There must be at least two players to use this command!");
			}
			return true;
		}
	}

	private List<String> getOnlinePlayers() {
		List<String> onlinePlayers = new LinkedList<String>();
		Player[] onlinePlayers1 = Bukkit.getOnlinePlayers();
		for(int i = 0; i < onlinePlayers1.length; i++){
			onlinePlayers.add(onlinePlayers1[i].getName());
		}
		return onlinePlayers;
	}
	
	public static void tagPlayer(Player player){
		nonTaggedPlayers.remove(player.getName());
		currentTagger = player;
		Bukkit.getServer().broadcastMessage(ChatColor.ITALIC + "" + ChatColor.RED + " has been tagged!");
		Bukkit.getServer().broadcastMessage("Run from them!");
		if(nonTaggedPlayers.size() != 0){
			Bukkit.getServer().broadcastMessage(nonTaggedPlayers.size() + " players have not been tagged once yet!");
		}else{
			Bukkit.getServer().broadcastMessage(ChatColor.RED + "Game over!");
			Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + originalTagger.getName() + " (the tagger) has won!");
			isTagOn = false;
			if(id != -1)
				Bukkit.getScheduler().cancelTask(id);
		}
	}
	
	public static List<Player> getNonTaggedPlayers(){
		if(isTagOn){
			List<Player> playerList = new LinkedList<Player>();
			for(String x : nonTaggedPlayers){
				try{
					playerList.add(Bukkit.getPlayer(x));
				}catch(Exception e){
					playerList.add((Player)Bukkit.getOfflinePlayer(x));
				}
			}
			return playerList;
		}else{
			return null;
		}
	}
	
	public static Player getCurrentTagger(){
		return currentTagger;
	}
}