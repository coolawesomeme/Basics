package coolawesomeme.basics_plugin;

import java.io.File;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Basics extends JavaPlugin {

	public String ownersRaw;
	public static String[] owners;
	public static String serverName = Bukkit.getServerName();
	public static Player[] banHammerVictims;
	public static Player banRouletteVictim;
	public static String message;
	
	@Override
    public void onEnable(){
		PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new EventListener(), this);
		this.saveDefaultConfig();
		this.config();
		this.createFolder();
        getLogger().info("Plugin enabled!");
    }
 
    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("serverhelp")){
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    			return false;
    		} else {
    			ServerCommands.serverHelpCommand(sender, cmd, label, args);
    			return true;
    		}
    	}else if(cmd.getName().equalsIgnoreCase("brb")){
    		if (!(sender instanceof Player)) {
    			ServerCommands.brbCommand(sender, cmd, label, args);
    			return true;
    		} else {
    			boolean flag = false;
    			for(int i=0;i<owners.length;i++){
    				if(sender.getName().equals(owners[i]) || sender.getName() == owners[i]){
    					flag=true;
    				}
    			}
    			if(flag){
    				ServerCommands.brbCommand(sender, cmd, label, args);
    				return true;
    			}else{
    				return false;
    			}
    		}
    	}else if(cmd.getName().equalsIgnoreCase("banhammer")){
    		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
    		if(onlinePlayers.length > 0){
    			for(int i = 0; i < onlinePlayers.length; i++){
    				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + onlinePlayers[i].getName() + " THOU HATH BEEN BAN HAMMERED");
    				banHammerVictims[i] = onlinePlayers[i];
    			}
    			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override 
					public void run() {
						for(int i = 0; i < banHammerVictims.length; i++){
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + banHammerVictims[i].getName());
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + banHammerVictims[i].getName());
						}
						getLogger().info("All ban hammer victims have been unbanned.");
					}
				}, 1200L);
    			return true;
    		}else{
    			getLogger().info("There must be player(s) online to use this command!");
    			return true;
    		}
    	}else if(cmd.getName().equalsIgnoreCase("banthecoon")){
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
    			Bukkit.getServer().dispatchCommand(sender, "ban " + highestPlayer.getName() + " You have been banned for five minutes, coon.");
    			Bukkit.getLogger().log(Level.INFO, "Player " + highestPlayer.getName() + " has been banned.");
    			final Player highestPlayer1 = highestPlayer;
    			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override 
					public void run() {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + highestPlayer1.getName());
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + highestPlayer1.getName());
						getLogger().info("The coon (" + highestPlayer1.getName() + ") has been unbanned.");
					}
				}, 6000L);
    			return true;
    		}else{
    			getLogger().info("There must be player(s) online to use this command!");
    			return true;
    		}
    	}else if(cmd.getName().equalsIgnoreCase("banroulette")){
    		if(banRouletteVictim == null || banRouletteVictim == (Player)null){
    			Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
    			if(onlinePlayers.length > 0){
    				Random random = new Random();
    				int r = random.nextInt(onlinePlayers.length);
    				banRouletteVictim = onlinePlayers[r];
    				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + banRouletteVictim.getName() + " You are the ban roulette victim! You have been banned for 30 minutes.");
    				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    					@Override 
    					public void run() {
    						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + banRouletteVictim.getName());
    						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "unban " + banRouletteVictim.getName());
    						getLogger().info("The ban roulette victim (" + banRouletteVictim.getName() + ") has been unbanned.");
    						banRouletteVictim = null;
    					}
    				}, 30000L);
    				return true;
    			}else{
        			getLogger().info("There must be player(s) online to use this command!");
        			return true;
    			}
    		}else{
    			sender.sendMessage("There is still a banned ban roulette victim!");
    			sender.sendMessage("Please try again after the current victim is unbanned.");
    			return true;
    		}
    	}else if(cmd.getName().equalsIgnoreCase("afactor")){
    		if(args.length > 0){
    			if(args.length > 1){
    				if(args[1].equals("set")){
    					PlayerDataStorage.setPlayerAFactor(Bukkit.getPlayer(args[0]), Integer.parseInt(args[2]));
    					sender.sendMessage("Annoying factor set!");
    					return true;
    				}
    			}else{
    				try{
    					sender.sendMessage("Annoying Factor: " + PlayerDataStorage.getPlayerAFactor(Bukkit.getPlayer(args[0])));
    				}catch(Exception e){
    					sender.sendMessage("Annoying Factor: " + PlayerDataStorage.getPlayerAFactor(Bukkit.getOfflinePlayer(args[0])));
    				}
    				return true;
    			}
    		}else{
    			sender.sendMessage("This command requires at least 1 argument!");
    			return false;
    		}
    	}else if(cmd.getName().equalsIgnoreCase("tproulette")){
    		if(args.length > 0){
    			if(args[0].equals("all")){
    				World world;
    				if(args.length > 1){
    					world = Bukkit.getWorld(args[1]);
    				}else{
    					world = Bukkit.getServer().getWorlds().get(0);
    				}
    				Player[] onlinePlayers = Bukkit.getOnlinePlayers();
    				Location randomLocation = getRandomLocation(world);
    				for(int i = 0; i < onlinePlayers.length;i++){
    					final Player victim = onlinePlayers[i];
        				final GameMode oldGameMode = victim.getGameMode();
        				if(!oldGameMode.equals(GameMode.CREATIVE))
        					victim.setGameMode(GameMode.CREATIVE);
        				victim.teleport(randomLocation);
        				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
        					@Override 
        					public void run() {
        						if(!oldGameMode.equals(GameMode.CREATIVE))
        							victim.setGameMode(oldGameMode);
        					}
        				}, 3600L);	
    				}
    				return true;
    			}else{
    				final Player victim = Bukkit.getPlayer(args[0]);
    				World world;
    				if(args.length > 1){
    					world = Bukkit.getWorld(args[1]);
    				}else{
    					world = victim.getWorld();
    				}
    				final GameMode oldGameMode = victim.getGameMode();
    				if(!oldGameMode.equals(GameMode.CREATIVE))
    					victim.setGameMode(GameMode.CREATIVE);
    				Location randomLocation = getRandomLocation(world);
    				victim.teleport(randomLocation);
    				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    					@Override 
    					public void run() {
    						if(!oldGameMode.equals(GameMode.CREATIVE))
    							victim.setGameMode(oldGameMode);
    					}
    				}, 3600L);	
    				return true;
    			}
    		}else{
    			if (!(sender instanceof Player)) {
    				sender.sendMessage("This command can only be run by a player with that syntax.");
    				return false;
    			}else{
    				final Player victim = (Player)sender;
    				final GameMode oldGameMode = victim.getGameMode();
    				if(!oldGameMode.equals(GameMode.CREATIVE))
    					victim.setGameMode(GameMode.CREATIVE);
    				Location randomLocation = getRandomLocation(victim.getWorld());
    				victim.teleport(randomLocation);
    				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    					@Override 
    					public void run() {
    						if(!oldGameMode.equals(GameMode.CREATIVE))
    							victim.setGameMode(oldGameMode);
    					}
    				}, 3600L);	
    				return true;
    			}
    		}
    	}
    	//If this has happened the function will return true. 
            // If this hasn't happened the a value of false will be returned.
    	return false; 
    }
    
    private int getRandomCoord(){
    	Random random = new Random();
    	int x = random.nextInt(6000);
    	boolean q = random.nextBoolean();
    	if(q){
    		x*=(-1);
    	}
    	return x;
    }
    
    private Location getRandomLocation(World world){
    	Location randomLocation = new Location(world, getRandomCoord(), 100, getRandomCoord());
		if(randomLocation.getWorld().getBlockAt(randomLocation).getType().equals(Material.AIR)){
		}else{
			boolean toContinue = true;
			while(toContinue){
				if(randomLocation.getBlockY() > 900){
					randomLocation.setY(100 - 1);
				}else if(randomLocation.getBlockY() < 100 && randomLocation.getBlockY() > 0){
					randomLocation.setY(randomLocation.getY() - 1);
				}else if(randomLocation.getBlockY() == 1){
					getRandomLocation(world);
				}else{
					randomLocation.setY(randomLocation.getY() + 1);
				}
				if(randomLocation.getWorld().getBlockAt(randomLocation).getType().equals(Material.AIR)){
					toContinue = false;
				}
			}
		}
		return randomLocation;
    }
    
	private void config(){
    	ownersRaw = this.getConfig().getString("server-owners");
    	if(!ownersRaw.isEmpty()){
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
    }
	
	private void createFolder(){
		File f = new File(this.getDataFolder().getAbsolutePath() + "/players");
		f.mkdirs();
		PlayerDataStorage.setDataFolder(this.getDataFolder().getAbsolutePath() + "/players");
	}
    
}