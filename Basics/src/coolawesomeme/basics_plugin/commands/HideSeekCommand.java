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

public class HideSeekCommand implements CommandExecutor{
	
	private static Player seeker;
	private static List<String> foundPlayers = new LinkedList<String>();
	private static List<String> unfoundPlayers = new LinkedList<String>();
	public static boolean isHSOn = false;
	private static int id = -1;
	private Basics basics;
	private int hsMinutes = Basics.hideseekMinutes;
	
	public HideSeekCommand(Basics instance){
		basics = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 1){
			return CommandErrorMessages.sendSyntaxError(sender);
		}else if(args.length == 1){
			if(args[0].equals("end")){
				if(sender.isOp() || sender.hasPermission("basics.hideseek.end")){
					if(isHSOn){
						Bukkit.getServer().broadcastMessage("The game has ended!");
						if(unfoundPlayers.size() > 1){
							Bukkit.getServer().broadcastMessage("The winners are:");
							Bukkit.getServer().broadcastMessage(unfoundPlayers.toString().replace("[", "").replace("]", ""));
						}else{
							Bukkit.getServer().broadcastMessage("The winner is:");
							Bukkit.getServer().broadcastMessage(unfoundPlayers.toString().replace("[", "").replace("]", "").replace(", ", ""));
						}
						isHSOn = false;
					}else{
						sender.sendMessage("No current game of hide & seek!");
					}
				}
				return true;
			}
			return false;
		}else{
			Player[] onlinePlayers = Bukkit.getOnlinePlayers();
			if(onlinePlayers.length > 1){
				if(!isHSOn){
					Random random = new Random();
					int playerIndex = random.nextInt(onlinePlayers.length);
					seeker = onlinePlayers[playerIndex];
					unfoundPlayers = getOnlinePlayers();
					unfoundPlayers.remove(seeker.getName());
					foundPlayers.add(seeker.getName());
					isHSOn = true;
					Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.ITALIC + "A game of hide & seek has started! You have " + hsMinutes + " minutes!");
					Bukkit.getServer().broadcastMessage(ChatColor.RED + seeker.getName() + " is the seeker!");
					seeker.sendMessage(ChatColor.LIGHT_PURPLE + "Right click other players to 'find' them!");
					id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(basics, new Runnable() {
						@Override 
						public void run() {
							if(isHSOn){
								Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "The game has ended!");
								if(unfoundPlayers.size() > 1){
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "The winners are:");
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + unfoundPlayers.toString().replace("[", "").replace("]", ""));
								}else if(unfoundPlayers.size() == 1){
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "The winner is: " + unfoundPlayers.toString().replace("[", "").replace("]", "").replace(", ", ""));
								}else{
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + seeker.getName() + " has won!");
								}
								isHSOn = false;
							}
						}
					}, (long)(hsMinutes*20*60));
				}else{
					sender.sendMessage(ChatColor.RED + "There is already a game of hide & seek in progress! Please finish that game first.");
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
	
	public static void tagFoundPlayer(Player player){
		foundPlayers.add(player.getName());
		unfoundPlayers.remove(player.getName());
		Bukkit.getServer().broadcastMessage(ChatColor.ITALIC + "" + ChatColor.RED + "A player has been found!");
		for(String user : unfoundPlayers){
			if(Bukkit.getServer().getPlayer(user) == null){
				unfoundPlayers.remove(user);
			}
		}
		if(unfoundPlayers.size() != 0){
			Bukkit.getServer().broadcastMessage(unfoundPlayers.size() + " unfound players left!");
		}else{
			Bukkit.getServer().broadcastMessage(ChatColor.RED + "Game over!");
			Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + seeker.getName() + " (the seeker) has won!");
			isHSOn = false;
			if(id != -1)
				Bukkit.getScheduler().cancelTask(id);
		}
	}
	
	public static List<Player> getUnfoundPlayers(){
		if(isHSOn){
			List<Player> playerList = new LinkedList<Player>();
			for(String x : unfoundPlayers){
				try{
					playerList.add(Bukkit.getPlayer(x));
				}catch(Exception e){
					//playerList.add((Player)Bukkit.getOfflinePlayer(x));
				}
			}
			return playerList;
		}else{
			return null;
		}
	}
	
	public static List<Player> getFoundPlayers(){
		if(isHSOn){
			List<Player> playerList = new LinkedList<Player>();
			for(String x : foundPlayers){
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
}