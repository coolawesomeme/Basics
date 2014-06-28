package coolawesomeme.basics_plugin;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerDataStorage {
	
	private static String dataFolderPath;
	
	public static void setDataFolder(String path){
		dataFolderPath = path;
	}
	
	public static void makePlayerDataFile(UUID playerID){
		File f = new File(dataFolderPath + "/" + playerID.toString() + ".dat");
		if(!f.exists()){
			try {
				f.createNewFile();
				FileWriter lol = new FileWriter(f);
				lol.write("0\n");
				lol.flush();
				lol.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public static int getPlayerAFactor(Player player){
		String content = "";
		try {
			content = new Scanner(new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[] lulz = content.split("\\n");
		return Integer.parseInt(lulz[0]);
	}
	
	public static int getPlayerAFactor(OfflinePlayer player){
		String content = "";
		try {
			content = new Scanner(new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			makePlayerDataFile(player.getUniqueId().toString());
			try {
				content = new Scanner(new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat")).useDelimiter("\\Z").next();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		String[] lulz = content.split("\\n");
		return Integer.parseInt(lulz[0]);
	}*/
	
	public static int getPlayerServerHelpCount(Player player){
		String content = "";
		try {
			content = new Scanner(new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat")).next();
		} catch (Exception e) {
			makePlayerDataFile(player.getUniqueId());
			return 0;
		}
		String[] lulz = content.split("\\n");
		return Integer.parseInt(lulz[0]);
	}
	
	public static int getPlayerServerHelpCount(OfflinePlayer player){
		String content = "";
		try {
			content = new Scanner(new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat")).next();
		} catch (Exception e) {
			makePlayerDataFile(player.getUniqueId());
			return 0;
		}
		String[] lulz = content.split("\\n");
		return Integer.parseInt(lulz[0]);
	}
	
	/*public static void setPlayerAFactor(Player player, int afactor){
		File f = new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat");
		try {
			int playerHelpCount = getPlayerServerHelpCount(player);
			FileWriter lol = new FileWriter(f);
			lol.write(afactor + "\n");
			lol.write(playerHelpCount + "");
			lol.flush();
			lol.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setPlayerAFactor(OfflinePlayer player, int afactor){
		File f = new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat");
		try {
			int playerHelpCount = getPlayerServerHelpCount(player);
			FileWriter lol = new FileWriter(f);
			lol.write(afactor + "\n");
			lol.write(playerHelpCount + "");
			lol.flush();
			lol.close();
		} catch (IOException e) {
			makePlayerDataFile(player.getUniqueId().toString());
			try {
				int playerHelpCount = getPlayerServerHelpCount(player);
				FileWriter lol = new FileWriter(f);
				lol.write(afactor + "\n");
				lol.write(playerHelpCount + "");
				lol.flush();
				lol.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}*/
	
	public static void setPlayerServerHelpCount(Player player, int playerHelpCount){
		File f = new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat");
		try {
			FileWriter lol = new FileWriter(f);
			lol.write(playerHelpCount + "\n");
			lol.flush();
			lol.close();
		} catch (Exception e) {
			makePlayerDataFile(player.getUniqueId());
			try {
				FileWriter lol = new FileWriter(f);
				lol.write(playerHelpCount + "\n");
				lol.flush();
				lol.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void setPlayerServerHelpCount(OfflinePlayer player, int playerHelpCount){
		File f = new File(dataFolderPath + "/" + player.getUniqueId().toString() + ".dat");
		try {
			FileWriter lol = new FileWriter(f);
			lol.write(playerHelpCount + "\n");
			lol.flush();
			lol.close();
		} catch (Exception e) {
			makePlayerDataFile(player.getUniqueId());
			try {
				FileWriter lol = new FileWriter(f);
				lol.write(playerHelpCount + "\n");
				lol.flush();
				lol.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
