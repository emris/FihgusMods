package fihgu.permission.io;

import java.io.File;
import java.util.ArrayList;

import fihgu.permission.CommonProxy;
import fihgu.permission.element.PermissionNode;
import fihgu.permission.element.PermissionOwner;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.io.SaveFile;

public class PermissionFile extends SaveFile
{
	public PermissionOwner owner;
	public ArrayList<PermissionNode> permissions = new ArrayList<PermissionNode>();
	public PermissionFile(PermissionOwner owner) 
	{
		name = owner.name + ".txt";
		path = "./fihgu/permission/";
		this.owner = owner;
		
		if(owner.isGroup)
		{
			name = "group_" + name;
			path += "groups/";
		}
		else
		{
			name = "player_" + name;
			path+= "players/";
		}
		
		file = new File(path + name);
		
		if(!file.exists())
		{
			createFile();
			if(!owner.isGroup)
				data.add("@group " + CommonProxy.defaultGroup);
			this.save(false);
		}
	}
}
