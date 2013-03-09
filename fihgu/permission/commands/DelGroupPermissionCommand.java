package permission.commands;

import permission.CommonProxy;
import permission.element.PermissionList;
import permission.element.PermissionNode;
import permission.element.PermissionOwner;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import core.elements.CommandBase;
import core.functions.Language;
import core.functions.McColor;

public class DelGroupPermissionCommand extends CommandBase
{
	public DelGroupPermissionCommand()
	{
		name = "delgrouppermission";
		usage = Language.translate(" <GroupName> <Permission>: remove a permission from the group.");
		
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
		target.removePermission(new PermissionNode(permission,new PermissionOwner(args[0])));
		target.save();
		sender.sendChatToPlayer(McColor.purple + permission + Language.translate(" Permission has been removed from ") + args[0]);
	}
	
}
