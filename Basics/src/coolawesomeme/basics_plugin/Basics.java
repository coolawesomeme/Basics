package coolawesomeme.basics_plugin;

import java.io.File;
import java.io.FileWriter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import coolawesomeme.basics_plugin.commands.BanHammerCommand;
import coolawesomeme.basics_plugin.commands.BanRouletteCommand;
import coolawesomeme.basics_plugin.commands.BrbCommand;
import coolawesomeme.basics_plugin.commands.HideSeekCommand;
import coolawesomeme.basics_plugin.commands.LockdownCommand;
import coolawesomeme.basics_plugin.commands.ServerHelpCommand;
import coolawesomeme.basics_plugin.commands.TPRCommand;
import coolawesomeme.basics_plugin.commands.TPRouletteCommand;
import coolawesomeme.basics_plugin.commands.TagCommand;

public final class Basics extends JavaPlugin {

	/** The string that server-owner is loaded into */
	public static String owner;
	
	/** The current threat level */
	private static ThreatLevel threatLevel = ThreatLevel.NULL;
	
	/** The message that is displayed to players when they  */
	public static String message;
	
	public static int versionMajor = 0;
	public static int versionMinor = 7;
	public static int versionRevision = 4;
	public static String version = versionMajor + "." + versionMinor + "." + versionRevision;
	public static boolean download;
	public static boolean teleportRequests;
	public static int tagMinutes;
	public static int hideseekMinutes;
	public static boolean useBeta;
	public static boolean disallowGamemode;
	public static boolean update;
	public static String updateFolder = YamlConfiguration.loadConfiguration(new File("bukkit.yml")).getString("settings.update-folder");
	public static String mcVersion;
	
	@Override
	/** Method that is executed when the plugin gets enabled */
    public void onEnable(){
		this.getMCVersion();
		PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new EventListener(), this);
		this.saveDefaultConfig();
		this.config();
		this.recopyConfig();
		this.configReadme();
		this.createPlayerFolder();
		this.commandHandlers();
		this.handleSpecialEvents();
		AutoDownloader.checkForUpdate(this);
		TempBanList.initializeList();
		TempBanList.unbanAll();
        getLogger().info("Plugin enabled!");
        //getLogger().info("Use /help basics for commands!");
    }

	@Override
    /** Method that is executed when the plugin gets disabled */
    public void onDisable() {
		if(!TempBanList.getTempBanList().isEmpty()){
			getLogger().info("Pre-disable unbanning of temporary bans...");
			TempBanList.unbanAll();
		}
        getLogger().info("Plugin disabled!");
    }
	
	private void getMCVersion(){
		String tempVersion = getServer().getVersion();
		String[] tempVersion1 = tempVersion.split("\\(MC: ");
		tempVersion1 = tempVersion1[1].split("\\)");
		mcVersion = tempVersion1[0];
	}
	
    /** Sets classes that handle commands */
    private void commandHandlers(){
    	getCommand("banhammer").setExecutor(new BanHammerCommand(this));
    	getCommand("banroulette").setExecutor(new BanRouletteCommand(this));
    	getCommand("brb").setExecutor(new BrbCommand(this));
    	getCommand("serverhelp").setExecutor(new ServerHelpCommand(this));
    	getCommand("tproulette").setExecutor(new TPRouletteCommand(this));
    	getCommand("tpr").setExecutor(new TPRCommand(this));
    	getCommand("lockdown").setExecutor(new LockdownCommand(this));
    	getCommand("tag").setExecutor(new TagCommand(this));
    	getCommand("hideseek").setExecutor(new HideSeekCommand(this));
    }
    
    private void handleSpecialEvents(){
    	HolidaySurprise.setUpHolidaySurprise();
    }
    
    /** Loads values from the config to memory */
	private void config(){
    	owner = this.getConfig().getString("server-owner");
    	message = this.getConfig().getString("message");
    	download = this.getConfig().getBoolean("download-latest-version", true);
    	update = this.getConfig().getBoolean("auto-update", true);
    	teleportRequests = this.getConfig().getBoolean("teleport-requests", true);
    	tagMinutes = this.getConfig().getInt("tag-playing-time-minutes", 20);
    	hideseekMinutes = this.getConfig().getInt("hideseek-playing-time-minutes", 20);
    	useBeta = this.getConfig().getBoolean("use-beta-version", false);
    	disallowGamemode = this.getConfig().getBoolean("disallow-gamemode-change", false);
    }
	
	/** Deletes the old config (with its values already loaded to memory), re-saves it and resets the previous values. This guarantees new config items are added */
	private void recopyConfig(){
		File config = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
		config.delete();
		this.saveDefaultConfig();
		this.getConfig().set("server-owner", owner);
		this.getConfig().set("message", message);
		this.getConfig().set("download-latest-version", download);
		this.getConfig().set("auto-update", update);
		this.getConfig().set("teleport-requests", teleportRequests);
		this.getConfig().set("tag-playing-time-minutes", tagMinutes);
		this.getConfig().set("hideseek-playing-time-minutes", hideseekMinutes);
		this.getConfig().set("use-beta-version", useBeta);
		this.getConfig().set("disallow-gamemode-change", disallowGamemode);
		this.saveConfig();
	}
	
	//Creates (if not already created) the folder where player stats are stored
	private void createPlayerFolder(){
		File f = new File(this.getDataFolder().getAbsolutePath() + "/players");
		f.mkdirs();
		PlayerDataStorage.setDataFolder(this.getDataFolder().getAbsolutePath() + "/players");
	}
 
	private void configReadme(){
		File f = new File(this.getDataFolder().getAbsolutePath() + "/Config-README.txt");
		try{
			String newLine = System.getProperty("line.separator");
			f.createNewFile();
			FileWriter lol = new FileWriter(f);
			lol.write("---------------------------" + newLine);
			lol.write("Config File Readme" + newLine);
			lol.write("---------------------------" + newLine);
			lol.write("IMPORTANT: IT IS NOT RECOMMENDED TO EDIT THE CONFIG FILE WITH NOTEPAD, USE NOTEPAD++. (TextEdit should be fine.)" + newLine + newLine);
			lol.write("use-beta-version: " + "This controls whether or not you get the latest beta (possibly quite buggy) version of Basics." + newLine + newLine);
			lol.write("server-owner: " + "This control whether or not a user can do /brb." + newLine + newLine);
			lol.write("message: " + "A brief server description that is shown to new players who join while the brb command is active." + newLine + newLine);
			lol.write("download-latest-version: " + "This controls whether or not you will get the latest version in your /plugins/Basics/updates/ folder." + newLine + newLine);
			lol.write("auto-update: " + "This controls whether or not the plugin will update itself. (Note: Requires download-latest-version to be set to true as well.)" + newLine + newLine);
			lol.write("teleport-requests: " + "This controls whether requests will be sent to players who do /tpr . This is the equivelant of /tpr setRequest." + newLine + newLine);
			lol.write("tag-playing-time-minutes: " + "This controls the amount of time that will pass before tag is over, and the winner(s) are declared." + newLine + newLine);
			lol.write("hideseek-playing-time-minutes: " + "This controls the amount of time that will pass before hide & seek is over, and the winner(s) are declared." + newLine + newLine);
			lol.write("disallow-gamemode-change: " + "Disallows OPs, other than Console, to changing gamemode.");
			lol.write("");
			lol.flush();
			lol.close();
		}catch(Exception e){
			getLogger().severe("ConfigReadme Error: " + e.getMessage());
		}
	}
	
	/** Truth. 
	 * @return true*/
	public static boolean isCoolawesomemeAwesome(){
    	return true;
    }

	/** The current threat level of the server
	 * @return ThreatLevel*/
	public static ThreatLevel getServerThreatLevel() {
		return threatLevel;
	}

	/** Sets the server threat level */
	public static void setServerThreatLevel(ThreatLevel threatLevel) {
		Basics.threatLevel = threatLevel;
	}
}