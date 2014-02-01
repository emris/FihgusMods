package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Teleport;
import fihgu.teleport.elements.WarpPoint;

public class HomeCommand extends CommandBase
{
	public HomeCommand()
	{
		name = "home";
		usage = Language.translate(" : Go home.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if (args.length > 0)
		{
			this.argumentMismatch(player);
		} 
		else if (args.length == 0)
		{
			WarpPoint home = WarpPoint.getHome(player.username);
			if (home != null)
			{
				Teleport.warp(player, home.location, false);
				player.addChatMessage(McColor.green + Language.translate("Warped home."));
			} else
			{
				player.addChatMessage(McColor.darkRed + Language.translate("You are homeless!"));
			}
		}
	}
}