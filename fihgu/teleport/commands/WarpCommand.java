package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.PlayerManager;
import fihgu.core.functions.Warp;

public class WarpCommand extends CommandBase
{
	public WarpCommand()
	{
		name = "warp";
		usage = Language.translate(" <name>: Warp you to a location or a player.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		final EntityPlayerMP sender = player;
		
		if (args.length != 1)
		{
			this.argumentMismatch(player);
		} 
		else if (args.length == 1)
		{
			final EntityPlayerMP target = PlayerManager.getPlayer(args[0], true);
			
			if (target != null)
			{
				player.sendChatToPlayer(McColor.green + Language.translate("Request sent to ")  + target.username + "!");
				target.sendChatToPlayer(McColor.aqua + player.username + McColor.green + Language.translate(" has send you a Warp request!"));
				target.sendChatToPlayer(McColor.green + Language.translate("Please accept with /y or deny with /n."));
				
				new Request(new Player(target), 30) 
				{
					EntityPlayerMP from = sender;
					EntityPlayerMP to = target;
					@Override
					public void accepted()
					{
						Warp.warpTo(from, to);
						from.sendChatToPlayer(McColor.aqua + to.username + McColor.green + Language.translate(" has accepted your warp request."));
						from.sendChatToPlayer(McColor.green + Language.translate("Warpped to ") + McColor.aqua + to.username);
					}
				};
			}
			else if (Warp.warpTo(player, args[0]))
			{
				//location warp
				player.sendChatToPlayer(McColor.green + Language.translate("Warped to ") + args[0] + ".");			
			}
			else if(PlayerManager.getPossiblePlayer(args[0]) != null)
			{
				final EntityPlayerMP target2 = PlayerManager.getPossiblePlayer(args[0]);
				
				player.sendChatToPlayer(McColor.green + Language.translate("Request sent to ")  + target2.username + "!");
				target2.sendChatToPlayer(McColor.aqua + player.username + McColor.green + Language.translate(" has send you a Warp request!"));
				target2.sendChatToPlayer(McColor.green + Language.translate("Please accept with /y or deny with /n."));
				
				new Request(new Player(target2), 30) 
				{
					EntityPlayerMP from = sender;
					EntityPlayerMP to = target2;
					@Override
					public void accepted()
					{
						Warp.warpTo(from, to);
						from.sendChatToPlayer(McColor.aqua + to.username + McColor.green + Language.translate(" has accepted your warp request."));
						from.sendChatToPlayer(McColor.green + Language.translate("Warpped to ") + McColor.aqua + to.username);
					}
				};
			}
			else
			{
				player.sendChatToPlayer(McColor.red	+ Language.translate("Warp ") + McColor.blue + args[0] + McColor.red + Language.translate(" does not exist!"));
			}
		}
	}
}
