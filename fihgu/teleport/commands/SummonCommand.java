package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.PlayerManager;
import fihgu.core.functions.Teleport;

public class SummonCommand extends CommandBase
{
	private EntityPlayerMP sender;

	public SummonCommand()
	{
		name = "summon";
		usage = Language.translate(" <Player name>: Summon a player to yourself.");
	}

	@Override
	public void processPlayer(EntityPlayerMP senderEntity, String[] args)
	{
		final Player sender = new Player(senderEntity);
		final Player target = new Player(PlayerManager.getPossiblePlayer(args[0]));

		if(args.length < 1 || args.length > 1)
		{
			this.argumentMismatch(sender.getEntity());
		} 
		else if (args.length == 1)
		{
			if (target != null)
			{
				sender.msg(McColor.green + Language.translate("Request sent to ") + target.name + "!");
				target.msg(McColor.aqua + sender.name + McColor.pink + Language.translate(" sent you a Warp request!"));
				target.msg(McColor.pink + Language.translate("Please accept with /y or deny with /n."));

				new Request(target, 30) 
				{
					Player from = sender;
					Player to = target;

					@Override
					public void accept()
					{
						Teleport.warp(to.getEntity(), from.getEntity());
					}
				};

			}
		}
	}
}