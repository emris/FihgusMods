package permission;

import java.util.ArrayList;

import core.io.ConfigFile;
import core.io.SaveFile;
import core.shortcut.Forge;
import permission.element.PermissionList;
import permission.element.PermissionOwner;
import permission.tools.EventHandler;

public class CommonProxy 
{
	public static ArrayList<PermissionList> players = new ArrayList<PermissionList>();
	public static ArrayList<PermissionList> groups = new ArrayList<PermissionList>();
	public static ConfigFile config = new ConfigFile("permission.cfg","./fihgu/permission/");
	
	public static String defaultGroup = "player";
	
	public void init() 
	{
		Forge.registerEventHandler(new EventHandler());
		registerCommands();
		
		if(config.get("regenerateDefaultPermission", "true").equals("true"))
			createDefaultPermission();
		
		defaultGroup = config.get("defaultGroup", "player");
	}
	private void createDefaultPermission()
	{
		SaveFile temp;
		
		temp = new SaveFile("group_player.txt","./fihgu/permission/groups/");
		temp.data.add("login.*");
		temp.data.add("register.*");
		temp.data.add("setPassword.$");
		temp.save(false);
		temp = new SaveFile("group_admin.txt","./fihgu/permission/groups/");
		temp.data.add("@group player");
		temp.save(false);
		temp = new SaveFile("group_owner.txt","./fihgu/permission/groups/");
		temp.data.add("@group admin");
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
		if(!owner.isGroup)
		{
			newList.groups.add(get(new PermissionOwner(defaultGroup,true)));
			newList.save();
		}
			
		
		return newList;
	}
	public void exit()
	{
		save();
	}
	private void registerCommands()
	{
		
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
