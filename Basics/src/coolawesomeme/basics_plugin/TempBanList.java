package coolawesomeme.basics_plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;

public class TempBanList {

	private static LinkedList<String> bannedPlayers;
	
	public static void initializeList(){
		bannedPlayers = new LinkedList<String>();
		readBansFromFile();
	}
	
	public static void addPlayer(String victimName){
		bannedPlayers.add(victimName);
		resaveFile();
	}
	
	public static void removePlayer(String victimName){
		bannedPlayers.remove(victimName);
		resaveFile();
	}
	
	public static List<String> getList(){
		return bannedPlayers;
	}
	
	public static void unbanAll(){
		boolean flag = false;
		for(String victim : bannedPlayers){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + victim);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + victim);
			bannedPlayers.remove(victim);
			flag = true;
		}
		if(flag){
			resaveFile();
		}
	}
	
	private static void readBansFromFile(){
		File f = new File("plugins/Basics/__tempbanlist__.dat");
		try{
	    	Scanner scanner = new Scanner(new FileInputStream(f));
	    	try {
	    		while (scanner.hasNextLine()){
	    	  		bannedPlayers.add(scanner.nextLine());
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
			for(String victim : bannedPlayers){
				q.write(victim + "\n");
			}
			q.flush();
			q.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
