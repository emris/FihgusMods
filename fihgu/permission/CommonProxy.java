package fihgu.permission;

import java.util.ArrayList;

import fihgu.core.io.ConfigFile;
import fihgu.core.io.SaveFile;
import fihgu.core.shortcut.Forge;
import fihgu.permission.tools.EventHandler;

public class CommonProxy 
{
	public static ConfigFile config = new ConfigFile("permission.cfg","./fihgu/permission/");
	
	public static String allNode = "all";
	public static String partNode = "part";
	public static String commandSenderNode = "commandsender";
	public static String onlinePlayerNode = "onlineplayer";
	public static String groupNode = "group";
	public static String rangeNode = "range";
	public static String permissionsFromNode = "permissionsFrom";
	
	public void init() 
	{
		Forge.registerEventHandler(new EventHandler());
		
		config.load();
		
		if(config.get("regenerateDefaultPermission", "true").equals("true"))
			createDefaultPermission();
		
		allNode = config.get("allNode", allNode);
		partNode = config.get("partNode", partNode);
		commandSenderNode = config.get("commandSenderNode", commandSenderNode);
		onlinePlayerNode = config.get("onlinePlayerNode", onlinePlayerNode);
		groupNode = config.get("groupNode", groupNode);
		rangeNode = config.get("rangeNode", rangeNode);
		permissionsFromNode = config.get("permissionsFrom", permissionsFromNode);
		
		config.save();
		
	}
	private void createDefaultPermission()
	{
		SaveFile temp;
		
		temp = new SaveFile("Player.txt","./fihgu/permission/groups/");
		temp.data.add("login");
		temp.data.add("login.*" + allNode  + "*");
		temp.data.add("register");
		temp.data.add("register.*" + allNode  + "*");
		temp.data.add("logout");
		temp.data.add("logout.*" + allNode  + "*");
		temp.data.add("setPassword");
		temp.data.add("setPassword.*" + partNode  + "*");
		temp.data.add("me");
		temp.data.add("me.*" + allNode  + "*");
		temp.data.add("?");
		temp.data.add("?.*" + allNode  + "*");
		temp.data.add("help");
		temp.data.add("help.*" + allNode  + "*");
		temp.data.add("tell");
		temp.data.add("tell.*" + allNode  + "*");
		temp.data.add("scoreboard");
		temp.data.add("scoreboard.*" + allNode  + "*");
		temp.data.add("seed");
		temp.data.add("seed.*" + allNode  + "*");
		temp.data.add("list");
		temp.data.add("list.*" + allNode  + "*");
		temp.save(false);
		temp = new SaveFile("Admin.txt","./fihgu/permission/groups/");
		temp.data.add("*"+ permissionsFromNode + ":Player*");
		temp.data.add("clear");
		temp.data.add("clear.*" + allNode  + "*");
		temp.data.add("defaultgamemode");
		temp.data.add("defaultgamemode.*" + allNode  + "*");
		temp.data.add("difficulty");
		temp.data.add("difficulty.*" + allNode  + "*");
		temp.data.add("effect");
		temp.data.add("effect.*" + allNode  + "*");
		temp.data.add("enchant");
		temp.data.add("enchant.*" + allNode  + "*");
		temp.data.add("gamemode");
		temp.data.add("gamemode.*" + allNode  + "*");
		temp.data.add("gamerule");
		temp.data.add("gamerule.*" + allNode  + "*");
		temp.data.add("give");
		temp.data.add("give.*" + allNode  + "*");
		temp.data.add("kill");
		temp.data.add("kill.*" + allNode  + "*");
		temp.data.add("say");
		temp.data.add("say.*" + allNode  + "*");
		temp.data.add("spawnpoint");
		temp.data.add("spawnpoint.*" + allNode  + "*");
		temp.data.add("time");
		temp.data.add("time.*" + allNode  + "*");
		temp.data.add("toggledownfall");
		temp.data.add("toggledownfall.*" + allNode  + "*");
		temp.data.add("tp");
		temp.data.add("tp.*" + allNode  + "*");
		temp.data.add("weather");
		temp.data.add("weather.*" + allNode  + "*");
		temp.data.add("xp");
		temp.data.add("xp.*" + allNode  + "*");
		temp.data.add("ban");
		temp.data.add("ban.*" + allNode  + "*");
		temp.data.add("ban-ip");
		temp.data.add("ban-ip.*" + allNode  + "*");
		temp.data.add("banlist");
		temp.data.add("banlist.*" + allNode  + "*");
		temp.data.add("kick");
		temp.data.add("kick.*" + allNode  + "*");
		temp.data.add("pardon");
		temp.data.add("pardon.*" + allNode  + "*");
		temp.data.add("pardon-ip");
		temp.data.add("pardon-ip.*" + allNode  + "*");
		temp.data.add("whitelist");
		temp.data.add("whitelist.*" + allNode  + "*");
		temp.data.add("op.*"+ commandSenderNode + "*");
		temp.data.add("deop.*"+ commandSenderNode + "*");
		temp.save(false);
		temp = new SaveFile("Owner.txt","./fihgu/permission/groups/");
		temp.data.add("*"+ permissionsFromNode + ":Admin*");
		temp.data.add("op");
		temp.data.add("op.*" + allNode  + "*");
		temp.data.add("deop");
		temp.data.add("deop.*" + allNode  + "*");
		temp.data.add("save-all");
		temp.data.add("save-all.*" + allNode  + "*");
		temp.data.add("save-on");
		temp.data.add("save-on.*" + allNode  + "*");
		temp.data.add("save-off");
		temp.data.add("save-off.*" + allNode  + "*");
		temp.data.add("stop");
		temp.data.add("stop.*" + allNode  + "*");
		temp.data.add("*" + allNode  + "*");
		temp.save(false);
		
		config.map.put("regenerateDefaultPermission", "false");
		config.save();
	}
	
	public void exit()
	{
		
	}
}
