package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.teleport.elements.WarpPoint;

public class SetHomeCommand extends CommandBase
{
	public SetHomeCommand()
	{
		name = "sethome";
		usage = Language.translate(" : Set or update Home at your location.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		WarpPoint home = WarpPoint.getHome(player.username);
		if (home == null)
		{
			WarpPoint.setHome(player);
			player.addChatMessage(McColor.green + Language.translate("Home set."));
		}
		else
		{
			player.addChatMessage(McColor.pink + Language.translate("You already have a home, would you like to relocate it?"));
			player.addChatMessage(McColor.pink + Language.translate("Please accept with /y or deny with /n."));
			new Request(new Player(player), 30)
			{
				@Override
				public void accept()
				{
					WarpPoint.setHome(player.getEntity());
					player.msg(McColor.green + Language.translate("Your home has been relocate to your current location!"));
				}
			};
		}
	}
}