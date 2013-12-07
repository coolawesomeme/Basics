package coolawesomeme.basics_plugin;

public enum ThreatLevel {

	NULL("Null", "Code " + MinecraftColors.green + "Green/ Null"),
	MILD("Mild", "Code " + MinecraftColors.gold + "Orange/ Mild"),
	SEVERE("Severe", "Code " + MinecraftColors.red + "Red/ Severe");
	
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
