package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Teleport;
import fihgu.teleport.elements.WarpPoint;

public class SetWarpCommand extends CommandBase
{

	public SetWarpCommand()
	{
		name = "setwarp";
		usage = Language.translate(" <Warp point Name>: Create a new warp point at your location.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if (args.length < 1 || args.length > 1)
		{
			this.argumentMismatch(player);
		} 
		else if (args.length == 1)
		{
			final String warpPointName = args[0];
			WarpPoint warpPoint = WarpPoint.getWarpPoint(warpPointName);
			
			if (warpPoint == null)
			{
				WarpPoint.setWarpPoint(player,warpPointName);
				player.sendChatToPlayer(McColor.aqua + warpPointName + McColor.green + Language.translate(" has been set."));
			}
			else
			{
				player.sendChatToPlayer(McColor.aqua + warpPointName + McColor.pink + Language.translate(" already exist, would you like to relocate it?"));
				player.sendChatToPlayer(McColor.pink + Language.translate("Please accept with /y or deny with /n."));
				new Request(new Player(player),30)
				{
					String name = warpPointName;
					@Override
					public void accept()
					{
						WarpPoint.setWarpPoint(player.getEntity(),name);
						player.msg(McColor.aqua + warpPointName + McColor.green + Language.translate(" has been relocate to your current location!"));
					}
				};
			}
		}
	}
}
