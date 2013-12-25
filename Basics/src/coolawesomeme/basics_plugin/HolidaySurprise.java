package coolawesomeme.basics_plugin;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class HolidaySurprise {
	
	private static HashMap<String, Boolean> surpriseList;

	public static void setUpHolidaySurprise(){
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
			if (calendar.get(Calendar.DAY_OF_MONTH) >= 25 && calendar.get(Calendar.DAY_OF_MONTH) <= 31) {
				surpriseList = new HashMap<String, Boolean>();
			}	
		}
	}
	
	public static void activate(PlayerJoinEvent event) {
		if(!surpriseList.containsKey(event.getPlayer().getName())){
			continueActivation(event);
		}
	}

	@SuppressWarnings("deprecation")
	private static void continueActivation(PlayerJoinEvent event) {
		Random random = new Random();
		Player player = event.getPlayer();
		Location playerLocation = player.getLocation();
		Location chestLocation = null;
		int direction = Math.round(playerLocation.getYaw() / 90f);
		if(direction == 0){
			chestLocation = new Location(playerLocation.getWorld(), playerLocation.getX(), playerLocation.getY(), playerLocation.getZ() + 1);
		}else if(direction == 1){
			chestLocation = new Location(playerLocation.getWorld(), playerLocation.getX() - 1, playerLocation.getY(), playerLocation.getZ());
		}else if(direction == 2){
			chestLocation = new Location(playerLocation.getWorld(), playerLocation.getX(), playerLocation.getY(), playerLocation.getZ() - 1);
		}else if(direction == 3){
			chestLocation = new Location(playerLocation.getWorld(), playerLocation.getX() + 1, playerLocation.getY(), playerLocation.getZ());
		}else{
			chestLocation = null;
		}
		if(chestLocation != null){
			if(!chestLocation.getBlock().isEmpty()){
				chestLocation = null;
			}
		}
		int q = random.nextInt(5);
		if(q < 2){
			q = 2;
		}
		if(chestLocation == null){
			for(int i = 0; i < q; i++){
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " " + getRandomItem(random).getTypeId());
			}
			player.sendMessage(ChatColor.RED + "Happy " + ChatColor.GREEN + "Holidays" + ChatColor.RESET + ", " + player.getName() + "!");
			player.sendMessage(ChatColor.ITALIC + "Check your inventory for a special surprise!");
		}else{
			player.getWorld().getBlockAt(chestLocation).setType(Material.CHEST);
			Chest c = (Chest) player.getWorld().getBlockAt(chestLocation).getState();
			for(int i = 0; i < q; i++){
				c.getBlockInventory().addItem(getRandomItem(random));
			}
			c.update();
			player.sendMessage(ChatColor.RED + "Happy " + ChatColor.GREEN + "Holidays" + ChatColor.RESET + ", " + player.getName() + "!");
			player.sendMessage(ChatColor.ITALIC + "Check the chest for a special surprise!");
		}
		surpriseList.put(player.getName(), true);
	}

	private static ItemStack getRandomItem(Random random) {
		ItemStack item = null;
		int n = random.nextInt(11);
			if(n == 0){ return new ItemStack(Material.COOKIE);}
			else if(n == 1){ return new ItemStack(Material.COAL, 20);}
			else if(n == 2){ return new ItemStack(Material.CAKE);}
			else if(n == 3){ return new ItemStack(Material.ARROW, 5);}
			else if(n == 4){ return new ItemStack(Material.MILK_BUCKET);}
			else if(n == 5){ return new ItemStack(Material.SAPLING, 1, (short) 1);}
			else if(n == 6){ return new ItemStack(Material.DIAMOND);}
			else if(n == 7){ return new ItemStack(Material.CHAINMAIL_BOOTS);}
			else if(n == 8){ return new ItemStack(Material.LEATHER_BOOTS);}
			else if(n == 9){ return new ItemStack(Material.BOOK_AND_QUILL);}
			else if(n == 10){ return new ItemStack(Material.COOKED_CHICKEN);}
			else if(n == 11){ return new ItemStack(Material.MINECART);}
		return item;
	}
	
}
