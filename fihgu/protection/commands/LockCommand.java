package fihgu.protection.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Protection;

public class LockCommand extends CommandBase
{
	
	private Protection protection;

	public LockCommand()
	{
		protection = new Protection();
		name = "lock";
		usage = Language.translate(" [Region Name]: Protect region or single block when no name is given.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP p, String[] args)
	{
		Player player = new Player(p);
		if(args.length > 1)
		{
			player.msg(McColor.red
					+ Language.translate("Invalad command arguments."));
			player.msg(McColor.red
					+ Language.translate("Usage: /lock [Region Name]"));
			player.msg(McColor.red
					+ Language.translate("or: /lock"));
		}
		else if(args.length == 1)
		{
			
		}
		else if(args.length == 0)
		{
			
		}
	}
}
