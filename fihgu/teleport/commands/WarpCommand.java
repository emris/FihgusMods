package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.functions.Language;
import fihgu.core.functions.Warp;

public class WarpCommand extends CommandBase{
	Warp warp;
	
	public WarpCommand(){
		warp = new Warp();
		name = "warp";
		usage = Language.translate(" <Warp Name>: Teleport to a set Warp");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length < 1 || args.length > 1){
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /warp <Warp Name>"));
		} else if(args.length == 1) {
			if(warp.warpTo(player, args[0])){
				player.sendChatToPlayer(Language.translate("Warped to " + args[0] + "."));			
			} else {
				player.sendChatToPlayer(Language.translate("Warp " + args[0] + " does not exist!"));
			}
		}
	}
}
