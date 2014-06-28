package coolawesomeme.basics_plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TempBanList {

	private static LinkedList<Player> bannedPlayers;
	
	public static void initializeList(){
		bannedPlayers = new LinkedList<Player>();
		readBansFromFile();
	}
	
	public static void addPlayer(Player victim){
		bannedPlayers.add(victim);
		resaveFile();
	}
	
	public static void removePlayer(Player victim){
		bannedPlayers.remove(victim);
		resaveFile();
	}
	
	public static List<Player> getTempBanList(){
		return bannedPlayers;
	}
	
	public static void unbanAll(){
		boolean flag = false;
		for(Player victim : bannedPlayers){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + victim.getName());
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + victim.getName());
			bannedPlayers.remove(victim);
			flag = true;
		}
		if(flag)
			resaveFile();
	}
	
	private static void readBansFromFile(){
		File f = new File("plugins/Basics/__tempbanlist__.dat");
		try{
	    	Scanner scanner = new Scanner(new FileInputStream(f));
	    	try {
	    		while (scanner.hasNextLine()){
	    	  		bannedPlayers.add(Bukkit.getPlayer(UUID.fromString(scanner.nextLine())));
	    		}
	    	}
	   		finally{
	   			scanner.close();
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void resaveFile(){
		File f = new File("plugins/Basics/__tempbanlist__.dat");
		try {
			f.createNewFile();
			FileWriter q = new FileWriter(f);
			for(Player victim : bannedPlayers){
				q.write(victim.getUniqueId() + "\n");
			}
			q.flush();
			q.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
