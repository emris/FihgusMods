package fihgu.protection.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.events.RequestInteractEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Protection;
import fihgu.protection.elements.ProtectedRegion;
import fihgu.protection.tools.EventHandler;

public class LockCommand extends CommandBase
{

	public LockCommand()
	{
		name = "lock";
		usage = Language
				.translate(" [Region Name]: Protect a region or a single block when no name is given.");
	}

	@Override
	public void processPlayer(EntityPlayerMP p, String[] args)
	{
		Player player = new Player(p);
		if (args.length > 1)
		{
			this.argumentMismatch(p);
		}
		else if (args.length == 1)
		{
			if (!Protection.exists(args[0]))
			{
				player.msg(McColor.green
						+ Language
								.translate("Please RIGHT click two blocks to protect a region."));
				EventHandler.name = args[0];
				EventHandler.watchPlayer(player);
			}else{
				player.msg("A region with that name already exists!");
			}
		}
		else if (args.length == 0)
		{
			player.msg(McColor.green
					+ Language
							.translate("Please RIGHT click block that you would like to lock."));
		}
	}
}
