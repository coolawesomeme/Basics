package coolawesomeme.basics_plugin;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;

public class TempBanList {

	private static LinkedList<String> bannedPlayers;
	
	public static void initializeList(){
		bannedPlayers = new LinkedList<String>();
	}
	
	public static void addPlayer(String victimName){
		bannedPlayers.add(victimName);
	}
	
	public static void removePlayer(String victimName){
		bannedPlayers.remove(victimName);
	}
	
	public static List<String> getList(){
		return bannedPlayers;
	}
	
	public static void unbanAll(){
		for(String victim : bannedPlayers){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + victim);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + victim);
		}
	}
}
