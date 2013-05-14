package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;

public class SetWarpCommand extends CommandBase{
	public SetWarpCommand()
	{
		name = "warp";
		usage = Language.translate(" <Warp Name>: Create a new warp at your location.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length <= 1){
			
		}
	}
}
