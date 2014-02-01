package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.teleport.elements.WarpPoint;

public class DelWarpCommand extends CommandBase
{
	public DelWarpCommand()
	{
		name = "delwarp";
		usage = Language.translate(" <Warp point Name>: delete the given warp point.");
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
			String warpPointName = args[0];
			WarpPoint warpPoint = WarpPoint.getWarpPoint(warpPointName);

			if (warpPoint == null)
			{
				player.addChatMessage(McColor.aqua + warpPointName + McColor.darkRed + Language.translate(" does not exist."));
			}
			else
			{
				WarpPoint.delWarpPoint(warpPointName);
				player.addChatMessage(McColor.aqua + warpPointName + McColor.grey + Language.translate(" has been deleted."));
			}
		}
	}
}
