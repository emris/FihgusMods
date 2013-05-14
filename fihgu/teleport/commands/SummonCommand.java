package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;

public class SummonCommand extends CommandBase{
	public SummonCommand()
	{
		name = "warp";
		usage = Language.translate(" <Player name>: Summon a player to yourself.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length <= 1){
			
		}
	}
}