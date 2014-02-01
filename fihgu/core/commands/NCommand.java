package fihgu.core.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;

public class NCommand extends CommandBase
{
	public NCommand()
	{
		this.name = "n";
		this.usage = Language.translate("use this command to deny a pending request.");
		this.trueName = name;
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length > 0)
			this.argumentMismatch(player);

		if(Request.map.containsKey(new Player(player)))
		{
			Request.map.get(new Player(player)).interact(false);
		}
		else
		{
			player.addChatMessage(Language.translate("You don't have any request."));
		}
	}
}
