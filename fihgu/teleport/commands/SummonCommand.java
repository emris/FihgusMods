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

public class SummonCommand extends CommandBase
{
	private Warp warp;
	private EntityPlayerMP sender;

	public SummonCommand()
	{
		warp = new Warp();
		name = "summon";
		usage = Language
				.translate(" <Player name>: Summon a player to yourself.");
	}

	@Override
	public void processPlayer(EntityPlayerMP p, String[] args)
	{
		Player player = new Player(p);
		Player player2 = new Player(PlayerManager.getPlayer(args[0], true));
		if (args.length < 1 || args.length > 1)
		{
			player.msg(McColor.red
					+ Language.translate("Invalad command arguments."));
			player.msg(Language.translate("Usage: /summon <Player Name>"));
		} else if (args.length == 1)
		{
			if (player2 != null)
			{
				player.msg(McColor.green
						+ Language.translate("Request sent to ") + player2.name
						+ Language.translate("!"));
				player2.msg(McColor.green + player.name
						+ Language.translate(" has send you a Warp request!"));
				player2.msg(McColor.green
						+ Language
								.translate("Would you like to warp? Use command /accept"));
				sender = p;
				new Request(player2, 30) {
					@Override
					public void accepted()
					{
						Location loc = new Location(sender);
						warp.warpTo(player.getEntity(), loc, false);
					}
				};
			}
		}
	}
}