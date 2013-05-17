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
	private EntityPlayerMP sender;
	
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
		if(args.length < 1 || args.length > 1)
		{
			player.msg(Language.translate("Invalad command arguments."));
			player.msg(Language.translate("Usage: /summon <Player Name>"));
		}
		else if(args.length == 1)
		{
			Player player2 = new Player(PlayerManager.getPossiblePlayer(args[0]));
			if(player2!=null)
			{
				player.msg(Language.translate("Request sent to ")  + player2.name + "!");
				player2.msg(player.name + Language.translate(" has send you a Warp request!"));
				player2.msg(Language.translate("you can use /y to accept or /n to deny."));
				sender = p;
				new Request(player2, 30)
				{
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