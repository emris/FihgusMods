package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.PlayerManager;
import fihgu.core.functions.Warp;

public class AcceptCommand extends CommandBase 
{
	private Warp warp;
	
	public AcceptCommand()
	{
		warp = new Warp();
		name = "accept";
		usage = Language.translate(" : Accepts a pending Warp request.");;
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length > 0 )
		{
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /summon <Player Name>"));
		}
		else if(args.length == 0)
		{
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
}
