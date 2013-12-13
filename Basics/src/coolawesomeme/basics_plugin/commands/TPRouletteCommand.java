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
import coolawesomeme.basics_plugin.CommandErrorMessages;

public class TPRouletteCommand implements CommandExecutor{
	
	private Basics basics;
	
	public TPRouletteCommand(Basics instance){
		basics = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 2){
			return CommandErrorMessages.sendSyntaxError(sender);
		}else if(args.length > 0){
			if(args[0].equalsIgnoreCase("all")){
				if(sender.isOp() || sender.hasPermission("basics.tproulette.all")){
					Player[] onlinePlayers = Bukkit.getOnlinePlayers();
					if(onlinePlayers.length > 0){
						World world;
						if(args.length > 1){
							world = Bukkit.getWorld(args[1]);
						}else{
							world = Bukkit.getServer().getWorlds().get(0);
						}
						Location randomLocation = getRandomLocation(world, onlinePlayers[0]);
						for(int i = 0; i < onlinePlayers.length;i++){
							final Player victim = onlinePlayers[i];
							tproulette(victim, randomLocation);	
						}
						return true;
					}else{
						sender.sendMessage("There must be player(s) online to use this command!");
						return true;
					}
				}else{
					return CommandErrorMessages.sendPermissionError(sender);
				}
			}else{
				final Player victim = Bukkit.getPlayer(args[0]);
				World world;
				if(args.length > 1){
					world = Bukkit.getWorld(args[1]);
				}else{
					world = victim.getWorld();
				}
				Location randomLocation = getRandomLocation(world, victim);
				tproulette(victim, randomLocation);
				return true;
			}
		}else{
			if (!(sender instanceof Player)) {
				return CommandErrorMessages.sendConsoleError(sender);
			}else{
				final Player victim = (Player)sender;
				Location randomLocation = getRandomLocation(victim.getWorld(), victim);
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

	private int getRandomCoord(int coordinateAxis, Player commandVictim){
    	Random random = new Random();
    	int x = random.nextInt(1500);
    	boolean q = random.nextBoolean();
    	if(q){
    		x*=(-1);
    	}
    	int y = coordinateAxis == 0 ? commandVictim.getLocation().getBlockX() : coordinateAxis == 2 ? commandVictim.getLocation().getBlockZ() : coordinateAxis == 1 ? commandVictim.getLocation().getBlockY() : 0;
    	return y + x;
    }
    
    private Location getRandomLocation(World world, Player commandVictim){
    	Location randomLocation = new Location(world, getRandomCoord(0, commandVictim), 100, getRandomCoord(2, commandVictim));
		if(randomLocation.getWorld().getBlockAt(randomLocation).getType().equals(Material.AIR)){
		}else{
			boolean toContinue = true;
			while(toContinue){
				if(randomLocation.getBlockY() > 900){
					randomLocation.setY(100 - 1);
				}else if(randomLocation.getBlockY() < 100 && randomLocation.getBlockY() > 0){
					randomLocation.setY(randomLocation.getY() - 1);
				}else if(randomLocation.getBlockY() == 1){
					getRandomLocation(world, commandVictim);
				}else{
					randomLocation.setY(randomLocation.getY() + 1);
				}
				if(randomLocation.getWorld().getBlockAt(randomLocation).getType().equals(Material.AIR)){
					toContinue = false;
				}
			}
		}
		world.loadChunk(randomLocation.getChunk());
		return randomLocation;
    }
}
