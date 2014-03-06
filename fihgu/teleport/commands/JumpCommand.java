package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Teleport;

public class JumpCommand extends CommandBase
{
	public JumpCommand()
	{
		name = "jump";
		usage = Language.translate(" Warp to a location you are looking at.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		Teleport.jump(player);
		player.addChatMessage(McColor.green + player.username + Language.translate(" jumped!"));
	}
}
