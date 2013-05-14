package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.functions.Language;
import fihgu.core.functions.Warp;

public class SetWarpCommand extends CommandBase{
	Warp warp;
	Location loc;
	
	public SetWarpCommand()
	{
		warp = new Warp();
		name = "setwarp";
		usage = Language.translate(" <Warp Name>: Create a new warp at your location.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length < 1 || args.length > 1){
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /setwarp <Warp Name>"));
		} else if(args.length == 1) {
			loc = new Location(player);
			if(warp.newWarp(loc, args[0])){
				player.sendChatToPlayer(Language.translate("Warp " + args[0] + " set to your current location!"));
			} else {				
				player.sendChatToPlayer(Language.translate("Warp " + args[0] + " already exists."));
			}
		}
	}
}
