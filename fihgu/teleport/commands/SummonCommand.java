package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;

public class SummonCommand extends CommandBase{
	public SummonCommand()
	{
		name = "summon";
		usage = Language.translate(" <Player name>: Summon a player to yourself.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length < 1 || args.length > 1)
		{
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /summon <Player Name>"));
		}
		else
		{
			
		}
	}
}