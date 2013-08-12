package coolawesomeme.basics_plugin;

public enum ThreatLevel {

	NULL("Null", MinecraftColors.green + "Code Green/ Null"),
	MILD("Mild", MinecraftColors.gold + "Code Orange/ Mild"),
	SEVERE("Severe", MinecraftColors.red + "Code Red/ Severe");
	
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
