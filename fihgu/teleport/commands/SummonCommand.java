package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.Warp;

public class SummonCommand extends CommandBase{
	Warp warp;
	public SummonCommand()
	{
		warp = new Warp();
		name = "summon";
		usage = Language.translate(" <Player name>: Summon a player to yourself.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		Player player1 = new Player(player);
		Player player2;
		if(args.length < 1 || args.length > 1)
		{
			player1.msg(Language.translate("Invalad command arguments."));
			player1.msg(Language.translate("Usage: /summon <Player Name>"));
		}
		else
		{
			player2 = new Player(args[0]);
			new Request(player1,30){
				public void onAccept()
				{
					
				}
			};
		}
	}
}