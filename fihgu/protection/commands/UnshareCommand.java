package fihgu.protection.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.protection.tools.EventHandler;

public class UnshareCommand extends CommandBase
{
	public UnshareCommand()
	{
		name = "unshare";
		usage = Language.translate(" : remove everyone from your share list.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length != 0)
		{
			this.argumentMismatch(player);
			return;
		}
		
		EventHandler.watchlist.put(new Player(player), args[0]);
		player.sendChatToPlayer(McColor.green + Language.translate("Please click on a block that you want to unshare."));
	}
}
