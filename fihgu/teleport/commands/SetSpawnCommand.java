package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;

public class SetSpawnCommand extends CommandBase
{
	public SetSpawnCommand()
	{
		name = "setspawn";
		usage = Language.translate(" : Set the server's spawn location.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		Location loc = new Location(player);
		player.worldObj.getWorldInfo().setSpawnPosition(loc.x,loc.z,loc.y);
		player.addChatMessage(McColor.green + Language.translate("spawn set."));
	}
}
