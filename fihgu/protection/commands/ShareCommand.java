package fihgu.protection.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.protection.tools.EventHandler;

public class ShareCommand extends CommandBase
{
	public ShareCommand()
	{
		name = "share";
		usage = Language.translate(" <Player Name>: Let a player access your locked block");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length != 1)
		{
			this.argumentMismatch(player);
			return;
		}
		
		EventHandler.watchlist.put(new Player(player), args[0]);
		player.addChatMessage(McColor.green + Language.translate("Please click on a block that you want to share."));
	}
}