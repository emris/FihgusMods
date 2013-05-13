package fihgu.permission.commands;

import fihgu.permission.CommonProxy;
import fihgu.permission.element.PermissionList;
import fihgu.permission.element.PermissionNode;
import fihgu.permission.element.PermissionOwner;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;

public class AddGroupPermissionCommand extends CommandBase
{
	public AddGroupPermissionCommand()
	{
		name = "addgrouppermission";
		usage = Language.translate(" <GroupName> <Permission>: give the Group a permission");
		
		//I know this is kind of unnecessary, but it should be there if someone mess with the event.
		opOnly = true;
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if(args.length < 2)
		{
			this.argumentMismatch(sender);
		}
		
		String permission = "";
		for(int i = 1; i < args.length; i++)
		{
			if(!(i==1))
				permission+=" ";
			permission+=args[i];
			
		}
		
		PermissionList target = CommonProxy.get(new PermissionOwner(args[0],true));
		target.addPermission(new PermissionNode(permission,new PermissionOwner(args[0])));
		target.save();
		sender.sendChatToPlayer(McColor.purple + permission + Language.translate(" Permission has been added to group ") + args[0]);
	}
	
}
