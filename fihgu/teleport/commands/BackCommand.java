package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Teleport;

public class BackCommand extends CommandBase
{
	public BackCommand()
	{
		name = "back";
		usage = Language.translate(" : Warp back to your previous location.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if (Teleport.goBack(player))
		{
			player.addChatMessage(McColor.green + Language.translate("Warped back to previous location."));
		} else
		{
			player.addChatMessage(McColor.darkRed	+ Language.translate("You have not warped anywhere!"));
		}
	}
}