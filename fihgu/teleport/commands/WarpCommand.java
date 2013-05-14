package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;

public class WarpCommand extends CommandBase{
	
	public WarpCommand(){
		name = "warp";
		usage = Language.translate(" <Player/Location>: Teleport to Player");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length <= 1){
			
		}
	}
}
