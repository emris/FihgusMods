package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Teleport;

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
		player.getServerForPlayer().setSpawnLocation(loc.x,loc.z,loc.y);
		player.sendChatToPlayer(McColor.green + Language.translate("spawn set."));
	}
}
