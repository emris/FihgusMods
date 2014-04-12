package fihgu.permission.element;

import java.util.ArrayList;

import fihgu.core.elements.Group;
import fihgu.core.elements.Player;
import fihgu.core.events.TryCommandEvent;
import fihgu.core.io.SaveFile;
import fihgu.core.shortcut.FML;
import fihgu.login.commands.LoginCommand;
import fihgu.login.commands.RegisterCommand;

public class PermissionOwner
{
	public String name;
	public boolean isGroup = false;
	private ArrayList<PermissionNode> allows = new ArrayList<PermissionNode>();
	private ArrayList<PermissionNode> denies = new ArrayList<PermissionNode>();

	private SaveFile saveFile;

	public PermissionOwner(Player player)
	{
		construct(player.name);
	}

	public PermissionOwner(Group group)
	{
		isGroup = true;
		construct(group.name);
	}

	private void construct(String name)
	{
		this.name = name;
		String savePath;
		String saveName = name + ".txt";

		if(isGroup)
			savePath = "./fihgu/permission/groups/";
		else
			savePath = "./fihgu/permission/players/";

		saveFile = new SaveFile(saveName , savePath);
		saveFile.load();

		for(String line : saveFile.data)
		{
			if(line.startsWith("!"))
				denies.add(new PermissionNode(line));
			else
				allows.add(new PermissionNode(line));
		}
	}

	public boolean canUse(TryCommandEvent e)
	{
		if(FML.isModLoaded("fihgus_login_mod"))
		{
			if(e.command.startsWith(RegisterCommand.instance.name) || e.command.startsWith(LoginCommand.instance.name))
				return true;
		}

		if(canDeny(e))
			return false;

		if(canAllow(e))
			return true;

		return false;
	}

	public boolean canAllow(TryCommandEvent e)
	{
		for(PermissionNode allow : allows)
		{
			if(allow.matches(e))
				return true;
		}

		if(!isGroup)
		{
			Player player = new Player(name);
			for(Group group: player.getGroups())
			{
				if(new PermissionOwner(group).canAllow(e))
					return true;
			}
		}
		return false;
	}

	public boolean canDeny(TryCommandEvent e)
	{
		for(PermissionNode deny : denies)
		{
			if(deny.matches(e))
				return true;
		}

		if(!isGroup)
		{
			Player player = new Player(name);
			for(Group group: player.getGroups())
			{
				if(new PermissionOwner(group).canDeny(e))
					return true;
			}
		}
		return false;
	}
}
