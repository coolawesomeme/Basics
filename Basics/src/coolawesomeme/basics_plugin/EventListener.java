package coolawesomeme.basics_plugin;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import coolawesomeme.basics_plugin.commands.BrbCommand;
import coolawesomeme.basics_plugin.commands.ServerHelpCommand;
import coolawesomeme.basics_plugin.commands.TagCommand;

public class EventListener implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
		if(Basics.getServerThreatLevel() != ThreatLevel.NULL){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kick " + event.getPlayer().getName() + " Server is in lockdown mode!");
		}
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
			if (calendar.get(Calendar.DAY_OF_MONTH) >= 25 && calendar.get(Calendar.DAY_OF_MONTH) <= 31) {
				HolidaySurprise.activate(event);
			}
		}
    }
	
	@EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinLower(PlayerJoinEvent event) {
		PlayerDataStorage.makePlayerDataFile(event.getPlayer().getName());
		if(BrbCommand.isOwnerBRBing){
			if(!event.getPlayer().hasPlayedBefore()){
				final PlayerJoinEvent newEvent = event;
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(new Basics(), new Runnable() {
					@Override 
					public void run() {
						ServerHelpCommand.actualServerHelp(newEvent.getPlayer());
						newEvent.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Server is currently in BRB mode because the server owner is brbing!");
					}
				}, 20L);
			}else{
				event.getPlayer().sendMessage("Welcome to " + Bukkit.getServerName() + ", " + event.getPlayer().getName() + "!");
				event.getPlayer().sendMessage("");
				event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Server is currently in BRB mode because the server owner is brbing!");
			}
		}else{
			event.getPlayer().sendMessage("Welcome to " + Bukkit.getServerName() + ", " + event.getPlayer().getName() + "!");
    	}
	}
	
	@EventHandler
    public void playerDamagePlayer(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player damager = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
            if(TagCommand.isTagOn){
    			if(TagCommand.getTaggedPlayers().contains(damager) && TagCommand.getNonTaggedPlayers().contains(victim)){
    				TagCommand.tagPlayer(victim);
    			}else if(TagCommand.getTaggedPlayers().contains(damager)){
    				damager.sendMessage(ChatColor.RED + "This player is already tagged!");
    			}
    		}
        }
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event){
		if(Basics.disallowGamemode && !event.getPlayer().hasPermission("basics.gamemode_ban.change")){
			if(event.getMessage().toLowerCase().startsWith("/gamemode") || event.getMessage().toLowerCase().startsWith("/gm") || event.getMessage().toLowerCase().startsWith("gamemode") || event.getMessage().toLowerCase().startsWith("gm")){
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "Sorry, this server has disabled the changing of gamemodes by players.");
				event.getPlayer().sendMessage(ChatColor.RED + "Only the Console may change players' gamemodes.");
			}
		}
	}
}
