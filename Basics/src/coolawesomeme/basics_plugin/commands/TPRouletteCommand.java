package coolawesomeme.basics_plugin.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolawesomeme.basics_plugin.Basics;

public class TPRouletteCommand implements CommandExecutor{
	
	private Basics basics;
	
	public TPRouletteCommand(Basics instance){
		basics = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 2){
			sender.sendMessage("Invalid command syntax!");
			return false;
		}else if(args.length > 0){
			if(args[0].equalsIgnoreCase("all")){
				if(sender.isOp()){
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
						tproulette(victim, randomLocation);	
					}
					return true;
				}else{
					sender.sendMessage("You must be OP to do that!");
					return true;
				}
			}else{
				final Player victim = Bukkit.getPlayer(args[0]);
				World world;
				if(args.length > 1){
					world = Bukkit.getWorld(args[1]);
				}else{
					world = victim.getWorld();
				}
				Location randomLocation = getRandomLocation(world);
				tproulette(victim, randomLocation);
				return true;
			}
		}else{
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to do that!");
				return true;
			}else{
				final Player victim = (Player)sender;
				Location randomLocation = getRandomLocation(victim.getWorld());
				tproulette(victim, randomLocation);
				return true;
			}
		}
	}

	private void tproulette(final Player victim, Location randomLocation) {
    	final GameMode oldGameMode = victim.getGameMode();
		if(!oldGameMode.equals(GameMode.CREATIVE))
			victim.setGameMode(GameMode.CREATIVE);
		victim.teleport(randomLocation);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(basics, new Runnable() {
			@Override 
			public void run() {
				if(!oldGameMode.equals(GameMode.CREATIVE))
					victim.setGameMode(oldGameMode);
			}
		}, 3600L);
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
}
