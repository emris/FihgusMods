package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.PlayerManager;
import fihgu.core.functions.Warp;

public class SummonCommand extends CommandBase{
	private Warp warp;
	
	public SummonCommand()
	{
		warp = new Warp();
		name = "summon";
		usage = Language.translate(" <Player name>: Summon a player to yourself.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP p, String[] args)
	{
		Player player = new Player(p);
		Player player2 = null;
		if(args.length < 1 || args.length > 1)
		{
			player.msg(Language.translate("Invalad command arguments."));
			player.msg(Language.translate("Usage: /summon <Player Name>"));
		}
		else if(args.length == 1)
		{
			player2 = new Player(PlayerManager.getPlayer(args[0], true));
			if(player2!=null)
			{
				player.msg(Language.translate("Request sent to " + player2.name + "!"));
				player2.msg(Language.translate(player.name + " has send you a Warp request!"));
				player2.msg(Language.translate("Would you like to warp? Use command /accept"));
				new Request(player2, 30)
				{
					 public void onAccept()
					 {
						 Player sent = player;
						 Location loc = new Location(player.getEntity());
						 Warp warp = new Warp();
						 warp.warpTo(sent.getEntity(), loc, false);
					 }
				};
			}
		}
	}
}