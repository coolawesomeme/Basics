package coolawesomeme.basics_plugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import coolawesomeme.basics_plugin.commands.AFactorCommand;
import coolawesomeme.basics_plugin.commands.BanHammerCommand;
import coolawesomeme.basics_plugin.commands.BanRouletteCommand;
import coolawesomeme.basics_plugin.commands.BanTheCoonCommand;
import coolawesomeme.basics_plugin.commands.BrbCommand;
import coolawesomeme.basics_plugin.commands.ServerHelpCommand;
import coolawesomeme.basics_plugin.commands.TPRCommand;
import coolawesomeme.basics_plugin.commands.TPRouletteCommand;

public final class Basics extends JavaPlugin {

	private String ownersRaw;
	public String[] owners;
	public static String serverName = Bukkit.getServerName();
	public static String message;
	public static int versionMajor = 0;
	public static int versionMinor = 4;
	public static int versionRevision = 0;
	public static String version = versionMajor + "." + versionMinor + "." + versionRevision;
	public static boolean isBetaVersion = true;
	public static boolean download;
	public static boolean teleportRequests;
	
	@Override
    public void onEnable(){
		PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new EventListener(), this);
		this.saveDefaultConfig();
		this.config();
		this.createFolder();
		this.commandHandlers();
		AutoUpdater.checkForUpdate(this);
        getLogger().info("Plugin enabled!");
    }
 
    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }
	
    private void commandHandlers(){
    	getCommand("afactor").setExecutor(new AFactorCommand(this));
    	getCommand("banhammer").setExecutor(new BanHammerCommand(this));
    	getCommand("banroulette").setExecutor(new BanRouletteCommand(this));
    	getCommand("banthecoon").setExecutor(new BanTheCoonCommand(this));
    	getCommand("brb").setExecutor(new BrbCommand(this));
    	getCommand("serverhelp").setExecutor(new ServerHelpCommand(this));
    	getCommand("tproulette").setExecutor(new TPRouletteCommand(this));
    	getCommand("tpr").setExecutor(new TPRCommand(this));
    }
    
	private void config(){
    	ownersRaw = this.getConfig().getString("server-owners");
    	if(ownersRaw.length()!= 0){
    		if(ownersRaw.contains(" ")){
    			ownersRaw.replace(" ", "");
    		}if(ownersRaw.contains(",")){
    			owners = ownersRaw.split(",");
    		}else{
    			owners = new String[1];
    			owners[0] = ownersRaw + "";
    		}
    	}else{
    		owners[0] = "";
    	}
    	message = this.getConfig().getString("message");
    	download = this.getConfig().getBoolean("download-latest-version", true);
    	teleportRequests = this.getConfig().getBoolean("teleport-requests", true);
    }
	
	private void createFolder(){
		File f = new File(this.getDataFolder().getAbsolutePath() + "/players");
		f.mkdirs();
		PlayerDataStorage.setDataFolder(this.getDataFolder().getAbsolutePath() + "/players");
	}
 
	/** Truth. 
	 * @return true*/
	public static boolean isCoolawesomemeAwesome(){
    	return true;
    }
}