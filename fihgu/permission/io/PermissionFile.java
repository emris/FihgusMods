package permission.io;

import java.io.File;
import java.util.ArrayList;

import permission.element.PermissionNode;
import permission.element.PermissionOwner;

import net.minecraft.entity.player.EntityPlayerMP;
import core.io.SaveFile;

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
		createFile();
	}
}
