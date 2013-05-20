package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Teleport;

public class SetHomeCommand extends CommandBase
{
	private Teleport warp;

	public SetHomeCommand()
	{
		warp = new Teleport();
		name = "sethome";
		usage = Language.translate(" : Set or update Home at your location.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if (args.length > 0)
		{
			player.sendChatToPlayer(McColor.red
					+ Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /sethome"));
		} else if (args.length == 0)
		{
			if (warp.newHome(player))
			{
				player.sendChatToPlayer(McColor.green
						+ Language
								.translate("Your home has been set to your location!"));
			} else
			{
				player.sendChatToPlayer(McColor.green
						+ Language
								.translate("Your home has been updated to your location!"));
			}
		}
	}
}