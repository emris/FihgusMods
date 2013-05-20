package fihgu.protection.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.events.RequestInteractEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.protection.elements.ProtectedBlock;
import fihgu.protection.elements.ProtectedRegion;
import fihgu.protection.tools.EventHandler;

public class LockCommand extends CommandBase
{
	public LockCommand()
	{
		name = "lock";
		usage = Language.translate(" [Region Name]: Protect region or single block when no name is given.");
	}

	@Override
	public void processPlayer(EntityPlayerMP p, String[] args)
	{
		Player player = new Player(p);
		if (args.length > 1)
		{
			this.argumentMismatch(p);
			return;
		}
		else if (args.length == 1)
		{
			ProtectedRegion region = new ProtectedRegion(args[0],null,null);
			if (!ProtectedRegion.protectedRegions.contains(region))
			{
				player.msg(McColor.green + Language.translate("Please click on two blocks to protect a region."));
				ProtectedRegion.watchlist.put(player, null);
				ProtectedRegion.namelist.put(player, args[0]);
			}
			else
			{
				player.msg(McColor.darkRed + Language.translate("A region with that name already exists!"));
			}
		}
		else if (args.length == 0)
		{
			player.msg(McColor.green + Language.translate("Please click a block that you would like to lock."));
			ProtectedBlock.watchlist.put(player, true);
		}
	}
}
