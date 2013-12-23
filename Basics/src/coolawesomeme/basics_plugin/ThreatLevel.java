package coolawesomeme.basics_plugin;

import org.bukkit.ChatColor;

public enum ThreatLevel {

	NULL("Null", "Code " + ChatColor.GREEN + "Green/ Null"),
	MILD("Mild", "Code " + ChatColor.GOLD + "Orange/ Mild"),
	SEVERE("Severe", "Code " + ChatColor.RED + "Red/ Severe");
	
	private final String name;
	private final String formattedName;
	
	private ThreatLevel(String s, String s1){
		name = s;
		formattedName = s1;
	}
	
	public String toString(){
		return name;
	}
	
	public String formattedName(){
		return formattedName;
	}
}
