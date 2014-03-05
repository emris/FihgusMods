package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.PlayerManager;
import fihgu.core.functions.Teleport;
import fihgu.teleport.elements.WarpPoint;

public class WarpCommand extends CommandBase
{
	public WarpCommand()
	{
		name = "warp";
		usage = Language.translate(" <PlayerName/WarpPointName>: Warp you to a location or a player.");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		final EntityPlayerMP sender = player;

		if (args.length != 1)
		{
			player.addChatMessage(McColor.green + "WarpPointNames: " + McColor.aqua + WarpPoint.getWarpPoints());
			this.argumentMismatch(player);
		}
		else if (args.length == 1)
		{
			final EntityPlayerMP target = PlayerManager.getPlayer(args[0], true);

			if (target != null)
			{
				player.addChatMessage(McColor.green + Language.translate("Request sent to ")  + target.username + "!");
				target.addChatMessage(McColor.aqua + player.username + McColor.pink + Language.translate(" has send you a Warp request!"));
				target.addChatMessage(McColor.pink + Language.translate("Please accept with /y or deny with /n."));

				new Request(new Player(target), 30)
				{
					EntityPlayerMP from = sender;
					EntityPlayerMP to = target;
					@Override
					public void accept()
					{
						Teleport.warp(from, to);
						from.addChatMessage(McColor.aqua + to.username + McColor.green + Language.translate(" has accepted your warp request."));
						from.addChatMessage(McColor.green + Language.translate("Warpped to ") + McColor.aqua + to.username);
					}
				};
			}
			else if (WarpPoint.getWarpPoint(args[0]) != null)
			{
				Teleport.warp(player, WarpPoint.getWarpPoint(args[0]).location, false);
				player.addChatMessage(McColor.green + Language.translate("Warped to ") + args[0] + ".");
			}
			else if(PlayerManager.getPossiblePlayer(args[0]) != null)
			{
				final EntityPlayerMP target2 = PlayerManager.getPossiblePlayer(args[0]);

				player.addChatMessage(McColor.green + Language.translate("Request sent to ")  + target2.username + "!");
				target2.addChatMessage(McColor.aqua + player.username + McColor.pink + Language.translate(" has send you a Warp request!"));
				target2.addChatMessage(McColor.pink + Language.translate("Please accept with /y or deny with /n."));

				new Request(new Player(target2), 30) 
				{
					EntityPlayerMP from = sender;
					EntityPlayerMP to = target2;
					@Override
					public void accept()
					{
						Teleport.warp(from, to);
						from.addChatMessage(McColor.aqua + to.username + McColor.green + Language.translate(" has accepted your warp request."));
						from.addChatMessage(McColor.green + Language.translate("Warpped to ") + McColor.aqua + to.username);
					}
				};
			}
			else
			{
				player.addChatMessage(McColor.darkRed	+ Language.translate("Warp Point ") + McColor.aqua + args[0] + McColor.darkAqua + Language.translate(" does not exist!"));
			}
		}
	}
}
