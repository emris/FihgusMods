package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Warp;

public class SetWarpCommand extends CommandBase
{
	Warp warp;
	Location loc;

	public SetWarpCommand()
	{
		warp = new Warp();
		name = "setwarp";
		usage = Language.translate(" <Warp Name>: Create a new warp at your location.");
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
			loc = new Location(player);
			if (warp.newWarp(loc, args[0]))
			{
				player.sendChatToPlayer(McColor.green + Language.translate("Warp ") + McColor.blue + args[0] + McColor.green + Language.translate(" set to your current location!"));
			}
			else
			{
				player.sendChatToPlayer(McColor.red	+ Language.translate("Warp ") + McColor.blue + args[0] + McColor.red + Language.translate(" already exists."));
			}
		}
	}
}
