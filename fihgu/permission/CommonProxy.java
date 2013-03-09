package permission;

import java.util.ArrayList;

import core.io.ConfigFile;
import core.io.SaveFile;
import core.shortcut.Forge;
import permission.commands.*;
import permission.element.PermissionList;
import permission.element.PermissionOwner;
import permission.tools.EventHandler;

public class CommonProxy 
{
	public static ArrayList<PermissionList> players = new ArrayList<PermissionList>();
	public static ArrayList<PermissionList> groups = new ArrayList<PermissionList>();
	public static ConfigFile config = new ConfigFile("permission.cfg","./fihgu/permission/");
	
	public static String allNode = "*";
	public static String partNode = "$";
	public static String commandSenderNode = "%commandsender%";
	public static String otherPlayerNode = "%otherplayer%";
	public static String onlinePlayerNode = "%onlineplayer%";
	public static String groupNode = "%group%"; // TODO: implement this!
	public static String rangeNode = "%between%";
	
	public static String defaultGroup = "player";
	
	public void init() 
	{
		Forge.registerEventHandler(new EventHandler());
		registerCommands();
		
		config.load();
		
		if(config.get("regenerateDefaultPermission", "false").equals("true"))
			createDefaultPermission();
		
		defaultGroup = config.get("defaultGroup", "player");
		allNode = config.get("allNode", allNode);
		partNode = config.get("partNode", partNode);
		commandSenderNode = config.get("commandSenderNode", commandSenderNode);
		otherPlayerNode = config.get("otherPlayerNode", otherPlayerNode);
		onlinePlayerNode = config.get("onlinePlayerNode", onlinePlayerNode);
		groupNode = config.get("groupNode", groupNode);
		rangeNode = config.get("rangeNode", rangeNode);
		
	}
	private void createDefaultPermission()
	{
		SaveFile temp;
		
		temp = new SaveFile("group_player.txt","./fihgu/permission/groups/");
		temp.data.add("login.*");
		temp.data.add("register.*");
		temp.data.add("logout.*");
		temp.data.add("setPassword.$");
		temp.data.add("me.*");
		temp.data.add("?.*");
		temp.data.add("help.*");
		temp.data.add("tell.*");
		temp.data.add("scoreboard.*");
		temp.data.add("seed.*");
		temp.data.add("list.*");
		temp.save(false);
		temp = new SaveFile("group_admin.txt","./fihgu/permission/groups/");
		temp.data.add("@group player");
		temp.data.add("clear.*");
		temp.data.add("defaultgamemode.*");
		temp.data.add("difficulty.*");
		temp.data.add("effect.*");
		temp.data.add("enchant.*");
		temp.data.add("gamemode.*");
		temp.data.add("gamerule.*");
		temp.data.add("give.*");
		temp.data.add("kill.*");
		temp.data.add("say.*");
		temp.data.add("spawnpoint.*");
		temp.data.add("time.*");
		temp.data.add("toggledownfall.*");
		temp.data.add("tp.*");
		temp.data.add("weather.*");
		temp.data.add("xp.*");
		temp.data.add("ban.*");
		temp.data.add("ban-ip.*");
		temp.data.add("banlist.*");
		temp.data.add("kick.*");
		temp.data.add("pardon.*");
		temp.data.add("pardon-ip.*");
		temp.data.add("whitelist.*");
		temp.data.add("addpermission.*");
		temp.data.add("delpermission.*");
		temp.data.add("addgrouppermission.*");
		temp.data.add("delgrouppermission.*");
		temp.save(false);
		temp = new SaveFile("group_owner.txt","./fihgu/permission/groups/");
		temp.data.add("@group admin");
		temp.data.add("op.*");
		temp.data.add("deop.*");
		temp.data.add("save-all.*");
		temp.data.add("save-on.*");
		temp.data.add("save-off.*");
		temp.data.add("stop.*");
		temp.save(false);
	}
	public static PermissionList get(PermissionOwner owner)
	{
		if(owner.isGroup)
		{
			for(PermissionList group:groups)
				if(group.owner.name.equals(owner.name))
					return group;
		}
		else
		{
			for(PermissionList player:players)
				if(player.owner.name.equals(owner.name))
					return player;
		}
		
		PermissionList newList = new PermissionList(owner);
		return newList;
	}
	public void exit()
	{
		save();
	}
	private void registerCommands()
	{
		new AddPermissionCommand().register();
		new AddGroupPermissionCommand().register();
		new DelPermissionCommand().register();
		new DelGroupPermissionCommand().register();
	}
	public void save()
	{
		config.save();
		for(PermissionList player:players)
			player.save();
		for(PermissionList group:groups)
			group.save();
	}
}
