package coolawesomeme.basics_plugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ServerCommands {

	protected static boolean isOwnerBRBing = false;

	public static void serverHelpCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)null;
		if(args == null || args.equals(null) || args.length == 0){
			player = (Player)sender;
		}else{
			if(args.length > 1){
				sender.sendMessage("This command has only 1 optional argument!");
				sender.sendMessage("/serverhelp <player>");
			}
			else{
				player = Bukkit.getServer().getPlayer(args[0]);
			}
		}	
		actualServerHelp(player);
	}

	public static void actualServerHelp(Player player){
		player.sendMessage(MinecraftColors.lightRed + "Welcome to the server, " + player.getName() + "!");
		int i = PlayerDataStorage.getPlayerServerHelpCount(player);
		if(i < 3){
			player.sendMessage(MinecraftColors.lightRed + "It's dangerous to go alone, take these!");
			player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
			player.getInventory().addItem(new ItemStack(Material.STONE_AXE, 1));
			player.getInventory().addItem(new ItemStack(Material.STONE_SPADE, 1));
			player.getInventory().addItem(new ItemStack(Material.PORK, 8));
			i+=1;
			PlayerDataStorage.setPlayerServerHelpCount(player, i);
		}
		player.sendMessage(Basics.message);
	}
	
	public static void brbCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args == null || args.equals(null) || args.length == 0){
			if(isOwnerBRBing){
				isOwnerBRBing = false;
				Bukkit.broadcastMessage(MinecraftColors.lightPink + "[Basics] The server owner is no longer away!");
			}else{
				isOwnerBRBing = true;
				Bukkit.broadcastMessage(MinecraftColors.lightPink + "[Basics] The server owner is away!");
			}
		}else{
			if(args.length > 1){
				sender.sendMessage("This command has 1 arguments!");
			}
			else{
				boolean oldBRB = isOwnerBRBing;
				isOwnerBRBing = Boolean.parseBoolean(args[0]);
				if(oldBRB != isOwnerBRBing)
					Bukkit.broadcastMessage(isOwnerBRBing ? (MinecraftColors.lightPink + "[Basics] The server owner is away!") : (MinecraftColors.lightPink + "[Basics] The server ownder is no longer away!"));
			}
		}
	}
	
}
