package fihgu.core.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;

public class YCommand extends CommandBase
{
	public YCommand()
	{
		this.name = "y";
		this.usage = Language.translate("use this command to accept a pending request.");
		this.trueName = name;
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length > 0)
			this.argumentMismatch(player);
		
		if(Request.map.containsKey(new Player(player)))
		{
			Request.map.get(new Player(player)).interact(true);
		}
		else
		{
			player.sendChatToPlayer(Language.translate("You don't have any request."));
		}
	}
}
