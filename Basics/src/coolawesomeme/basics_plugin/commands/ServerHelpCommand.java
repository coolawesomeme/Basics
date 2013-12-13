package coolawesomeme.basics_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import coolawesomeme.basics_plugin.Basics;
import coolawesomeme.basics_plugin.CommandErrorMessages;
import coolawesomeme.basics_plugin.MinecraftColors;
import coolawesomeme.basics_plugin.PlayerDataStorage;

public class ServerHelpCommand implements CommandExecutor{
	
	public ServerHelpCommand(Basics instance){
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if(args.length == 1){
				return serverHelpCommand(sender, cmd, label, args);
			}else if(args.length > 1){
				return CommandErrorMessages.sendSyntaxError(sender);
			}else{
				return CommandErrorMessages.sendSyntaxError(sender);
			}
		} else {
			return serverHelpCommand(sender, cmd, label, args);
		}
	}

	private boolean serverHelpCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if(args.length == 0){
			player = (Player)sender;
		}else{
			if(args.length > 1){
				return CommandErrorMessages.sendSyntaxError(sender);
			}
			else{
				player = Bukkit.getServer().getPlayer(args[0]);
			}
		}	
		actualServerHelp(player);
		return true;
	}

	public static void actualServerHelp(Player player){
		player.sendMessage("Welcome to the server, " + MinecraftColors.red + player.getName() + "!");
		player.sendMessage(Basics.message);
		int i = PlayerDataStorage.getPlayerServerHelpCount(player);
		if(i < 3){
			player.sendMessage(MinecraftColors.red + "It's dangerous to go alone, take these!");
			player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
			player.getInventory().addItem(new ItemStack(Material.STONE_AXE, 1));
			player.getInventory().addItem(new ItemStack(Material.STONE_SPADE, 1));
			player.getInventory().addItem(new ItemStack(Material.PORK, 8));
			i++;
			PlayerDataStorage.setPlayerServerHelpCount(player, i);
		}
	}

	
}
